import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class UVa_1160_X_Plosives {
   
    public static void main(String[] args) throws IOException {

      if (!new Object(){}.getClass().getName().contains("Main"))    
         try {   // redirect System.in and System.out to in/out text files
            System.setIn (new FileInputStream("data/uva1160.in.txt" ));
            System.setOut(new     PrintStream("data/uva1160.out.txt") );
         } catch (Exception e) {}      
      ///////////////////////////////////////////////////////////////
       
      Reader1.init( System.in );    
      
      while (Reader1.hasNext()) {
          Union_Find uf = new Union_Find(100000);
         int refusals = 0;
          do {
              int x = Reader1.nextInt();  
              if (x==-1) 
                 break;
              int y = Reader1.nextInt();

              if (uf.findSet(x) == uf.findSet(y)) // elements already on the same graph?
                  refusals++;
              else                                // if not, join their graphs
                 uf.union(x,y);
          } while (true);

          System.out.println(refusals);
       }
    }
}