import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class UniqueCaps {

    static final int TOTAL_CAPS = 101;
    static final long MOD = 1_000_000_007;

    // define array caps where we keep who can wear cap i
    static List<Integer>[] caps;

    // In sols[i][j], i denotes the bitmask, ie, who are wearing caps
    // and j denotes the number of caps 1 to j used so far,
    // with the restriction that no one is wearing the same cap
    // There are up to 10 persons (2^10 = 1024) and 100 caps
    static long[][] sols;

    // init data structures
    static {
        caps = new List[TOTAL_CAPS];
        for(int i=1; i<TOTAL_CAPS; i++)
            caps[i] = new LinkedList<Integer>();

        sols = new long[1025][TOTAL_CAPS]; // let's start indexes at 1
        for(int i=0; i<1025; i++)
            for(int j=0; j<TOTAL_CAPS; j++)
                sols[i][j] = -1L;
    }

    static int allmask; // all are wearing caps, ie, all bits are set to 1

    // bitmask is the set of persons in consideration
    // the caps are already processed from 1 to i-1
    // complexity O(2^nPersons * TOTAL_CAPS)
    private static long solve(int bitmask, int i) {

        if (allmask == bitmask)  // all persons are using caps, we are done
            return 1;

        if (i>=TOTAL_CAPS) // not all are wearing caps, but no caps left
            return 0;      // so, no solution was found

        if (sols[bitmask][i] != -1)      // solution already found
            return sols[bitmask][i];

        // if we don't include this cap, how many solutions are there?
        long nSols = solve(bitmask, i+1);

        // assign cap i to every person that has it
        for(int person : caps[i]) {
            // if this person is already wearing a cap, go to next one
            if ((bitmask & (1 << (person-1))) != 0)
                continue;

            // otherwise, assign it, and solve for the remaining caps
            int newBitmask = bitmask | (1 << (person-1));
            nSols += solve(newBitmask, i+1);
            nSols %= MOD;
        }

        return sols[bitmask][i] = nSols;
    }

     public static void main(String[] args) {
        if (!new Object(){}.getClass().getName().contains("Main"))
            // if true: read from files; else: read from System.in
            try {
                System.setIn (new FileInputStream("data/uniquecaps.in.txt" ));
                System.setOut(new PrintStream("data/uniquecaps.out.txt") );
            } catch (Exception e) {}
        ///////////////////////

        Scanner sc = new Scanner(System.in);

        int nPersons = sc.nextInt();

        // read caps
        sc.nextLine(); // consume first newline
        for(int person=1; person<=nPersons; person++) {
            int[] caps_i = Arrays.stream(sc.nextLine().split(" "))
                                 .mapToInt(Integer::parseInt)
                                 .toArray();
            for(int cap : caps_i)
                caps[cap].add(person);
        }

        allmask  = (1<<nPersons) - 1; // set bits to 1
        System.out.println( solve(0,1) );

        sc.close();
    }

} // end class