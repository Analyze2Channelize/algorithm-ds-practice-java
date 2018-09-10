package edu.saurabh.sorting;

import java.util.Comparator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class InsertionSort {

    // This class should not be instantiated.
    private InsertionSort() { }

    public static void sort(Comparable[] a) {
    	int n = a.length;
    	for(int i=0;i<n;i++) {
    		for(int j=i;j>0 && less(a[j],a[j-1]) ; j--) {
    				exch(a,j,j-1);
    		}
    	}
    }

  
    public static void sort(Object[] a, Comparator comparator) {
    	int n = a.length;
    	for(int i=0;i<n;i++) {
    		for(int j=i;j>0;j--) {
    			if(less(comparator,a[j],a[j-1])) {
    				exch(a,j,j-1);
    			}else {
    				break;
    			}
    		}
    		assert(isSorted(a,comparator,0,i));
    	}
    	assert(isSorted(a,comparator,0,n));
         
    }


   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
    }
        
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/

    // is the array a[] sorted?
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }
        
    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is the array a[] sorted?
    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(comparator, a[i], a[i-1])) return false;
        return true;
    }



    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }


    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        InsertionSort.sort(a);
        show(a);
    }
}

