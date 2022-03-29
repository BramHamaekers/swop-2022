package swop.Tests.UseCaseTests;


import org.junit.jupiter.api.Test;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.Users.GarageHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderNewCarTest {

    AssemAssist assem;
    GarageHolder garageHolder;
    InputStream input;

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
        ListIterator<String> output = setupUITest("a\r\ny\r\n0\r\n1\r\n1\r\n1\r\n1\r\n1\r\n1\r\n1\r\nQUIT"); // Setup

        presentOverview(output); // 1. The system presents an overview of the orders placed by the user

        indicateOrderPlacement(output); // 2. user indicates he wants to place an order

        displayModels(output); // 3. The system shows a list of available car models

        indicateModel(output); // 4. The user indicates the car model he wishes to order.

        displayOrderingForm(output); // 5. The system displays the ordering form.

        completeOrderingForm(output); // 6. User completes the ordering form

        storeOrder(); // 7. The system stores the new order and updates the production schedule.

        presentEstimatedCompletionDate(output); // 8. The system presents an estimated completion date for the new order.
    }

    @Test
    void garageHolderUITestAlternateFlow1() {
        ListIterator<String> output = setupUITest("a\r\nn\r\nQUIT"); // Setup

        presentOverview(output); // 1. The system presents an overview of the orders placed by the user

        indicateOrderPlacementDenied(output); // 1a. user indicates he wants to leave the overview
    }

    @Test
    void garageHolderUITestAlternateFlow2() {
        ListIterator<String> output = setupUITest("a\r\ny\r\n0\r\nCANCEL\r\nQUIT"); // Setup

        presentOverview(output); // 1. The system presents an overview of the orders placed by the user

        indicateOrderPlacement(output); // 2. user indicates he wants to place an order

        displayModels(output); // 3. The system shows a list of available car models

        indicateModel(output); // 4. The user indicates the car model he wishes to order.

        displayOrderingForm(output); // 5. The system displays the ordering form.

        cancelOrderingForm(output); // 6a. User completes the ordering form

        storeOrderNoOrderPlaced(); // 7. The system stores the new order and updates the production schedule.
    }

    private void storeOrder() {
        assert garageHolder.getOrders().size() == 1;
        assert assem.getController().getCarQueue().size() == 1;
        garageHolder.getOrders().forEach(
                x -> assertEquals(x.getCar(), assem.getController().getCarQueue().get(0)));
    }

    private void storeOrderNoOrderPlaced() {
        assert garageHolder.getOrders().size() == 0;
        assert assem.getController().getCarQueue().size() == 0;
    }


    private ListIterator<String> setupUITest(String inputString) {
        this.assem = new AssemAssist();
        this.garageHolder = (GarageHolder) this.assem.getUserMap().get("a"); 

        this.input = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(input);
        LoginUI.scanner.updateScanner();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assem.run();

        ListIterator<String> output = Arrays.asList(outContent.toString().split("\r\n"))
                .listIterator();

        for (int i = 0; i < 3; i++) {
            output.next();
        }
        return output;
    }

    private void presentEstimatedCompletionDate(ListIterator<String> output) {
        assertEquals("============ Estimated Completion Time ============", output.next());
        assertEquals("day: 0, time: 09:00", output.next());
        assertEquals("", output.next());
        assertEquals("=======================================", output.next());
    }

    private void completeOrderingForm(ListIterator<String> output) {
        assertEquals(
                "Choose Airco: " +
                        "Choose Body: " +
                        "Choose Color: " +
                        "Choose Engine: " +
                        "Choose GearBox: " +
                        "Choose Seats: " +
                        "Choose Wheels: "
                , output.next());
    }

    private void cancelOrderingForm(ListIterator<String> output) {
        assertEquals(
                "Choose Airco:" +
                        " CANCELED"
                , output.next());
    }

    private void displayOrderingForm(ListIterator<String> output) {
        assertEquals("============ Ordering Form ============", output.next());
        assertEquals("Airco: [0] manual, [1] automatic climate control, ", output.next());
        assertEquals("Body: [0] sedan, [1] break, ", output.next());
        assertEquals("Color: [0] red, [1] blue, [2] black, [3] white, ", output.next());
        assertEquals("Engine: [0] standard 2l 4 cilinders, [1] performance 2.5l 6 cilinders, ", output.next());
        assertEquals("GearBox: [0] 6 speed manual, [1] 5 speed automatic, ", output.next());
        assertEquals("Seats: [0] leather black, [1] leather white, [2] vinyl grey, ", output.next());
        assertEquals("Wheels: [0] comfort, [1] sports (low profile), ", output.next());
        assertEquals("=======================================", output.next());
    }

    private void indicateModel(ListIterator<String> output) {
        assertEquals("Which model would you like to order?", output.next());
        assertEquals("", output.next());
    }

    private void displayModels(ListIterator<String> output) {
        assertEquals("============ Car Models ============", output.next());
        assertEquals("[0] car", output.next());
        assertEquals("=======================================", output.next());
        assertEquals("", output.next());
    }

    private void indicateOrderPlacement(ListIterator<String> output) {
        assertEquals("Do you want to place an order? ", output.next());
        assertEquals("[y] Yes [n] No", output.next());
        assertEquals("", output.next());
    }

    private void indicateOrderPlacementDenied(ListIterator<String> output) {
        assertEquals("Do you want to place an order? ", output.next());
        assertEquals("[y] Yes [n] No", output.next());
        assertEquals("Welcome!", output.next());
    }

    private void presentOverview(ListIterator<String> output) {
        assertEquals("============ Orders ============", output.next());
        assertEquals("Pending:", output.next());
        assertEquals("", output.next()); // No Pending orders yet
        assertEquals("Completed:", output.next());
        assertEquals("", output.next());
        assertEquals("=======================================", output.next());
        assertEquals("", output.next());
    }

}

