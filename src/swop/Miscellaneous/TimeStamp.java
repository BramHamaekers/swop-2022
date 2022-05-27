package swop.Miscellaneous;

import java.util.Objects;

/**
 * class created for easy store of a moment in time
 */
public class TimeStamp implements Comparable<TimeStamp> {

    /**
     * The day associated with this TimeStamp
     */
    private final int day;
    /**
     * The minutes associated with this TimeStamp
     */
    private final int minutes;

    /**
     * create a new time stamp
     * @param day is an integer of the day it occurred
     * @param minutes an integer of the exact minute
     */
    public TimeStamp(int day, int minutes)  {
        if (!isValidTimeStamp(day, minutes))
            throw new IllegalArgumentException("Timestamp not valid");
        this.day = day;
        this.minutes = minutes;
    }

    /**
     * get the current day of the program
     * @return this.day
     */
    public int getDay() {
        return this.day;
    }

    /**
     * get the current minutes of the program
     * @return this.minutes
     */
    public int getMinutes() {
        return this.minutes;
    }

    /**
     * Check is the given timestamp is valid
     * @param day the given day
     * @param minutes the given minutes
     * @return True if the timestamp consists of a positive day and minutes value
     */
    private boolean isValidTimeStamp(int day, int minutes) {
        return day > -1 && minutes > -1;
    }

    @Override
    public String toString() {
        int minutes = this.minutes;
        int hours = minutes / 60;
        hours += 6;
        minutes = minutes % 60;

        return String.format("day: %d, time: %02d:%02d", day, hours, minutes);
    }

    @Override
    public int compareTo(TimeStamp timeStamp) {
        if (timeStamp == null)
            throw new IllegalArgumentException("can't compare timestamp to null object");
        int day1 = this.day;
        int day2 = timeStamp.day;
        if (day1 != day2) return Integer.compare(day1, day2);
        return Integer.compare(this.minutes, timeStamp.minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeStamp timeStamp = (TimeStamp) o;
        return day == timeStamp.day && minutes == timeStamp.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, minutes);
    }
}
