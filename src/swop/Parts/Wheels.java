package swop.Parts;

public class Wheels extends CarOptionCategory {
	
	public Wheels() {
		super(new String[] {"comfort", "sports (low profile)"}, "Wheels");
	}

	public Wheels(String value) {
		super(value, new String[] {"comfort", "sports (low profile)"}, "Wheels");
	}

}
