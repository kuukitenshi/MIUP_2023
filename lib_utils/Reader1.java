import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Reader1 {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    static String next;
    static boolean eof;

    /** call this method to initialize reader for InputStream 
     * @throws IOException 
     */
    static void init(InputStream input) throws IOException {
        reader = new BufferedReader( new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
        next = reader.readLine();
    }
    
    static boolean hasNext() {
       return tokenizer.hasMoreTokens() || next!=null;
    }

    /** get next word
     * @throws IOException 
     */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
           if (next == null) return null;
           tokenizer = new StringTokenizer( next );
            next = reader.readLine();
        }
           return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
}
