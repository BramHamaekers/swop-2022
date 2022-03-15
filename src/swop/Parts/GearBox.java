package swop.Parts;

public class GearBox extends Part{
	
	public GearBox() {
		super(new String[] {"6 speed manual", "5 speed automatic"});
	}

	public GearBox(String value) {
		super(value, new String[] {"6 speed manual", "5 speed automatic"});
	}

}
