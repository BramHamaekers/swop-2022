package swop.Tests.UnitTests.CarManufactoring;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import swop.Car.Car;
import swop.Car.CarModelSpecification;
import swop.Car.CarOrder;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModel.ModelB;
import swop.Car.CarModel.ModelC;
import swop.CarManufactoring.CarManufacturingController;
import swop.CarManufactoring.Scheduler;
import swop.Miscellaneous.TimeStamp;

public class SchedulerTest {

	Scheduler scheduler;
	CarManufacturingController carManufacturingController;
	 Map<String, String> validOptionsModelA = Map.ofEntries(
             entry("Body", "sedan"),
             entry("Color", "red"),
             entry("Engine", "standard 2l v4"),
             entry("Gearbox", "6 speed manual"),
             entry("Seats", "leather white"),
             entry("Airco", "manual"),
             entry("Wheels", "winter")
     );
	 Map<String, String> validOptionsModelB = Map.ofEntries(
             entry("Body", "sedan"),
             entry("Color", "red"),
             entry("Engine", "standard 2l v4"),
             entry("Gearbox", "6 speed manual"),
             entry("Seats", "leather white"),
             entry("Airco", "manual"),
             entry("Wheels", "winter"),
             entry("Spoiler", "low")
     );
	 Map<String, String> validOptionsModelC = Map.ofEntries(
             entry("Body", "sport"),
             entry("Color", "black"),
             entry("Engine", "performance 2.5l v6"),
             entry("Gearbox", "6 speed manual"),
             entry("Seats", "leather white"),
             entry("Airco", "manual"),
             entry("Wheels", "winter"),
             entry("Spoiler", "low")
     );
	
	@Test
	void intitiateNewSchedular() {
		refresh();
		assertEquals(960,this.scheduler.getWorkingDayMinutes());
		assertEquals(0,this.scheduler.getMinutes());
		assertEquals(0,this.scheduler.getDay());
		assertTrue(this.scheduler.canAddCarToAssemblyLine(0));	
		assertNull(this.scheduler.getNextScheduledCar());
		assertTrue(this.scheduler.getTime().getDay() == this.scheduler.getDay() 
				&& this.scheduler.getTime().getMinutes() == this.scheduler.getMinutes());
		assertEquals("FIFO",this.scheduler.getSchedulingAlgorithm());
	}
	
	@Test 
	void addTime(){
		refresh();
		this.scheduler.addTime(50);
		assertEquals(50,this.scheduler.getMinutes());
		assertEquals(0,this.scheduler.getDay());
		assertTrue(this.scheduler.getTime().getDay() == this.scheduler.getDay() 
				&& this.scheduler.getTime().getMinutes() == this.scheduler.getMinutes());
		assertThrows(IllegalArgumentException.class, () -> this.scheduler.addTime(-50));
	}
	
	@Test 
	void advanceDay(){
		refresh();
		this.scheduler.addTime(950);
		assertEquals(950,this.scheduler.getMinutes());
		assertEquals(0,this.scheduler.getDay());
		this.scheduler.advanceDay();
		assertEquals(0,this.scheduler.getMinutes());
		assertEquals(1,this.scheduler.getDay());
		assertEquals(960,this.scheduler.getWorkingDayMinutes());
		this.scheduler.addTime(960+460);
		this.scheduler.advanceDay();
		assertEquals(2,this.scheduler.getDay());
		assertEquals(500,this.scheduler.getWorkingDayMinutes());
		this.scheduler.addTime(500+960+10);
		this.scheduler.advanceDay();
		//should skip a day + 10 min less work on day 4 due to overtime
		assertEquals(4,this.scheduler.getDay());
		assertEquals(950,this.scheduler.getWorkingDayMinutes());	
	}
	
	@Test 
	void canAddToAssembly(){
		refresh();
		assertTrue(this.scheduler.canAddCarToAssemblyLine(0));
		assertThrows(IllegalArgumentException.class, () -> this.scheduler.canAddCarToAssemblyLine(-50));
		assertFalse(this.scheduler.canAddCarToAssemblyLine(900));
		this.scheduler.addTime(500);
		assertTrue(this.scheduler.canAddCarToAssemblyLine(0));
		this.scheduler.addTime(460-150);
		//should be true cause there is no car on line/in queue
		//so minimum est. time completing a car will be checked which is 150
		assertTrue(this.scheduler.canAddCarToAssemblyLine(0));
		this.scheduler.addTime(1);
		assertFalse(this.scheduler.canAddCarToAssemblyLine(0));
		this.scheduler.advanceDay();
		assertTrue(this.scheduler.canAddCarToAssemblyLine(0));
		this.scheduler.addTime(960+900);
		assertFalse(this.scheduler.canAddCarToAssemblyLine(0));
		this.scheduler.advanceDay();
		//should also be false since there are only 60 minutes left due to overtime
		assertFalse(this.scheduler.canAddCarToAssemblyLine(0));
		this.scheduler.advanceDay();
		assertTrue(this.scheduler.canAddCarToAssemblyLine(0));
		this.carManufacturingController.addOrderToQueue(createOrder("c"));
		this.scheduler.addTime(960-150);
		//this is not enough now since next car is of type c (takes 180 minutes to complete)
		assertFalse(this.scheduler.canAddCarToAssemblyLine(0));
		this.scheduler.advanceDay();
		this.scheduler.addTime(960-180);
		assertTrue(this.scheduler.canAddCarToAssemblyLine(0));
	}
	
