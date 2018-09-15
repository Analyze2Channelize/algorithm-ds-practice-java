package edu.saurabh.sorting;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.StdRandom;

/*  A disorganized carpenter has a mixed pile of N nuts and N bolts.
    The goal is to find the corresponding pairs of nuts and bolts.
    Each nut fits exactly one bolt and each bolt fits exactly one nut.
    By fitting a nut and a bolt together, the carpenter can see which one is bigger
    (but the carpenter cannot compare two nuts or two bolts directly).
    Design an algorithm for the problem that uses NlogN compares (probabilistically).
 */
public class MatchNutsToBoltsLinearthmic {

	static class Nut implements Comparable<Bolt> {
		private int size;

		public Nut(int size) {
			this.size = size;
		}
		@Override
		public int compareTo(Bolt bolt) {
			if (this.size < bolt.size) return -1;
			else if (this.size > bolt.size) return 1;
			else return 0;
		}

		@Override
		public String toString() {
			return String.valueOf(size);

		}

	}

	static class Bolt implements Comparable<Nut>{
		private int size;

		public Bolt(int size) {
			this.size = size;
		}
		@Override
		public int compareTo(Nut nut) {
			if (this.size < nut.size) return -1;
			else if (this.size > nut.size) return 1;
			else return 0;
		}
		@Override
		public String toString() {
			return String.valueOf(size);

		}
	}


	private static void matchPairs(Nut[] nuts, Bolt[] bolts, int low,int high)
	{
		if (low < high)
		{
			// Choose last Bolt for nuts partition.
			int pivot = partition(nuts, low, high, bolts[high]);

			// Now using the partition index of nuts choose that for bolts partition.
			partition(bolts, low, high, nuts[pivot]);

			// Recur for [low...pivot-1] & [pivot+1...high] for nuts and bolts array.
			matchPairs(nuts, bolts, low, pivot-1);
			matchPairs(nuts, bolts, pivot+1, high);
		}
	}

	/*find the bolt which exactly fits the given nut and return its position, O(n)*/
	private static int partition(Comparable[] array,int lo, int hi,Comparable pivot) {
		int i = lo;
		int j = hi+1;
		while (true) {
			// traverse Array from left to right ,stop when element is equal or greater than Pivot
			while (less(array[++i],pivot)) if (i == hi) break;
			// traverse Array from right to left ,stop when a element is equal or less than Pivot
			while (less(pivot,array[--j])) if (j == lo) break;
			if (i >= j) break;
			exch(array,i,j);
		}
		return j;

	}
	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}



	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}

	public static void main(String[] args)
	{
		int N = 10;
		Nut nuts[] = new Nut[N];
		Bolt bolts[] = new Bolt[N];
		Set<Integer> usedIndicesForNuts = new HashSet<>();
		Set<Integer> usedIndicesForBolts = new HashSet<>();

		// simulate mixup of nuts and bolts using random shuffle,can't do standard shuffle since nuts cannot be compared to nuts, and same for bolts
		for(int i=0;i<N;i++) {
			Nut n = new Nut(i);
			Bolt b = new Bolt(i);
			while(true) {
				int index = StdRandom.uniform(N);
				if(!usedIndicesForNuts.contains(index)) {
					nuts[index] = n;
					usedIndicesForNuts.add(index);
					break;
				}
			}
			while(true) {
				int index = StdRandom.uniform(N);
				if(!usedIndicesForBolts.contains(index)) {
					bolts[index] = b;
					usedIndicesForBolts.add(index);
					break;
				}
			}
		}

		System.out.println("Jumbled nuts and bolts are : ");
		show(nuts);
		show(bolts);

		// Method based on quick select which matches nuts and bolts
		matchPairs(nuts, bolts, 0, N-1);

		System.out.println("Matched nuts and bolts are : ");
		show(nuts);
		show(bolts);
	}

}
