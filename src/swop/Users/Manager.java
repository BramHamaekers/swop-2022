package swop.Users;

import java.util.Objects;
import java.util.Queue;

import swop.CarManufactoring.Order;
import swop.CarManufactoring.Schedular;
import swop.UI.ManagerUI;

public class Manager extends User{
	

    public Manager(String id) {
        super(id);
    }

	@Override
	public void load() {
		ManagerUI.init(getId());
		ManagerUI.displayOrderQueues(Schedular.returnAllQueuesAsMap(), Schedular.getCurrentIndex());
		this.advanceAssemblyLine();
	}

	private void advanceAssemblyLine() {
		String t = "t";
		while(!isValidAction(t)) t = ManagerUI.advanceAssemblyLine();
	}
	
	
	//nog niet nodig, staat niet in opgave
	/*private void OdersToConfirm() {
		Queue<Order> queue = Schedular.getOrdersToApprove();
		if(queue.size() == 0) ManagerUI.comfirmOrder(null);
		for(Order order : queue) {
			//TODO verder implementeren
		}
		////test////
		String t = "t";
		while(!isValidAction(t)) t = ManagerUI.comfirmOrder(new Order(null));
		
	}*/
	
	
	
	private boolean isValidAction(String action) {
        return Objects.equals(action, "y") || Objects.equals(action, "n");
    }
    
    
}
