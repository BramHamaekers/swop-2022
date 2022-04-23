package swop.Users;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import swop.Car.Car;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
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
		List<String> actions = Arrays.asList("Check Production Statistics", "Adapt Scheduling Algorithm", "Exit");
		int action = ManagerUI.selectFlow(actions, "What would you like to do?");

		switch (action) {
			case 0 -> this.checkProductionStatistics();
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
		if (!possibleBatch.isEmpty()) {
			Map<String, String> selection = ManagerUI.getBatchSelection(possibleBatch);
			assemAssist.getController().getScheduler().setSchedulingAlgorithm("BATCH", selection);
		}
		else {
			ManagerUI.printError("No possible batchoptions, too few cars with the same configurations give priority");
		}
	}

	private void checkProductionStatistics() {
		System.out.println("checkProductionStatistics not yet implemented");
	}
}
