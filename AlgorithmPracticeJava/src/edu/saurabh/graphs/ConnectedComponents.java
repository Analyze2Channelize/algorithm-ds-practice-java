package edu.saurabh.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class ConnectedComponents {

	boolean marked[];
	int [] id;
	int [] size;
	int count;

	public ConnectedComponents(AdjacencyListGraph G) {
		marked = new boolean[G.V()];
		id = new int[G.V()];
		size = new int[G.V()];
		for(int i=0;i<G.V();i++) {
			if(!marked[i]) {
				dfs(G,i);	
				count++;
			}
		}
	}

	private void dfs(AdjacencyListGraph G, int v) {

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
	public boolean connected(int v,int w) {
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
		In in = new In("tinyG.txt");
		AdjacencyListGraph G = new AdjacencyListGraph(in);
		ConnectedComponents cc = new ConnectedComponents(G);

		// number of connected components
		int m = cc.count();
		StdOut.println(m + " components");

		// compute list of vertices in each connected component
		Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
		for (int i = 0; i < m; i++) {
			components[i] = new Queue<Integer>();
		}
		for (int v = 0; v < G.V(); v++) {
			components[cc.id(v)].enqueue(v);
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
