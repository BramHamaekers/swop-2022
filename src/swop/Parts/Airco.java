package swop.Parts;

public class Airco extends Part{
	
	
	public Airco() {
		super(new String[] {"manual", "automatic climate control"}, "Airco");
	}

	public Airco(String value) {
		super(value, new String[] {"manual", "automatic climate control"}, "Airco");
	}

}
