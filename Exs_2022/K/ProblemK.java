package K;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class ProblemK {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int handleCompNum = Integer.parseInt(sc.nextLine());
        List<String> airlines = new ArrayList<>();
        List<String> cities = Arrays.asList("Lisbon", "Porto", "Faro", "Funchal", "Beja");
        HashMap<Integer, Integer> count = new HashMap<>();

        for (int i = 0; i < handleCompNum; i++) {
            airlines.add(sc.next());
        }
        int numFlights = sc.nextInt();
        for (int i = 0; i < numFlights; i++) {
            String[] line = sc.nextLine().split(" ");
            if (airlines.contains(line[0])) {
                if (cities.contains(line[1]) || cities.contains(line[4])) {
                    int startDay = Integer.parseInt(line[2]);
                    int endDay = Integer.parseInt(line[5]);
                    if (startDay == endDay) {
                        if (!count.containsKey(startDay))
                            count.put(startDay, 1);
                        else {
                            count.put(startDay, count.get(startDay) + 1);
                        }
                    } else {
                        if (!count.containsKey(startDay))
                            count.put(startDay, 1);
                        else {
                            count.put(startDay, count.get(startDay) + 1);
                        }
                        if (!count.containsKey(endDay))
                            count.put(endDay, 1);
                        else {
                            count.put(endDay, count.get(endDay) + 1);
                        }
                    }
                }
            }
        }
        int maxDay = 1;
        if(!count.isEmpty())
            maxDay = Collections.max(count.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        System.out.println(maxDay);
        if(maxDay == 1)
            System.out.println(0);
        else
            System.out.println(count.get(maxDay));
        System.out.println("");
        sc.close(); 
    }  
}
