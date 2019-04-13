package INT02.Solution;

public class Atm
{
    private String name;
    private String open;
    private String close;

    public Atm(String name, String open, String close)
    {
        this.name = name;
        this.open = open;
        this.close = close;
    }

    public String getClose()
    {
        return close;
    }

    public String getOpen()
    {
        return open;
    }

    public  String getName()
    {
        return this.name;
    }

    public String toString()
    {
        return this.getName();
    }
}
