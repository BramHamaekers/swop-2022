package swop.Tests;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.junit.jupiter.api.Test;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.Car.CarOrder;
import swop.CarManufactoring.Task;
import swop.Exceptions.IllegalUserException;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;

public class IllegalUsePublicMethodsAssemAssistTest {
	AssemAssist assem = new AssemAssist();
	InputStream input;


	
	@Test
	void IllegalUsePublicMethods() {
		//test access when assemAssist has no activeUser at the moment (Should be impossible normally): Should throw IllegalArgumentException
		instantQuit();
		assertThrows(IllegalArgumentException.class,
				() -> assem.addOrder(null));
		//test activeUser = car mechanic but wants to add an order: Should throw IllegalArgumentException
		loadCarMechanic();
		assertThrows(IllegalArgumentException.class,
				() -> assem.addOrder(null));
		//test activeUser = garage holder but wants to advance assambly: Should throw IllegalUserException
		loadGarageHolder();
		//test activeUser = manager but wants to complete a task: Should throw IllegalArgumentException
		//TODO;
//		loadManager();
//		CarModel a = new ModelA();
//		a.setCarModelSpecification(new CarModelSpecification(Map.ofEntries(
//				entry("Body", "sedan"),
//				entry("Color", "red"),
//				entry("Engine", "standard 2l v4"),
//				entry("Gearbox", "6 speed manual"),
//				entry("Seats", "leather white"),
//				entry("Airco", "manual"),
//				entry("Wheels", "winter")
//		)));
//		assertThrows(IllegalUserException.class, () -> assem.addOrder(new CarOrder(a)));
	}



	void instantQuit() {
		String s = "QUIT";
		s+=System.lineSeparator();
		input = new ByteArrayInputStream(s.getBytes());
		System.setIn(input);
		//scanner can't update system.in on its own... moet ge update telkens ge een nieuw argument meegeeft via system.in.
		LoginUI.scanner.updateScanner();
		//reset
	}

	private void loadGarageHolder() {
		String s = "a";
		 s += "a";
		 s+=System.lineSeparator();
		 s+="0";
		 s+=System.lineSeparator();
		 s+="cancel";
		 s+=System.lineSeparator();
		 s+="QUIT";
		input = new ByteArrayInputStream(s.getBytes());
		System.setIn(input);
		//scanner can't update system.in on its own... moet ge update telkens ge een nieuw argument meegeeft via system.in.
		LoginUI.scanner.updateScanner(); 
		assem.run();
	}
	
	private void loadCarMechanic() {
		String s = "";
		 s += "b";
		 s+=System.lineSeparator();
		 s+="0";
		 s+=System.lineSeparator();
		 s+="cancel";
		 s+=System.lineSeparator();
		 s+="QUIT";
		input = new ByteArrayInputStream(s.getBytes());
		System.setIn(input);
		//scanner can't update system.in on its own... moet ge update telkens ge een nieuw argument meegeeft via system.in.
		LoginUI.scanner.updateScanner(); 
		assem.run();
	}
	
	 void loadManager() {			
		 String s = "";
		 s += "c";
		 s+=System.lineSeparator();
		 s+="0";
		 s+=System.lineSeparator();
		 s+="cancel";
		 s+=System.lineSeparator();
		 s+="QUIT";
		input = new ByteArrayInputStream(s.getBytes());
		System.setIn(input);
		//scanner can't update system.in on its own... moet ge update telkens ge een nieuw argument meegeeft via system.in.
		LoginUI.scanner.updateScanner(); 
		assem.run();
		
	}

	
}
