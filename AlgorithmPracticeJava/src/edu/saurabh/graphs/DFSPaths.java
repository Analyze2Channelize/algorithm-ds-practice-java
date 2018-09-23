package edu.saurabh.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DFSPaths implements Paths {

	boolean [] marked;
	int [] edgeTo;
	private final int s;

	//find Paths in graph G from source s
	public DFSPaths(AdjacencyListGraph G, int s) {
		this.s = s;
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		validateVertex(s);
		dfs(G,s);
	}

	private void dfs(AdjacencyListGraph G, int v) {

		marked[v]  = true;
		for(int w : G.adj(v)) {
			if(!marked[w]) {
				edgeTo[w] = v;
				dfs(G,w);
			}
		}
	}

	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}

	@Override
	public boolean hasPathTo(int v) {
		return marked[v];

	}

	@Override
	public Iterable<Integer> pathTo(int v){
		if(!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>();
		while(v !=s) {
			path.push(v);
			v = edgeTo[v];
		}
		path.push(s);
		return path;
	}


	public static void main(String[] args) {
		In in = new In("tinyG.txt");
		AdjacencyListGraph G = new AdjacencyListGraph(in);
		System.out.println(G);
		int s = 0;
		DFSPaths dfs = new DFSPaths(G, s);

		for (int v = 0; v < G.V(); v++) {
			if (dfs.hasPathTo(v)) {
				StdOut.printf("%d to %d:  ", s, v);
				for (int x : dfs.pathTo(v)) {
					if (x == s) StdOut.print(x);
					else        StdOut.print("-" + x);
				}
				StdOut.println();
			}

			else {
				StdOut.printf("%d to %d:  not connected\n", s, v);
			}

		}
	}

	@Override
	public int distTo(int v) {
		throw new UnsupportedOperationException();
	}

}
