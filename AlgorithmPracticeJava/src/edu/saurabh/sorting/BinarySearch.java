package edu.saurabh.sorting;

import edu.princeton.cs.algs4.StdIn;

public class BinarySearch {


	private BinarySearch() {}

	public static int search(Comparable[] a,Comparable searchItem, int lo,int hi) {
		assert isSorted(a);

		if(hi>=lo) {
			int mid = lo + (hi-lo)/2;
			int cmp = searchItem.compareTo(a[mid]);
			if(cmp <0){
				return search(a,searchItem,lo,mid-1);
			}else if(cmp>0) {
				return search(a,searchItem,mid+1,hi);
			}else {
				return mid;
			}	
		}
		return -1;

	}
	
	public static int rank(Comparable[] a,Comparable k) {
		// assume array is sorted in ascending order, return number of elements < k
		int lo=0,hi = a.length-1;
		while(lo<=hi) {
			int mid = lo + (hi-lo)/2;
			int cmp = k.compareTo(a[mid]);
			if(cmp <0){
				hi = mid-1;
			}else if(cmp>0) {
				lo=mid+1;
			}else {
				return mid;
			}	
		}
		return lo;

	}

	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}


	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}

	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		String search = "A";
		if(!isSorted(a)) {
			MergeSort.sort(a);
		}
		int index = BinarySearch.search(a, search, 0,a.length-1);
		if(index==-1) {
			System.out.println("Not found!");
		}else {
			System.out.println("Index:"+index);
		}

	}

}
