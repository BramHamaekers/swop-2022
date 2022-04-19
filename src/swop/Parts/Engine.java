package swop.Parts;

public class Engine extends CarOptionCategory {
	
	public Engine() {
		super(new String[] {"standard 2l 4 cilinders", "performance 2.5l 6 cilinders"}, "Engine");
	}

	public Engine(String value) {
		super(value, new String[] {"standard 2l 4 cilinders", "performance 2.5l 6 cilinders"}, "Engine");
	}


}
