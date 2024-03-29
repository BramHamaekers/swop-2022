package swop.Tests.UseCaseTests;


import org.junit.jupiter.api.Test;

import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModel.ModelB;
import swop.Car.CarModel.ModelC;
import swop.Main.AssemAssist;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.GarageHolderGenerator;
import swop.Users.GarageHolder;

import java.io.InputStream;
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
        this.garageHolder = new GarageHolder("user1", this.assem);

        // New user has no orders placed
        assert garageHolder.getOrders().size() == 0;
    }
    

    @Test
    void oderNewCarFullUITest() {
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

        presentOverview(output); // The system presents an overview of the orders placed by the user -> which are none

        output.next();
        
        presentActions(output); // user indicates he wants to place an order
    }

   @Test
   void cancelOrderingFormTest() {
       ListIterator<String> output = setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%nCANCEL%nQUIT"), 12); // Setup
	   CarModel a = new ModelA();
       displayAndIndicateModels(output); // The system shows a list of available car models and how 2 indicate them
       displayOrderingForm(output, a); // The system displays the ordering form for model A.
       output.next();
	   cancelOrderingFormOnFifthPosition(output, a); // should be cancelled after selecting 5 part preferences
   }

   @Test
   void orderingFormInvalidInputTest() {
       ListIterator<String> output = setupUITest(String.format("a%n0%n0%n1%n5%n1%nCANCEL%nQUIT"), 12); // Setup
       CarModel a = new ModelA();
       displayAndIndicateModels(output); // The system shows a list of available car models and how 2 indicate them
       displayOrderingForm(output, a); // The system displays the ordering form for model A.
       output.next();
       invalidOptionForPartTwo(output, a);
   }

   @Test
   void invalidSpecificationTest_AircoAndEngine() {
       ListIterator<String> output = setupUITest(String.format("a%n0%n1%n1%n0%n0%n2%n0%n0%n1%n0%nCANCEL%nQUIT"), 12); // Setup
       CarModel b = new ModelB();
       displayAndIndicateModels(output); // The system shows a list of available car models and how 2 indicate them
       displayOrderingForm(output, b); // The system displays the ordering form for model A.
       skip(output, 2);
       assertEquals("Airco must be manual or none if you select the ultra engine", output.next());
       //invalidOptionForPartTwo(output, b);
   }

    @Test
    void invalidSpecificationTest_SportNoSpoiler() {
        ListIterator<String> output = setupUITest(String.format("a%n0%n1%n2%n2%n0%n2%n0%n0%n1%n0%nCANCEL%nQUIT"), 12); // Setup
        CarModel b = new ModelB();
        displayAndIndicateModels(output); // The system shows a list of available car models and how 2 indicate them
        displayOrderingForm(output, b); // The system displays the ordering form for model A.
        skip(output, 2);
        assertEquals("Spoiler is mandatory when choosing a sport body", output.next());
        //invalidOptionForPartTwo(output, b);
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
    	op += "CANCELLED";
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
    	op += "CANCELLED";
    	assertEquals(op, output.next());
    }
    
    private void cancel(ListIterator<String> output) {
        assertEquals("CANCELLED", output.next());
    }
    

    private void displayOrderingForm(ListIterator<String> output, CarModel carModel) {
    	DisplayStatus builder = new DisplayStatus();
    	this.garageHolderGenerator.generateOrderingForm(builder, carModel.getValidOptions(), carModel.getName());
        for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());
    }

    private void displayAndIndicateModels(ListIterator<String> output) {
    	DisplayStatus builder = new DisplayStatus();
    	this.garageHolderGenerator.generateCarModels(builder, CarModel.types);
        for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());
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

    ////////////////////////////////////////////////////////////////////////////

    private void storeOrder(int i) {
        assert garageHolder.getOrders().size() == i;
    }

    private ListIterator<String> continueUITest(String inputString, int skips) { 
        return handleInputOutput.continueUITest(inputString, skips);
    }

    private ListIterator<String> setupUITest(String inputString, int skips) {
       	ListIterator<String> output = handleInputOutput.setupUITest(inputString, skips);
    	this.assem = handleInputOutput.getAssem();  
    	this.garageHolder = (GarageHolder) handleInputOutput.getUser("a");
    	return output;
    }
    
    void skip(ListIterator<String> output, int skips) {
    	handleInputOutput.skip(output, skips);	
	}

}

