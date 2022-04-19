package swop.Parts;

public class GearBox extends CarOptionCategory {
	
	public GearBox() {
		super(new String[] {"6 speed manual", "5 speed automatic"}, "GearBox");
	}

	public GearBox(String value) {
		super(value, new String[] {"6 speed manual", "5 speed automatic"}, "GearBox");
	}

}
