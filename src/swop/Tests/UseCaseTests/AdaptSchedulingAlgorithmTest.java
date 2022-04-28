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
import swop.Users.Manager;

public class AdaptSchedulingAlgorithmTest {

	private ByteArrayInputStream input;
	private AssemAssist assem;
	private Manager manager;
	private final ManagerGenerator managerGenerator = new ManagerGenerator();



	@Test
	void AdaptSchedulingAlgorithmBatchOptionsAvailableTest() {
		ListIterator<String> output = setupUITest(String.format("c%n1%n0%n1%nQUIT"), 13); // log in as manager and try to change batch option
		
		noTasksAvailableMessage(output); // should print no tasks available message
		
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order	
		output = continueUITest(String.format("c%n1%n0%nQUIT"), 13); //log in as manager and try to change batch option
		
		noTasksAvailableMessage(output); // should print no tasks available message
		
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order		
		output = continueUITest(String.format("c%n1%n0%nQUIT"), 13); //log in as manager and try to change batch option
		
		noTasksAvailableMessage(output); // should print no tasks available message
		
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order		
		output = continueUITest(String.format("c%n1%n0%nQUIT"), 13); //log in as manager and try to change batch option
		
		noTasksAvailableMessage(output); // should print no tasks available message
		
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order		
		output = continueUITest(String.format("c%n1%n0%nCANCEL%nQUIT"), 13); //log in as manager and try to change batch option
		
		showBatchOptions(output);
		
		cancel(output);
		
		assertEquals(assem.getController().getScheduler().getSchedulingAlgorithm(), "FIFO"); // stays on fifo since we cancel the change
		
	}
	
	@Test
	void changeToBATCHandBackToFIFO() {
		ListIterator<String> output = setupUITest(String.format("c%n1%n0%n1%nQUIT"), 13); // setup
		
		assertEquals(assem.getController().getScheduler().getSchedulingAlgorithm(), "FIFO");
		
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order	
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order	
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order	
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order	
		output = continueUITest(String.format("c%n1%n0%n1%n0%nQUIT"), 13); // try to change batch option
		
		assertEquals(assem.getController().getScheduler().getSchedulingAlgorithm(), "BATCH");
		
		output = continueUITest(String.format("c%n1%n1%nQUIT"), 13); // try to change fifo option
		
		assertEquals(assem.getController().getScheduler().getSchedulingAlgorithm(), "FIFO");
	}




//////////////////////////////////////////////////////////////////////////////////////////////////
	
private void noTasksAvailableMessage(ListIterator<String> output) {
		assertEquals("No batchoptions available -> Algoritm will stay FIFO", output.next());		
	}

private void showBatchOptions(ListIterator<String> output) {
	List<Map<String, String>> partMaps = assem.getController().getCarQueue().stream().map(Car::getPartsMap).toList();
	List<Map<String, String>> possibleBatch = this.manager.getBatchOptions(partMaps);
	
	assert !possibleBatch.isEmpty(); 
	
	DisplayStatus builder = new DisplayStatus();
	managerGenerator.generateBatchSelection(builder, possibleBatch);	
	ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
            .listIterator();
	while (iterator.hasNext())
    	assertEquals(iterator.next(), output.next());
}

private void cancel(ListIterator<String> output) {
    assertEquals("CANCELED", output.next());
}





















////////////////////////////////////////////////////////////////////////////




	private void skip(ListIterator<String> output, int skips) {
		for(int i =0; i < skips; i++)
			output.next();
	
	}
	
	private ListIterator<String> continueUITest(String inputString, int skips) {
		this.input = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(input);
		LoginUI.scanner.updateScanner();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		assem.run();
		
		ListIterator<String> output = Arrays.asList(outContent.toString().split(String.format("%n")))
				.listIterator();
		
		for(int i =0; i < skips; i++)
			output.next();
		
	return output;
	}
	
	private ListIterator<String> setupUITest(String inputString, int skips) {
		this.assem = new AssemAssist();
		this.manager = (Manager) this.assem.getUserMap().get("c"); 
		
		this.input = new ByteArrayInputStream(inputString.getBytes());
		System.setIn(input);
		LoginUI.scanner.updateScanner();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		assem.run();
		
		ListIterator<String> output = Arrays.asList(outContent.toString().split(String.format("%n")))
				.listIterator();
		
		for(int i =0; i < skips; i++)
			output.next();
	
	return output;
	}
}
