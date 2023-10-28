import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UVa_624_CD {

    static final int NTA = -1;      // means No Track Available

    static int   N;                 // the tape capacity in minutes

    static int   n_tracks;          // the number of tracks at a given problem instance
    static int[] tracks_length;     // each track's length (in minutes)

    static int[] current;           // keeps the current chosen tracks
    static int   sum_current;       // and its sum
    static int   n_included_tracks; // keeps how many tracks are selected

    static int[] best;              // keeps the best solutions found so far
    static int   sum_best;          // and its sum

    private static void backtrack(int track) {
        if (track == n_tracks) // tested all tracks, end of backtracking
            return;

        if (sum_current + tracks_length[track] <= N) {
            // it's feasible to add it
            current[n_included_tracks++] = track;
            sum_current += tracks_length[track];

            backtrack(track+1);

            if (sum_current > sum_best) { // found a better one
                best = current.clone();
                sum_best = sum_current;
            }

            // now, remove track to test solution without it
            sum_current -= tracks_length[track];
            current[--n_included_tracks] = NTA;
        }

        backtrack(track+1);

        if (sum_current > sum_best) { // found a better one
            best = current.clone();
            sum_best = sum_current;
        }
    }

    public static void main(String[] args) {
        if (1>0)    // if true: read from files; else: read from System.in
            try {   // redirect System.in and System.out to in/out text files
                System.setIn (new FileInputStream("data/uva624.in.txt" ));
                System.setOut(new PrintStream("data/uva624.out.txt") );
            } catch (Exception e) {}
        ///////////////////////////////////////////////////////////////

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {              // for each line:

            // read input
            N = sc.nextInt();
            n_tracks = sc.nextInt();
            tracks_length = new int[n_tracks];
            for(int i = 0; i< n_tracks; i++)
                tracks_length[i] = sc.nextInt();

            // init variables used in backtrack
            current = new int[n_tracks];  // a solution may have all tracks available
            for(int i = 0; i< n_tracks; i++)
                current[i] = NTA;
            n_included_tracks = sum_best = sum_current = 0;

            backtrack(0);

            // print solution
            for(int i = 0; i< n_tracks && best[i]!=NTA; i++)
                System.out.print(tracks_length[best[i]] + " ");
            System.out.println("sum:" + sum_best);
        }
        sc.close();
    }
}