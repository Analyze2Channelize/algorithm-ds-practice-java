package edu.saurabh.graphs;

import edu.princeton.cs.algs4.StdOut;

/*An articulation vertex (or cut vertex) is a vertex whose removal increases the number of connected components. 
 * A graph is biconnected if it has no articulation vertices.*/
public class BiconnectedFinder {

	  private int[] low;
	    private int[] pre;
	    private int cnt;
	    private boolean[] articulation;

	    public BiconnectedFinder(AdjacencyListGraph G) {
			low = new int[G.V()];
	        pre = new int[G.V()];
	        articulation = new boolean[G.V()];
	        for (int v = 0; v < G.V(); v++)
	            low[v] = -1;
	        for (int v = 0; v < G.V(); v++)
	            pre[v] = -1;
	        
	        for (int v = 0; v < G.V(); v++)
	            if (pre[v] == -1)
	                dfs(G, v, v);
		}


	    private void dfs(AdjacencyListGraph G, int u, int v) {
	        int children = 0;
	        pre[v] = cnt++;
	        low[v] = pre[v];
	        for (int w : G.adj(v)) {
	            if (pre[w] == -1) {
	                children++;
	                dfs(G, v, w);

	                // update low number
	                low[v] = Math.min(low[v], low[w]);

	                // non-root of DFS is an articulation point if low[w] >= pre[v]
	                if (low[w] >= pre[v] && u != v) 
	                    articulation[v] = true;
	            }

	            // update low number - ignore reverse of edge leading to v
	            else if (w != u)
	                low[v] = Math.min(low[v], pre[w]);
	        }

	        // root of DFS is an articulation point if it has more than 1 child
	        if (u == v && children > 1)
	            articulation[v] = true;
	    }

	    // is vertex v an articulation point?
	    public boolean isArticulation(int v) { return articulation[v]; }

	    // test client
	    public static void main(String[] args) {
	        
	        AdjacencyListGraph G = GraphGeneratorUtil.simple(10, 5);
	        StdOut.println(G);

	        BiconnectedFinder bic = new BiconnectedFinder(G);

	        // print out articulation points
	        StdOut.println();
	        StdOut.println("Articulation points");
	        StdOut.println("-------------------");
	        for (int v = 0; v < G.V(); v++)
	            if (bic.isArticulation(v)) StdOut.println(v);
	    }


	
	
	
}
