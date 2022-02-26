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
	}


	void setUIType(UIType type) {
		legalType = type;
		
	}
	
	private void updateAcitveUI(String strUIType) {
	}
	
	/**
	 * Will check if current active UI is legally active.
	 * @param activeUI is the active UI
	 * @return true if activeUI.id == legalType.getType()
	 */
	public boolean isCorrectUIType(UI activeUI) {
		return legalType.getType().equals(activeUI.getID());
	}
	
	public UI getInstanceOfCorrectUI(UI activeUI) {
		switch(legalType) {
			case LOGIN: 
				activeUI = new LoginUI();
				return activeUI;
			case GARAGE_HOLDER:
				activeUI = new GarageHolderUI();
				return activeUI;			
			case CAR_MECHANIC:
				activeUI = new CarMechanicUI();
				return activeUI;	
			case MANAGER:
				activeUI = new ManagerUI();
				return activeUI;	
			default:
				return null;
		}
	}
	

	
}
