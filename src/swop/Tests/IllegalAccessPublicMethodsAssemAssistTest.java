package swop.Tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import swop.Exceptions.IlligalUserException;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;

public class IllegalAccessPublicMethodsAssemAssistTest {
	AssemAssist assem;
	InputStream input;


	
	@Test
	void testPublicMethodsThatWillChangeChangeStateAssembly() {
		//test access when assemAssist has no activeUser at the moment (Should be impossible normally): Should throw IlligalUserException
		instantQuit();
		assertThrows(IlligalUserException.class, 
				() -> assem.addOrder(null));
		//test activeUser = car mechanic but wants to add an order: Should throw IlligalUserException
		loadCarMechanic();
		assertThrows(IlligalUserException.class, 
				() -> assem.addOrder(null));
		//test activeUser = car mechanic but wants to advance assambly: Should throw IlligalUserException
		loadGarageHolder();
		assertThrows(IlligalUserException.class, 
				() -> assem.advanceAssembly(0));
		//test activeUser = manager but wants to complete a task: Should throw IlligalUserException
		loadManager();
		assertThrows(IlligalUserException.class, 
				() -> assem.completeTask(null));
	}



	void instantQuit() {
		String s = "QUIT";
		s+=System.lineSeparator();
		input = new ByteArrayInputStream(s.getBytes());
		System.setIn(input);
		//scanner can't update system.in on its own... moet ge update telkens ge een nieuw argument meegeeft via system.in.
		LoginUI.scanner.updateScanner(); 
		assem = new AssemAssist();
		//reset
	}

	private void loadGarageHolder() {
		String s = "";
		 s += "a";
		 s+=System.lineSeparator();
		 s+="n";
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
		 s+="n";
		 s+=System.lineSeparator();
		 s+="QUIT";
		input = new ByteArrayInputStream(s.getBytes());
		System.setIn(input);
		//scanner can't update system.in on its own... moet ge update telkens ge een nieuw argument meegeeft via system.in.
		LoginUI.scanner.updateScanner(); 
		assem.run();
		
	}

	
}
