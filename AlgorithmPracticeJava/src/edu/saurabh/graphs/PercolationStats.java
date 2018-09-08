package edu.saurabh.graphs;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private final double mean;
	private final double stddev;
	private final double confidenceLo;
	private final double confidenceHi;
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <=0) {
			throw new IllegalArgumentException("illegal");
		}
		double[] percolationThresholds = new double[trials];
		for(int i =0;i<trials;i++) {
			Percolation p = new Percolation(n);	
			while(true) {
				int row = StdRandom.uniform(1, n+1);
				int col = StdRandom.uniform(1, n+1);
				if(p.isOpen(row, col)) continue;
				// if we are here we got a blocked site
				p.open(row, col);
				if(p.percolates()) {
					percolationThresholds[i] = (double)p.numberOfOpenSites()/(n*n);
					break;
				}

			}
		}
		mean = StdStats.mean(percolationThresholds);
		stddev = StdStats.stddev(percolationThresholds);
		confidenceLo = mean - (1.96*stddev)/Math.sqrt(trials);
		confidenceHi = mean + (1.96*stddev)/Math.sqrt(trials);
	}


	public double mean()  {
		return mean;

	}

	public double stddev() {	
		return stddev;

	}

	public double confidenceLo() {
		return confidenceLo;

	}

	public double confidenceHi()  {
		return confidenceHi;

	}
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);
		int trials =  Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(n,trials);
		StdOut.println("mean="+ps.mean());
		StdOut.println("stddev="+ps.stddev());
		StdOut.println("95% confidence interval=["+ps.confidenceLo()+", "+ps.confidenceHi()+"]");
	}

}
