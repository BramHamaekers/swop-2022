package swop.Tests.UseCaseTests;


import org.junit.jupiter.api.Test;

import swop.Car.Car;
import swop.Car.CarOrder;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.GarageHolderGenerator;
import swop.Users.GarageHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GarageHolderUseCasesTest {

    AssemAssist assem;
    GarageHolder garageHolder;
    InputStream input;
    GarageHolderGenerator garageHolderGenerator = new GarageHolderGenerator();

    // 1. New user has no orders placed
    @Test
    void NewGarageHolderTest() {
        this.assem = new AssemAssist();
        this.garageHolder = new GarageHolder("user1");

        // New user has no orders placed
        assert garageHolder.getOrders().size() == 0;
    }
    

    @Test
    void garageHolderUITest() {
        ListIterator<String> output = setupUITest(String.format("a%nCANCEL%nQUIT"), 2); // Setup
        

        /**************** UI for garage holder at first login + select no action****************/
        
        presentOverview(output); // 1. The system presents an overview of the orders placed by the user

        output.next();
        
        presentActions(output); // 2. user indicates he wants to place an order
        
        
        /**************** UI for garage holder order new car ****************/
        
        output = setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 12); // Setup
        
        CarModel a = new ModelA();

        displayAndIndicateModels(output); // 3. The system shows a list of available car models and how 2 indicate them

        displayOrderingForm(output, a); // 5. The system displays the ordering form for model A.
        
        output.next();

        completeOrderingForm(output, a); // 6. User completes the ordering form for model A

        storeOrder(); // 7. The system stores the new order.
        
        /**************** UI for garage holder check order details ****************/
        
        output = continueUITest(String.format("a%n1%n"+ this.garageHolder.getOrders().iterator().next().getID() + "%nCANCEL%nQUIT"), 2);
        
        presentOverview(output); //the overview should now contain a new pending order
        
        skip(output, 6);

        presentOrderDetails(output); // 8. The system presents an estimated completion date for the new order.
    }

    
private void skip(ListIterator<String> output, int skips) {
		for(int i =0; i < skips; i++)
			output.next();
		
	}


//    @Test
//    void garageHolderUITestAlternateFlow1() {
//        ListIterator<String> output = setupUITest(String.format("a%nn%nQUIT")); // Setup
//
//        presentOverview(output); // 1. The system presents an overview of the orders placed by the user
//
//        indicateOrderPlacementDenied(output); // 1a. user indicates he wants to leave the overview
//    }
//
//    @Test
//    void garageHolderUITestAlternateFlow2() {
//        ListIterator<String> output = setupUITest(String.format("a%ny%n0%nCANCEL%nQUIT")); // Setup
//
//        presentOverview(output); // 1. The system presents an overview of the orders placed by the user
//
//        indicateOrderPlacement(output); // 2. user indicates he wants to place an order
//
//        displayModels(output); // 3. The system shows a list of available car models
//
//        indicateModel(output); // 4. The user indicates the car model he wishes to order.
//
//        displayOrderingForm(output); // 5. The system displays the ordering form.
//
//        cancelOrderingForm(output); // 6a. User completes the ordering form
//
//        storeOrderNoOrderPlaced(); // 7. The system stores the new order and updates the production schedule.
//    }

    private void storeOrder() {
        assert garageHolder.getOrders().size() == 1;
    }

    private void storeOrderNoOrderPlaced() {
        assert garageHolder.getOrders().size() == 0;
        assert assem.getController().getCarQueue().size() == 0;
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

    private void presentOrderDetails(ListIterator<String> output) {
    	String orderinfo ="Input an orderID: " + this.garageHolder.getOrders().iterator().next().toString();
    	ListIterator<String> iterator = Arrays.asList(orderinfo.split(String.format("%n")))
                .listIterator();
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());
    }

    private void completeOrderingForm(ListIterator<String> output, CarModel model) {
    	Set<Entry<String, List<String>>> options = model.getValidOptions().entrySet();
    	String op = "";
    	for(var option: options) {
    		op += "Choose " + option.getKey() + ": ";
    	}
    	assertEquals(op, output.next());
    }

    private void cancelOrderingForm(ListIterator<String> output) {
        assertEquals(
                "Choose Airco:" +
                        " CANCELED"
                , output.next());
    }

    private void displayOrderingForm(ListIterator<String> output, CarModel carModel) {
    	DisplayStatus builder = new DisplayStatus();
    	this.garageHolderGenerator.generateOrderingForm(builder, carModel.getValidOptions(), carModel.getName());
    	ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
                .listIterator();
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());
    }

    private void indicateModel(ListIterator<String> output) {
        assertEquals("Which model would you like to order?", output.next());
        assertEquals("", output.next());
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

    private void indicateOrderPlacementDenied(ListIterator<String> output) {
        assertEquals("Do you want to place an order? ", output.next());
        assertEquals("[y] Yes [n] No", output.next());
        assertEquals("Welcome!", output.next());
    }

    private void presentOverview(ListIterator<String> output) {
		DisplayStatus builder = new DisplayStatus();
		this.garageHolderGenerator.generateOrderStatus(builder, this.garageHolder.getOrders());
		ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
                .listIterator();
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());
    }

}

