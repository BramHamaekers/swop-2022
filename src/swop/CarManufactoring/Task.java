package swop.CarManufactoring;

import java.util.*;

import swop.Parts.*;

public enum Task {
	
	
	AssemblyCarBody(new HashMap<Part,String>(){{
		put( new Body(),"Mount a body on the chassis of type: ");
	}},"Assembly Car Body"), 
	PaintCar(new HashMap<Part,String>(){{
		put(new Color(),"Paint the body in colour: ");
	}},"Paint Car"),
	InsertEngine(new HashMap<Part,String>(){{
		put( new Engine(),"Insert engine of type: ");
	}},"Insert Engine"), 
	InstallGearbox(new HashMap<Part,String>(){{
		put( new GearBox(),"Insert gearbox of type: ");
	}},"Install Gearbox"), 
	InstallSeats(new HashMap<Part,String>(){{
		put( new Seats(),"Install seats of type: ");
	}},"Install Seats"), 
	InstallAirco(new HashMap<Part,String>(){{
		put( new Airco(),"Install airco of type: ");
	}},"Install Airco"), 
	MountWheels(new HashMap<Part,String>(){{
		put( new Wheels(),"Mount wheels of type: ");
	}},"Mount Wheels");
	
	private Map<Part,String> partsMap;
	private final String name;
	
	Task(Map<Part,String> map, String name) {
		this.partsMap = map;
		this.name = name;
	}
	
	public boolean isCompleted() {
		return this.partsMap.isEmpty();
	}
	
	public void completeTask() {
		this.partsMap.clear();
	}
	public List<Part> getParts() {
		List<Part> p = new LinkedList<Part>();
		for(Part part : partsMap.keySet()) {
			p.add(part);
		}
		return p;
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
	 * @param part
	 * @return
	 */
	public String getDescription(Part part) {
		return this.partsMap.get(part);
	}
}
