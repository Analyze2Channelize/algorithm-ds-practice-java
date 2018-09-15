package edu.saurabh.sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
/*Using quick select 10 times for k=0...n/10
*/
public class DecimalDominantsLinear {
	

	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	


	// print array to standard output
	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.println(a[i]);
		}
	}

	
	
	public static void main(String[] args) {
		Integer[] a = new Integer[] {1,2,5,6,7,8,9,20,100,5,1,1,5,6,7,5,19,16,5,10,5,11,5,5,5};
	    int n = a.length;
	    StdOut.println("Length:"+n+",n/10:"+n/10);
	    Comparable[] quintiles = new Comparable[10];
	    int [] quintileCounts = new int[10];
	    //find quintile elements
	    /* If an element occurs more than n/10 times, then it must be at position i*n/10 for some i in the range 0 to n/10 in the sorted array*/
        for (int i = 0; i < 10; i++) {
        	quintiles[i] = QuickSelect.select(a, i*(n/10));
        }
		
        for(int i =0;i<10;i++) {
        	Comparable q = quintiles[i];
        	for(int j=0;j<n;j++) {
        		if(a[j].equals(q)) quintileCounts[i]++;
        	}
        }
        System.out.println("====Decimal Dominants======");
        for(int i =0;i<10;i++) {
        	if(quintileCounts[i] > n/10) {
        		System.out.println("key:"+quintiles[i]+",count:"+quintileCounts[i]);	
        	}
        	
        }
	}


}
