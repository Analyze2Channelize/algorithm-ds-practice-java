package edu.saurabh.graphs;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolDigraph;
import edu.princeton.cs.algs4.Topological;

public class TopologicalOrder {

	private int [] rank;
	private Iterable<Integer> order; // topological order
	
	public TopologicalOrder(AdjacencyListDigraph G) {
		DirectedCycleDetector dcd = new DirectedCycleDetector(G);
		if(!dcd.hasCycle()) {
			DirectedDFSTraversal dfst = new DirectedDFSTraversal(G);
			order = dfst.reversepostorder();
			rank = new int[G.V()];
			int i=0;
			for(int v: order) {
				rank[v] = i++;
			}
		}
	}

	public Iterable<Integer> order() {
        return order;
    }

	public boolean hasOrder() {
        return order != null;
    }
	
    public int rank(int v) {
        validateVertex(v);
        if (hasOrder()) return rank[v];
        else            return -1;
    }
    
    private void validateVertex(int v) {
        int V = rank.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    
    public static void main(String[] args) {
        String filename  = "routes.txt";
        String delimiter = " ";
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Topological topological = new Topological(sg.digraph());
        for (int v : topological.order()) {
            StdOut.println(sg.nameOf(v));
        }
    }

}
