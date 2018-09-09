package edu.saurabh.graphs;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final WeightedQuickUnionUF uf;
	private final WeightedQuickUnionUF backwash;
	private int vtop;
	private int vbot;
	private boolean [] siteStatus;// holds open close state for each site, 0 means blocked, 1 means open
	private final int size;
	private int numOfOpenSites = 0;
	public Percolation(int n)    {
		if (n <= 0) {
			throw new IllegalArgumentException("illegal");
		}
		size=n;
		vtop=0;
		vbot = n*n+1;
		uf = new WeightedQuickUnionUF(n*n+2);
		backwash = new WeightedQuickUnionUF(n*n+1);
		siteStatus = new boolean[n*n+2];
	}

	public  void open(int row, int col) {
		validateSite(row, col);
		int nodeId = getFlattenedIndex(row,col);
		if(!isOpen(row,col)){
			siteStatus[nodeId] = true;
			if(isTopRow(row)) {
				siteStatus[vtop]=true;
				uf.union(vtop, getFlattenedIndex(row, col));
				backwash.union(vtop, getFlattenedIndex(row, col));
			}
			if (isBotRow(row)) {
				siteStatus[vbot] = true;
				// make sites in bottom row union to virtual bottom
				uf.union(vbot, getFlattenedIndex(row, col));
			}
			// connect the neighbors
			if(isValidSite(row-1,col) && isOpen(row-1,col)) {
				uf.union(getFlattenedIndex(row-1,col),nodeId);
				backwash.union(getFlattenedIndex(row-1,col),nodeId);
			}
			if(isValidSite(row+1,col) && isOpen(row+1,col)) { 
				uf.union(getFlattenedIndex(row+1,col),nodeId);
				backwash.union(getFlattenedIndex(row+1,col),nodeId);
			}
			if(isValidSite(row,col-1) && isOpen(row,col-1)) {
				uf.union(getFlattenedIndex(row,col-1),nodeId);
				backwash.union(getFlattenedIndex(row,col-1),nodeId);
			}
			if(isValidSite(row,col+1) && isOpen(row,col+1)) {
				uf.union(getFlattenedIndex(row,col+1),nodeId);
				backwash.union(getFlattenedIndex(row,col+1),nodeId);
			}
			numOfOpenSites++;
		}
	}
	
	private boolean isTopRow(final int i) {
		return i == 1;
	}
	
	private boolean isBotRow(final int i) {
		return i == size;
	}

	private boolean isValidSite(int row, int col) {
		return (row >=1 && row <=size && col >=1 && col<=size);
	}

	private int getFlattenedIndex(int row, int col) {
		return (row-1)*(size)+col;
	}

	public boolean isOpen(int row, int col)  {
		validateSite(row, col);
		int nodeId = getFlattenedIndex(row,col);
		return (siteStatus[nodeId]);

	}
	public boolean isFull(int row, int col)  {
		validateSite(row, col);
		if(!isOpen(row,col)) return false;
		int nodeId = getFlattenedIndex(row,col);
		return backwash.connected(vtop, nodeId);

	}

	public  int numberOfOpenSites()   {
		return numOfOpenSites;

	}

	public boolean percolates()     {
		return uf.connected(vtop, vbot);

	}

	private void validateSite(int row, int col) {
		if (row <= 0 || row > size ) {
			throw new IllegalArgumentException("illegal");
		}
		if (col <= 0 || col > size ) {
			throw new IllegalArgumentException("illegal");
		}
	}

}
