import java.io.FileWriter;
import java.io.PrintWriter;


//time consola

public class ff {
    private static int N  = 50000;
    private static int  M = 10000;
    public static void main(String[] args) {
        PrintWriter p = new PrintWriter("i.txt");
        p.println(N);
        for(int i = 0; i <=M; i++){
            p.println(i+" "+ " "+ "i"+ " + i"+ "i"+ "i");
            if(i<M )
                p.print(" ");
            else 
                p.println(" ");

        }
        p.print(M);
        for(int i = 1; i <= M; i++){
            p.print(i+ " ");
            if(i<M )
                p.print(" ");
            else 
                p.println(" ");
        }
    }
}   
