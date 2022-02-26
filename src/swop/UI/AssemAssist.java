package swop.UI;

public class AssemAssist {
	
	private UI activeUI;
	private UIController uiController;

    public AssemAssist() {
    	this.run();
    }
    /**
     * Starts the program
     */
	private void run() {
		uiController = new UIController();
		login();
		//temp oplossing voor uit login te geraken
		if(activeUI instanceof LoginUI) {
			String type = ((LoginUI)activeUI).getKeyValue();
			uiController.setUIType(type);
			loadUI();
		}
		
	}
	
	/**
	 * Loads login swop.CarManufactoring.UI
	 */
	private void login() {
		uiController.setUIType(UIType.LOGIN);
		this.loadUI();
	}
	/**
	 * Loads new instance of legal swop.CarManufactoring.UI
	 */
	private void loadUI() {
		activeUI = uiController.getInstanceOfCorrectUI();
		activeUI.load();
	}
}
