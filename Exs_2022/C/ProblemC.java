

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ProblemC {

    private static final String[] COLS = { "A", "B", "C", "D", "E", "F", "G", "H" };

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int numCases = sc.nextInt();
        for (int i = 0; i < numCases; i++) {
            int s = sc.nextInt();
            String start = sc.next();
            List<String> toVisit = new ArrayList<>();
            for(int k = 0; k < s; k++)
            {
                toVisit.add(sc.next());
            }
            // System.out.println(toVisit.toString());

            HashMap<String, Integer> result = new HashMap<>();
            int movesA = getMovesA(start, toVisit);
            // System.out.println(movesA);
            result.put("A", movesA);
            int movesC = getMovesC(start, toVisit);
            // System.out.println(movesA);
            result.put("C", movesC);
            int movesQ = getMovesQ(start, toVisit);
            // System.out.println(movesA);
            result.put("Q", movesQ);

            List<String> savePiece = new ArrayList<>();
            int saveMin = Integer.MAX_VALUE;
            for (String key : result.keySet()) {
                if (result.get(key) < saveMin) {
                    saveMin = result.get(key);
                    savePiece.add(key);
                } else if (result.get(key) == saveMin) {
                    savePiece.add(key);
                }
            }
            Collections.sort(savePiece);
            System.out.println(saveMin + " "+ String.join(", ", savePiece).replace(", ", ""));
        }
        sc.close();
    }

    public static int getMovesQ(String start, List<String> toVisit) {
        int count = 0;
        int sRow = Integer.parseInt("" + start.charAt(1));
        int sCol = Arrays.asList(COLS).indexOf("" + start.charAt(0));

        for (String visit : toVisit) {
            int row = Integer.parseInt((visit.charAt(1))+"");
            int col = Arrays.asList(COLS).indexOf((visit.charAt(0))+"");

            int rowdif = Math.abs(sRow - row);
            int coldif = Math.abs(sCol - col);
            if (rowdif == coldif)
                count += 1; // diag
            else if (rowdif == 0 || coldif == 0)
                count += 1; // hor ou vert
            else
                count += 2; //obriga a fazer 2 mov vert hor
            sRow = row;
            sCol = col;
        }
        return count;
    }

    public static int getMovesA(String start, List<String> toVisit) { 
        int count = 0;
        int sRow = Integer.parseInt("" + start.charAt(1));
        int sCol = Arrays.asList(COLS).indexOf("" + start.charAt(0));

        for (String visit : toVisit) {
            int row = Integer.parseInt((visit.charAt(1))+"");
            int col = Arrays.asList(COLS).indexOf((visit.charAt(0))+"");

            int rowdif = Math.abs(sRow - row);
            int coldif = Math.abs(sCol - col);
            if (rowdif == coldif)
                count += 1; // diag
            // else if (rowdif == 0 || coldif == 0)
            //     count += 1; // hor ou vert
            else if ((coldif == 1 && rowdif == 2) || (coldif == 2 && rowdif == 1))
                count += 1; // cavalo
            else
                count += 2;
            sRow = row;
            sCol = col;
        }
        return count;
    }

    public static int getMovesC(String start, List<String> toVisit) {
        int count = 0;
        int sRow = Integer.parseInt("" + start.charAt(1));
        int sCol = Arrays.asList(COLS).indexOf("" + start.charAt(0));

        for (String visit : toVisit) {
            int row = Integer.parseInt((visit.charAt(1))+"");
            int col = Arrays.asList(COLS).indexOf((visit.charAt(0))+"");

            int rowdif = Math.abs(sRow - row);
            int coldif = Math.abs(sCol - col);
            // if (rowdif == coldif)
            //     count += 1; // diag
            if (rowdif == 0 || coldif == 0)
                count += 1; // hor ou vert // torre
            else if ((coldif == 1 && rowdif == 2) || (coldif == 2 && rowdif == 1))
                count += 1; // cavalo
            else
                count += 2;
            sRow = row;
            sCol = col;
        }
        return count;
    }

}
