package swop.Main;

import swop.UI.LoginUI;

/**
 * Main class to run the project
 */
public class Main {
    /**
     * main method to run the project
     * @param args input arguments
     */
    public static void main(String[] args) {
        AssemAssist assemAssist = new AssemAssist();
        new LoginUI(assemAssist);
    }
}
