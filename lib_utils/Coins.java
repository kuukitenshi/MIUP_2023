import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Coins {

    static int[] coins = {200, 100, 50, 20, 10, 5, 2, 1};

    static int[] coinSolution(int amount) {
        List<Integer> sol = new LinkedList<Integer>(); // list used to keep selected coins

        int currentCoin = 0;
        while (amount>0)
            if (amount >= coins[currentCoin]) { // if we can still use current coin
                amount -= coins[currentCoin];
                sol.add(coins[currentCoin]);
            } else
                currentCoin++;                  // otherwise, go to the next smaller one

        return sol.stream().mapToInt(i -> i).toArray(); // convert list to int[]
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(coinSolution(16)));
    }
}