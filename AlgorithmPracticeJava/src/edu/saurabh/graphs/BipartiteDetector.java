package edu.saurabh.graphs;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/*Can the vertices of a given graph be assigned one of two colors in such a way that no edge connects vertices of the same color?*/
public class BipartiteDetector {

	private boolean isBipartite;
	private boolean [] marked;
	private boolean [] color;
	private int[] edgeTo;
	private Stack<Integer> cycle;


	public BipartiteDetector(AdjacencyListGraph G) {
		isBipartite =true;
		marked = new boolean[G.V()];
		color = new boolean[G.V()];
		edgeTo = new int[G.V()];
		for(int v=0;v<G.V();v++) {
			dfs(G,v);
		}
		assert check(G);
	}

	private void dfs(AdjacencyListGraph G, int v) {
		marked[v] = true;

		for(int w:G.adj(v)) {

			if(cycle !=null) return;

			if(!marked[w]) {
				edgeTo[w]= v;
				color[w] = !color[v];
				dfs(G,w);
			}else if(color[v] == color[w]){
				isBipartite = false;
				cycle = new Stack<Integer>();
				cycle.push(w);
				for(int x=v; x !=w; x= edgeTo[x]) {
					cycle.push(x);
				}
				cycle.push(w);

			}
		}
	}

	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}

	public boolean isBipartite() {
		return isBipartite;
	}

	public Iterable<Integer> oddCycle() {
		return cycle;
	}

	private boolean check(AdjacencyListGraph G) {
		// graph is bipartite
		if (isBipartite) {
			for (int v = 0; v < G.V(); v++) {
				for (int w : G.adj(v)) {
					if (color[v] == color[w]) {
						System.err.printf("edge %d-%d with %d and %d in same side of bipartition\n", v, w, v, w);
						return false;
					}
				}
			}
		}

		// graph has an odd-length cycle
		else {
			// verify cycle
			int first = -1, last = -1;
			for (int v : oddCycle()) {
				if (first == -1) first = v;
				last = v;
			}
			if (first != last) {
				System.err.printf("cycle begins with %d and ends with %d\n", first, last);
				return false;
			}
		}

		return true;
	}

	/* Returns the side of the bipartite that vertex is on.*/
	public boolean color(int x) {
		validateVertex(x);
		if(!isBipartite) throw new UnsupportedOperationException("graph is not bipartite");
		return color[x];
	}

	public static void main(String[] args) {
		int V1 = 10;
		int V2 = 5;
		int E  = 20;
		int F  = 5;

		// create random bipartite graph with V1 vertices on left side,
		AdjacencyListGraph G = GraphGeneratorUtil.bipartite(V1, V2, E);
		/*System.out.println("Adding new random edges to Graph...\n");
		for (int i = 0; i < F; i++) {
		int v = StdRandom.uniform(V1 + V2);
		int w = StdRandom.uniform(V1 + V2);
		G.addEdge(v, w);
		}*/
		
		
		StdOut.println(G);

		detectBipartiteAndPrintOddCycle(G);
	}


	private static void detectBipartiteAndPrintOddCycle(AdjacencyListGraph G) {
		BipartiteDetector b = new BipartiteDetector(G);
		if (b.isBipartite()) {
			StdOut.println("Graph is bipartite");
			for (int v = 0; v < G.V(); v++) {
				StdOut.print(v + ": " + b.color(v)+",");
			}
		}
		else {
			StdOut.print("Graph has an odd-length cycle: ");
			for (int x : b.oddCycle()) {
				StdOut.print(x + " ");
			}
			StdOut.println();
		}
		StdOut.println();
	}

}
