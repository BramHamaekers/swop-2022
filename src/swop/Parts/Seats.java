package swop.Parts;

public class Seats extends CarOptionCategory {
	
	public Seats() {
		super(new String[] {"leather black", "leather white", "vinyl grey"}, "Seats");
	}

	public Seats(String value) {
		super(value, new String[] {"leather black", "leather white", "vinyl grey"},"Seats");
	}

}
