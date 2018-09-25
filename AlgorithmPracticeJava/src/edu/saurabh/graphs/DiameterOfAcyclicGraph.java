package edu.saurabh.graphs;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/*O(n)- Given a graph that is a tree (connected and acyclic), find the longest path, 
 * i.e., a pair of vertices v and w that are as far apart as possible.
 * **/
/*Pick any vertex v. Compute the shortest path from v to every other vertex. 
 * Let w be the vertex with the largest shortest path distance. Compute the shortest path from w to every other vertex. 
 * Let x be the vertex with the largest shortest path distance. The path from w to x gives the diameter*/
public class DiameterOfAcyclicGraph {

	private int[] edgeTo;
	private int[] distTo;
	private boolean[] marked;
	private Stack<Integer> diameter;



	public DiameterOfAcyclicGraph(AdjacencyListGraph G) {

		initializeAuxiliaryData(G);
		//int v = StdRandom.uniform(G.V());
		System.out.println("Running bfs with source node:"+2);
		// first bfs
		int w = bfs(G,2);
		System.out.println("First longest path");
		printPath(getPath(w));
		// second bfs
		initializeAuxiliaryData(G);
		System.out.println("Running bfs with source node:"+w);
		int x = bfs(G,w);
		System.out.println("Second longest path");
		printPath(getPath(x));
		diameter = getPath(x);

	}

	private int bfs(AdjacencyListGraph G, int s) {

		Queue<Integer> q = new Queue<>();
		marked[s] = true;
		distTo[s] = 0;
		int maxDistance = Integer.MIN_VALUE;
		int farthestNode = s;
		q.enqueue(s);

		while(!q.isEmpty()) {
			int  v = q.dequeue();
			for(int w: G.adj(v)) {
				if(!marked[w]) {
					marked[w] = true;
					edgeTo[w]= v;
					distTo[w] = distTo[v]+1;
					if(distTo[w] > maxDistance) {
						maxDistance = distTo[w];
						farthestNode = w;
					}
					q.enqueue(w);
				}
			}
		}
		return farthestNode;
	}

	public void printPath(Iterable<Integer> s) {
		System.out.println();
		for(int i:s) {
			System.out.print(i+" ");
		}
		System.out.println();
	}

	private Stack<Integer> getPath(int v) {
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;
	}

	public Iterable<Integer> diameter(){
		return diameter;
	}

	public int center() {
		int [] nodesOnDiameter = new int[diameter.size()];
		int i=0;
		for(int v:diameter) {
			nodesOnDiameter[i++]= v;
		}
		return nodesOnDiameter[diameter.size()/2];

	}

	private void initializeAuxiliaryData(AdjacencyListGraph G) {
		edgeTo = new int[G.V()];
		distTo = new int[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Integer.MAX_VALUE;
		marked = new boolean[G.V()];
	}


	public static void main(String [] args) {
		AdjacencyListGraph g = new AdjacencyListGraph(10); 
		g.addEdge(0, 1); 
		g.addEdge(1, 2); 
		g.addEdge(2, 3); 
		g.addEdge(2, 9); 
		g.addEdge(2, 4); 
		g.addEdge(4, 5); 
		g.addEdge(1, 6); 
		g.addEdge(6, 7); 
		g.addEdge(6, 8); 

		DiameterOfAcyclicGraph d = new DiameterOfAcyclicGraph(g);
		d.printPath(d.diameter());
		System.out.println("Center Node:"+d.center());

	}

}
