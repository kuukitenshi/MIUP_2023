import java.util.*;
import java.util.function.Supplier;

public class Search {

    // Procura nao informada
    // DFS -> Stack / recursão
    // BFS -> Queue
    // UC -> PriorityQueue
    // ID -> DFS com limite

    // Procura informada: expandir nos com menor f(n)
    // greedy -> f(n) = h(n)

    static final Map<String, Map<String, Integer>> CITIES = new HashMap<>();

    static final Map<GraphNode, Map<GraphNode, Integer>> GRAPH = new HashMap<>();

    static class GraphNode {
        int h;
        String name;

        public GraphNode(String name, int h) {
            this.name = name;
            this.h = h;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof GraphNode gn && gn.h == this.h && gn.name.equals(this.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(h, name);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static class Node {
        GraphNode content;
        int cost;
        Node parent;

        public Node(Node parent, GraphNode content, int cost) {
            this.parent = parent;
            this.content = content;
            this.cost = cost;
        }

        public List<Node> expand() {
            List<Node> childs = new ArrayList<>();
            Map<GraphNode, Integer> next = GRAPH.get(this.content);
            for (Map.Entry<GraphNode, Integer> entry : next.entrySet()) {
                childs.add(new Node(this, entry.getKey(), this.cost + entry.getValue()));
            }
            return childs;
        }

        public List<Node> path() {
            List<Node> path = new ArrayList<>();
            Node curr = this;
            while (curr != null) {
                path.add(0, curr);
                curr = curr.parent;
            }
            return path;
        }

        public int depth() {
            int depth = 0;
            Node curr = this;
            while (curr != null) {
                depth++;
                curr = curr.parent;
            }
            return depth - 1;
        }

        public boolean isSolution() {
            return this.content.name.equals("G");
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node n && n.content.equals(this.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.content);
        }

        @Override
        public String toString() {
            return this.content.toString();
        }
    }

//    private static Node depthSearch() {
//        Stack<Node> frontier = new Stack<>();
//        frontier.push(new Node(null, "Sibiu", 0));
//        Set<String> exploredNodes = new HashSet<>();
//        while(!frontier.isEmpty()){
//            Node curr = frontier.pop();
//            exploredNodes.add(curr.city);
//            if (curr.isSolution()) {
//                return curr;
//            }
//            for(Node child : curr.expand()) {
//                if (!frontier.contains(child) && !exploredNodes.contains(child.city)) {
//                    frontier.push(child);
//                }
//            }
//        }
//        return null;
//    }
//
//    private static Node breathFirstSearch() {
//        Queue<Node> frontier = new ArrayDeque<>();
//        frontier.add(new Node(null, "Sibiu", 0));
//        Set<String> exploredNodes = new HashSet<>();
//        while(!frontier.isEmpty()){
//            Node curr = frontier.poll();
//            exploredNodes.add(curr.city);
//            for(Node child : curr.expand()) {
//                if (!frontier.contains(child) && !exploredNodes.contains(child.city)) {
//                    if (child.isSolution()) {
//                        return child;
//                    }
//                    frontier.add(child);
//                }
//            }
//        }
//        return null;
//    }
//
//    private static Node uniformCost() {
//        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
//        frontier.add(new Node(null, "Sibiu", 0));
//        Set<String> exploredNodes = new HashSet<>();
//        while(!frontier.isEmpty()){
//            Node curr = frontier.poll();
//            exploredNodes.add(curr.city);
//            if (curr.city.equals("Bucharest")) {
//                return curr;
//            }
//            for(Node child : curr.expand()) {
//                if (!frontier.contains(child) && !exploredNodes.contains(child.city)) {
//                    frontier.add(child);
//                } else if (frontier.contains(child)) {
//                    boolean removed = frontier.removeIf(n -> n.equals(child) && n.cost > child.cost);
//                    if (removed)
//                        frontier.add(child);
//                }
//            }
//        }
//        return null;
//    }
//
//    private static Node iterativeDeepening() {
//        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            Stack<Node> frontier = new Stack<>();
//            Set<String> explored = new HashSet<>();
//            frontier.push(new Node(null, "Sibiu", 0));
//            while (!frontier.isEmpty()) {
//                Node node = frontier.pop();
//                explored.add(node.city);
//                if (node.isSolution()) {
//                    return node;
//                }
//                for (Node child : node.expand()) {
//                    if (!frontier.contains(child) && !explored.contains(child.city) && child.depth() <= i)
//                        frontier.add(child);
//                }
//            }
//        }
//        return null;
//    }


    private static Node greedySearch(GraphNode start) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> n.content.h));
        frontier.add(new Node(null, start, 0));
        Set<Node> exploredNodes = new HashSet<>();
        while(!frontier.isEmpty()){
            Node curr = frontier.poll();
            exploredNodes.add(curr);
            if (curr.isSolution()) {
                return curr;
            }
            for(Node child : curr.expand()) {
                if (!frontier.contains(child) && !exploredNodes.contains(child)) {
                    frontier.add(child);
                } else if (frontier.contains(child)) {
                    boolean removed = frontier.removeIf(n -> n.equals(child) && n.cost > child.cost);
                    if (removed)
                        frontier.add(child);
                }
            }
        }
        return null;
    }

