package edu.saurabh.sorting;

import edu.princeton.cs.algs4.StdOut;

public class MergeWithHalfAuxiliary {

	

	private static void mergeWithHalfAux(Comparable[] array,Comparable[] firstHalf) {

		int N = array.length/2;
		for ( int k=0;k<N;k++) {
			firstHalf[k] = array[k];
		}

		int i = 0;
		int j = N;
		for(int k =0;k<2*N;k++) {

			if(i>=N) {
				array[k] = array[j++];
			}
			else if( j>= 2*N) {
				array[k] = firstHalf[i++];
			}
			else if(less(array[j],firstHalf[i])) {
				array[k] = array[j++];
			}
			else {
				array[k] = firstHalf[i++];
			}
		}
	}

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
		
		Comparable[] b = {40, 61, 70, 71, 99, 20, 51, 55, 75, 100};
		int N = b.length / 2;
		Comparable[] aux = new Comparable[N];
		mergeWithHalfAux(b, aux);
		System.out.println("After merging:");
		show(b);
	}


}
