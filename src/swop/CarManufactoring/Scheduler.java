package swop.CarManufactoring;

public class Scheduler {

    private final CarManufactoringController controller;
    private int minutes;
    private int day;
    private int workingDayMinutes;

    public Scheduler(CarManufactoringController carManufactoringController) {

        this.controller = carManufactoringController;
        this.minutes = 0;
        this.workingDayMinutes = 960; // 06:00 -> 22:00
    }



    /**
     * Calculates the estimated completion time based on the CarQueue and overtime done on previous days
     *
     * @return Time formatted as string
     */
    public String getEstimatedCompletionTime() {
    	  int day = this.day;
          int minutes = this.minutes;
          int workingDayMinutes = this.workingDayMinutes;

          // not all tasks on assembly line are completed
          if (!this.controller.getAssembly().allTasksCompleted()) {
              minutes += 60;
          }

          // Assumses FCFS as scheduling algorithm
          for (int i = 0; i < this.controller.getCarQueuesize() - 1; i++) {
              minutes += 60;
          }

          // estimate 3 hours for completion of car
          //TODO: should actually check how many tasks are left and how long those take
          minutes += 180;

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
     * advances this.day by 1 and calculates the length of next day
     */
    public void advanceDay() {
        this.day += 1;  // Go to next day
        int overTime = this.getMinutes() - this.workingDayMinutes; // Calculate the amount of overtime this day
        this.workingDayMinutes = 960 - overTime;    // Calculate how long the shifts of next day will be
        this.minutes = 0; // Reset amount of minutes that have past this day
    }

    /**
     * Check if there is enough time today to add a new car to the assemblyLine
     * @return
     */
    public boolean canAddCarToAssemblyLine() {
        //System.out.println("day: "  + this.day);
        //System.out.println("minutes : " + this.minutes);
        //System.out.println("workingDayMinutes : " + this.workingDayMinutes);
        //TODO: assumes all cars take 3 hours to complete -> should work with part times
        return this.minutes <= this.workingDayMinutes - 180;
    }
}