    private static Node AStar(GraphNode start){
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost + n.content.h));
        frontier.add(new Node(null, start, 0));
        Set<Node> exploredNodes = new HashSet<>();
        while(!frontier.isEmpty()){
            Node curr = frontier.poll();
            exploredNodes.add(curr);
            if (curr.isSolution()) {
                return curr;
            }
            for(Node child : curr.expand()) {
                if (!frontier.contains(child) && !exploredNodes.contains(child)) {
                    frontier.add(child);
                } else if (frontier.contains(child)) {
                    boolean removed = frontier.removeIf(n -> n.equals(child) && n.cost > child.cost);
                    if (removed)
                        frontier.add(child);
                }
            }
        }
        return null;
    }

    private static void printSearch(Supplier<Node> supp, String name) {
        Node sol = supp.get();
        System.out.print(name + ": ");
        if (sol == null)
            System.out.println("No solution");
        else
            System.out.println(sol.path());
    }

    public static void main(String[] args) {
//        Map<String, Integer> sibiu = new HashMap<>();
//        sibiu.put("Fagaras", 99);
//        sibiu.put("Rimnicu Vilcea", 80);
//        Map<String, Integer> fagaras = new HashMap<>();
//        fagaras.put("Bucharest", 211);
//        Map<String, Integer> rimnicu = new HashMap<>();
//        rimnicu.put("Pitesti", 97);
//        Map<String, Integer> pitesti = new HashMap<>();
//        pitesti.put("Bucharest", 101);
//
//        CITIES.put("Sibiu", sibiu);
//        CITIES.put("Fagaras", fagaras);
//        CITIES.put("Rimnicu Vilcea", rimnicu);
//        CITIES.put("Pitesti", pitesti);
//
//        System.out.println(CITIES);
//
//        printSearch(Search::depthSearch, "DFS");
//        printSearch(Search::breathFirstSearch, "BFS");
//        printSearch(Search::uniformCost, "UC");
//        printSearch(Search::iterativeDeepening, "ID");


        GraphNode S = new GraphNode("S", 3);
        GraphNode A = new GraphNode("A", 4);
        GraphNode C = new GraphNode("C", 3);
        GraphNode B = new GraphNode("B", 2);
        GraphNode G = new GraphNode("G", 0);
        Map<GraphNode, Integer> s = new HashMap<>();
        s.put(A, 1);
        s.put(B, 2);
        GRAPH.put(S, s);
        Map<GraphNode, Integer> a = new HashMap<>();
        a.put(C, 1);
        GRAPH.put(A, a);
        Map<GraphNode, Integer> c = new HashMap<>();
        c.put(G, 3);
        GRAPH.put(C, c);
        Map<GraphNode, Integer> b = new HashMap<>();
        b.put(G, 4);
        GRAPH.put(B, b);

        printSearch(() -> greedySearch(S), "Greedy");
        printSearch(() -> AStar(S), "AStar");
    }

}
