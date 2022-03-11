package swop.CarManufactoring;

public enum TaskInfo{
	
	AssemblyCarBody("Assembly car body", "Mount a body on the chassis of type: ", "Car Body Post","body"), 
	PaintCar("Paint car", "Paint the body in colour: ", "Car Body Post","color"), 
	InsersEngine("Insert engine", "Insert engine of type: ", "Drivetrain Post","engine"), 
	InstallGearbox("Insert gearbox", "Insert gearbox of type: ", "Drivetrain Post","gearBox"), 
	InstallSeats("Install seats", "Install seats of type: ", "Accessories Post","seats"),
	InstallAirco("Install airco", "Install airco of type: ", "Accessories Post","airco"),
	MountWheels("Mount wheels", "Mount wheels of type: ", "Accessories Post","wheels");
	
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
			case "Assembly car body" -> AssemblyCarBody;
			case "Paint car" -> PaintCar;
			case "Insert engine" -> InsersEngine;
			case "Insert gearbox" -> AssemblyCarBody;
			case "Install seats" -> InstallGearbox;
			case "Install airco" -> InstallAirco;
			case "Mount wheels" -> MountWheels;
			default -> null;
		};
	}

	String getPartOfTask() {
		return this.part;
	}
	
}
