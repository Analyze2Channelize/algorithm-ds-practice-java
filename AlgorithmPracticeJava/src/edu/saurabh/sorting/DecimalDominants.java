package edu.saurabh.sorting;

import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class DecimalDominants {

	public static Map<Comparable, Integer> findCounts(Comparable[] a) {
		StdRandom.shuffle(a);
		Map<Comparable,Integer> keyCounts = new HashMap<>();
		for(Comparable c: a) {
			keyCounts.put(c, 1);// everything occurs atleast once
        }
		findCounts(a, 0, a.length - 1,keyCounts);
		return keyCounts;
	}

	public static void findCounts(Comparable[] array,int lo, int hi,Map<Comparable,Integer> keyCounts) {

		if(hi<=lo) return;
		int lt = lo,i =lo+1,gt=hi;
		while(i<=gt) {
			if(less(array[i],array[lt])) {
				exch(array,i++,lt++);
			}else if(less(array[lt],array[i])) {
				exch(array,i,gt--);
			}else {
				i++;
				if(keyCounts.containsKey(array[lt])) {
					int currentCount = keyCounts.get(array[lt]);
					keyCounts.put(array[lt], currentCount+1);
				}
			}
		}
		findCounts(array,lo,lt-1,keyCounts);
		findCounts(array,gt+1,hi,keyCounts);

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
	        int N = a.length;
	        Map<Comparable, Integer> keyCounts = DecimalDominants.findCounts(a);
	        System.out.println("==Counts===");
	        for(Map.Entry<Comparable, Integer> en: keyCounts.entrySet()) {
	        	System.out.println("key:"+en.getKey()+",count:"+en.getValue());
	        }
	        System.out.println("==Decimal Dominants===");
	        for(Map.Entry<Comparable, Integer> en: keyCounts.entrySet()) {
	        	if(en.getValue() > N/2) {
	        		System.out.println("key:"+en.getKey()+",count:"+en.getValue());
	        	}
	        	
	        }
	    }


}
