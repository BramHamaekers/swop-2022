package swop.Users;

import java.util.*;
import java.util.stream.Collectors;

import swop.Car.Car;
import swop.Main.AssemAssist;
import swop.Records.AllStats;

/**
 * A manager user
 */
public class Manager extends User{
	/**
	 * initializes a manager user
	 * @param id a given id for the manager
	 */
    public Manager(String id, AssemAssist assemAssist) {
        super(id, assemAssist);
    }

	/**
	 * Method lets the manager select a new schedulingAlgorithm
	 * @param algorithm the selected algorithm
	 * @param batchOptions the batchOptions you want to use in the case of choosing the 'batch' algorithm
	 */
	public void setSchedulingAlgorithm(String algorithm, Map<String, String> batchOptions){
		if (algorithm == null)
			throw new IllegalArgumentException("null string provided");
		if (algorithm.equals("BATCH") && batchOptions == null)
			throw new IllegalArgumentException("batch options provided are null");
		this.assemAssist.getController().getScheduler().setSchedulingAlgorithm(algorithm, batchOptions);
	}

	/**
	 * Get the scheduling algorithm that is currently active from the scheduler
	 * @return the active scheduling algorithm
	 */
	public String getActiveAlgorithm() {
		if (this.assemAssist == null) throw new IllegalStateException("no assemAssist instantiated");
		return this.assemAssist.getController().getScheduler().getSchedulingAlgorithm();
	}

	/**
	 * Get a list of all valid scheduling algorithms of the scheduler
	 * @return list of valid scheduling algorithms
	 */
	public List<String> getValidAlgorithms(){
		if (this.assemAssist == null) throw new IllegalStateException("no assemAssist instantiated");
		return this.assemAssist.getController().getScheduler().getValidAlgorithms();
	}

	/**
	 * gets a list of partMaps from all the cars in the car queue
	 * @return a list of partMaps
	 */
	public List<Map<String, String>> getPartMapsOfCarQueue() {
		if (this.assemAssist == null) throw new IllegalStateException("no assemAssist instantiated");
		return this.assemAssist.getController().getCarQueue().stream().map(Car::getPartsMap).toList();
	}

	/**
	 * Returns the possible batch options given a list of part maps
	 * @return List of batch options
	 */
	public List<Map<String, String>> getBatchOptions() {
		List<Map<String, String>> partMaps = this.getPartMapsOfCarQueue();
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

	/**
	 * Get the stats of the production so far from the assemAssist
	 * @return a record of mean, average cars produced in a day and info on delays in the production
	 */
	public AllStats getStats(){
		if (this.assemAssist == null) throw new IllegalStateException("no assemAssist instantiated");
		return this.assemAssist.getStats();
	}

}
