import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ProblemC {

    public static class Node {
        String piece;
        String postion;
        int cost;

        public Node(String piece, String position, int cost) {
            this.piece = piece;
            this.postion = position;
            this.cost = cost;
        }
    }

    public static Set<String> getRookPositions(String position) {
        Set<String> positions = new HashSet<>();
        for (int i = 1; i <= 8; i++) {
                positions.add(position.charAt(0) + "" + i );
        }
        for (int i = 'A'; i <= 'H'; i++) {
            positions.add((char) i + "" + position.charAt(1));
        }
        return positions;
    }

    public static Set<String> getBishopPositions(String position) {
        Set<String> positions = new HashSet<>();
        return positions;
    }

    public static Set<String> getKnightPositions(String position) {
        Set<String> positions = new HashSet<>();

        return positions;
    }

    public static Set<String> getPositions(String piece, String position) {
        Set<String> positions = new HashSet<>();
        if (piece.equals("Q") || piece.equals("C"))
            positions.addAll(getRookPositions(position));
        if (piece.equals("C") || piece.equals("A"))
            positions.addAll(getKnightPositions(position));
        if (piece.equals("A") || piece.equals("Q"))
            positions.addAll(getBishopPositions(position));
        return positions;
    }

    public static String getSquareColor(String position) {
        int column = position.charAt(0) - 'A';
        int row = position.charAt(1) - '0';
        if ((row % 2 == 0 && column % 2 == 0) || (row % 2 == 1 && column % 2 == 1))
            return "black";
        return "white";
    }

    public static int cost(String piece, String position, String end) {
        Set<String> positions = getPositions(piece, position);
        if (positions.contains(end))
            return 1;
        if (piece.equals("Q") || piece.equals("C"))
            return 2;
        if (getSquareColor(position).equals(getSquareColor(end)))
            return 2;
        return 3;
    }

    public static void main(String[] args) {
        System.out.println(getRookPositions("A1"));
        System.out.println(getKnightPositions("D4"));
        // Scanner sc = new Scanner(System.in);
        // int nTests = sc.nextInt();
        // for (int i = 0; i < nTests; i++) {
        //     int nPlaces = sc.nextInt();
        //     String start = sc.next();
        //     Node startA = new Node("A", start, 0);
        //     Node startC = new Node("C", start, 0);
        //     Node startQ = new Node("Q", start, 0);
        //     sc.nextLine();
        //     for (int j = 0; j < nPlaces; j++) {
        //         String place = sc.nextLine();
        //     }
        // }
    }
}