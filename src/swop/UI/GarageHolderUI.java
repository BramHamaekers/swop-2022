package swop.UI;

public class GarageHolderUI extends UI {

	public GarageHolderUI() {
		super("garage holder");
		System.out.println("je zit nu in Garage UI");

		//just so program doesnt exit right away
		try {
			int a = System.in.read();
		}
		catch (java.io.IOException e) {
			System.out.println(e);
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

}
