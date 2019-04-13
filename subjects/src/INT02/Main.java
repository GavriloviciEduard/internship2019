package INT02;

import INT02.Solution.Atm;
import INT02.Solution.TSP_ATM;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main
{


    public static void main(String[] args)
    {

        int[][] graph = { { 0, 5, 60, 30, 45 },
                        { 5, 0, 40, 40, 45 },
                        { 60, 40, 0 , 15, 30 },
                        { 30, 40, 15, 0, 15 },
                        { 45, 45, 30 , 15 , 0 }
                      };

        //The approach I've taken is to generate all possible routes by Backtracking. And to update the best route if a better
        //time was found. The cards part is not relevant to finding thr best route because we always use the same cards and the
        //way we withdraw money does not affect our time.

        TSP_ATM tsp_atm = new TSP_ATM(graph,new ArrayList<>(Arrays.asList(
                        new Atm("A","0","0"),new Atm("ATM1","12","18"),new Atm("ATM2","10","17"),
                new Atm("ATM3","22","13"),new Atm("ATM4","17","01"))));


        List<Atm> solution = null;

        try
        {
            solution = new ArrayList<>(tsp_atm.getAtmsRoute());
            //The first best solution is returned which will be A->ATM1->ATM2->ATM3->ATM4. 8000 LEI will be withdrawn as following
            // ATM1 : 2000 - Platinum and 2000 - Silver (with the fee 1996) so we have 3996 LEI
            // ATM2 : 1000 - Platinum and 2000 - Silver (with the fee 1996) so we have 6992 LEI
            // ATM3 : 505,008 - Silver (with the fee 504) so we have 7496 LEI
            // ATM4 : 505,008 - Silver (with the fee 504) so we have 8000 LEI
        }

        catch (ParseException e)
        {
            e.printStackTrace();
        }

        Objects.requireNonNull(solution).forEach(atm->System.out.print(atm.toString()+" "));

    }


}