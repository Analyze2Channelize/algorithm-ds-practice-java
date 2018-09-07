package edu.saurabh.graphs;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickFindUF {

	private int[] nodes;
	int count;

	public QuickFindUF(int N){
		count = N;
		nodes = new int[N];
		//initially each node in its own component
		for(int i=0 ; i< N;i++) {
			nodes[i] =i;
		}
	}

	public int count() {
		return count;
	}

	private void validate(int p) {
		int n = nodes.length;
		if (p < 0 || p >= n) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
		}
	}

	public boolean connected(int p ,int q) {
		validate(p);
		validate(q);
		return nodes[p]==nodes[q];// in same component or not ?
	}

	public void union(int p,int q) {
		validate(p);
		validate(q);
		if(!connected(p,q)) {
			int pComponent = nodes[p];
			int qComponent = nodes[q];
			for(int i =0;i<nodes.length;i++) {
				if(nodes[i] == pComponent) {
					nodes[i]= qComponent;
				}
			}
			count--;
		}
	}
	public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickFindUF uf = new QuickFindUF(n);
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
