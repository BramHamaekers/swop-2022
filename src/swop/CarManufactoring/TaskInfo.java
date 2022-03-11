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
		switch(task) {
		case "Assembly car body":
			return AssemblyCarBody;
		case "Paint car":
			return PaintCar;
		case "Insert engine":
			return InsersEngine;
		case "Insert gearbox":
			return AssemblyCarBody;
		case "Install seats":
			return InstallGearbox;
		case "Install airco":
			return InstallAirco;
		case "Mount wheels":
			return MountWheels;
		default: 
			return null;}
	}

	String getPartOfTask() {
		return this.part;
	}
	
}
