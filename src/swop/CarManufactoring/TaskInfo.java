package swop.CarManufactoring;

public enum TaskInfo{
	
	AssemblyCarBody("assembly car body", "Mount a body on the chassis of type: ", "Car Body Post","body"), 
	PaintCar("paint car", "Paint the body in colour: ", "Car Body Post","color"), 
	InsertEngine("insert engine", "Insert engine of type: ", "Drivetrain Post","engine"),
	InstallGearbox("insert gearbox", "Insert gearbox of type: ", "Drivetrain Post","gearBox"), 
	InstallSeats("install seats", "Install seats of type: ", "Accessories Post","seats"),
	InstallAirco("install airco", "Install airco of type: ", "Accessories Post","airco"),
	MountWheels("mount wheels", "Mount wheels of type: ", "Accessories Post","wheels");
	
	private String name;
	private String description;
	private String workStation;
	private String part; //kunnen we naar list van alle parts nodig voor een task maken.
	
	TaskInfo(String name, String description, String workStation, String part){
		this.name = name;
		this.description = description;
		this.workStation = workStation;
		this.part = part;
	}
	
	public String getString() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getWorkStation() {
		return this.workStation;
	}
	
	public static TaskInfo getTaksInfo(String task) {
		return switch (task) {
			case "assembly car body" -> AssemblyCarBody;
			case "paint car" -> PaintCar;
			case "insert engine" -> InsertEngine;
			case "insert gearbox" -> InstallGearbox;
			case "install seats" -> InstallSeats;
			case "install airco" -> InstallAirco;
			case "mount wheels" -> MountWheels;
			default -> null;
		};
	}

	String getPartOfTask() {
		return this.part;
	}
	
}
