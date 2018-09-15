package edu.saurabh.sorting;

import java.util.Comparator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MergeSort {

	public static void merge(Comparable[] array,Comparable[] aux,int low ,int mid, int high) {

		isSorted(array, low, mid);
		isSorted(array, mid+1, high);
		for ( int k=low;k<=high;k++) {
			aux[k] = array[k];
		}

		int i = low;
		int j = mid+1;
		for(int k =low;k<=high;k++) {
			if(i>mid) array[k] = aux[j++];
			else if( j> high) array[k] = aux[i++];
			else if(less(aux[j],aux[i])) array[k] = aux[j++];
			else array[k] = aux[i++];
		}
		isSorted(array, low, high);
	}

	

	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	// is v < w ?
	private static boolean less(Comparator comparator, Object v, Object w) {
		return comparator.compare(v, w) < 0;
	}

	// is the array sorted from a[lo] to a[hi]
	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}

	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}

	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length-1);
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

	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		MergeSort.sort(a);
		show(a);
		
	}


}
