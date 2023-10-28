import java.util.Scanner;

public class P793 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int netNum = sc.nextInt();

        for (int i = 0; i < netNum; i++) {
            int sucess = 0;
            int unsucc = 0;

            int nNodes = sc.nextInt();
            sc.nextLine();
            Graph grafo = new Graph(nNodes, false);

            while (sc.hasNext()) {

                String line = sc.nextLine();
                if (line.isEmpty())
                    break;
                String[] nexRow = line.split(" ");
                if (nexRow[0].equals("c")) {
                    grafo.add(Integer.parseInt(nexRow[1])-1, Integer.parseInt(nexRow[2])-1);
                } else if (nexRow[0].equals("q")) {
                    if (grafo.isConnected(Integer.parseInt(nexRow[1])-1, Integer.parseInt(nexRow[2])-1))
                        sucess++;
                    else
                        unsucc++;
                } else
                    System.out.println("Invalid input");
            }
            System.out.println(sucess + "," + unsucc + "\n");
        }
        sc.close();
    }
}