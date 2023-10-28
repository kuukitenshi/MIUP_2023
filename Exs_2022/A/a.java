package aa;

import java.util.Scanner;

public class a {
    public static void main(String[] args) {
        
        //12405 - uVA
        //min coverge 
        //intervalo de a a b e tem varios subintervalos
        // escolhe intervalo q cobre e cobre mais longe e susb por esse
        //o mais a dir possivel para cobrir 3

        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int i = 0; i < cases; i++){
            int d = sc.nextInt();
            sc.nextLine();
            String line = sc.nextLine();
            System.out.println(solve(line, d));
        }
        sc.close();
    }

    private static int solve(String line, int d) {
        int sum = 0;

        for(int i = 0; i < line.length();){
            if(line.charAt(i) == 'F'){
                sum++;
                i = i+d;
            }else
                i++;
        }
        return sum;
    }
}
