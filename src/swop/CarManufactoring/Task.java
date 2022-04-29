package swop.CarManufactoring;

import java.util.*;

/**
 * This enum class contains all the tasks that need to be completed off a car
 */
public enum Task {
	
	/**
	 * Assembly Car Body Task
	 */
	AssemblyCarBody(new HashMap<>(){{
		put( "Body", "Mount a body on the chassis of type: ");
	}},"Assembly Car Body"), 
	/**
	 * Paint Car Task
	 */
	PaintCar(new HashMap<>(){{
		put("Color", "Paint the body in colour: ");
	}},"Paint Car"),
	/**
	 * Insert Engine Task
	 */
	InsertEngine(new HashMap<>(){{
		put( "Engine", "Insert engine of type: ");
	}},"Insert Engine"), 
	/**
	 * Install Gear box Task
	 */
	InstallGearbox(new HashMap<>(){{
		put( "Gearbox", "Insert gearbox of type: ");
	}},"Install Gearbox"), 
	/**
	 * Install Seats task
	 */
	InstallSeats(new HashMap<>(){{
		put( "Seats", "Install seats of type: ");
	}},"Install Seats"), 
	/**
	 * Install Airco task
	 */
	InstallAirco(new HashMap<>(){{
		put( "Airco", "Install airco of type: ");
	}},"Install Airco"), 
	/**
	 * Install Spoiler task
	 */
	InstallSpoiler(new HashMap<>(){{
		put( "Spoiler", "Install spoiler of type: ");
	}},"Install Spoiler"), 
	/**
	 * Mount Wheels task
	 */
	MountWheels(new HashMap<>(){{
		put( "Wheels", "Mount wheels of type: ");
	}},"Mount Wheels");
	
	private final Map<String,String> partsDescriptionMap;
	private final String name;
	private WorkStation w;
	private String value;
	
	/**
	 * create a new task
	 * @param map containing the parts and descriptions of the task
	 * @param name the name of the task
	 */
	Task(Map<String,String> map, String name) {
		this.partsDescriptionMap = map;
		this.name = name;
	}
	
	/**
	 * Will complete the task of the car from the workstation this task is assigned to
	 * @param time time it took to complete this task
	 */
	public void completeTask(int time) {
		this.getWorkStation().completeTask(this, time);
	}
	
	/**
	 * Returns a list of parts that are part of this task
	 * @return List of parts that are part of this task
	 */
	public List<String> getParts() {
		return new ArrayList<>(partsDescriptionMap.keySet());
	}
	
	/**
	 * Sets the workstation this task is part of
	 */
	public void setWorkStation(WorkStation w) {
		this.w = w;
	}

	/**
	 * Returns the workstation this task is assigned to.
	 * @return workstation || throws IllegalArgumentException if there is no workstation
	 */
	public WorkStation getWorkStation() {
		if(w == null) throw new IllegalArgumentException("This task has no Workstation");
		return w;
	}
	
	
	/**
	 * Get the description of the given task
	 * @return The description of the given Task consisting of the different parts
	 */
	public List<String> getTaskDescription() {
		List<String> taskExplenations = new LinkedList<>();
		//retrieve all parts that are part of this task
		for(String part : this.getParts()) {
			taskExplenations.add(this.getDescription(part) + this.getWorkStation().getValueOfPart(part)); 
		}
		return taskExplenations;
		
	}
	

	/**
	 * Return Set of all the tasks based on the chosen options
	 * @param chosenOptions: the chosen options of a car
	 * @return A set of tasks
	 */
	public static Set<Task> getAllTasks(Map<String, String> chosenOptions) {
		Set<String> chosenParts = chosenOptions.keySet();
		Set<Task> allTasks = getAllTasks();
		Set<Task> tasks = new HashSet<>();
		//filter the tasks based on what parts are chosen
		for(Task task: allTasks) {
			List<String> parts = task.getParts();
			parts.retainAll(chosenParts);
			if(!parts.isEmpty()) tasks.add(task);
			
		}
		return tasks;
	}
	
	/**
	 * Return Set of all the tasks
	 * @return A set of tasks
	 */
	private static Set<Task> getAllTasks() {
		Set<Task> tasks = new HashSet<>();
		tasks.add(InsertEngine);
		tasks.add(InstallAirco);
		tasks.add(InstallGearbox);
		tasks.add(InstallSeats);
		tasks.add(PaintCar);
		tasks.add(AssemblyCarBody);
		tasks.add(MountWheels);
		tasks.add(InstallSpoiler);
		return tasks;
	}

	/**
	 * returns the name of this task
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns instructions for a given part
	 * @param category the carOptionCategory to get the description from
	 * @return this.partsMap.get(category)
	 */
	private String getDescription(String category) {
		return this.partsDescriptionMap.get(category);
	}
}
