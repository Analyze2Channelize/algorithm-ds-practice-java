package edu.saurabh.graphs;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/*enumerates all simple paths in a graph between two specified vertices. */
public class AllPaths {

	private boolean [] onPath; //is a node on current path
	private Stack<Integer> path; // current path
	private int numberOfPaths;

	public AllPaths(AdjacencyListGraph G,int s ,int t) {
		onPath = new boolean[G.V()];
		path = new Stack<>();

		dfs(G,s,t);
	}

	private void dfs(AdjacencyListGraph G,int v,int t) {
		// add v to current path
		path.push(v);
		onPath[v] = true;

		// found path from s to t
		if(v==t) {
			// print out the current path
			processCurrentPath();
			numberOfPaths++;
		}

		// consider all neighbors that would continue path with repeating a node
		for(int w:G.adj(v)) {
			if(!onPath[w]) {
				dfs(G,w,t);
			}
		}

		// done exploring from v, so remove from path
		path.pop();
		onPath[v]= false;

	}

	public int numberOfPaths() {
		return numberOfPaths;
	}
	private void processCurrentPath() {
		Stack<Integer> reverse = new Stack<Integer>();
		for (int v : path)
			reverse.push(v);
		if (reverse.size() >= 1)
			StdOut.print(reverse.pop());
		while (!reverse.isEmpty())
			StdOut.print("-" + reverse.pop());
		StdOut.println();
	}

	public static void main(String[] args) {
		AdjacencyListGraph G = new AdjacencyListGraph(7);
		G.addEdge(0, 1);
		G.addEdge(0, 2);
		G.addEdge(2, 3);
		G.addEdge(3, 4);
		G.addEdge(2, 5);
		G.addEdge(1, 5);
		G.addEdge(5, 4);
		G.addEdge(3, 6);
		G.addEdge(4, 6);
		StdOut.println(G);

		StdOut.println();
		StdOut.println("all simple paths between 0 and 6:");
		AllPaths allpaths1 = new AllPaths(G, 0, 6);
		StdOut.println("# paths = " + allpaths1.numberOfPaths());

		StdOut.println();
		StdOut.println("all simple paths between 1 and 5:");
		AllPaths allpaths2 = new AllPaths(G, 1, 5);
		StdOut.println("# paths = " + allpaths2.numberOfPaths());
	}


}
