package swop.CarManufactoring;

public class Scheduler {

    private final CarManufacturingController controller;
    private int minutes;
    private int day;
    private int workingDayMinutes;
    private int timePerWorkstation = 60; // TODO: Map for all models
    private String schedulingAlgorithm;

    public Scheduler(CarManufacturingController carManufacturingController) {

        this.controller = carManufacturingController;
        this.minutes = 0;
        this.workingDayMinutes = 960; // 06:00 -> 22:00
        setSchedulingAlgorithm("FIFO");
    }

    /**
     * Calculates the estimated completion time based on the CarQueue and overtime done on previous days
     *
     * @return Time formatted as string
     */
    public String getEstimatedCompletionTime(Car car) {
    	  int day = this.day;
          int minutes = this.minutes;
          int workingDayMinutes = this.workingDayMinutes;

          System.out.println(day);
          System.out.println(minutes);



        // 70 + 70

        // QUEUE   ->     w1      w2      w3
        // 60,50,70       [60]      70      50 -> 0
        //60, 50            70      [60]    70 ->
        // 60              50       70      [60]
        //

        // 60,50,70       [60]      null      null -> 0


        // if car in queue:
        // decide with scheduling algorithm how many cars will be before it
        // Add time per workstation for every car before it
        if (this.controller.getCarQueue().contains(car)) {
            // TODO: Make use of iterator based on scheduling algorithm
            // TODO timePerWorkstation based on wich car it is
            minutes += this.controller.getCarQueue().indexOf(car) * timePerWorkstation;
            minutes += 3 * timePerWorkstation;

        }

        // else if on workstation:
        // check on which workstation
        // TODO max of orders on assembly + future orders of assembly based on scheduling algorithm
        if (this.controller.getAssembly().getWorkStations().get(0).getCar() == car) {
            minutes += 3 * timePerWorkstation;
        }
        if (this.controller.getAssembly().getWorkStations().get(1).getCar() == car) {
            minutes += 2 * timePerWorkstation;
        }
        if (this.controller.getAssembly().getWorkStations().get(2).getCar() == car) {
            minutes += timePerWorkstation;
        }



          // assume no overtime should be made: assignment -> scheduler should minimize overtime
          // scheduling a car to make overtime to complete should not be allowed
          while (minutes > workingDayMinutes) {
              day += 1;
              minutes -= workingDayMinutes;
              workingDayMinutes = 960;
          }

          if (day != this.day) {
              if (minutes < 180) {minutes = 180;} // First car of the new day
              else {minutes = (int) (Math.ceil( (float) minutes/60) * 60);} // Other cars

          }
          // Convert to format
          int hours = minutes / 60;
          hours += 6;
          minutes = minutes % 60;

          return String.format("day: %s, time: %02d:%02d%n", day, hours, minutes);
    }

    /**
     * Add time in minutes to this.minutes
     *
     * @param minutes Minutes to add to this.minutes
     */
    public void addTime(int minutes) {
        this.minutes = this.getMinutes() + minutes;
    }

    /**
     * Get amount of minutes that have already past in the day
     * @return this.day
     */
    public int getMinutes() {
        return minutes;
    }

    public int getDay() {
        return this.day;
    }

    /**
     * advances day by 1 and calculates the length of next day
     */
    public void advanceDay() {
        this.day += 1;  // Go to next day
        int overTime = this.getMinutes() - this.workingDayMinutes; // Calculate the amount of overtime this day
        this.workingDayMinutes = 960 - overTime;    // Calculate how long the shifts of next day will be
        this.minutes = 0; // Reset amount of minutes that have past this day
    }

    /**
     * Check if there is enough time today to add a new car to the assemblyLine
     * @return this.minutes <= this.workingDayMinutes - 3 * this.timePerWorkstation
     */
    public boolean canAddCarToAssemblyLine() {
        //TODO: assumes all cars take 3 hours to complete -> should work with part times
        return this.minutes <= this.workingDayMinutes - 3 * this.timePerWorkstation;
    }

    /**
     * Returns the current schedulingAlgorithm
     * @return this.schedulingAlgorithm
     */
    private String getSchedulingAlgorithm() {
        return schedulingAlgorithm;
    }

    /**
     * set the currenct schedulingAlgorithm to the new given algorithms
     * @param schedulingAlgorithm
     */
    public void setSchedulingAlgorithm(String schedulingAlgorithm) {
        if (!isValidSchedulingAlgorithm(schedulingAlgorithm)) {
            throw new IllegalArgumentException();
        }
        this.schedulingAlgorithm = schedulingAlgorithm;
    }

    /**
     * Checks if the given schedulingAlgorithm is a valid algorithm
     * @param schedulingAlgorithm
     * @return True if schedulingAlgorithm == "FIFO" || "BATCH"
     */
    private boolean isValidSchedulingAlgorithm(String schedulingAlgorithm) {
        return schedulingAlgorithm.equals("FIFO") || schedulingAlgorithm.equals("BATCH");
    }

    /**
     * Returns the car that is scheduled to be the next car on the assemblyLine
     * @return this.carQueue.removeFirst()
     */
    public Car getNextScheduledCar() {
        if (this.getSchedulingAlgorithm().equals("FIFO")) {
            return this.controller.getCarQueue().get(0);
        }
        else throw new IllegalStateException();
    }
}
