package swop.CarManufactoring;

public class Scheduler {

    private final AssemblyLine assemblyLine;
    private int minutes;
    private int day;
    private int workingDayMinutes;

    public Scheduler(AssemblyLine assemblyLine) {

        this.assemblyLine = assemblyLine;
        this.minutes = 0;
        this.workingDayMinutes = 960; // 06:00 -> 22:00
    }



    /**
     * Calculates the estimated completion time based on the CarQueue and overtime done on previous days
     *
     * @return Time formatted as string
     */
    public String getEstimatedCompletionTime() {

        int hoursPast = (getMinutes() / 60);
        hoursPast += (getMinutes() % 60 != 0) ? 1 : 0;

        int overTime = 0;
        int day = 0;
        int hour = 3 + hoursPast % 16; //TODO: Assumes no overtime is made on previous days

        for (int i = 0; i < this.assemblyLine.getCarQueue().size() - 1; i++) { // should look at cars that are ahead of it not just
            // in the carqueue
            hour += 1;
            if (hour > 16 - overTime + 2) {
                day += 1;
                hour = 3;
                overTime = 2;
            }
        }
        hour += 6;
        return String.format("day: %s, time: %s:00%n", day, hour);

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
        System.out.println("day: "  + this.day);
        System.out.println("minutes : " + this.minutes);
        System.out.println("workingDayMinutes : " + this.workingDayMinutes);
        //TODO: assumes all cars take 3 hours to complete
        return this.minutes <= this.workingDayMinutes - 180;
    }
}
