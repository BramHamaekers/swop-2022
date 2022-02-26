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
		activeUI = new LoginUI();
		this.loadUI();
	}
	/**
	 * Loads current active UI
	 */
	private void loadUI() {
		if(uiController.isCorrectUIType(activeUI))
		activeUI.load();
	}
}
