package swop.Tests.UseCaseTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.Records.allStats;
import swop.UI.LoginUI;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.ManagerGenerator;
import swop.Users.GarageHolder;
import swop.Users.Manager;

public class CheckProductionStatisticsTest {

	private AssemAssist assem;
	private Manager manager;
	private ByteArrayInputStream input;
	private ManagerGenerator managerGenerator = new ManagerGenerator();
	
	
	@Test
	void instaCheckProductionStatisticsUITest() {
		ListIterator<String> output = setupUITest(String.format("c%n0%n%nQUIT"), 7); // log in as manager and check production stats
		
		showProductionNofinishedCarsStatistics(output);
		
		assert assem.getStats().delayLast2().isEmpty() && assem.getStats().ordersLast2().isEmpty();
	}
	
	@Test
	void checkProductionStatisticsAfterCarsCompleted() {
		ListIterator<String> output = setupUITest(String.format("c%n0%n%nQUIT"), 7); // insta check production stats
		
		showProductionNofinishedCarsStatistics(output);
		
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		
		output = continueUITest(String.format("b%n0%n0%n0%n0%n20%n0%n0%n0%n20%n" + // complete tasks work post 1
				"1%n0%n0%n20%n1%n0%n0%n20%n"+ // complete tasks work post 2
				"2%n0%n0%n20%n2%n0%n0%n20%n2%n0%n0%n20%n0%nQUIT"), 7); // complete tasks work post 3
		
		output = continueUITest(String.format("c%n0%n%nQUIT"), 7); // check production stats
		
		showProductionOnefinishedCarStatistics(output);
		
		output = continueUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		
		output = continueUITest(String.format("b%n0%n0%n0%n0%n10%n0%n0%n0%n10%n" + // complete tasks work post 1
				"1%n0%n0%n10%n1%n0%n0%n10%n"+ // complete tasks work post 2
				"2%n0%n0%n10%n2%n0%n0%n10%n2%n0%n0%n10%n0%nQUIT"), 7); // complete tasks work post 3
		
		output = continueUITest(String.format("c%n0%n%nQUIT"), 7); // check production stats
		
		showProductionTwoOrMorefinishedCarsStatistics(output);
	
	}





/////////////////////////////////////////////////////////////////////////

	private void showProductionNofinishedCarsStatistics(ListIterator<String> output){
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateProductionStatistics(builder, assem.getStats());
		assertEquals(Arrays.asList(builder.getDisplay().split(String.format("%n"))).size(), 9);
		ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
	            .listIterator();
		while (iterator.hasNext())
	    	assertEquals(iterator.next(), output.next());
	}
	
	private void showProductionOnefinishedCarStatistics(ListIterator<String> output){
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateProductionStatistics(builder, assem.getStats());
		assertEquals(Arrays.asList(builder.getDisplay().split(String.format("%n"))).size(), 11);
		ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
	            .listIterator();
		while (iterator.hasNext())
	    	assertEquals(iterator.next(), output.next());
	}
	
	private void showProductionTwoOrMorefinishedCarsStatistics(ListIterator<String> output){
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateProductionStatistics(builder, assem.getStats());
		String s = builder.getDisplay();
		assertEquals(Arrays.asList(builder.getDisplay().split(String.format("%n"))).size(), 12);
		ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
	            .listIterator();
		while (iterator.hasNext())
	    	assertEquals(iterator.next(), output.next());
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