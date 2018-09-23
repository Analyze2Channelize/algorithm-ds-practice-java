package edu.saurabh.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BFSPaths implements Paths {

	boolean [] marked;
	int [] edgeTo;
	int [] distTo;

	//find shortest Paths in graph G from source s
	public BFSPaths(AdjacencyListGraph G, int s) {
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		validateVertex(s);
		bfs(G,s);
		assert check(G, s);
	}
	
	 public BFSPaths(AdjacencyListGraph G, Iterable<Integer> sources) {
	        marked = new boolean[G.V()];
	        distTo = new int[G.V()];
	        edgeTo = new int[G.V()];
	        for (int v = 0; v < G.V(); v++)
	            distTo[v] = Integer.MAX_VALUE;
	        validateVertices(sources);
	        bfs(G, sources);
	    }

	private void bfs(AdjacencyListGraph G, int s) {

		Queue<Integer> q = new Queue<>();
		// in the start initialize distance of source from all vertices to be max
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Integer.MAX_VALUE;
		distTo[s] = 0;// distance of source to itself is 0
		marked[s] = true;
		q.enqueue(s);
		while(!q.isEmpty()) {
			int v = q.dequeue();
			for(int w:G.adj(v)) {
				if(!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v]+1;
					marked[w] = true;
					q.enqueue(w);
				}

			}
		}
	}
	
	  // breadth-first search from multiple sources
    private void bfs(AdjacencyListGraph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<Integer>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }


	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}
	
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }

	@Override
	public boolean hasPathTo(int v) {
		return marked[v];

	}

	@Override
	public Iterable<Integer> pathTo(int v){
		if(!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>();
		while(distTo[v] !=0) {
			path.push(v);
			v = edgeTo[v];
		}
		path.push(v);
		return path;
	}

	@Override
	public int distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}


	// check optimality conditions for single source
	private boolean check(AdjacencyListGraph G, int s) {

		// check that the distance of s = 0
		if (distTo[s] != 0) {
			StdOut.println("distance of source " + s + " to itself = " + distTo[s]);
			return false;
		}

		// check that for each edge v-w dist[w] <= dist[v] + 1
		// provided v is reachable from s
		for (int v = 0; v < G.V(); v++) {
			for (int w : G.adj(v)) {
				if (hasPathTo(v) != hasPathTo(w)) {
					StdOut.println("edge " + v + "-" + w);
					StdOut.println("hasPathTo(" + v + ") = " + hasPathTo(v));
					StdOut.println("hasPathTo(" + w + ") = " + hasPathTo(w));
					return false;
				}
				if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
					StdOut.println("edge " + v + "-" + w);
					StdOut.println("distTo[" + v + "] = " + distTo[v]);
					StdOut.println("distTo[" + w + "] = " + distTo[w]);
					return false;
				}
			}
		}

		// check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
		// provided v is reachable from s
		for (int w = 0; w < G.V(); w++) {
			if (!hasPathTo(w) || w == s) continue;
			int v = edgeTo[w];
			if (distTo[w] != distTo[v] + 1) {
				StdOut.println("shortest path edge " + v + "-" + w);
				StdOut.println("distTo[" + v + "] = " + distTo[v]);
				StdOut.println("distTo[" + w + "] = " + distTo[w]);
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		In in = new In("tinyG.txt");
		AdjacencyListGraph G = new AdjacencyListGraph(in);
		System.out.println(G);
		int s = 0;
		BFSPaths bfs = new BFSPaths(G, s);

		for (int v = 0; v < G.V(); v++) {
			if (bfs.hasPathTo(v)) {
				 StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
				for (int x : bfs.pathTo(v)) {
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

}
