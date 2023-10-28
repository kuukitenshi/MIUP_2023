import java.io.*;
import java.util.*;

/** Class for buffered reading int and double values
 *  Ref: https://www.cpe.ku.ac.th/~jim/java-io.html 
 */
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader( new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer( reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
    
    // an example
    public static void main(String[] args) throws IOException {
        
        Reader.init( System.in );

        int n = Reader.nextInt();
        double x = Reader.nextDouble();
        
        System.out.printf("%d, %4.2f\n", n, x);
    }
}