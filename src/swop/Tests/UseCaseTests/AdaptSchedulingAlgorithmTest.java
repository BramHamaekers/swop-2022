package swop.Tests.UseCaseTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.jupiter.api.Test;

import swop.Car.Car;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.ManagerGenerator;
import swop.UI.TempUI;
import swop.Users.Manager;

public class AdaptSchedulingAlgorithmTest {

	private AssemAssist assem;
	private Manager manager;
	private final ManagerGenerator managerGenerator = new ManagerGenerator();



	@Test
	void AdaptSchedulingAlgorithmBatchOptionsAvailableTest() {
		ListIterator<String> output = setupUITest(String.format("c%n1%n0%n1%nQUIT"), 13); // log in as manager and try to change batch option
		
		noTasksAvailableMessage(output); // should print no tasks available message
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		output = continueUITest(String.format("c%n1%n0%nQUIT"), 13); //log in as manager and try to change batch option
		
		noTasksAvailableMessage(output); // should print no tasks available message
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		output = continueUITest(String.format("c%n1%n0%nQUIT"), 13); //log in as manager and try to change batch option
		
		noTasksAvailableMessage(output); // should print no tasks available message
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		output = continueUITest(String.format("c%n1%n0%nQUIT"), 13); //log in as manager and try to change batch option
		
		noTasksAvailableMessage(output); // should print no tasks available message
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		output = continueUITest(String.format("c%n1%n0%nCANCEL%nQUIT"), 13); //log in as manager and try to change batch option
		
		showBatchOptions(output);
		
		cancel(output);
		
		assertEquals(assem.getController().getScheduler().getSchedulingAlgorithm(), "FIFO"); // stays on fifo since we cancel the change
		
	}
	
	@Test
	void changeToBATCHandBackToFIFO() {
		setupUITest(String.format("c%n1%n0%n1%nQUIT"), 13); // setup
		
		assertEquals(assem.getController().getScheduler().getSchedulingAlgorithm(), "FIFO");
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		continueUITest(String.format("c%n1%n0%n1%n0%nQUIT"), 13); // try to change batch option
		
		assertEquals(assem.getController().getScheduler().getSchedulingAlgorithm(), "BATCH");
		
		continueUITest(String.format("c%n1%n1%nQUIT"), 13); // try to change fifo option
		
		assertEquals(assem.getController().getScheduler().getSchedulingAlgorithm(), "FIFO");
	}




//////////////////////////////////////////////////////////////////////////////////////////////////
	
private void noTasksAvailableMessage(ListIterator<String> output) {
		assertEquals("No batchoptions available -> Algorithm will stay FIFO", output.next());
	}

private void showBatchOptions(ListIterator<String> output) {
	List<Map<String, String>> possibleBatch = this.manager.getBatchOptions();
	
	assert !possibleBatch.isEmpty(); 
	
	DisplayStatus builder = new DisplayStatus();
	managerGenerator.generateBatchSelection(builder, possibleBatch);
	for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());
}

private void cancel(ListIterator<String> output) {
    assertEquals("CANCELLED", output.next());
}




////////////////////////////////////////////////////////////////////////////


	
    private ListIterator<String> continueUITest(String inputString, int skips) { 
        return handleInputOutput.continueUITest(inputString, skips);
    }

    private ListIterator<String> setupUITest(String inputString, int skips) {
    	ListIterator<String> output = handleInputOutput.setupUITest(inputString, skips);
    	this.assem = handleInputOutput.getAssem();  
    	this.manager = (Manager) handleInputOutput.getUser("c");
    	return output;
    }
    
    void skip(ListIterator<String> output, int skips) {
    	handleInputOutput.skip(output, skips);	
	}
}
