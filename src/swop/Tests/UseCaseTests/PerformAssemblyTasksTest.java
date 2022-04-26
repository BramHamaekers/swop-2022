package swop.Tests.UseCaseTests;

import org.junit.jupiter.api.Test;
import swop.Car.CarOrder;
import swop.CarManufactoring.Task;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.CarMechanicGenerator;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PerformAssemblyTasksTest {

    AssemAssist assem;
    CarMechanic carMechanic;
    InputStream input;
    private static final CarMechanicGenerator carMechanicGenerator = new CarMechanicGenerator();


    @Test
    void performAssemblyTasksUITest() {

        ListIterator<String> output = setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
        output = continueUITest(String.format("b%n0%n0%n0%n0%n45%nCANCEL%nQUIT"), 7);// 

        askWorkPost(output); // ask user for work post
        
        Task task = presentAvailableTasks(output,0); // 3. show overview of pending tasks

        showTaskInfo(output, task); // system shows the assembly task information

        indicateTimePassed(output);
        
        output.next();

        askWorkPost(output);
        
        storeChanges(); // the system stores the changes

    }

    @Test
    void CarMechanicUITestUITestAlternateFlow1() {

//        ListIterator<String> output = setupUITest(String.format(
//                "a%ny%n0%n1%n1%n1%n1%n1%n1%n1%n" + // place order
//                        "c%ny%ny%n45%n%n" + // advance assemblyLine
//                        "b%n0%n1%n%nCANCEL%nQUIT") // Perform assembly Tasks
//        ); // Setup

//        askWorkPost(output); // 1. ask user for work post
//
//        selectWorkPost(output); // 2. User selects work post
//
//        presentTasks(output); // 3. show overview of pending tasks
//
//        selectTask(output); // 4. User selects task
//
//        showTaskInfo(output); // 5. system shows the assembly task information
//
//        indicateFinished(output); // 6. indicate when task is finished
//
//        storeChangesAfter1(); // 7. the system stores the changes
//
//        indicateStop(output); // 8. user indicates to stop performing tasks

    }



    private void storeChanges() {
        Set<String> nameSet = new HashSet<>();
//        for (CarOrder x : this.carMechanic.getOrders())
//            x.getCar().getUncompletedTasks().forEach(t -> nameSet.add(t.getName()));
//        assertFalse(nameSet.contains("Assembly Car Body"));
//        assertFalse(nameSet.contains("Paint Car"));
    }

    private Task presentAvailableTasks(ListIterator<String> output, int i) {
    	List<Task> taskList = new LinkedList<>(assem.getStations().get(i).getTasks());
		DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateAvailableTasks(builder,taskList);
		ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n"))).listIterator();
		iterator.next();
		assertEquals(iterator.next(), output.next());
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());
		return taskList.get(0);
    }

    private void indicateTimePassed(ListIterator<String> output) {
        assertEquals("How much time did it took to finish the task? (in min)", output.next());
    }
    
    private void indicateFinished(ListIterator<String> output) {
        assertEquals("Press enter when you are finished", output.next());
    }

    private void showTaskInfo(ListIterator<String> output, Task t) {
		DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateTaskInfo(builder, t.getTaskDescription());
		ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
                .listIterator();
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());

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
    	DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateStationList(builder, assem.getStations());
    	ListIterator<String> iterator = Arrays.asList(builder.getDisplay().split(String.format("%n")))
                .listIterator();
		while (iterator.hasNext())
        	assertEquals(iterator.next(), output.next());
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
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
        this.carMechanic = (CarMechanic) this.assem.getUserMap().get("b"); 

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
