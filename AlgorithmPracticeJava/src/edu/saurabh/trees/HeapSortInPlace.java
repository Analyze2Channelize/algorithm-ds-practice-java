package edu.saurabh.trees;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class HeapSortInPlace {

	private HeapSortInPlace() { }

	public static void sort(Comparable[] pq) {
		int n = pq.length;
		/*bottom up heap-construction, 
		 * all nodes to the right of middle element are already heaps,so we just need to pink their roots from bottom to top and sink them*/
		for (int k = n/2; k >= 1; k--)
			sink(pq, k, n);
		/*sortdown phase, repeatedly swap largest with smallest and sink largest*/
		while (n > 1) {
			exch(pq, 1, n--);
			sink(pq, 1, n);
		}
	}


	private static void sink(Comparable[] pq, int k, int n) {
		while (2*k <= n) {
			int j = 2*k;
			if (j < n && less(pq, j, j+1)) j++;
			if (!less(pq, k, j)) break;
			exch(pq, k, j);
			k = j;
		}
	}

	private static boolean less(Comparable[] pq, int i, int j) {
		return pq[i-1].compareTo(pq[j-1]) < 0;
	}

	private static void exch(Object[] pq, int i, int j) {
		Object swap = pq[i-1];
		pq[i-1] = pq[j-1];
		pq[j-1] = swap;
	}
	
	 // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

  
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        HeapSortInPlace.sort(a);
        show(a);
    }
}
