package swop.Parts;

public class Part {

	private String value;
	private String[] validValues;
	

	
	public Part(String[] validValues) {
		this.validValues = validValues;
	}
	
	public Part(String value, String[] validValues) {
		this.setValue(value);
		this.validValues = validValues;
	}
	
	String getValue() {		
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
	
}
