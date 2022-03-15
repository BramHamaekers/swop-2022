package swop.Parts;

public class Wheels extends Part{
	
	public Wheels() {
		super(new String[] {"comfort", "sports (low profile)"});
	}

	public Wheels(String value) {
		super(value, new String[] {"comfort", "sports (low profile)"});
	}

}
