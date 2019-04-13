package INT02.Solution;

public class Time
{
    private int hour;
    private int minutes;

    public Time(int hour,int minutes)
    {
        this.hour=hour;
        this.minutes=minutes;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public void setHour(int hour)
    {
        this.hour = hour;
    }

    public void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }


    public String toString()
    {
        return this.getHour() + " " +this.getMinutes();
    }
}
