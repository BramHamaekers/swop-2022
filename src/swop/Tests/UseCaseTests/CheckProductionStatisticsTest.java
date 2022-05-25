package swop.Tests.UseCaseTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.ManagerGenerator;
import swop.UI.TempManUI;
import swop.UI.TempUI;
import swop.Users.Manager;

public class CheckProductionStatisticsTest {

	private AssemAssist assem;
	private Manager manager;
	private ByteArrayInputStream input;
	private final ManagerGenerator managerGenerator = new ManagerGenerator();
	
	
	@Test
	void instaCheckProductionStatisticsUITest() {
		ListIterator<String> output = setupUITest(String.format("c%n0%n%nQUIT"), 7); // log in as manager and check production stats
		
		noFinishedCarsStatistics(output);
		
		assert assem.getStats().delayLast2().isEmpty() && assem.getStats().ordersLast2().isEmpty();
	}
	
	@Test
	void checkProductionStatisticsAfterCarsCompleted() {
		ListIterator<String> output = setupUITest(String.format("c%n0%n%nQUIT"), 7); // insta check production stats
		
		noFinishedCarsStatistics(output);
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		
		continueUITest(String.format("b%n0%n0%n0%n0%n20%n0%n0%n20%n" + // complete tasks work post 1
				"b%n0%n1%n0%n0%n20%n0%n0%n20%n"+ // complete tasks work post 2
				"b%n0%n2%n0%n0%n20%n0%n0%n20%n0%n0%n20%n0%nQUIT"), 7); // complete tasks work post 3
		
		output = continueUITest(String.format("c%n0%n%nQUIT"), 7); // check production stats
		
		noCarsWithDelayOneFinished(output);
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		
		continueUITest(String.format("b%n0%n0%n0%n0%n10%n0%n0%n10%n" + // complete tasks work post 1
				"b%n0%n1%n0%n0%n10%n0%n0%n10%n"+ // complete tasks work post 2
				"b%n0%n2%n0%n0%n10%n0%n0%n10%n0%n0%n10%n0%nQUIT"), 7); // complete tasks work post 3
		
		output = continueUITest(String.format("c%n0%n%nQUIT"), 7); // check production stats
		
		noCarsWithDelayTwoFinished(output);
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		
		continueUITest(String.format("b%n0%n0%n0%n0%n20%n0%n0%n20%n" + // complete tasks work post 1
				"b%n0%n1%n0%n0%n960%n0%n0%n20%n"+ // complete tasks work post 2
				"b%n0%n2%n0%n0%n20%n0%n0%n20%n0%n0%n20%n0%nQUIT"), 7); // complete tasks work post 3
		
		output = continueUITest(String.format("c%n0%n%nQUIT"), 7);
		
		
		oneCarsWithDelayTwoFinished(output);
		
		continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		
		continueUITest(String.format("b%n0%n0%n0%n0%n20%n0%n0%n20%n" + // complete tasks work post 1
				"b%n0%n1%n0%n0%n960%n0%n0%n20%n"+ // complete tasks work post 2
				"b%n0%n2%n0%n0%n20%n0%n0%n20%n0%n0%n20%n0%nQUIT"), 7); // complete tasks work post 3
		
		output = continueUITest(String.format("c%n0%n%nQUIT"), 7);
		
		twoCarsWithDelayTwoFinishedDifferentDays(output);
	}










/////////////////////////////////////////////////////////////////////////

	private void noFinishedCarsStatistics(ListIterator<String> output){
		checkStatistics(output, 9);
	}
	
	private void checkStatistics(ListIterator<String> output, int size) {
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateProductionStatistics(builder, assem.getStats());
		assertEquals(Arrays.asList(builder.getDisplay().split(String.format("%n"))).size(), size);
		ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
	            .listIterator();
		while (iterator.hasNext())
	    	assertEquals(iterator.next(), output.next());
	}
	
	private void noCarsWithDelayOneFinished(ListIterator<String> output){
		checkStatistics(output, 10);
	}
	
	private void noCarsWithDelayTwoFinished(ListIterator<String> output) {		
		checkStatistics(output, 10);
	}
	
	private void oneCarsWithDelayTwoFinished(ListIterator<String> output) {
		checkStatistics(output, 11);
		
	}

	private void twoCarsWithDelayTwoFinishedDifferentDays(ListIterator<String> output) {
		checkStatistics(output, 13);
		
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
