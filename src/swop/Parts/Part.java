package swop.Parts;

public class Part {

	private String value;
	private String[] validValues;
	private final String name;

	
	public Part(String[] validValues, String name) {
		this.validValues = validValues;
		this.name = name;
	}
	
	public Part(String value, String[] validValues, String name) {
		this.validValues = validValues;
		this.setValue(value);
		this.name = name;
	}
	
	public String getValue() {		
		return value;
	}
	
	boolean containsValue(String value) {
		for(String v: validValues) if (v.equals(value)) return true;
		return false;
	}
	
	public void setValue(String value) {
		if(this.isValidValue(value)) this.value = value;
		else System.out.println("invalid value");
	}
	public String[] getValidValues() {
		return this.validValues;
	}
	
	boolean isValidValue(String value) {
		if (this.containsValue(value)) return true;
		return false;
	}

	public String getName() {
		return name;
	}
	
}
