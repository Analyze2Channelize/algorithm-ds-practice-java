package edu.saurabh.graphs;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final WeightedQuickUnionUF uf;
	private int [] siteStatus;// holds open close state for each site, 0 means blocked, 1 means open,2 means full
	private final int maxRowCol;
	private int numOfOpenSites = 0;
	private boolean anySiteOpenInFirstRow = false;
	public Percolation(int n)    {
		if (n <= 0) {
			throw new IllegalArgumentException("illegal");
		}
		maxRowCol=n;
		uf = new WeightedQuickUnionUF(n*n);
		siteStatus = new int[n*n];
		for(int i=0;i<n*n;i++) {
			siteStatus[i] = 0; //initially all sites blocked
		}

	}

	public  void open(int row, int col) {
		validateSite(row, col);
		int nodeId = getFlattenedIndex(row,col);
		if(!isOpen(row,col)){
			siteStatus[nodeId] = 1;
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
		return (siteStatus[nodeId] == 1 || siteStatus[nodeId]==2);

	}
	public boolean isFull(int row, int col)  {
		validateSite(row, col);
		if(!isOpen(row,col) || numOfOpenSites==0 || !anySiteOpenInFirstRow) return false;
		int nodeId = getFlattenedIndex(row,col);
		if(siteStatus[nodeId] ==2) return true;
		boolean connectedToTop= checkConnectedToTop(row, col);
		if(connectedToTop) {
			siteStatus[nodeId] = 2;
			// set open neighbors also to connected
			if((isValidSite(row-1,col) && isOpen(row-1,col) )) {
				int a = getFlattenedIndex(row-1,col);
				siteStatus[a] = 2;
			}
			if((isValidSite(row+1,col) && isOpen(row+1,col) )) {
				int a = getFlattenedIndex(row+1,col);
				siteStatus[a] = 2;
			}
			if((isValidSite(row,col-1) && isOpen(row,col-1) )) {
				int a = getFlattenedIndex(row,col-1);
				siteStatus[a] = 2;
			}
			if((isValidSite(row,col+1) && isOpen(row,col+1) )) {
				int a = getFlattenedIndex(row,col+1);
				siteStatus[a] = 2;
			}
		}
		return connectedToTop;

	}

	private boolean checkConnectedToTop(int row, int col) {
		for(int firstRowIndex=1;firstRowIndex<=maxRowCol;firstRowIndex++) {
			int nodeId = getFlattenedIndex(1,firstRowIndex);
			if(isOpen(1,firstRowIndex) && uf.connected(nodeId,getFlattenedIndex(row,col))) {
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

}
