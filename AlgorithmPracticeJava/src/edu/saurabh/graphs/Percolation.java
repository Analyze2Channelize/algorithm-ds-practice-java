package edu.saurabh.graphs;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	WeightedQuickUnionUF uf;
	int []openBlocked;// holds open close state for each site, 0 means blocked, 1 means open
	int maxRowCol=0;
	int numOfOpenSites = 0;
	boolean anySiteOpenInFirstRow = false;
	public Percolation(int n)    {
		if (n <= 0) {
			throw new IllegalArgumentException("illegal");
		}
		maxRowCol=n;
		uf = new WeightedQuickUnionUF(n*n);
		openBlocked = new int[n*n];
		for(int i=0;i<n*n;i++) {
			openBlocked[i] = 0; //initially all sites blocked
		}

	}

	public  void open(int row, int col) {
		validateSite(row, col);
		int nodeId = getFlattenedIndex(row,col);
		if(!isOpen(row,col)){
			openBlocked[nodeId] = 1;
			if(!anySiteOpenInFirstRow && row==1) { // first row
				anySiteOpenInFirstRow=true;
			}
			// connect the neighbors
			if(isValidSite(row-1,col) && isOpen(row-1,col)) uf.union(getFlattenedIndex(row-1,col),getFlattenedIndex(row,col));
			if(isValidSite(row+1,col) && isOpen(row+1,col)) uf.union(getFlattenedIndex(row+1,col),getFlattenedIndex(row,col));
			if(isValidSite(row,col-1) && isOpen(row,col-1)) uf.union(getFlattenedIndex(row,col-1),getFlattenedIndex(row,col));
			if(isValidSite(row,col+1) && isOpen(row,col+1)) uf.union(getFlattenedIndex(row,col+1),getFlattenedIndex(row,col));
			numOfOpenSites++;
		}
	}

	private boolean isValidSite(int row, int col) {
		return (row >=1 && row <=maxRowCol && col >=1 && col<=maxRowCol);
	}

	private int getFlattenedIndex(int row, int col) {
		return (row-1)*(maxRowCol)+(col-1);
	}

	public boolean isOpen(int row, int col)  {
		validateSite(row, col);
		int nodeId = getFlattenedIndex(row,col);
		return openBlocked[nodeId] == 1;

	}
	public boolean isFull(int row, int col)  {
		validateSite(row, col);
		if(!isOpen(row,col) || numOfOpenSites==0 || !anySiteOpenInFirstRow) return false;
		for(int firstRowIndex=1;firstRowIndex<=maxRowCol;firstRowIndex++) {
			int nodeId = getFlattenedIndex(1,firstRowIndex);
			if(openBlocked[nodeId] ==1 && uf.connected(nodeId,getFlattenedIndex(row,col))) {
				return true;
			}
		}
		return false;

	}
	public  int numberOfOpenSites()   {
		return numOfOpenSites;

	}
	public boolean percolates()     {
		for(int i=1;i<=maxRowCol;i++) {
			if(isFull(maxRowCol,i)) {
				return true;
			}
		}
		return false;

	}

	private void validateSite(int row, int col) {
		if (row <= 0 || row > maxRowCol ) {
			throw new IllegalArgumentException("illegal");
		}
		if (col <= 0 || col > maxRowCol ) {
			throw new IllegalArgumentException("illegal");
		}
	}

	public static void main(String[] args) {

	}


}
