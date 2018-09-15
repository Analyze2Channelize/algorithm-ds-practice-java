package edu.saurabh.sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Quick3WaySort {

	public static void sort(Comparable[] a) {
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
		assert isSorted(a);
	}

	public static void sort(Comparable[] array,int lo, int hi) {

		if(hi<=lo) return;
		int lt = lo,i =lo+1,gt=hi;
		while(i<=gt) {
			if(less(array[i],array[lt])) {
				exch(array,i++,lt++);
			}else if(less(array[lt],array[i])) {
				exch(array,i,gt--);
			}else {
				i++;
			}
		}
		sort(array,lo,lt-1);
		sort(array,gt+1,hi);
		assert isSorted(array, lo, hi);

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
	        Quick3WaySort.sort(a);
	        show(a);
	    }


}
