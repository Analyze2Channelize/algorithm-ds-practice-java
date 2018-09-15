package edu.saurabh.sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {

	public static int partition(Comparable[] array,int lo, int hi) {
		int i = lo,j =hi+1;
		Comparable pivot = array[lo];
		while(true) {
			// traverse left to right ,stop when current element is equal or greater
			while(less(array[++i],pivot)) {
				if(i==hi) break;
			}
			// traverse right to left , stop when current element is equal or lesser
			while(less(pivot,array[--j])) {
				if(j==lo) break;
			}
			
			if(i>=j) {
				// indices have crossed
				break;
			}
			exch(array,i,j);		
		}
		exch(array,lo,j);
		return j;
		
	}

	

	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	// is the array sorted from a[lo] to a[hi]
	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}

	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo) return;
		int partitionIndex = partition(a,lo,hi);
		sort(a,lo, partitionIndex-1);
		sort(a,partitionIndex+1, hi);
	}

	public static void sort(Comparable[] a) {
		StdRandom.shuffle(a);
		sort(a,0, a.length-1);
		assert isSorted(a);
	}


	// print array to standard output
	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.println(a[i]);
		}
	}

	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	 // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
	
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		QuickSort.sort(a);
		show(a);
		assert isSorted(a);
		
		
	}


}
