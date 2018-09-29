package edu.saurabh.graphs;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class AdjacencyListDigraph {

	private static final String NEWLINE = System.getProperty("line.separator");
	private final int V;
	private int E;
	private Bag<Integer>[] adj;
	private int[] indegree;

	public AdjacencyListDigraph(int V) {
		this.V = V;
		this.E =0;
		this.indegree = new int[V];
		adj = (Bag<Integer>[]) new Bag[V];
		for(int i=0;i<V;i++) {
			adj[i] = new Bag<Integer>();
		}
	}

	public AdjacencyListDigraph(In in) {
		try {
			this.V = in.readInt();
			if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
			indegree = new int[V];
			adj = (Bag<Integer>[]) new Bag[V];
			for (int v = 0; v < V; v++) {
				adj[v] = new Bag<Integer>();
			}
			int E = in.readInt();
			if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
			for (int i = 0; i < E; i++) {
				int v = in.readInt();
				int w = in.readInt();
				validateVertex(v);
				validateVertex(w);
				addEdge(v, w); 
			}
		}
		catch (NoSuchElementException e) {
			throw new IllegalArgumentException("invalid input format in Graph constructor", e);
		}
	}

	// copy constructor
	public AdjacencyListDigraph(AdjacencyListDigraph G) {
		this(G.V());
		this.E = G.E();
		for (int v = 0; v < V; v++)
			this.indegree[v] = G.indegree(v);
		for (int v = 0; v < G.V(); v++) {
			// reverse so that adjacency list is in same order as original
			Stack<Integer> reverse = new Stack<Integer>();
			for (int w : G.adj[v]) {
				reverse.push(w);
			}
			for (int w : reverse) {
				adj[v].add(w);
			}
		}
	}

	private void validateVertex(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;

	}

	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		E++;
		adj[v].add(w);
		indegree[w]++;
	}

	Iterable<Integer> adj(int v){
		validateVertex(v);
		return adj[v];
	}

	public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

	public int indegree(int v) {
		validateVertex(v);
		return indegree(v);
	}

	public int outdegree(int v) {
		validateVertex(v);
		return adj[v].size();
	}

	public AdjacencyListDigraph reverse() {
		AdjacencyListDigraph reverse = new AdjacencyListDigraph(V);
		for (int v = 0; v < V; v++) {
			for (int w : adj(v)) {
				reverse.addEdge(w, v);
			}
		}
		return reverse;
	}



	public static void main(String[] args) {
		In in = new In("tinyDG.txt");
		AdjacencyListDigraph G = new AdjacencyListDigraph(in);
		System.out.println(G);

	}

}
