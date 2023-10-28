import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Graph {

    public static final boolean DIRECT = true,
            UNDIRECT = !DIRECT;

    public static final boolean SPARSE = true,
            NOTSPARSE = !SPARSE;

    private boolean isSparse; // is the graph using a sparse representation?
    private boolean isDirected; // is the graph directed?
    private int size; // number of nodes

    // Non-sparse representation:
    // A 2D array where rows and columns represent the nodes and
    // each position represents the weight between nodes (zero means no connection)
    // The graph will consist of V nodes and E edges (E <= V^2)

    private int[][] graphMatrix;

    // Sparse representation
    // An array of hashmaps to represent sparse graphs
    // The edge (i,j,w) will be added as graphList[i].put(j,w)

    private ArrayList<HashMap<Integer, Integer>> graphList;

    /////////////////////////// BASIC METHODS ////////////////////////////////////

    // by default we use a matrix to represent a graph, ie, a non-sparse
    // representation
    public Graph(int nodes, boolean graphType) {
        size = nodes;
        isDirected = graphType;
        isSparse = false;
        graphMatrix = new int[size][size];
    }

    public Graph(int nodes, boolean graphType, boolean sparse) {
        size = nodes;
        isDirected = graphType;
        isSparse = sparse;

        if (isSparse) {
            graphList = new ArrayList<HashMap<Integer, Integer>>(size);
            for (int i = 0; i < size; i++)
                graphList.add(i, new HashMap<Integer, Integer>());
        } else
            graphMatrix = new int[size][size];
    }

    /**
     * Add edge to graph
     */
    public void add(int from, int to, int weight) {
        if (isSparse) {
            graphList.get(from).put(to, weight);
            if (isDirected == UNDIRECT)
                graphList.get(to).put(from, weight);
        } else {
            graphMatrix[from][to] = weight;
            if (isDirected == UNDIRECT)
                graphMatrix[to][from] = weight;
        }
    }

    public void add(int from, int to) {
        add(from, to, 1);
    }

    /**
     * Remove edge to graph
     */
    public void remove(int from, int to) {
        if (isSparse) {
            graphList.get(from).remove(to);
            if (isDirected == UNDIRECT)
                graphList.get(to).remove(from);
        } else
            add(from, to, 0); // remove edge
    }

    public int weight(int from, int to) {
        if (isSparse) {
            Integer w = graphList.get(from).get(to);
            return w == null ? 0 : w;
        } else
            return graphMatrix[from][to];
    }

    public int size() {
        return size;
    }

    /**
     * Remove all in-edges and out-edges from/into node
     */
    public void isolate(int node) {
        if (isSparse) {
            graphList.set(node, new HashMap<Integer, Integer>()); // remove out-edges
            for (int i = 0; i < size; i++)
                graphList.get(i).remove(node); // remove in-edges (slow)
        } else {
            if (isDirected == DIRECT)
                graphMatrix[node] = new int[size];
            for (int i = 0; i < size; i++)
                remove(i, node);
        }
    }

    /**
     * @param node The node which successors we need
     * @requires a directed graph
     * @return an array with the indexes of the node's successors
     */
    public int[] sucessors(int node) {
        ArrayList<Integer> l = new ArrayList<Integer>();

        if (isSparse) {
            for (Integer successor : graphList.get(node).keySet())
                l.add(successor);
        } else {
            for (int i = 0; i < size; i++)
                if (weight(node, i) != 0)
                    l.add(i);
        }
        return list2array(l);
    }

    /**
     * @param node The node which predecessors we need
     * @requires a directed graph
     * @return an array with the indexes of the node's predecessors
     */
    public int[] predecessors(int node) {
        ArrayList<Integer> l = new ArrayList<Integer>();

        if (isSparse) {
            for (int i = 0; i < size; i++) { // slow
                Integer weight = graphList.get(i).get(node);
                if (weight != null)
                    l.add(i);
            }
        } else {
            for (int i = 0; i < size; i++)
                if (weight(i, node) != 0)
                    l.add(i);
        }
        return list2array(l);
    }

    /**
     * Make a copy of this
     * 
     * @return the reference to the copy
     */
    @SuppressWarnings("unchecked")
    public Graph copy() {

        Graph cp = new Graph(this.size, this.isDirected, this.isSparse);

        if (isSparse) {
            for (int i = 0; i < cp.size; i++)
                cp.graphList.set(i,
                        (HashMap<Integer, Integer>) this.graphList.get(i).clone());
        } else {
            for (int i = 0; i < cp.size; i++)
                cp.graphMatrix[i] = this.graphMatrix[i].clone();
        }
        return cp;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (isSparse) {
            for (int i = 0; i < size; i++)
                for (Integer j : graphList.get(i).keySet()) {
                    int weight = graphList.get(i).get(j);
                    if (weight != 0)
                        if (weight == 1)
                            sb.append(i + "->" + j + " "); // don't show weights 1
                        else
                            sb.append(i + "-{" + weight + "}->" + j + " ");
                }
        } else {
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    if (weight(i, j) != 0)
                        if (weight(i, j) == 1)
                            sb.append(i + "->" + j + " "); // don't show weights 1
                        else
                            sb.append(i + "-{" + weight(i, j) + "}->" + j + " ");
        }
        return sb.toString();
    }

    private int[] list2array(ArrayList<Integer> list) {
        int[] array = new int[list.size()];
        int index = 0;

        for (int elem : list)
            array[index++] = elem;
        return array;
    }

    // -----------------SEARCH ALGORITHMS----------------------------

    /**
     * Depth-first search from a given node
     * 
     * @param node The node from which to start
     * @complexity O(V + E)
     * @requires a directed graph
     * @return an array with the indexes of the dfs
     */
    public int[] dfs(int node) {
        Stack<Integer> stack = new Stack<Integer>(); // for backtracking
        ArrayList<Integer> l = new ArrayList<Integer>(); // contains the visiting order
        boolean[] visited = new boolean[size]; // false means not visited

        stack.push(node);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (visited[current])
                continue;
            visited[current] = true;
            l.add(current);
            for (int next : sucessors(current))
                if (!visited[next])
                    stack.push(next);
        }
        return list2array(l);
    }

    /**
     * Breath-first search from a given node
     * 
     * @param node The node from which to start
     * @complexity O(V + E)
     * @requires a directed graph
     * @return an array with the indexes of the bfs
     */
    public int[] bfs(int node) {
        Queue<Integer> queue = new LinkedList<Integer>();
        ArrayList<Integer> l = new ArrayList<Integer>();
        boolean[] visited = new boolean[size];
        queue.add(node);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (visited[current])
                continue;
            visited[current] = true;
            l.add(current);
            for (int next : sucessors(current))
                if (!visited[next])
                    queue.add(next);
        }
        return list2array(l);
    }

    // ---------------ORDENACAO TOPOLOGICA---------------------------------
    private boolean[] deactivated; // vector useful for topSort

    // get the index of a node with no in-edges (for topSort)
    // returns -1 if no such node found (ie, a cycle exists)
    private int getOutNode() {
        int i;
        boolean foundIn = true;

        for (i = 0; i < size && foundIn; i++) {
            if (deactivated[i])
                continue;
            foundIn = false;
            for (int j = 0; j < size && !foundIn; j++)
                foundIn = weight(j, i) != 0;
        }
        if (!foundIn) {
            isolate(i - 1);
            deactivated[i - 1] = true;
        }
        return !foundIn ? i - 1 : -1; // if all have in-edges, there's a cycle
    }

    /**
     * Performs Topological Sort
     * 
     * @requires a directed graph
     * @complexity O(V + E)
     * @return an array of indexes with one topological sort of the graph
     *         or null if the graph has cycles (ie, it is not a DAG)
     */
    public int[] topSort() {
        ArrayList<Integer> l = new ArrayList<Integer>();
        int node, nodesLeft = size;
        Graph cp = this.copy();
        cp.deactivated = new boolean[size];

        while (nodesLeft-- > 0) {
            node = cp.getOutNode(); // find & deactivate node with no in-edges
            if (node == -1)
                return null;
            l.add(node);
        }
        return list2array(l);
    }

    /**
     * Checks if graph is a Direct Acyclic Graph
     * 
     * @complexity O(V + E)
     * @return true iff graph is DAG
     */
    public boolean isDAG() {
        return isDirected == DIRECT && topSort() != null;
    }

    // ----------------------CAMINHO MAIS
    // CURTO-----------------------------------------
    /**
     * Compute Shortest Path using Dijkstra's greedy algorithm
     * 
     * @complexity O(V^2)
     * @return an array with the indexes of the path, or null if no path exists
     */
    public int[] shortestPath(int from, int to) {

        int[] costs = new int[size];
        for (int i = 0; i < size; i++) // init costs
            costs[i] = Integer.MAX_VALUE - 1;
        costs[from] = 0;

        boolean[] visited = new boolean[size];
        int visitedNodes = 0;

        int[] prev = new int[size];

        while (visitedNodes++ < size) {
            // select unvisited node with min cost
            int min = -1, minCost = Integer.MAX_VALUE;
            for (int i = 0; i < size; i++)
                if (!visited[i] && costs[i] < minCost) {
                    min = i;
                    minCost = costs[i];
                }

            if (minCost == Integer.MAX_VALUE - 1) // no need to continue
                break;

            for (int succ : sucessors(min))
                if (minCost + weight(min, succ) < costs[succ]) {
                    costs[succ] = minCost + weight(min, succ);
                    prev[succ] = min;
                }
            visited[min] = true;
        }

        // construct array with shortest path
        ArrayList<Integer> l = new ArrayList<Integer>();

        l.add(to);
        while (prev[l.get(0)] != from) {
            if (l.size() > size) // something went wrong, no path is possible
                return null;
            l.add(0, prev[l.get(0)]); // add to begin of list, so the order is from --> to
        }
        l.add(0, from);
        return list2array(l);
    }

    /**
     * Is there a path from 'from' to 'to'?
     * 
     * @complexity O(V^2)
     */
    public boolean isConnected(int from, int to) {
        return shortestPath(from, to) != null;
    }

    // ------------COMPONENTES DE GRAFO-----------------------------

    /**
     * Find connected components of an undirected graph
     * 
     * @requires an undirected graph
     * @complexity O(V + E)
     * @return An array of components (each component is an array of connected
     *         nodes)
     */
    public int[][] components() {
        boolean[] visited = new boolean[size];
        ArrayList<int[]> l = new ArrayList<int[]>();

        while (true) {
            int next = 0;
            for (; next < size && visited[next]; next++)
                ; // find next unvisited node
            if (next == size) // no more nodes to visit
                break;

            int[] nodes = dfs(next);
            for (int node : nodes)
                visited[node] = true;
            l.add(nodes);
        }

        int[][] result = new int[l.size()][];
        int i = 0;
        for (int[] component : l)
            result[i++] = component;
        return result;
    }

    public boolean isConnected() {
        return components().length == 1;
    }

    private final int SSC_UNVISITED = -1;
    private int SSC_counter;
    private Stack<Integer> SSC_Stack;
    private boolean[] SSC_OnStack;
    private int[] SSC_low, SSC_index;

    private void SSC(int node) {

        // Set the depth index for 'node' to the smallest unused index
        SSC_low[node] = SSC_index[node] = SSC_counter++;
        SSC_Stack.push(node);
        SSC_OnStack[node] = true;

        for (int succ : sucessors(node)) // Consider successors of node
            if (SSC_index[succ] == SSC_UNVISITED) {
                SSC(succ);
                SSC_low[node] = Math.min(SSC_low[node], SSC_low[succ]);
            } else if (SSC_OnStack[succ])
                SSC_low[node] = Math.min(SSC_low[node], SSC_index[succ]);
    }

    public int[][] strongComponents() {
        ArrayList<int[]> l = new ArrayList<int[]>();

        SSC_Stack = new Stack<Integer>();
        SSC_OnStack = new boolean[size];
        SSC_low = new int[size];
        SSC_index = new int[size];
        SSC_counter = 0;

        for (int i = 0; i < size; i++)
            SSC_index[i] = SSC_UNVISITED;

        for (int i = 0; i < size; i++)
            if (SSC_index[i] == SSC_UNVISITED) {
                SSC(i); // process a connect set of nodes
                int k = SSC_Stack.size();
                while (!SSC_Stack.isEmpty()) { // create each strong component
                    ArrayList<Integer> lSSC = new ArrayList<Integer>();
                    do {
                        int node = SSC_Stack.pop();
                        SSC_OnStack[node] = false;
                        lSSC.add(node);
                        k--;
                    } while (SSC_low[k] != SSC_index[k]);
                    l.add(list2array(lSSC));
                }
            }

        int[][] result = new int[l.size()][];
        int i = 0;
        for (int[] component : l)
            result[i++] = component;
        return result;
    }

    // -------------BIPARTIDE--------------------------------------
    /**
     * Checks if a graph is bipartide, ie, it's 2-colorable
     * 
     * @requires a connected undirected graph (including node 0)
     * @complexity O(V + E)
     * @return true iff is bipartide
     */
    public boolean isBipartide() {
        int[] colors = new int[size]; // we use colors 1 and 2
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(0); // add a graph's node
        colors[0] = 1; // ...and 'paint' it with color 1

        boolean isBipartide = true;
        while (!queue.isEmpty() && isBipartide) {
            int current = queue.poll();
            for (int node : sucessors(current))
                if (colors[node] == 0) { // the color isn't yet assigned
                    colors[node] = 3 - colors[current]; // 1 --> 2, 2 --> 1
                    queue.add(node);
                } else
                    isBipartide = colors[current] != colors[node]; // must have diff colors
        }
        return isBipartide;
    }

    // --------SPANNING TREE---------------------------------------
    /**
     * Computes a minimum spanning tree of this graph using Kruskal's Algorithm
     * Cf. https://en.wikipedia.org/wiki/Kruskal's_algorithm
     * 
     * @complexity O(E log V)
     * @requires an undirected graph
     * @return a set of edges inside an int[].
     *         Eg, if the result was 1->2 and 2->4, it returns [1,2,2,4]
     */
    public int[] minimumSpanningTree() {
        ArrayList<Integer> l = new ArrayList<Integer>();
        Union_Find uf = new Union_Find(size);
        int[] edges = sortEdgesByWeight();

        for (int i = 0; i < edges.length; i += 2)
            if (uf.findSet(edges[i]) != uf.findSet(edges[i + 1])) {
                l.add(edges[i]);
                l.add(edges[i + 1]);
                uf.union(edges[i], edges[i + 1]);
            }
        return list2array(l);
    }

    // Auxiliary class that creates a comparable edge
    // Useful to use Collection.sort()
    @SuppressWarnings("rawtypes")
    private class Triple implements Comparable {
        int from, to;
        Integer weight;

        public Triple(int f, int t, int w) {
            from = f;
            to = t;
            weight = new Integer(w);
        }

        @Override
        public int compareTo(Object obj) {
            return weight.compareTo(((Triple) obj).weight);
        }
    }

    // returns the edges sorted by weight (increasing) in an int[]
    // eg, if 1-{2}->3 and 0-{1}->4, the result is [0,4,1,3]
    @SuppressWarnings("unchecked")
    private int[] sortEdgesByWeight() {
        ArrayList<Triple> l = new ArrayList<Triple>();

        for (int row = 0; row < size; row++) // copy all non-zero edges to list
            for (int col = 0; col < row; col++) {
                int w = weight(row, col);
                if (w != 0)
                    l.add(new Triple(row, col, w));
            }

        Collections.sort(l);

        int[] result = new int[2 * l.size()]; // translate list of Triples to int[]
        int i = 0;
        for (Triple t : l) {
            result[i++] = t.from;
            result[i++] = t.to;
        }
        return result;
    }

    /**
     * Compute the cost of the minimum spanning tree
     * 
     * @param edges array of indexes. Eg, if MST is 1->2 2->3, edges=[1,2,2,3]
     * @return the cumulative cost of all given edges
     */
    public int costMST(int[] edges) {
        int sum = 0;

        for (int i = 0; i < edges.length; i += 2)
            sum += weight(edges[i], edges[i + 1]);
        return sum;
    }

    // -----------------FLOOD FILL-------------------------------
    static int dr[] = { 1, 1, 0, -1, -1, -1, 0, 1 }; // trick to explore an implicit 2D grid
    static int dc[] = { 0, 1, 1, 1, 0, -1, -1, -1 }; // S,SE,E,NE,N,NW,W,SW neighbors

    // static int dr[] = {1,0,-1, 0}; //
    // static int dc[] = {0,1, 0,-1}; // S,E,N,W neighbors

    /**
     * Use graph structure to simulate a 2D grid
     * Replaces the old color by the new considering all directions,
     * and returns the total number of replacements
     * 
     * @ensures a total chaotic graph if interpreted by default
     * @complexity O(V + E)
     * @throws Exception If representation is non-sparce (ie, it needs the adjacency
     *                   matrix)
     */
    public int floodFill(int row, int col, int oldColor, int newColor) throws Exception {

        if (isSparse)
            throw new Exception("Flood Fill only works on a non-sparce representation");

        if (row < 0 || row >= size || col < 0 || col >= size)
            return 0; // outside grid

        if (graphMatrix[row][col] != oldColor)
            return 0; // does not have old color

        graphMatrix[row][col] = newColor; // recolors vertex to avoid cycling
        int ans = 1; // adds 1 because vertex (row, col) was replaced
        for (int d = 0; d < 8; d++)
            ans += floodFill(row + dr[d], col + dc[d], oldColor, newColor);
        return ans; //
    }
}