package swop.UI;

import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.Users.GarageHolder;
import swop.Users.User;

public class TempUI {

    private final TempGarUI tempGarUI;

    public TempUI(AssemAssist assem) {
        this.tempGarUI = new TempGarUI();
        this.run(assem.getUser());
    }

    private void run(User user) {
        //TODO: login loop
        try {
            if (user instanceof GarageHolder)
                tempGarUI.run((GarageHolder)user);
        }catch (CancelException e){
            System.out.println("This is still todo");
        }
    }
}
