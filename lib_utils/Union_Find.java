public class Union_Find {
   
    /*  The graph is represented as a forest of n-ary trees,
        which result in a linear search of a node
        (check details on CORMEN et al., chp.21) */

    int[]   p, // p[i]    is the representative of i
         rank; // rank[i] is the height upper bound of i
   
    public Union_Find(int nNodes) {
       rank = new int[nNodes]; // auto init to zero
       p    = new int[nNodes];
       
       for(int i=0;i<p.length;i++)
          p[i]=i;
    }
   
    public int findSet(int x) {
       if (x!=p[x])
          p[x] = findSet(p[x]);
       return p[x];
    }
   
    public void union(int x, int y) {
       link(findSet(x), findSet(y));
    }
   
    private void link(int x, int y) {
       if (rank[x] > rank[y])
           p[y] = x;
       else {
           p[x] = y;
           if (rank[x] == rank[y])
              rank[y] = rank[y]+1;
       }
    }
}
