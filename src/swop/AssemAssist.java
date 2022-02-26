package swop;

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
		
	}
	
	/**
	 * Loads login UI
	 */
	private void login() {
		uiController.setUIType(UIType.LOGIN);
		this.loadUI();
	}
	/**
	 * Loads new instance of legal UI
	 */
	private void loadUI() {
		activeUI = uiController.getInstanceOfCorrectUI(activeUI);
		activeUI.load();
	}
}
