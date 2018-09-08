package edu.saurabh.graphs;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class ErdosRenyi {

	public static int count(int n) {
        int edges = 0;
        WeightedQuickUnionByHeightPathHalving uf = new WeightedQuickUnionByHeightPathHalving(n);
        while (uf.count() > 1) {
            int i = StdRandom.uniform(n);
            int j = StdRandom.uniform(n);
            uf.union(i, j);
            edges++;
        }
        return edges;
    }

    public static void main(String[] args) {
    	int n = StdIn.readInt();       // number of vertices
        int trials = StdIn.readInt();     // number of trials
        int[] edges = new int[trials];

        // repeat the experiment trials times
        for (int t = 0; t < trials; t++) {
            edges[t] = count(n);
        }

        // report statistics
        StdOut.println("1/2 n ln n = " + 0.5 * n * Math.log(n));
        StdOut.println("mean       = " + StdStats.mean(edges));
        StdOut.println("stddev     = " + StdStats.stddev(edges));
    }

}
