package swop.UI;

import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.Users.GarageHolder;
import swop.Users.User;

public class TempUI {

    private final TempGarUI tempGarUI;

    public TempUI(AssemAssist assem) {
        this.tempGarUI = new TempGarUI();
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
            }catch (CancelException e){
                e.printMessage();
            }
        }
    }

    /**
     * Handles logging in to the system
     */
    private User login(AssemAssist assem) {
        User activeUser;
        do {
            LoginUI.init();
            String id = LoginUI.getUserID();
            if (id.equalsIgnoreCase("QUIT"))
                return null;
            activeUser = assem.getUser(id);
        } while(activeUser == null);
        return activeUser;
    }
}
