import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;



//ou init arr com max
public class ProblemF {

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int numCards = Reader.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < numCards; i++){
            int val  = Reader.nextInt();
            if(map.containsKey(val)){
                int v = (int) map.get(val);
                map.put(Reader.nextInt(), v+1);
            }else{
                map.put(Reader.nextInt(), 1);
            }
        }
        int n = Reader.nextInt();
        int sum = 0;
        for(int i  = 0; i < n; i++){
            if(map.containsKey(i))
                sum += (int) map.get(i);
        }
        System.out.println(sum);
    
    }

    static class Reader {
        static BufferedReader reader;
        static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }
}