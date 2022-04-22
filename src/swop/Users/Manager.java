package swop.Users;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
		int action = ManagerUI.selectAction(actions, "What would you like to do?");

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

		Set<String> algorithms = assemAssist.getController().getScheduler().getValidAlgorithms();
		int option = ManagerUI.selectAction(algorithms.stream().toList(), "Which algorithm do you want to enable?");

		switch (option) {
			case 0 -> this.changeAlgorithmToBatch(assemAssist);
			case 1 -> assemAssist.getController().getScheduler().setSchedulingAlgorithm("FIFO", null);
			default -> throw new IllegalArgumentException("Unexpected value: " + option);
		}

	}

	private void changeAlgorithmToBatch(AssemAssist assemAssist) {
		List<Map<String, String>> partMaps =  assemAssist.getController().getCarQueue().stream().map(c -> c.getPartsMap()).toList();
		Map<String, String> possibleBatch = new HashMap<>();
		//TODO improve deze nest
		for (Map<String, String> map1:partMaps) {
			for (Map.Entry<String, String> entry1 : map1.entrySet()) {
				int count = 0;
				for (Map<String, String> map2:partMaps) {
					for (Map.Entry<String, String> entry2 : map2.entrySet()) {
						if(entry1.getKey().equals(entry2.getKey()) && entry1.getValue().equals(entry2.getValue()))
								count++;
					}
				}
				if(count>2)
					possibleBatch.put(entry1.getKey(), entry1.getValue());
			}
		}
		
		System.out.println(possibleBatch);
	}

	private void checkProductionStatistics() {
		System.out.println("checkProductionStatistics not yet implemented");
	}
}
