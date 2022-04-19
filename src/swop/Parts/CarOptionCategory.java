package swop.Parts;

public class CarOptionCategory {

	private String value;
	private String[] validValues;
	private final String name;

	
	public CarOptionCategory(String[] validValues, String name) {
		this.validValues = validValues;
		this.name = name;
	}
	
	public CarOptionCategory(String value, String[] validValues, String name) {
		this.validValues = validValues;
		this.setValue(value);
		this.name = name;
	}
	
	public String getValue() {		
		return value;
	}

	/**
	 * Helper method to check if string is in list of valid strings
	 * @param value given a string
	 * @return whether the parameter value is in validvalues
	 */
	private boolean containsValue(String value) {
		for(String v: validValues) if (v.equals(value)) return true;
		return false;
	}

	public void setValue(String value) {
		if(!this.containsValue(value)) throw new IllegalArgumentException("invalid value for part");
		this.value = value;
	}
	public String[] getValidValues() {
		return this.validValues;
	}

	public String getName() {
		return name;
	}
}
