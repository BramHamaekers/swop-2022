package swop.Parts;

public class Seats extends Part{
	
	public Seats() {
		super(new String[] {"leather black", "leather white", "vinyl grey"});
	}

	public Seats(String value) {
		super(value, new String[] {"leather black", "leather white", "vinyl grey"});
	}

}
