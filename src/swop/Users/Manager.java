package swop.Users;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import swop.CarManufactoring.Car;
import swop.Main.AssemAssist;
import swop.UI.ManagerUI;

public class Manager extends User{
	

    public Manager(String id) {
        super(id);
    }

	@Override
	public void load(AssemAssist assemAssist) {
		ManagerUI.init(getId());
		//1.
		String indicate = ManagerUI.indicateAdvance();
		while (!isValidYesNo(indicate)) {
			indicate = ManagerUI.indicateAdvance();
		}
		if (Objects.equals(indicate, "n")) return;

		// TEST
		assemAssist.advanceAssembly();

	}
	

	// TODO remove?
	private LinkedHashMap<String, List<Car>> simulateAdvance(LinkedHashMap<String, List<Car>> lists) {
		if(lists == null) return null;
		Car temp = null;
		LinkedHashMap<String, List<Car>> newMap = new LinkedHashMap<>();
		for(var entry: lists.entrySet()) {
			String t = entry.getKey();
			List<Car> cloneList = entry.getValue();
			if(!(temp == null)) cloneList.add(temp);
			if(!cloneList.isEmpty() && !(cloneList.size() == 1 && cloneList.contains(temp))) {
				temp = cloneList.remove(0);
			}
			else temp = null;
			newMap.put(t, cloneList);
		}
		return newMap;
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
    
	private boolean isValidTime(int t) {
		if(t > 0 && t < 60*14) return true;
		return false;
	}
    
}
