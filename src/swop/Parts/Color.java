package swop.Parts;

public class Color extends Part{
	
	public Color() {
		super( new String[] {"red", "blue", "black", "white"});
	}

	public Color(String value) {
		super(value, new String[] {"red", "blue", "black", "white"});
	}

}
