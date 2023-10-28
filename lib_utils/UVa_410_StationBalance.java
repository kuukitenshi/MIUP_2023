import java.util.Arrays;
import java.util.Scanner;

public class UVa_410_StationBalance {

    static double computeImbalance(int[] chambers) {

        double avg = 0.0;  // compute masses' average
        for(int i=0; i<chambers.length; i++)
            avg += chambers[i];
        avg /= chambers.length;

        double imbalance = 0.0;  // compute imbalance = \sum_i | chamber_i - avg |
        for(int i=0; i<chambers.length; i++)
            imbalance += Math.abs(chambers[i] - avg);
        return imbalance;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int count_set = 0;
        while (sc.hasNext()) {
            count_set++;

            // read data for each input set
            int C = sc.nextInt();
            int[] chambers = new int[C];
            int[] masses   = new int[2*C]; // already leaving the extra zeros

            int S = sc.nextInt();
            for(int i=0;i<S; i++)
                masses[i] = sc.nextInt();

            Arrays.sort(masses);

            // the greedy solution
            for(int i=0; i<masses.length/2; i++)
                chambers[i] = masses[i] + masses[masses.length-i-1];

            // print report
            if (count_set>1) System.out.println();
            System.out.println("Set #" + count_set);
            for(int i=0; i<C; i++) {
                System.out.print(" " + i + ":");
                if (masses[i] != 0)
                    System.out.print(" " + masses[i]);
                if (masses[masses.length-i-1] != 0)
                    System.out.print(" " + masses[masses.length-i-1]);
                System.out.println();
            }
            System.out.println(
                    String.format("IMBALANCE = %1.5f", computeImbalance(chambers)));
        }
        System.out.println();
        sc.close();
    }
}