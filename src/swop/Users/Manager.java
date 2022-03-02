package swop.Users;

import swop.CarManufactoring.Schedular;
import swop.UI.ManagerUI;

public class Manager extends User{
    public Manager(String id) {
        super(id);
    }

	@Override
	public void load() {
		ManagerUI.init(getId());
		ManagerUI.displayOrderQueues(Schedular.returnAllQueuesAsMap());
	}
    
    
}
