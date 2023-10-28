package J;
import java.util.HashMap;
import java.util.Scanner;

public class ProblemJ {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Integer> map = new HashMap<>();

        int numDays = sc.nextInt();
        for(int i = 0; i < numDays; i++){
            map.put(i, sc.nextInt());
        }
        int numOps = sc.nextInt();
        for(int i = 0; i < numOps; i++){
            int typeOp = sc.nextInt();
            if(typeOp == 1){
                int start = sc.nextInt();
                int end = sc.nextInt();
                int temp = sc.nextInt();
                int count = 0;
                for(int j = start-1; j < end; j++){
                    int dayTemp = map.get(j);
                    if(dayTemp > temp)
                        count++;
                }
                System.out.println(count);
            }else {
                int day = sc.nextInt();
                int temp = sc.nextInt();
                map.put(day-1, temp);
            }
        }
        sc.close();
    }
}