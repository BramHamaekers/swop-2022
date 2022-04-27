package swop.Tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
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
		loadManager();
		assertThrows(IllegalArgumentException.class,
				() -> assem.completeTask(null, 0));
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
