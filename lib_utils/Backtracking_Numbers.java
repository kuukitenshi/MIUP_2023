import java.io.*;
import java.util.*;

public class Backtracking_Numbers {
    
    public static void main(String[] args) {

        if (!new Object(){}.getClass().getName().contains("Main"))    
            try {   // redirect System.in and System.out to in/out text files
                System.setIn (new FileInputStream("data/backtract_sums.in.txt" ));
                System.setOut(new     PrintStream("data/backtract_sums.out.txt") );
            } catch (Exception e) {}        
        ///////////////////////////////////////////////////////////////
        
        Scanner sc = new Scanner(System.in);
        
        while (true) {  
            int t = sc.nextInt();
            int n = sc.nextInt();
            
            if (t==0 && n==0)
                break;
            
            int[] xs = new int[n];
            for(int i=0; i<n; i++)
                xs[i] = sc.nextInt();

            // print header
            System.out.println("Sums of " + t + ":");
            
            // backtrack all possible sums of t (cf method's javadoc for details)
            solve(t, xs, 0, new int[xs.length], 0, 0);
        }
        
        sc.close();
    }

    /**
     * Backtracking method
     * @param t           the total (does not change)
     * @param xs          all the input options (does not change)
     * @param currentIdx  what is the index of the current option being considered
     * @param saved       the numbers we saved so far, for this backtracking path
     * @param nSaved      how many numbers are saved
     * @param currentSum  the current sum of the saved numbers (faster to keep this sum)
     */
    private static void solve(int t,
                              int[] xs, int currentIdx,
                              int[] saved, int nSaved, int currentSum) {
        if (t == currentSum)
            printSolution(saved, nSaved);

        // no more options to consider, backtrack
        if (currentIdx == xs.length)
            return;
        
        for(int i=currentIdx; i<xs.length; i++) {
            // the sum with this number would be bigger than t, skip it
            if (xs[i]+currentSum > t)
                continue;
            
            saved[nSaved++] = xs[i];
            solve(t, xs, i+1, saved, nSaved, currentSum+xs[i]);
            saved[--nSaved] = 0;
        }
    }

    private static void printSolution(int[] saved, int nSaved) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<nSaved-1; i++)
          sb.append(saved[i]).append("+");    
        sb.append(saved[nSaved-1]);
        System.out.println(sb);
    }
}