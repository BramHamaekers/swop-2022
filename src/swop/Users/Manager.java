package swop.Users;

import java.util.*;
import java.util.stream.Collectors;

import swop.Car.Car;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.Miscellaneous.Statistics;
import swop.Miscellaneous.TimeStamp;
import swop.Records.allStats;
import swop.UI.ManagerUI;

public class Manager extends User{
	

    public Manager(String id) {
        super(id); 
    }

    /**
     * Called when logging in as Manager
	 * @param assemAssist assemAssist given the main program
     */
	@Override
	public void load(AssemAssist assemAssist) {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");

		try {
			ManagerUI.init(this.getId());
			this.selectAction(assemAssist);
		} catch (CancelException e) {
			e.printMessage();
		}
		}

	/**
	 * Function that handles selecting an action for Manager
	 * @param assemAssist the central system the action is performed on
	 */
	@Override
	public void selectAction(AssemAssist assemAssist) throws CancelException {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		List<String> actions = Arrays.asList("Check Production Statistics", "Adapt Scheduling Algorithm", "Exit");
		int action = ManagerUI.selectFlow(actions, "What would you like to do?");

		switch (action) {
			case 0 -> this.checkProductionStatistics(assemAssist);
			case 1 -> this.AdaptSchedulingAlgorithm(assemAssist);
			case 2 -> {
				// Do Nothing
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	/**
	 * Ask the user which algorithm it would like to use and change the scheduling algorithm
	 * @param assemAssist the central system the action is performed on
	 * @throws CancelException when the user types 'cancel'
	 */
	private void AdaptSchedulingAlgorithm(AssemAssist assemAssist) throws CancelException {

		List<String> algorithms = assemAssist.getController().getScheduler().getValidAlgorithms();
		String active = assemAssist.getController().getScheduler().getSchedulingAlgorithm();
		int option = ManagerUI.selectAction(algorithms, active);

		switch (option) {
			case 0 -> this.changeAlgorithmToBatch(assemAssist);
			case 1 -> assemAssist.getController().getScheduler().setSchedulingAlgorithm("FIFO", null);
			case 2 -> {}
			default -> throw new IllegalArgumentException("Unexpected value: " + option);
		}

	}

	private void changeAlgorithmToBatch(AssemAssist assemAssist) throws CancelException {
		// get all parts from carrqueue
		List<Map<String, String>> partMaps =  assemAssist.getController().getCarQueue().stream().map(Car::getPartsMap).toList();
		List<Map<String, String>> possibleBatch = getBatchOptions(partMaps);
		if (!possibleBatch.isEmpty()) {
			Map<String, String> selection = ManagerUI.getBatchSelection(possibleBatch);
			assemAssist.getController().getScheduler().setSchedulingAlgorithm("BATCH", selection);
		}
		else {
			ManagerUI.printError("No possible batchoptions, too few cars with the same configurations give priority");
		}
	}

	/**
	 * Returns the possible batch options given a list of part maps
	 * @param partMaps map of parts
	 * @return List of batch options
	 */
	private List<Map<String, String>> getBatchOptions(List<Map<String, String>> partMaps) {
		List<Map<String, String>> possibleBatch = new ArrayList<>();

		// map all selections on top of optionCategory
		Map<String,List<String>> chosenParts = partMaps.stream()
				.flatMap(map -> map.entrySet().stream())
				.collect(Collectors.groupingBy(
						Map.Entry::getKey,
						Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

		for(Map.Entry<String,List<String>> entry: chosenParts.entrySet()){
			Set<String> parts = new HashSet<>(entry.getValue());
			for(String part: parts) {
				int x = Collections.frequency(entry.getValue(), part);
				if (x>2)
					possibleBatch.add(Map.of(entry.getKey(),part));
			}
		}
		return possibleBatch;
	}

	private void checkProductionStatistics(AssemAssist assemAssist) throws CancelException {
		//TODO -> the average and median delay on an order, 
		// together with the 2 last delays and the days they occurred. 
		allStats stats = assemAssist.getStats();
		System.out.print(stats.avgDelay());
		
		List<TimeStamp> finishedCarTimes = new LinkedList<>(assemAssist.getController().getFinishedCars().stream().map(Car::getCompletionTime).toList());
		if(finishedCarTimes.isEmpty()) {
			ManagerUI.printError("Not enough data to give you statistics");
			return;
		}
		
		/************the average and median + the exact numbers cars for the last 2 day************/
		List<Integer> numberOfCarsEachDay = getFinishedCarsEachDay(finishedCarTimes);	
		//average
		double average = numberOfCarsEachDay.stream().mapToDouble(d -> d).average().getAsDouble();
		//median
		double median = getMedianOfList(numberOfCarsEachDay);
		ManagerUI.showProductionStatistics(new HashMap<String,Double>(){{
			put( "Average cars finished", average);
			put( "Median cars finished", median);
			if(numberOfCarsEachDay.size() < 2) {
				put( "Today completed cars", numberOfCarsEachDay.get(0).doubleValue());
				put( "Yesterday completed cars", null);
			}
			else {
				put( "Today completed cars", numberOfCarsEachDay.get(-1).doubleValue());
				put( "Yesterday completed cars", numberOfCarsEachDay.get(-2).doubleValue());
			}
		}});
	}

	/**
	 * Calculates the median value of a given list.
	 * @param numberOfCarsEachDay amount of cars each day
	 * @return median
	 */
	private double getMedianOfList(List<Integer> numberOfCarsEachDay) {
		 if (numberOfCarsEachDay.size() % 2 == 0) {
			 return (numberOfCarsEachDay.get((numberOfCarsEachDay.size()/2) - 1) +
					  numberOfCarsEachDay.get(numberOfCarsEachDay.size()/2)/2);
		 }
		 return Math.ceil(numberOfCarsEachDay.get(numberOfCarsEachDay.size()/2));
	}
	
	/**
	 * Returns a list of all the car finished each day: index = the day.
	 * @param finishedCarTimes the times at which cars are finished
	 * @return List<Integer> numberOfCarsEachDay
	 */
	private List<Integer> getFinishedCarsEachDay(List<TimeStamp> finishedCarTimes) {
		List<Integer> numberOfCarsEachDay = new ArrayList<>();
		
		// calculate the cars for each day
		while(!finishedCarTimes.isEmpty()) {
			int day = finishedCarTimes.remove(0).getDay();
			while((numberOfCarsEachDay.size()-1) < day)
				numberOfCarsEachDay.add(0);
			numberOfCarsEachDay.set(day, numberOfCarsEachDay.get(day) + 1);			
		}
		Collections.sort(numberOfCarsEachDay);
		return numberOfCarsEachDay;
	}
}
