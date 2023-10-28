public class Queens {

    static final int SIZE = 4;  // board size

    // is it feasible to place the n-th queen there?
    public static boolean isFeasible(int[] queen_positions, int n) {

        for (int i=0; i<n; i++)
            if ((queen_positions[i] == queen_positions[n])           ||  // same row
                (queen_positions[i] - queen_positions[n]) == (n - i) ||  // same major diagonal
                (queen_positions[n] - queen_positions[i]) == (n - i))    // same minor diagonal
              return false;

        return true;
    }

    public static void backtrack(int[] queen_positions, int k) {

        if (k == SIZE) {
            showResult(queen_positions);
            return;
        }
    
        for (int i=0; i<SIZE; i++) {
            queen_positions[k] = i;
            if (isFeasible(queen_positions, k))
                backtrack(queen_positions, k+1);
        }
    }

    public static void showResult(int[] queen_positions) {
        for (int i = 0; i < SIZE; i++) {  // for each row
            for (int j = 0; j < SIZE; j++)  // for each column
                System.out.print(queen_positions[i] == j ? "Q ": ". ");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        backtrack(new int[SIZE], 0);
    }
}