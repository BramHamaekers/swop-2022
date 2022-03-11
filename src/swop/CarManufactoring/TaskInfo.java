package swop.CarManufactoring;

public enum TaskInfo{
	
	AssemblyCarBody("Assembly car body", "Mount a body on the chassis of type: ", "Car Body Post"), 
	PaintCar("Paint car", "Paint the body in colour: ", "Car Body Post"), 
	InsersEngine("Insert engine", "Insert engine of type: ", ""), 
	InstallGearbox("Insert gearbox", "Insert gearbox of type: ", ""), 
	InstallSeats("Install seats", "Install seats of type: ", ""),
	InstallAirco("Install airco", "Install airco of type: ", ""),
	MountWheels("Mount wheels", "Mount wheels of type: ", "");
	
	String name;
	String description;
	String workStation;
	
	TaskInfo(String name, String description, String workStation){
		this.name = name;
		this.description = description;
		this.workStation = workStation;
	}
	
	public String getString() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
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
	
}
