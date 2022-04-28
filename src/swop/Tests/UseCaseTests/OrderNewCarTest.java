package swop.Tests.UseCaseTests;


import org.junit.jupiter.api.Test;

import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModel.ModelB;
import swop.Car.CarModel.ModelC;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.GarageHolderGenerator;
import swop.Users.GarageHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderNewCarTest {

    AssemAssist assem;
    GarageHolder garageHolder;
    InputStream input;
    GarageHolderGenerator garageHolderGenerator = new GarageHolderGenerator();


    @Test
    void NewGarageHolderTest() {
        this.assem = new AssemAssist();
        this.garageHolder = new GarageHolder("user1");

        // New user has no orders placed
        assert garageHolder.getOrders().size() == 0;
    }
    

    @Test
    void oderNewCarFullUITest() {
        
        /**************** UI for garage holder order new car ****************/
        
    	 ListIterator<String> output = setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 12); // Setup
        
        CarModel a = new ModelA();

        displayAndIndicateModels(output); // The system shows a list of available car models and how 2 indicate them

        displayOrderingForm(output, a); // The system displays the ordering form for model A.
        
        output.next();

        completeOrderingForm(output, a); // User completes the ordering form for model A

        storeOrder(1); // The system stores the new order.
    }
    
    @Test
    void instaCancelAsGarageHolderTest() {
        ListIterator<String> output = setupUITest(String.format("a%nCANCEL%nQUIT"), 2); // Setup
        /**************** UI for garage holder at first login + select no action****************/
        
        presentOverview(output); // The system presents an overview of the orders placed by the user -> which are none

        output.next();
        
        presentActions(output); // user indicates he wants to place an order
    }

   @Test
   void cancelAndInvalidOptionOrderingFormTest() {
	   
	   /**************** Cancel ****************/
	   ListIterator<String> output = setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%nCANCEL%nQUIT"), 12); // Setup
	   
	   CarModel a = new ModelA();
	   
       displayAndIndicateModels(output); // The system shows a list of available car models and how 2 indicate them

       displayOrderingForm(output, a); // The system displays the ordering form for model A.
       
       output.next();
	   
	   cancelOrderingFormOnFifthPosition(output, a); // should be cancelled after selecting 5 part preferences
	   
	   /**************** Invalid Option ****************/
	   
	   output = setupUITest(String.format("a%n0%n0%n1%n5%n1%nCANCEL%nQUIT"), 12); // Setup
	   
       displayAndIndicateModels(output); // The system shows a list of available car models and how 2 indicate them

       displayOrderingForm(output, a); // The system displays the ordering form for model A.
       
       output.next();
	   
	   invalidOptionForPartTwo(output, a);
   }

   @Test
   void orderMultipleCars(){
	   ListIterator<String> output = setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 12); // order car modal a
	   
       CarModel a = new ModelA();

       displayAndIndicateModels(output); // The system shows a list of available car models and how 2 indicate them

       displayOrderingForm(output, a); // The system displays the ordering form for model A.
       
       output.next();

       completeOrderingForm(output, a); // User completes the ordering form for model A

       storeOrder(1); // The system stores the new order.
	   
	   output = continueUITest(String.format("a%n0%n1%n0%n0%n0%n0%n0%n0%n0%n0%nQUIT"), 19); // order car modal b
	   
       CarModel b = new ModelB();

       displayOrderingForm(output, b); // The system displays the ordering form for model B
       
       output.next();

       completeOrderingForm(output, b); // User completes the ordering form for model B

       storeOrder(2); // The system stores the new order.
	   
	   output = continueUITest(String.format("a%n0%n2%n0%n0%n0%n0%n0%n0%n0%n0%nQUIT"), 20); // order car modal c
	   
       CarModel c = new ModelC();

       displayOrderingForm(output, c); // The system displays the ordering form for model B
       
       output.next();

       completeOrderingForm(output, c); // User completes the ordering form for model B

       storeOrder(3); // The system stores the new order.
   }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////////////////
    
    
	private void invalidOptionForPartTwo(ListIterator<String> output, CarModel model) {
    	Set<Entry<String, List<String>>> options = model.getValidOptions().entrySet();
    	String op = "";
    	int i = 0;
    	for(var option: options) {
    		if(i<2) op += "Choose " + option.getKey() + ": ";
    		if(i == 2) op += "Please give valid input:" +  "Choose " + option.getKey() + ": ";
    		i++;
    	}
    	op += "CANCELED";
    	assertEquals(op, output.next());
	}
    
    


    private void completeOrderingForm(ListIterator<String> output, CarModel model) {
    	Set<Entry<String, List<String>>> options = model.getValidOptions().entrySet();
    	String op = "";
    	for(var option: options) {
    		op += "Choose " + option.getKey() + ": ";
    	}
    	assertEquals(op, output.next());
    }

    private void cancelOrderingFormOnFifthPosition(ListIterator<String> output, CarModel model) {
    	Set<Entry<String, List<String>>> options = model.getValidOptions().entrySet();
    	String op = "";
    	int i = 0;
    	for(var option: options) {
    		if(i<5) op += "Choose " + option.getKey() + ": ";
    		i++;
    	}
    	op += "CANCELED";
    	assertEquals(op, output.next());
    }
    
    private void cancel(ListIterator<String> output) {
        assertEquals("CANCELED", output.next());
    }
    

    private void displayOrderingForm(ListIterator<String> output, CarModel carModel) {
    	DisplayStatus builder = new DisplayStatus();
    	this.garageHolderGenerator.generateOrderingForm(builder, carModel.getValidOptions(), carModel.getName());
    	ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
                .listIterator();
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());
    }

    private void displayAndIndicateModels(ListIterator<String> output) {
    	DisplayStatus builder = new DisplayStatus();
    	this.garageHolderGenerator.generateCarModels(builder, CarModel.types);
    	ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
                .listIterator();
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());
    }

    private void presentActions(ListIterator<String> output) {
    	List<String> actions = Arrays.asList("Place new order", "Check order details", "Exit");
		DisplayStatus builder = new DisplayStatus();
		this.garageHolderGenerator.selectAction(builder, actions, "What would you like to do?");
		ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
                .listIterator();
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());
    }


    private void presentOverview(ListIterator<String> output) {
		DisplayStatus builder = new DisplayStatus();
		this.garageHolderGenerator.generateOrderStatus(builder, this.garageHolder.getOrders());
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

    private void storeOrder(int i) {
        assert garageHolder.getOrders().size() == i;
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

