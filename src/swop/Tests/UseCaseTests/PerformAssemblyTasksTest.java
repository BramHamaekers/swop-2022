package swop.Tests.UseCaseTests;

import org.junit.jupiter.api.Test;
import swop.Car.CarOrder;
import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.CarMechanicGenerator;
import swop.UI.TempUI;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
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

        setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
        
        List<Task> tasks = this.assem.getController().getAssembly().getWorkStations().get(0).getUncompletedTasks();
        
        assert tasks.size() == 2; // no tasks are finished on workstation

		ListIterator<String> output = continueUITest(String.format("b%n0%n0%n0%n0%n45%nCANCEL%nQUIT"), 7);// complete 1 task and cancel

        askWorkPost(output); // ask user for work post
        
        presentAvailableTasks(output, tasks); // shows 2 tasks can be finished

        showTaskInfo(output, tasks.get(0)); // system shows the assembly task information

        output.next();
        
        indicateTimePassed(output);
        
        tasks = this.assem.getController().getAssembly().getWorkStations().get(0).getUncompletedTasks();

        presentAvailableTasks(output, tasks); // shows 1 task can be finished
        
        tasks = this.assem.getController().getAssembly().getWorkStations().get(0).getUncompletedTasks();
        
        assert tasks.size() == 1; // 1 task is finished on workstation
    }

	@Test
    void completeAllTasksOfStationTest() {
		setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		
        List<Task> tasks = this.assem.getController().getAssembly().getWorkStations().get(0).getUncompletedTasks();
        
        assert tasks.size() == 2; // no tasks are finished on workstation

		ListIterator<String> output = continueUITest(String.format("b%n0%n0%n0%n0%n45%nCANCEL%nQUIT"), 12); // complete task on pos 0
		
		presentAvailableTasks(output, tasks);

		tasks = this.assem.getController().getAssembly().getWorkStations().get(0).getUncompletedTasks();
		
		assert tasks.size() == 1; // 1 task is finished on workstation
		
		output = continueUITest(String.format("b%n0%n0%n0%n0%n45%n0%nQUIT"), 12); // complete task on pos 0
		
		presentAvailableTasks(output, tasks);
		
		tasks = this.assem.getController().getAssembly().getWorkStations().get(0).getUncompletedTasks();
		
		assert tasks == null; 
		
		skip(output, 6);
		
		noMoreTasksMessage(output);	
		
	}

	@Test
    void completeAllTasksOfCarTest() {
		setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		continueUITest(String.format("b%n0%n0%n0%nENTER%n45%n0%nENTER%n45%n" + // complete tasks work post 1
				"b%n0%n1%n0%nENTER%n45%n0%nENTER%n45%n"+ // complete tasks work post 2
				"b%n0%n2%n0%n0%n45%n0%n0%n45%n0%n0%n45%n0%nQUIT"), 7); // complete tasks work post 3 
		GarageHolder a = (GarageHolder) this.assem.getUserMap().get("a"); 
		
		carIsFinished(a);

    }
	
	@Test
    void InvalidOptions() {
		setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order
		ListIterator<String> output = continueUITest(String.format("b%ninvalid%n0%ninvalid%n0%n10%n0%n0%n45%nCANCEL%nQUIT"), 7); // complete tasks work post 1
		invalidOptionMessage(output); //invalid 1
		skip(output, 4);
		invalidOptionMessage(output); //invalid 2
		
		List<Task> tasks = this.assem.getController().getAssembly().getWorkStations().get(0).getUncompletedTasks();
		
		assert tasks.size() == 1; // 1 task is finished on workstation
	}



	
	
	
	
	
	
	
	


	////////////////////////////////////////////////////////
	
	private void invalidOptionMessage(ListIterator<String> output) {
		assertTrue(output.next().contains("Please give valid input:"));
	}
	
	private void noMoreTasksMessage(ListIterator<String> output) {
		assertEquals("No tasks need to be completed!", output.next());
		
		
	}

    private void carIsFinished(GarageHolder a) {
		Set<CarOrder> orders = a.getOrders();
		assert orders.size() == 1;
		for(CarOrder order: orders)
			assertTrue(order.isCompleted());
		
	}

    private void presentAvailableTasks(ListIterator<String> output, List<Task> tasks) {
		DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateAvailableTasks(builder,tasks);
		for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());
    }

    private void indicateTimePassed(ListIterator<String> output) {
        assertEquals("How much time did it take to finish the task? (in min)", output.next());
    }
    
    private void indicateFinished(ListIterator<String> output) {
        assertEquals("Press enter when you are finished", output.next());
    }

    private void showTaskInfo(ListIterator<String> output, Task t) {
		DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateTaskInfo(builder, t.getDescription());
		for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());

    }

    private void askWorkPost(ListIterator<String> output) {
    	DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateStationList(builder, assem.getController().getAssembly().getWorkStations().stream().map(WorkStation::getName).toList());
		for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    private ListIterator<String> continueUITest(String inputString, int skips) { 
        return handleInputOutput.continueUITest(inputString, skips);
    }

    private ListIterator<String> setupUITest(String inputString, int skips) {
       	ListIterator<String> output = handleInputOutput.setupUITest(inputString, skips);
    	this.assem = handleInputOutput.getAssem(); 
    	this.carMechanic = (CarMechanic) handleInputOutput.getUser("b");
    	return output;
    }
    
    void skip(ListIterator<String> output, int skips) {
    	handleInputOutput.skip(output, skips);	
	}

}
