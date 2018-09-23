package edu.saurabh.graphs;

public interface Paths {

	/*is there a path between source and vertex*/
	public boolean hasPathTo(int v);

	/*returns the path from source to vertex*/
	public Iterable<Integer> pathTo(int v);

	/*return the number of edges in path from source to vertex v*/
	int distTo(int v);
	
}
