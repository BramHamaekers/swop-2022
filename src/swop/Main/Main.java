package swop.Main;

import swop.UI.TempUI;

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
//        assemAssist.run();
        new TempUI(assemAssist);
    }
}
