package swop.Miscellaneous;

import java.util.Objects;

public class TimeStamp implements Comparable<TimeStamp> {

    private final int day;
    private final int minutes;

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
