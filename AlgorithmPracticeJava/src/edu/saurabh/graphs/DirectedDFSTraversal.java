package edu.saurabh.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DirectedDFSTraversal {

	private int[] pre; // pre[v] is preorder number of v
	private int[] post;
	private int preCounter;
	private int postCounter;
	private Queue<Integer> preOrder;
	private Queue<Integer> postOrder;
	private boolean[] marked;

	public DirectedDFSTraversal(AdjacencyListDigraph G) {
		preOrder = new Queue<>();
		postOrder =  new Queue<>();
		pre = new int[G.V()];
		post = new int[G.V()];
		marked = new boolean[G.V()];
		for(int v=0;v<G.V();v++) {
			if(!marked[v]) {
				dfs(G,v);
			}
		}
	}

	private void dfs(AdjacencyListDigraph G,int v) {

		marked[v] = true;
		pre[v] = preCounter++;
		preOrder.enqueue(v);

		for(int w: G.adj(v)) {
			if(!marked[w]) {
				dfs(G,w);
			}
		}
		postOrder.enqueue(v);
		post[v] = postCounter++;
	}

	public Iterable<Integer> preorder(){
		return preOrder;
	}

	public Iterable<Integer> postorder(){
		return postOrder;
	}

	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}

	public Iterable<Integer> reversepostorder(){
		Stack<Integer> reverse = new Stack<Integer>();
		for (int v : postOrder)
			reverse.push(v);
		return reverse;
	}

	public int pre(int v) {
		validateVertex(v);
		return pre[v];
	}

	public int post(int v) {
		validateVertex(v);
		return post[v];
	}

	public static void main(String[] args) {
		In in = new In("tinyDG.txt");
		AdjacencyListDigraph G = new AdjacencyListDigraph(in);

		DirectedDFSTraversal dfs = new DirectedDFSTraversal(G);
		StdOut.println("   v  pre post");
		StdOut.println("--------------");
		for (int v = 0; v < G.V(); v++) {
			StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
		}

		StdOut.print("Preorder:  ");
		for (int v : dfs.preorder()) {
			StdOut.print(v + " ");
		}
		StdOut.println();

		StdOut.print("Postorder: ");
		for (int v : dfs.postorder()) {
			StdOut.print(v + " ");
		}
		StdOut.println();

		StdOut.print("Reverse postorder: ");
		for (int v : dfs.reversepostorder()) {
			StdOut.print(v + " ");
		}
		StdOut.println();

	}

}
