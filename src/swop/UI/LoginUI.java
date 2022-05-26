package swop.UI;

import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.Manager;
import swop.Users.User;

import static swop.UI.UI.scanner;

public class LoginUI {

    private final GarageHolderUI garageHolderUI;

    private final CarMechanicUI carMechanicUI;

    private final ManagerUI managerUI;

    public LoginUI(AssemAssist assem) {
        if (assem == null)
            throw new IllegalArgumentException("provided assemAssist is null");
        this.garageHolderUI = new GarageHolderUI();
        this.carMechanicUI = new CarMechanicUI();
        this.managerUI = new ManagerUI();
        this.run(assem);
    }

    private void run(AssemAssist assem) {
        while (true) {
            User user = login(assem);
            if (user == null)
                break;
            try {
                if (user instanceof GarageHolder)
                    garageHolderUI.run((GarageHolder)user);
                else if (user instanceof CarMechanic)
                    carMechanicUI.run((CarMechanic) user);
                else if (user instanceof Manager)
                    managerUI.run((Manager) user);
            }catch (CancelException e){
                e.printMessage();
            }
        }
    }

    /**
     * Handles logging in to the system
     */
    private User login(AssemAssist assem) {
        init();
        String id = getUserID();
        if (id.equalsIgnoreCase("QUIT"))
            return null;
        User activeUser = assem.getUser(id);
        while(activeUser == null) {
            System.out.println("Invalid user ID, type QUIT to exit");
            id = getUserID();
            if (id.equalsIgnoreCase("QUIT"))
                return null;
            activeUser = assem.getUser(id);
        }
        return activeUser;
    }

    /**
     * Greets the user with a welcome message
     */
    private static void init() {
        System.out.println("Welcome!");
    }

    /**
     * Gets the user id from the input
     * @return return the userID or an empty string if {@code CancelException}
     */
    public static String getUserID() {
        System.out.print("Please login with userID, type QUIT to exit: ");
        try {
            return scanner.scanNextLineOfTypeString();
        } catch (CancelException e) {
            return "";
        }
    }
}
