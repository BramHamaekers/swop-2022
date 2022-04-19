package swop.Tests.UseCaseTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Set;

import org.junit.jupiter.api.Test;

import swop.Car.CarOrder;
import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;

public class AdvanceAssemblyLineTest {
    AssemAssist assem;
    CarMechanic carMechanic;
    InputStream input;
    GarageHolder garageHolder;
    
    @Test
    void CompleteFullCarTest() {
    	 ListIterator<String> output = setupUITest(String.format(
				 "a%ny%n0%n1%n1%n1%n1%n1%n1%n1%n" + // place order
                 "c%ny%ny%n45%n%n" + // advance assemblyLine
                 "b%n0%n0%n%n0%n%n"+ // Perform assembly Tasks (Car enters first workstation + complete all tasks)
                 "c%ny%ny%n45%n%n"+ //advance assemblyLine (Should be OK since all tasks are completed)
                 "b%n1%n0%n%n0%n%n"+ // Perform assembly Tasks (Car enters second workstation + complete all tasks)
                 "c%ny%ny%n45%n%n" +//advance assemblyLine (Should be OK since all tasks are completed)
                 "b%n2%n0%n%n0%n%n0%n%n"+ // Perform assembly Tasks (Car enters third workstation + complete all tasks)
                 "c%ny%ny%n45%n%nQUIT")//advance assemblyLine (Should be OK since all tasks are completed)
         ); // Setup
    	 skipOutputLines(output,52);
    	 checkAdvanceCarInWorkStation(output,0); //checks car in given workStation
    	 skipOutputLines(output,48);
    	 checkAdvanceCarInWorkStation(output,1);
    	 skipOutputLines(output,48);
    	 checkAdvanceCarInWorkStation(output,2);
    	 checkCarIsCompleted(); //checks if the car has been fully assembled
    	
    }
    
    @Test
    void AdvanceAssemblyLineAlternateFlowTest() {
    	 ListIterator<String> output = setupUITest(String.format(
                 "a%ny%n0%n1%n1%n1%n1%n1%n1%n1%n" + // place order
                 "c%ny%ny%n45%n%n" + // advance assemblyLine
                 "b%n0%n0%n%nCANCEL%n" + // Perform only 1 Task and cancels
                 "c%ny%ny%n45%n%nQUIT")); //try advance but will fail since not all tasks were completed in Car Body Post
    	 skipOutputLines(output,52);
    	 checkAdvanceCarInWorkStation(output,0);
    	 skipOutputLines(output,44);
    	 showNotAllTasksCompletedMessage(output); //checks if user get's message that not all tasks are completed
    	 
    }

	private void showNotAllTasksCompletedMessage(ListIterator<String> output) {
		 assertEquals("Not all tasks completed in: Car Body Post", output.next());
		
	}

	private void checkCarIsCompleted() {
		Set<CarOrder> orders = garageHolder.getOrders(); //should contain 1 car
		assertEquals(orders.size(),1);
		for(CarOrder order: orders) assertTrue(order.isCompleted());
		
	}



	private void checkAdvanceCarInWorkStation(ListIterator<String> output, int i) {
		if(i == 0) {		
			 assertEquals("============ Advanced Assembly Line ============", output.next());
			 assertEquals("Car Body Post: {Airco=automatic climate control, GearBox=5 speed automatic, Wheels=sports (low profile), Color=blue, Body=break, Engine=performance 2.5l 6 cilinders, Seats=leather white} (PENDING)", output.next());
			 assertEquals("Drivetrain Post: EMPTY", output.next());
			 assertEquals("Accessories Post: EMPTY", output.next());
			 assertEquals("================================", output.next());
		}
		if(i == 1) {		
			 assertEquals("============ Advanced Assembly Line ============", output.next());
			 assertEquals("Car Body Post: EMPTY", output.next());
			 assertEquals("Drivetrain Post: {Airco=automatic climate control, GearBox=5 speed automatic, Wheels=sports (low profile), Color=blue, Body=break, Engine=performance 2.5l 6 cilinders, Seats=leather white} (PENDING)", output.next());
			 assertEquals("Accessories Post: EMPTY", output.next());
			 assertEquals("================================", output.next());
		}
		if(i == 2) {		
			 assertEquals("============ Advanced Assembly Line ============", output.next());
			 assertEquals("Car Body Post: EMPTY", output.next());
			 assertEquals("Drivetrain Post: EMPTY", output.next());
			 assertEquals("Accessories Post: {Airco=automatic climate control, GearBox=5 speed automatic, Wheels=sports (low profile), Color=blue, Body=break, Engine=performance 2.5l 6 cilinders, Seats=leather white} (PENDING)", output.next());
			 assertEquals("================================", output.next());
		}
	}
	
    private void skipOutputLines(ListIterator<String> output, int skips) {
   	 for (int i = 0; i < skips; i++) {
            output.next();
        }
		
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
		System.out.println(output);
        return output;
    }
	
}
