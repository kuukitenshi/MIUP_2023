import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

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

    public boolean isConnected(int from, int to) {
        ArrayList<Integer> visitedNodes = new ArrayList<>();
        Queue<Integer> queuePorExpandir = new ArrayDeque<>(); //fronteira //largura

        queuePorExpandir.add(from);
        while (!queuePorExpandir.isEmpty()) {
            int next = queuePorExpandir.poll();
            if (next == to)
                return true;
            visitedNodes.add(next);
            int[] neighboors = sucessors(next);
            for (int j = 0; j < neighboors.length; j++)
                if (!visitedNodes.contains(neighboors[j]))
                    queuePorExpandir.add(neighboors[j]); // adicionamos os nos ao final da fila
        }
        return false;
    }
}