package swop.Tests.UseCaseTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

import swop.CarManufactoring.WorkStation;
import swop.Main.AssemAssist;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.CarMechanicGenerator;

public class CheckAssemblyLineStatusTest {
    AssemAssist assem;
    //CarMechanic carMechanic;
    InputStream input;
    private static final CarMechanicGenerator carMechanicGenerator = new CarMechanicGenerator(); 
    
    @Test
    void checkAssemblyLineStatusAllEmptyTest(){   	
    	ListIterator<String> output = setupUITest(String.format("b%n1%nQUIT"), 7);
    	
    	presentAssemblyLineAllEmptyStatus(output);
    }
    
    
    @Test
    void allStatesOneWorkStationTest(){ 
    	setupUITest(String.format("a%n0%n0%n1%n1%n1%n1%n1%n1%n1%nQUIT"), 1);// place order

		ListIterator<String> output = continueUITest(String.format("b%n1%nQUIT"), 7); // check status
    	
    	pendingInWorkstationStatus(output, 0, true); // has pending (2) tasks on ws 1
    	
    	output = continueUITest(String.format("b%n1%nQUIT"), 7); // check status
    	
    	completedInWorkstationStatus(output, 0, false); // has no completed tasks on ws 1
    	
    	continueUITest(String.format("b%n0%n0%n0%n0%n45%nCANCEL%nQUIT"), 1); // complete task on pos 0, ws 1
    	
    	output = continueUITest(String.format("b%n1%nQUIT"), 7); // check status
    	
    	completedInWorkstationStatus(output, 0, true); // has completed (1) tasks on ws 1
    	
    	output = continueUITest(String.format("b%n1%nQUIT"), 7); // check status
    	
    	pendingInWorkstationStatus(output, 0, true); // has pending (1) tasks on ws 1
    	
    	continueUITest(String.format("b%n0%n0%n0%n0%n45%nCANCEL%nQUIT"), 1); // complete task on pos 0, ws 1
    	
    	output = continueUITest(String.format("b%n1%nQUIT"), 7); // check status
    	
    	emptyWorkStation(output, 0, true); // Automatic Advance: workstation is empty
    	emptyWorkStation(output, 1, false); // not empty
    	emptyWorkStation(output, 2, true); // empty
    	
    }

	//////////////////////////////////////////////////////////////////


    private void emptyWorkStation(ListIterator<String> output, int i, boolean b) {
    	WorkStation w = this.assem.getController().getAssembly().getWorkStations().get(i);
    	if(b) {
			assertTrue(w.getCompletedTasks().isEmpty());
			assertTrue(w.getUncompletedTasks().isEmpty());
    	}
    	else {
    		assert w.getCompletedTasks() != null || w.getUncompletedTasks() != null || !w.getUncompletedTasks().isEmpty() || !w.getCompletedTasks().isEmpty();
    	}
	}

	private void presentAssemblyLineAllEmptyStatus(ListIterator<String> output) {
		DisplayStatus builder = new DisplayStatus();
		List<WorkStation> workstations = this.assem.getController().getAssembly().getWorkStations();
		for(WorkStation w:  workstations) {
			carMechanicGenerator.generateWorkStationStatus(builder, w.getName(), w.getUncompletedTasks(), w.getCompletedTasks());
			assertTrue(w.getCompletedTasks().isEmpty());
			assertTrue(w.getUncompletedTasks().isEmpty());
		}
		for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());

	}

    private void pendingInWorkstationStatus(ListIterator<String> output, int station, boolean b) {
    	DisplayStatus builder = new DisplayStatus();
    	List<WorkStation> workstations = this.assem.getController().getAssembly().getWorkStations();
    	int i = 0;
    	for(WorkStation w:  workstations) {
    		carMechanicGenerator.generateWorkStationStatus(builder, w.getName(), w.getUncompletedTasks(), w.getCompletedTasks());
    		if (i == station) {
    			if(w.getUncompletedTasks() != null) assert w.getUncompletedTasks().isEmpty() != b;
    			else assertFalse(b);
    		}
    		else {
    			assert w.getCompletedTasks() == null || w.getCompletedTasks().isEmpty();
        		assert w.getUncompletedTasks() == null || w.getCompletedTasks().isEmpty();
    		}
    		i++;
    	}
		for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());

	}

    private void completedInWorkstationStatus(ListIterator<String> output, int station, boolean b) {
    	DisplayStatus builder = new DisplayStatus();
    	List<WorkStation> workstations = this.assem.getController().getAssembly().getWorkStations();
    	int i = 0;
    	for(WorkStation w:  workstations) {
    		carMechanicGenerator.generateWorkStationStatus(builder, w.getName(), w.getUncompletedTasks(), w.getCompletedTasks());
    		if (i == station) {
    			if(w.getUncompletedTasks() != null) assert w.getCompletedTasks().isEmpty() != b;
    			else assertFalse(b);
    		}
    		else {
    			assert w.getCompletedTasks() == null || w.getCompletedTasks().isEmpty();
        		assert w.getUncompletedTasks() == null || w.getCompletedTasks().isEmpty();
    		}
    		i++;
    	}
		for (String s : builder.getDisplay().split(String.format("%n"))) assertEquals(s, output.next());
		
	}

    
///////////////////////////////////////////////////////



    private ListIterator<String> continueUITest(String inputString, int skips) { 
        return handleInputOutput.continueUITest(inputString, skips);
    }

    private ListIterator<String> setupUITest(String inputString, int skips) {
       	ListIterator<String> output = handleInputOutput.setupUITest(inputString, skips);
    	this.assem = handleInputOutput.getAssem();  
    	return output;
    }
    
    void skip(ListIterator<String> output, int skips) {
    	handleInputOutput.skip(output, skips);	
	}
}





