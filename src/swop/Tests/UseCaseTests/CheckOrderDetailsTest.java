package swop.Tests.UseCaseTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.GarageHolderGenerator;
import swop.Users.GarageHolder;

public class CheckOrderDetailsTest {
    AssemAssist assem;
    GarageHolder garageHolder;
    InputStream input;
    GarageHolderGenerator garageHolderGenerator = new GarageHolderGenerator();
    

    @Test
    void CheckOrderDetailsFullUITest() {
        
        setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 12); // Setup
        ListIterator<String> output = continueUITest(String.format("a%n1%n"+ this.garageHolder.getOrders().iterator().next().getID() + "%nCANCEL%nQUIT"), 2);
        
        presentOverview(output); //the overview should now contain a pending order
        
        output.next();
        
        presentActions(output);  // 2. user indicates he wants to check details

        presentOrderDetails(output); // 8. The system presents the details of the asked order.
    }

    @Test
    void OrderDetailsInvalidIDTest() {
    	ListIterator<String> output = setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%na%n1%ninvalid%nCANCEL%nQUIT"), 47); // Setup
    	
    	askOrderID(output); // user fills an invalid order id
    	
    	invalidID(output); // system says it's an invalid id
    	
    	askOrderID(output); // user fills an invalid order id
    	
    	cancel(output); //cancel
    	
    }

    @Test
    void OrderDetailsNoOrdersPlacedTest() {
        ListIterator<String> output = setupUITest(String.format("a%n1%nCANCEL%nQUIT"), 12); // Setup
        noOrdersPlaced(output);
        skip(output, 5);
        cancel(output); //cancel
    }
    
    @Test
    void CheckOrderDetailsTwiceTest() {
        setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 12); // Setup create new order
    	String id = this.garageHolder.getOrders().iterator().next().getID();
        ListIterator<String> output = continueUITest(String.format("a%n1%n"+ id
        		+ "%ny%n"+ id + "%nCANCEL%nQUIT"), 2);
        
        presentOverview(output); //the overview should now contain a pending order
        
        output.next();
        
        presentActions(output);  // user indicates he wants to check details

        presentOrderDetails(output); // The system presents the details of the asked order.
    }

	///////////////////////////////////////////////////////////////////////////////////////

    private void invalidID(ListIterator<String> output) {
    	assertEquals("Please provide a valid orderID", output.next());
	}
    
    
    private void askOrderID(ListIterator<String> output) {	
    	assertEquals("Input an orderID: ", output.next());
	}

    private  void noOrdersPlaced(ListIterator<String> output) {
        assertEquals("No orders available to check!", output.next());
    }
    
    private void cancel(ListIterator<String> output) {
        assertEquals("CANCELED", output.next());
    }
    
    private void presentOrderDetails(ListIterator<String> output) {
    	assertEquals("Input an orderID: ", output.next());
    	String orderinfo = this.garageHolder.getOrders().iterator().next().toString();
        for (String s : orderinfo.split(String.format("%n"))) assertEquals(s, output.next());
    }

    private void presentActions(ListIterator<String> output) {
    	List<String> actions = Arrays.asList("Place new order", "Check order details", "Exit");
		DisplayStatus builder = new DisplayStatus();
		this.garageHolderGenerator.selectAction(builder, actions, "What would you like to do?");
        for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());
    }

    private void presentOverview(ListIterator<String> output) {
		DisplayStatus builder = new DisplayStatus();
		this.garageHolderGenerator.generateOrderStatus(builder, this.garageHolder.getOrders());
        for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    

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
        this.garageHolder = (GarageHolder) this.assem.getUserMap().get("a"); 

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
