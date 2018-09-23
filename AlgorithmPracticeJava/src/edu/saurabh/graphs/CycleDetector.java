package edu.saurabh.graphs;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class CycleDetector {

	private boolean[] marked;
	private int[] edgeTo;
	private Stack<Integer> cycle;


	public CycleDetector(AdjacencyListGraph G) {
		if(hasSelfLoop(G)) return;
		if(hasParallelEdges(G)) return;

		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];

		for(int v=0;v<G.V();v++) {
			if(!marked[v]) {
				dfs(G,-1,v);
			}
		}


	}

	// here 'u' tracks the immediate neighbor of v from where we came to this dfs call
	private void dfs(AdjacencyListGraph G, int u, int v) {

		marked[v] = true;

		for(int w:G.adj(v)) {

			if(cycle!=null) return;
			if(!marked[w]) {
				edgeTo[w] = v;
				dfs(G,v,w);
			}
			else if(w !=u) { // got a neighbor that is already marked and we are not retracing the same path from where we just came
				cycle = new Stack<>();

				for(int x = v; x != w;x = edgeTo[x]) {
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}

	}

	private boolean hasSelfLoop(AdjacencyListGraph G) {
		for(int v=0;v<G.V();v++) {
			for(int w: G.adj(v)) {
				if(v==w) {
					cycle = new Stack<Integer>();
					cycle.push(v);
					cycle.push(v);
					return true;
				}
			}
		}
		return false;

	}

	private boolean hasParallelEdges(AdjacencyListGraph G) {
		marked = new boolean[G.V()];

		for (int v = 0; v < G.V(); v++) {

			// check for parallel edges incident to v
			for (int w : G.adj(v)) {
				if (marked[w]) {
					cycle = new Stack<Integer>();
					cycle.push(v);
					cycle.push(w);
					cycle.push(v);
					return true;
				}
				marked[w] = true;
			}

			// reset so marked[v] = false for all v
			for (int w : G.adj(v)) {
				marked[w] = false;
			}
		}
		return false;

	}
	public boolean hasCycle() {
		return cycle != null;
	}

	public Iterable<Integer> cycle() {
		return cycle;
	}

	public static void main(String[] args) {
		AdjacencyListGraph selfLoopG = new AdjacencyListGraph(3);
		selfLoopG.addEdge(0, 1);
		selfLoopG.addEdge(1, 1);
		selfLoopG.addEdge(1, 2);
		detectAndPrintCycle(selfLoopG);
		AdjacencyListGraph parallelEdge = new AdjacencyListGraph(3);
		parallelEdge.addEdge(0, 1);
		parallelEdge.addEdge(1, 2);
		parallelEdge.addEdge(1, 0);
		detectAndPrintCycle(parallelEdge);
		AdjacencyListGraph acyclic = new AdjacencyListGraph(3);
		acyclic.addEdge(0, 1);
		acyclic.addEdge(1, 2);
		detectAndPrintCycle(acyclic);
		AdjacencyListGraph oneCycle = new AdjacencyListGraph(4);
		oneCycle.addEdge(0, 1);
		oneCycle.addEdge(1, 2);
		oneCycle.addEdge(2, 3);
		oneCycle.addEdge(3, 0);
		detectAndPrintCycle(oneCycle);
		AdjacencyListGraph twoCycles = new AdjacencyListGraph(4);
		twoCycles.addEdge(0, 1);
		twoCycles.addEdge(1, 2);
		twoCycles.addEdge(2, 3);
		twoCycles.addEdge(3, 0);
		twoCycles.addEdge(2, 0);
		detectAndPrintCycle(twoCycles);
		
	}

	private static void detectAndPrintCycle(AdjacencyListGraph selfLoopG) {
		CycleDetector detector = new CycleDetector(selfLoopG);
		if (detector.hasCycle()) {
			for (int v : detector.cycle()) {
				StdOut.print(v + " ");
			}
			StdOut.println();
		}
		else {
			StdOut.println("Graph is acyclic");
		}
	}
}
