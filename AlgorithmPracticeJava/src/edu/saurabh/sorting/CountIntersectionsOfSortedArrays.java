package edu.saurabh.sorting;

public class CountIntersectionsOfSortedArrays {

	public static int intersectionsWhenArraysAlmostSameSize(Comparable[] a,Comparable[] b) {

		int m = a.length;
		int n = b.length;
		int count =0;
		int i=0,j=0;
		while(i<m && j<n) {

			if(less(a[i],b[j])) {
				i++;
			}
			else if(less(b[j],a[i])) {
				j++;
			}
			else {
				count++;
				i++;
				j++;
			}
		}
		return count;
	}

	/*use binary search in larger array*/
	public static int intersectionsWhenArraysVeryDifferentSameSize(Comparable[] a,Comparable[] b) {

		
		Comparable[] smallerArray;
		Comparable[] largerArray;
		if(a.length< b.length) {
			smallerArray = a;
			largerArray= b;
		}else {
			smallerArray = b;
			largerArray =a;
		}
		int count =0;
		int lo=0;
		int hi = largerArray.length-1;
		
		for(int i=0;i<smallerArray.length;i++) {
			int indexInLargeArray = BinarySearch.search(largerArray, smallerArray[i],lo,hi);
			if(indexInLargeArray==-1) continue;
			count++;
			lo = indexInLargeArray+1;
			
		}
		
		return count;
	}



	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	// is the array sorted from a[lo] to a[hi]
	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}


	public static void main(String[] args) {
		
		Integer[] a = new Integer[] {7,5,1,3,8};
		MergeSort.sort(a);
		assert isSorted(a);
		Integer[] b = new Integer[] {10,11,9,7,1};
		MergeSort.sort(b);
		assert isSorted(b);
		int count1 = CountIntersectionsOfSortedArrays.intersectionsWhenArraysAlmostSameSize(a,b);
		System.out.println("Intersection count#1:"+count1);
		Integer[] c = new Integer[] {10,11,9,7,1,5,7,8,21,56,89};
		MergeSort.sort(c);
		int count2 = CountIntersectionsOfSortedArrays.intersectionsWhenArraysVeryDifferentSameSize(a,c);
		System.out.println("Intersection count#2:"+count2);

	}


}
