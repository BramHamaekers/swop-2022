package swop.CarManufactoring;

import java.util.*;

import swop.Parts.*;

public enum Task {
	
	
	AssemblyCarBody(new HashMap<>(){{
		put( "Body", "Mount a body on the chassis of type: ");
	}},"Assembly Car Body"), 
	PaintCar(new HashMap<>(){{
		put("Color", "Paint the body in colour: ");
	}},"Paint Car"),
	InsertEngine(new HashMap<>(){{
		put( "Engine", "Insert engine of type: ");
	}},"Insert Engine"), 
	InstallGearbox(new HashMap<>(){{
		put( "GearBox", "Insert gearbox of type: ");
	}},"Install Gearbox"), 
	InstallSeats(new HashMap<>(){{
		put( "Seats", "Install seats of type: ");
	}},"Install Seats"), 
	InstallAirco(new HashMap<>(){{
		put( "Airco", "Install airco of type: ");
	}},"Install Airco"), 
	MountWheels(new HashMap<>(){{
		put( "Wheels", "Mount wheels of type: ");
	}},"Mount Wheels");
	
	private final Map<String,String> partsMap;
	private final String name;
	private WorkStation w;
	
	
	Task(Map<String,String> map, String name) {
		this.partsMap = map;
		this.name = name;
	}
	
	/**
	 * Will complete the task of the car from the workstation this task is assigned to
	 */
	public void completeTask() {
		this.getWorkStation().completeTask(this);
	}
	
	/**
	 * Returns a list of parts that are part of this task
	 * @return List<Part>
	 */
	public List<String> getParts() {
		return new ArrayList<>(partsMap.keySet());
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
	public String getTaskDescription() {
		String value = "Empty";
		for(String category : this.getParts()) {
			value = this.getDescription(category) + this.getWorkStation().getValueOfPart(category); //als er meerdere parts bij een task horen geef je maar een array van strings terug
		}
		return value;
		
	}
	
	/**
	 * Return Set<Task> of all the tasks 
	 * @return A set of tasks
	 */
	public static Set<Task> getAllTasks() {
		Set<Task> tasks = new HashSet<>();
		tasks.add(InsertEngine);
		tasks.add(InstallAirco);
		tasks.add(InstallGearbox);
		tasks.add(InstallSeats);
		tasks.add(PaintCar);
		tasks.add(AssemblyCarBody);
		tasks.add(MountWheels);
		return tasks;
	}

	public String getName() {
		return this.name;
	}
	/**
	 * Returns instructions for a given part
	 * @param carOptionCategory
	 * @return
	 */
	public String getDescription(String category) {
		return this.partsMap.get(category);
	}
}
