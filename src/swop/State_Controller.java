package swop;

import java.io.Console;

enum State {
	LOGIN("login"), GARAGE_HOLDER("garage holder"), CAR_MECHANIC("car mechanic"), MANAGER("manager");
	
	private final String state;
	
	State(String str){
		state = str;
	}
	
	public String getstrState() {
		return state;
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public class State_Controller {
	
	private State state = State.LOGIN; 

	private UI active_UI;
	
	public State_Controller() {
		this.initiate();
		
	}

	private void initiate() {
		this.updateAcitveUI();
		System.out.println("Welcome!");
		active_UI.load();
	}

	private void setState(State state) {
		// (°_°)
		
	}
	
	private void updateAcitveUI() {
		if(state == State.LOGIN) {
			this.active_UI = new Login_UI();
		}
	}

	
}
