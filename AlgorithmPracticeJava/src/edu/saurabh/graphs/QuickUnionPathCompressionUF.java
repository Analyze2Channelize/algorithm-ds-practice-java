package edu.saurabh.graphs;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnionPathCompressionUF {

	private int[] parents;
	int count;

	public QuickUnionPathCompressionUF(int N){
		count = N;
		parents = new int[N];
		//initially each node in its own component
		for(int i=0 ; i< N;i++) {
			parents[i] =i;
		}
	}

	public int count() {
		return count;
	}

	private void validate(int p) {
		int n = parents.length;
		if (p < 0 || p >= n) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
		}
	}

	public boolean connected(int p ,int q) {
		validate(p);
		validate(q);
		return getRoot(p)==getRoot(q);// share same root
	}

	public int getRoot(int p) {
		int root = p;
		while(root!=parents[root]) {
			root = parents[root];
		}
		// point every node along the path to root
		while(p!=root) {
			int newP = parents[p];
			parents[p] = root;
			p = newP;
		}
		return root;
	}

	public void union(int p,int q) {
		validate(p);
		validate(q);
		int rootOfP = getRoot(p);
		int rootOfQ = getRoot(q);
		if(rootOfP == rootOfQ) return;
		parents[rootOfP] = rootOfQ;
		count--;
	}
	public static void main(String[] args) {
		int n = StdIn.readInt();
		QuickUnionPathCompressionUF uf = new QuickUnionPathCompressionUF(n);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (uf.connected(p, q)) continue;
			uf.union(p, q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(uf.count() + " components");
	}
}
