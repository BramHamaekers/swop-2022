package swop.Users;

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
		ManagerUI.displayOrderQueues(Schedular.returnAllQueuesAsMap());
		this.OdersToConfirm();
	}

	private void OdersToConfirm() {
		Queue<Order> queue = Schedular.getOrdersToApprove();
		if(queue.size() == 0)ManagerUI.comfirmOrder(null);
		for(Order order : queue) {
			String a = ManagerUI.comfirmOrder(order);
			//TODO verder implementeren
		}
		
	}
    
    
}
