package edu.saurabh.graphs;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnionUF {

	private int[] parents;
	int count;

	public QuickUnionUF(int N){
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
		while(p!=parents[p]) {
			p = parents[p];
		}
		return p;
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
		QuickUnionUF uf = new QuickUnionUF(n);
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
