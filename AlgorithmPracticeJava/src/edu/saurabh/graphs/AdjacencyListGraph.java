package edu.saurabh.graphs;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class AdjacencyListGraph {

	private static final String NEWLINE = System.getProperty("line.separator");
	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	public AdjacencyListGraph(int V) {
		this.V = V;
		this.E =0;
		adj = (Bag<Integer>[]) new Bag[V];
		for(int i=0;i<V;i++) {
			adj[i] = new Bag<Integer>();
		}
	}

	public AdjacencyListGraph(In in) {
		try {
			this.V = in.readInt();
			if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
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
	public AdjacencyListGraph(AdjacencyListGraph G) {
		this(G.V());
		this.E = G.E();
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
		adj[w].add(v);
	}

	Iterable<Integer> adj(int v){
		validateVertex(v);
		return adj[v];
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (int w : adj[v]) {
				s.append(w + " ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
	
	public int degree(int v) {
		validateVertex(v);
		return adj[v].size();
	}

	public int maxDegree() {
		int max = 0;
		for (int v = 0; v < V(); v++)
			if (degree(v) > max)
				max = degree(v);
		return max;
	}

	public int avgDegree() {
		return 2 * E() / V();
	}

	public int numberOfSelfLoops() {
		int count = 0;
		for (int v = 0; v < V(); v++)
			for (int w : adj(v))
				if (v == w) count++;
		return count/2;   // self loop appears in adjacency list twice
	} 

	public static void main(String[] args) {
		In in = new In("tinyG.txt");
		AdjacencyListGraph G = new AdjacencyListGraph(in);
		System.out.println(G);
		System.out.println("Self loops:"+G.numberOfSelfLoops());
		System.out.println("Max degree:"+G.maxDegree());
		System.out.println("avgDegree:"+G.avgDegree());
	}

}
