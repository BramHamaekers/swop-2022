package swop.UI;

import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.User;

public class TempUI {

    private final TempGarUI tempGarUI;

    private final TempCarMechUI tempCarMechUI;

    public TempUI(AssemAssist assem) {
        this.tempGarUI = new TempGarUI();
        this.tempCarMechUI = new TempCarMechUI();
        this.run(assem);
    }

    private void run(AssemAssist assem) {
        //TODO: login loop
        while (true) {
            User user = login(assem);
            if (user == null)
                break;
            try {
                if (user instanceof GarageHolder)
                    tempGarUI.run((GarageHolder)user);
                else if (user instanceof CarMechanic)
                    tempCarMechUI.run((CarMechanic) user);
            }catch (CancelException e){
                e.printMessage();
            }
        }
    }

    /**
     * Handles logging in to the system
     */
    private User login(AssemAssist assem) {
        LoginUI.init();
        String id = LoginUI.getUserID();
        if (id.equalsIgnoreCase("QUIT"))
            return null;
        User activeUser = assem.getUser(id);
        while(activeUser == null) {
            System.out.println("Invalid user ID, type QUIT to exit");
            id = LoginUI.getUserID();
            if (id.equalsIgnoreCase("QUIT"))
                return null;
            activeUser = assem.getUser(id);
        }
        return activeUser;
    }
}
