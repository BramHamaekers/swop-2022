package swop;

import java.util.Objects;

enum UIType {
	LOGIN("login"), GARAGE_HOLDER("garage holder"), CAR_MECHANIC("car mechanic"), MANAGER("manager");
	private final String type;

	UIType(String str){
		type = str;
	}
	
	/**
	 * 
	 * @return String of current UI type in lowercase
	 */
	public String getType() {
		return type;
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public class UIController {
	
	private UIType legalType;
	
	public UIController() {
		legalType = UIType.LOGIN;
	}


	private void setUIType(UIType t) {
		legalType = t;
		
	}
	
	private void updateAcitveUI(String strUIType) {
		this.setUIType(legalType);
	}
	
	/**
	 * Will check if current active UI is legally active.
	 * @param activeUI is the active UI
	 * @return true if activeUI.id == legalType.getType()
	 */
	public boolean isCorrectUIType(UI activeUI) {
		return legalType.getType().equals(activeUI.getID());
	}

	
}
