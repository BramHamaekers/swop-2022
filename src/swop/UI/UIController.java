

package swop.UI;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public class UIController {
	
	private UIType legalType;
	
	public UIController() {
	}


	void setUIType(UIType type) {
		legalType = type;
		
	}
	
	/**
	 * snelle oplossing
	 * @param type
	 */
	public void setUIType(String type) {
		if(type.equals(UIType.LOGIN.getType())) {
			this.legalType = UIType.LOGIN;
		}
		if(type.equals(UIType.GARAGE_HOLDER.getType())) {
			this.legalType = UIType.GARAGE_HOLDER;
		}
		if(type.equals(UIType.CAR_MECHANIC.getType())) {
			this.legalType = UIType.CAR_MECHANIC;
		}
		if(type.equals(UIType.MANAGER.getType())) {
			this.legalType = UIType.MANAGER;
		}
		
	}
	/**
	 * Will check if current active swop.CarManufactoring.UI is legally active.
	 * @param activeUI is the active swop.CarManufactoring.UI
	 * @return true if activeUI.id == legalType.getType()
	 */
	//public boolean isCorrectUIType(UI activeUI) {
	//	return legalType.getType().equals(activeUI.getID());}
	/**
	 * Returns a new instance of legal swop.CarManufactoring.UI
	 * @return
	 */
	public UI getInstanceOfCorrectUI() {
		UI activeUI;
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