	@Test 
	void estimatedCompletionTime(){
		refresh();
		CarOrder order = createOrder("a");
		TimeStamp stamp = new TimeStamp(0,150);
		this.carManufacturingController.addOrderToQueue(order);
		//should be 0 since first car gets auto pushed on assembly line
		assertEquals(this.carManufacturingController.getCarQueue().size(),0);
		TimeStamp carstamp = this.scheduler.getEstimatedCompletionTime(order.getCar());
		assertEquals(stamp.getDay(),carstamp.getDay());
		assertEquals(stamp.getMinutes(),carstamp.getMinutes());
		order = createOrder("c");
		//first 50 from car 1
		stamp = new TimeStamp(0,50+60+60+60);
		this.carManufacturingController.addOrderToQueue(order);
		assertEquals(this.carManufacturingController.getCarQueue().size(),1);
		carstamp = this.scheduler.getEstimatedCompletionTime(order.getCar());
		assertEquals(stamp.getDay(),carstamp.getDay());
		assertEquals(stamp.getMinutes(),carstamp.getMinutes());
		order = createOrder("c");
		
		
		refresh();
		this.scheduler.addTime(900);	
		order = createOrder("a");
		//car moved to next day since there isn't enough time to finish it today
		stamp = new TimeStamp(1,150);
		assertEquals(900,this.scheduler.getMinutes());
		assertFalse(this.scheduler.canAddCarToAssemblyLine(0));
		this.carManufacturingController.addOrderToQueue(order);
		//should auto advanced to next day since there isn't enough time to finish it today
		assertEquals(0,this.scheduler.getMinutes());
		assertEquals(this.scheduler.getDay(),1);
		assertEquals(this.carManufacturingController.getCarQueue().size(),0);
		carstamp = this.scheduler.getEstimatedCompletionTime(order.getCar());
		assertEquals(stamp.getDay(),carstamp.getDay());
		assertEquals(stamp.getMinutes(),carstamp.getMinutes());
	}
	
	@Test 
	void schedulingAlgorithm(){
		refresh();
		CarOrder order = createOrder("a");
		this.carManufacturingController.addOrderToQueue(order);
		order = createOrder("a");
		this.carManufacturingController.addOrderToQueue(order);
		order = createOrder("c");
		this.carManufacturingController.addOrderToQueue(order);
		order = createOrder("c");
		this.carManufacturingController.addOrderToQueue(order);
		order = createOrder("c");
		this.carManufacturingController.addOrderToQueue(order);
		
		assertEquals("FIFO",this.scheduler.getSchedulingAlgorithm());
		Car firstCarFifo = this.scheduler.getNextScheduledCar();
		assertThrows(IllegalArgumentException.class, () -> this.scheduler.setSchedulingAlgorithm("test", null));
		Map<String, String> batchOption = Map.ofEntries(
	             entry("Spoiler", "low")
	     );
		this.scheduler.setSchedulingAlgorithm("BATCH", batchOption);
		Car firstCarBatch = this.scheduler.getNextScheduledCar();
		assertFalse(firstCarFifo.equals(firstCarBatch));
		assertEquals(firstCarFifo.getCarModel().getClass(), ModelA.class);
		assertEquals(firstCarBatch.getCarModel().getClass(), ModelC.class);	
	}
	
	
	
	
	
	
	CarOrder createOrder(String model) {
		if(model.equals("a")) {
			CarModel m = new ModelA();
			CarModelSpecification specification = new CarModelSpecification(this.validOptionsModelA);
		    m.setCarModelSpecification(specification);  
		    return new CarOrder(m);
		}
		if(model.equals("b")) {
			CarModel m = new ModelB();
			CarModelSpecification specification = new CarModelSpecification(this.validOptionsModelB);
			m.setCarModelSpecification(specification);
			return new CarOrder(m);
		}
		if(model.equals("c")) {
			CarModel m = new ModelC();
			CarModelSpecification specification = new CarModelSpecification(this.validOptionsModelC);
			m.setCarModelSpecification(specification);
			return new CarOrder(m);
		}
		return null;
		
			
	}
	
	
	
	
	
	
	void refresh() {
		carManufacturingController = new CarManufacturingController();
		scheduler = this.carManufacturingController.getScheduler();
	}
	
	
	 
}
