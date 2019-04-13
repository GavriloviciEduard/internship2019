package INT02.Solution;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TSP_ATM
{
    private int[][] route ; // the given graph
    private int[] solution ; // the solution array
    private int[] waiting_time_per_node; // array for saving how much time we wait for an atm to open
    private Time time ; // generic variable for time
    private List<Atm> final_route; // the final route
    private List<Atm> atms = new ArrayList<>(); // a list with all the atms
    private int total_time; // total time for each solution
    private int best_time; // best time for the best solution
    private Time last_time; // updating time for Backtracking


    /**
     * Constructor for the solution Class
     *
     * @param graph - the graph from which will search the solution
     * @param atms - a list with all the atms
     *
     **/
    public TSP_ATM(int[][] graph,ArrayList<Atm> atms)
    {
        this.route = graph;
        this.solution = new int[6];
        this.waiting_time_per_node = new int[6];
        int date = 19;
        this.time = new Time(11,30);
        this.final_route = new ArrayList<>();
        this.atms.addAll(atms);
        this.total_time = 0;
        this.best_time = 1000;
        this.last_time =null;
    }


    /**
     * The method that returns the best route
     *
     **/
    public List<Atm> getAtmsRoute() throws ParseException
    {
        Bkt(); // Backtracking for finding best solution

        return this.final_route;
    }



    /**
     * The method checks if the current constructed solution is valid
     *
     * @param k - the current position in the solution array
     * @return boolean - true if the current solution is valid false otherwise
     **/
    private boolean Valid(int k) throws ParseException
    {


        if(this.route[solution[k]][solution[k-1]]==0)//Check if the vertex exists
            return false;

        if(k==5 && solution[k]==1)//If at end
            return true;

        for(int i=0;i<k-1;i++)
            if(solution[k]==solution[i])//Check for duplicates
                return false;

            String open = this.atms.get(this.solution[k]).getOpen() +":"+"00"+":"+"00";
            String close = this.atms.get(this.solution[k]).getClose() +":"+"00"+":"+"00";
            String time;

            String time_temp = Integer.toString(this.time.getHour());

            if (time_temp.length()==1)
                time = "0"+time_temp+":"+"00"+":"+"00";
            else
                time=time_temp+":"+"00"+":"+"00";


            //wait for atm to open
            while (!open(open,time,close,k))
            {

                wait_atm(k);

                time_temp = Integer.toString(this.time.getHour());

                if (time_temp.length()==1)
                    time = "0"+time_temp+":"+"00"+":"+"00";
                else
                    time=time_temp+":"+"00"+":"+"00";
            }

        this.last_time = new Time(this.time.getHour(),this.time.getMinutes()); //update time

        return true;
    }


    /**
     * The method simulates a waiting until a given atm is opened
     *
     * @param k - the current position in the solution array
     **/
    private void wait_atm(int k)
    {
        this.waiting_time_per_node[k]+=5;//update time for the current atm
        this.time.setMinutes(this.time.getMinutes()+5);//update the current time
        update_minutes();
    }


    /**
     * The method checks if an atm is open
     *
     * @param initialTime - open time for an atm
     * @param currentTime - the current time
     * @param finalTime - close time for an atm
     * @return boolean - true if the atm is open false otherwise
     **/
    private boolean open(String initialTime,String currentTime,String finalTime,int k) throws ParseException
    {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        boolean valid=false;

        if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg))
        {
            //Start Time
            //all times are from java.util.Date
            Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);

            //Current Time
            Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);

            //End Time
            Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);

           if (this.solution[k]==3)
            {
                calendar2.add(Calendar.DATE, 1);

                if(calendar3.getTime().compareTo(new SimpleDateFormat("HH:mm:ss").parse("13:00:00"))<0)
                calendar3.add(Calendar.DATE, 1);
            }

           else if (this.solution[k]==4)
               calendar2.add(Calendar.DATE,1);

            java.util.Date actualTime = calendar3.getTime();

            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0) && actualTime.before(calendar2.getTime()))
            {
                valid = true;
            }
        }

        return valid;
    }

    /**
     * The method checks if the current constructed array is a possible final solution
     *
     * @param k - the current position in the solution array
     * @return boolean - true if the current solution is complete, false otherwise
     **/
    private boolean Solution(int k)
    {
        return (k==4);
    }


    /**
     * The method calculates the total time for a solution and updates the final solution if a better time is found
     *
     **/
    private void Calculate_Time()
    {
        this.total_time=0;
        int sum =0;

        for (int i=1 ;i<this.waiting_time_per_node.length; i++)//add the waiting time at the total time
        {
            this.total_time += this.waiting_time_per_node[i];
        }

        int j;

        for (j=0 ;j < 5; j++ )//compute the total time for the route
        {
            total_time+=this.route[solution[j]][solution[j+1]];
            sum+=this.route[solution[j]][solution[j+1]];


        }

        total_time-=this.route[solution[j]][solution[j-1]];
        sum-=this.route[solution[j]][solution[j-1]];


        if(total_time<best_time) // if a better time was find calculate new solution
        {

            best_time=total_time;
            this.final_route.clear();
            this.final_route.add(this.atms.get(0));


            for(int i = 1 ; i < this.solution.length-1;i++)
            {
                switch (solution[i])
                {

                    case 1 :
                        this.final_route.add(this.atms.get(1));
                        break;

                    case 2 :
                        this.final_route.add(this.atms.get(2));
                        break;

                    case 3 :
                        this.final_route.add(this.atms.get(3));
                        break;

                    case 4 :
                        this.final_route.add(this.atms.get(4));
                        break;
                }
            }
        }

        this.last_time = null;
        this.total_time-=sum;//update total time for Backtracking

    }

    /**
     * The Backtracking method
     *
     **/
    private void Bkt() throws ParseException
    {
        int k;

        this.solution[0]=0;//start position, always the same
        this.waiting_time_per_node[0]=0;//starting node, no waiting time ever
        k=1;

        while(k>0)
        {
            while(solution[k]<4)
            {

                this.solution[k]++;

                update_time(k);

                if(Valid(k))
                {

                    if(Solution(k))
                        Calculate_Time();
                    else
                        k++;
                }
            }


            this.total_time-=this.waiting_time_per_node[k];//update the total time
            this.waiting_time_per_node[k]=0;
            this.solution[k--]=0;


            this.total_time-=this.waiting_time_per_node[k];
            this.waiting_time_per_node[k]=0;

        }
    }

    /**
     * The method updates the current time
     *
     * @param k - the current position in the solution array
     **/
    private void update_time(int k)
    {
        if(this.last_time==null)
        {
            this.time = new Time(11,30);

            for(int i=1;i<=k;i++)
            {
                this.time.setMinutes(this.time.getMinutes()+this.route[solution[i-1]][solution[i]]);
                update_minutes();

            }

            for (int i1 : this.waiting_time_per_node)
            {
                this.time.setMinutes(this.time.getMinutes()+i1);
                update_minutes();
            }

        }

        else
        {
            this.time = new Time(this.last_time.getHour(),this.last_time.getMinutes());
            this.time.setMinutes(this.time.getMinutes()+this.route[solution[k-1]][solution[k]]);
            update_minutes();
        }

    }

    /**
     * The method updates the time in a correct format
     *
     **/
    private void update_minutes()
    {
        while (this.time.getMinutes()>=60)
        {
            this.time.setHour(this.time.getHour()+1);
            this.time.setMinutes(this.time.getMinutes()-60);

            if(this.time.getHour()>23)
                this.time.setHour(0);
        }
    }


}
