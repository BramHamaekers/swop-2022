package swop.Tests.UseCaseTests;

import org.junit.jupiter.api.Test;
import swop.Car.CarOrder;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PerformAssemblyTasksTest {

    AssemAssist assem;
    CarMechanic carMechanic;
    InputStream input;
    GarageHolder garageHolder;


    @Test
    void CarMechanicUITest() {

        ListIterator<String> output = setupUITest(String.format(
                "a%ny%n0%n1%n1%n1%n1%n1%n1%n1%n" + // place order
                "c%ny%ny%n45%n%n" + // advance assemblyLine
                "b%n0%n1%n%n0%n%nQUIT") // Perform assembly Tasks
        ); // Setup

        askWorkPost(output); // 1. ask user for work post

        selectWorkPost(output); // 2. User selects work post

        presentTasks(output); // 3. show overview of pending tasks

        selectTask(output); // 4. User selects task

        showTaskInfo(output); // 5. system shows the assembly task information

        indicateFinished(output); // 6. indicate when task is finished

        storeChanges(); // 7. the system stores the changes

        doNextTask(output); // 8. Usecase continues in 4

    }

    @Test
    void CarMechanicUITestUITestAlternateFlow1() {

        ListIterator<String> output = setupUITest(String.format(
                "a%ny%n0%n1%n1%n1%n1%n1%n1%n1%n" + // place order
                        "c%ny%ny%n45%n%n" + // advance assemblyLine
                        "b%n0%n1%n%nCANCEL%nQUIT") // Perform assembly Tasks
        ); // Setup

        askWorkPost(output); // 1. ask user for work post

        selectWorkPost(output); // 2. User selects work post

        presentTasks(output); // 3. show overview of pending tasks

        selectTask(output); // 4. User selects task

        showTaskInfo(output); // 5. system shows the assembly task information

        indicateFinished(output); // 6. indicate when task is finished

        storeChangesAfter1(); // 7. the system stores the changes

        indicateStop(output); // 8. user indicates to stop performing tasks

    }

    private void indicateStop(ListIterator<String> output) {
        doNextTask(output);
        assertEquals("CANCELED", output.next());
        assertEquals("Welcome!", output.next());
    }


    private void doNextTask(ListIterator<String> output) {
        assertEquals("", output.next());
        assertEquals("============ Available Tasks ============", output.next());
        assertEquals("Assembly Car Body [0] ", output.next());
        assertEquals("=======================================", output.next());
        selectTask(output);


    }

    private void storeChanges() {
        Set<String> nameSet = new HashSet<>();
        for (CarOrder x : this.garageHolder.getOrders())
            x.getCar().getUncompletedTasks().forEach(t -> nameSet.add(t.getName()));
        assertFalse(nameSet.contains("Assembly Car Body"));
        assertFalse(nameSet.contains("Paint Car"));
    }

    private void storeChangesAfter1() {
        Set<String> nameSet = new HashSet<>();
        this.garageHolder.getOrders().forEach(x -> x.getCar().getUncompletedTasks().forEach(t ->
                nameSet.add(t.getName())));
        assertTrue(nameSet.contains("Assembly Car Body"));
        assertFalse(nameSet.contains("Paint Car"));
    }

    private void indicateFinished(ListIterator<String> output) {
        assertEquals("Press enter when you are finished", output.next());
    }

    private void showTaskInfo(ListIterator<String> output) {
        assertEquals("-----------Info For Task----------", output.next());
        assertEquals("Paint the body in colour: blue", output.next());
        assertEquals("----------------------------------", output.next());
    }


    private void selectTask(ListIterator<String> output) {
        assertEquals("Select task: ", output.next());
    }

    private void presentTasks(ListIterator<String> output) {
        assertEquals("============ Available Tasks ============", output.next());
        assertEquals("Assembly Car Body [0] ", output.next());
        assertEquals("Paint Car [1] ", output.next());
        assertEquals("=======================================", output.next());

    }

    private void selectWorkPost(ListIterator<String> output) {
        assertEquals("Select station: ", output.next());
        assertEquals("", output.next());
    }

    private void askWorkPost(ListIterator<String> output) {
        assertEquals("============ Current Stations ============", output.next());
        assertEquals("Car Body Post [0] ", output.next());
        assertEquals("Drivetrain Post [1] ", output.next()); // No Pending orders yet
        assertEquals("Accessories Post [2] ", output.next());
        assertEquals("=======================================", output.next());
    }

    private ListIterator<String> setupUITest(String inputString) {
        this.assem = new AssemAssist();
        this.carMechanic = (CarMechanic) this.assem.getUserMap().get("b");
        this.garageHolder = (GarageHolder) this.assem.getUserMap().get("a");

        this.input = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(input);
        LoginUI.scanner.updateScanner();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assem.run();

        ListIterator<String> output = Arrays.asList(outContent.toString().split(String.format("%n")))
                .listIterator();

        for (int i = 0; i < 61; i++) {
            output.next();
        }
        return output;
    }
}
