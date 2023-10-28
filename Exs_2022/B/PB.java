import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class PB {

    private class Node {
        private int cost = 0;
        private int posRow;
        private int posCol;

        public Node(int posRow, int posCol, int cost){
            this.cost = cost;
            this.posRow = posRow;
            this.posCol = posCol;
        }
    }

    public Node breadthSearch(Queue<Node> front, Node first, String[][]map, int goalCol, int goalRow){
        front.add(first);
        List<Node> exploredNodes = new ArrayList<>();

        while(!front.isEmpty()){
            Node outFront = front.poll();
            if(outFront.posCol == goalCol && outFront.posRow == goalRow){
                return outFront;
            }
            exploredNodes.add(outFront);
            // se map nessa pos == h retorna os passos do no
            // para vizinho se aindan for visitado acrescenta

            List<Node> succs = optionMoves(map, outFront);
            for(int i = 0; i < succs.size(); i++){
                front.add(succs.get(i));
            }
            // equals
        }
        return null;
    }

    public List<Node> optionMoves(String[][] map, Node node){
        String actions = "udlr";
        // para cada teste chana a oesquisa -1 pelo index

        // return null;
        // meter adjacentes q sao vizinho
        // -1,-1 ou pos onde chega filtra
        // decrementa linha se pate sabe oq cria senao guarda com coord

    }


    public static void main(String[] args) {
        
        Scanner readInput = new Scanner(System.in);
        String[] sizes = readInput.nextLine().split(" ");
        int[] sizesInt = new int[3];
        for(int i= 0; i<sizes.length; i++){
            sizesInt[i] = Integer.parseInt(sizes[i]);
        }
        String[][] map = new String[sizesInt[1]][sizesInt[0]];

        for(int i = 0; i < map.length; i++){
            String[] line = readInput.nextLine().split("");
            map[i] = line;
        }
        int testCasesNum = sizesInt[2];
        String[] tests = new String[testCasesNum];
        for(int i = 0; i < testCasesNum; i++) {
            tests[i] = readInput.nextLine();
        }//print mapa e coord e tdo explorado projeto iia
    }
}