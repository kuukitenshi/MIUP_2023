import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class UVa_10020_MinimalCoverage {


    private static class Pair implements Comparable<Pair>{
        int l, r;
        Pair(int l, int r) {
            this.l=l; this.r=r;
        }
        public String toString(){
            return "[" + l + "," + r + "]";
        }

        // eg: [0,2] < [0,1] < [1,2]
        @Override
        public int compareTo(Pair p2) {
            if (this.l>p2.l) return 1;
            if (this.l<p2.l) return -1;
            return this.r<p2.r ? 1 : -1;
        }
    }

    public static void main(String[] args) {

        if (!new Object(){}.getClass().getName().contains("Main"))
            try {   // redirect System.in and System.out to in/out text files
                System.setIn (new FileInputStream("data/uva10020.in.txt" ));
                System.setOut(new PrintStream("data/uva10020.out.txt") );
            } catch (Exception e) {}
        ///////////////////////////////////////////////////////////////

        try {

            Reader.init( System.in );

            int nCases = Reader.nextInt();
            while (nCases-- > 0) {

                int M = Reader.nextInt(); // defining coverage interval [0,M]

                List<Pair> available = new LinkedList<Pair>(); // list of available intervals
                List<Pair> coverage = new LinkedList<Pair>();  // list of minimal coverage intervals

                int l, r;  // read & keep pairs of available intervals until a 0 0 appears
                do {
                    l = Reader.nextInt();
                    r = Reader.nextInt();
                    if (l != 0 || r != 0)
                        available.add(new Pair(l, r));
                } while (l != 0 || r != 0);

                available.sort((p1, p2) -> p1.compareTo(p2));  // sort pairs for greedy algorithm

                // greedy algorithm
                int need_left = 0;                  // from where we still need to cover
                Pair current_best = new Pair(0, 0); // our best interval so far
                boolean have_current_best = false;  // have we a good interval?
                boolean not_feasible = false;       // is it unfeasible to cover [0,M]?

                for (Pair p : available) {
                    if (p.l <= need_left) {           // if p has good left value
                        if (p.r > current_best.r) {   //   and better right value that what we found so far
                            current_best = p;         // let's keep it
                            have_current_best = true; // & flag it!
                        }
                    } else {
                        if (have_current_best) {
                            // we want to keep this one, nothing after this is going to be better
                            // (due to the previous pair ordering)
                            coverage.add(current_best);
                            // update requirements
                            need_left = current_best.r; // we need to start looking from the current best right
                            have_current_best = false;  // and we'll restart looking for good candidates
                            // ...unless
                            if (need_left >= M)
                                break;  // then, it's job done!
                        }

                        // since we are looking for updated values, we need to recheck the current pair
                        if (p.l <= need_left) {
                            if (p.r > current_best.l) {
                                current_best = p;       // let's keep it
                                have_current_best = true;
                            }
                        } else {
                            // if the current's left value is higher that what we need, then we will 
                            // not be able to satisfy the coverage, and must announce failure!
                            not_feasible = true;
                            break;
                        }
                    }
                } // for

                if (have_current_best) {
                    coverage.add(current_best);
                    if (current_best.r < M)  // it might not be enough
                        not_feasible = true;
                }

                if (not_feasible) {
                    System.out.println(0 + "\n");
                } else {
                    System.out.println(coverage.size());
                    for(Pair p : coverage)
                        System.out.println(p.l + " " + p.r);
                    if (nCases>0)  // give extra newline except for the last case
                        System.out.println();
                }
            } // while

        } catch (Exception e) {};
    }
}