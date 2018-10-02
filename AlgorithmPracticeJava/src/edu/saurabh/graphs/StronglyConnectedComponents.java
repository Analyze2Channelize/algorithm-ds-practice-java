package edu.saurabh.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class StronglyConnectedComponents {

	boolean marked[];
	int [] id;
	int [] size;
	int count;

	public StronglyConnectedComponents(AdjacencyListDigraph G) {
		marked = new boolean[G.V()];
		id = new int[G.V()];
		size = new int[G.V()];
		DirectedDFSTraversal dfst = new DirectedDFSTraversal(G.reverse());
		//run DFS on G, using reverse postorder of G.reverse to guide calculation
		for(Integer v: dfst.reversepostorder()) {
			if(!marked[v]) {
				dfs(G,v);	
				count++;
			}
		}
	}

	private void dfs(AdjacencyListDigraph G, int v) {

		marked[v]  = true;
		id[v] = count;
		size[count]++;
		for(int w : G.adj(v)) {
			if(!marked[w]) {
				dfs(G,w);
			}
		}
	}

	/*are vertices in same component*/
	public boolean stronglyConnected(int v,int w) {
		return id[v]==id[w];

	}

	/*returns number of connected components*/
	public int count() {
		return count;

	}

	/*Returns the component id of the connected component containing vertex*/
	public int id(int v) {
		return id[v];

	}

	/* Returns the number of vertices in the connected component containing vertex*/
	public int size(int v) {
		return size[id[v]];
	}

	public static void main(String[] args) {
		In in = new In("mediumDG.txt");
        AdjacencyListDigraph G = new AdjacencyListDigraph(in);
        StronglyConnectedComponents scc = new StronglyConnectedComponents(G);

        // number of connected components
        int m = scc.count();
        StdOut.println(m + " strong components");

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

	}

}
