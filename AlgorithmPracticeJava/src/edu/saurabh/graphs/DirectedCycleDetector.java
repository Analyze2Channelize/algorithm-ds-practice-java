package edu.saurabh.graphs;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DirectedCycleDetector {

	private boolean[] marked;
	private int[] edgeTo;
	private boolean[] onPath;// is vertex on current path
	private Stack<Integer> cycle;


	public DirectedCycleDetector(AdjacencyListDigraph G) {
		marked = new boolean[G.V()];
		onPath = new boolean[G.V()];
		edgeTo = new int[G.V()];

		for(int v=0;v<G.V();v++) {
			if(!marked[v] && cycle == null) {
				dfs(G,v);
			}
		}


	}
    // check that algorithm computes either the topological order or finds a directed cycle
	private void dfs(AdjacencyListDigraph G, int v) {

		marked[v] = true;
		onPath[v] = true;

		for(int w:G.adj(v)) {

			if(cycle!=null) return;
			
			if(!marked[w]) {
				edgeTo[w] = v;
				dfs(G,w);
			}
			 /* this neighbor is already marked and also on current path, 
			  * so we know that we got it to from some new node(v), hence forms a cycle with v */
			else if(onPath[w]) { 
				cycle = new Stack<>();
				for(int x = v; x != w;x = edgeTo[x]) {
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
				assert check();
			}
		}
		//Backtracking, we are done exploring this node, so remove from path
		onPath[v] = false;

	}
	
	private boolean check() {

        if (hasCycle()) {
            // verify cycle
            int first = -1, last = -1;
            for (int v : cycle()) {
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

	
	public boolean hasCycle() {
		return cycle != null;
	}

	public Iterable<Integer> cycle() {
		return cycle;
	}

	public static void main(String[] args) {
		AdjacencyListDigraph acyclic = new AdjacencyListDigraph(3);
		acyclic.addEdge(0, 1);
		acyclic.addEdge(1, 2);
		detectAndPrintCycle(acyclic);
		AdjacencyListDigraph oneCycle = new AdjacencyListDigraph(4);
		oneCycle.addEdge(0, 1);
		oneCycle.addEdge(1, 2);
		oneCycle.addEdge(2, 3);
		oneCycle.addEdge(3, 0);
		detectAndPrintCycle(oneCycle);
	}

	private static void detectAndPrintCycle(AdjacencyListDigraph G) {
		DirectedCycleDetector detector = new DirectedCycleDetector(G);
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
