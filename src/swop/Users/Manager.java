package swop.Users;

import java.util.*;
import java.util.stream.Collectors;

import swop.Car.Car;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.Records.AllStats;
import swop.UI.ManagerUI;

/**
 * A manager user
 */
public class Manager extends User{
	private AssemAssist assemAssist;
	/**
	 * initializes a manager user
	 * @param id a given id for the manager
	 */
    public Manager(String id, AssemAssist assemAssist) {
        super(id);
		this.assemAssist = assemAssist;
    }

	public void setSchedulingAlgorithm(String algorithm, Map<String, String> batchOptions){
		this.assemAssist.getController().getScheduler().setSchedulingAlgorithm(algorithm, batchOptions);
	}

	public String getActiveAlgorithm() {
		if (this.assemAssist == null) throw new IllegalStateException("no assemassist instantiated");
		return this.assemAssist.getController().getScheduler().getSchedulingAlgorithm();
	}

	public List<String> getAlgorithms(){
		if (this.assemAssist == null) throw new IllegalStateException("no assemassist instantiated");
		return this.assemAssist.getController().getScheduler().getValidAlgorithms();
	}

//	/**
//	 * Change the scheduling algorithm to batch
//	 * @param assemAssist the central system to change the algorithm on
//	 * @throws CancelException when the user types "cancel"
//	 */
//	private void changeAlgorithmToBatch(AssemAssist assemAssist) throws CancelException {
//		// get all parts from carQueue
////		List<Map<String, String>> partMaps = getPartMaps();
////		List<Map<String, String>> possibleBatch = getBatchOptions(partMaps);
//		if (!possibleBatch.isEmpty()) {
//			Map<String, String> selection = ManagerUI.getBatchSelection(possibleBatch);
//			assemAssist.getController().getScheduler().setSchedulingAlgorithm("BATCH", selection);
//		}
//		else {
//			ManagerUI.printError("No batchoptions available -> Algoritm will stay FIFO");
//		}
//	}

	public List<Map<String, String>> getPartMaps() {
		return this.assemAssist.getController().getCarQueue().stream().map(Car::getPartsMap).toList();
	}

	/**
	 * Returns the possible batch options given a list of part maps
	 * @return List of batch options
	 */
	public List<Map<String, String>> getBatchOptions() {
		List<Map<String, String>> partMaps = this.getPartMaps();
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

	public AllStats getStats(){
		if (this.assemAssist== null) throw new IllegalArgumentException("no assemassist specified");
		return this.assemAssist.getStats();
	}

}
