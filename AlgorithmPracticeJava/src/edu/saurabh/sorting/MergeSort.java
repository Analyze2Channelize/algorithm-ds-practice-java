package edu.saurabh.sorting;

import java.util.Arrays;

public class MergeSort {

	public static void merge(int[] array, int l ,int m, int r) {
		int n1 = m-l+1;
		int n2 = r-m;
		int L[] = new int[n1];
		int R[] = new int[n2];
		
		for(int i=0;i<n1;i++) {
			L[i] = array[l+i];
		}
		for(int j=0;j<n2;j++) {
			R[j] = array[m+1+j];
		}
		
		int i=0,j=0,k = l;
		
		while(i < n1 && j<n2) {
			if (L[i] <= R[j]) {
				array[k] = L[i];
				i++;
			}else {
				array[k] = R[j];
				j++;
			}
			k++;
		}
		
		while(i<n1) {
			array[k] = L[i];
			k++;i++;
		}
		while(j<n2) {
			array[k] = R[j];
			k++;j++;
		}
		System.out.println("merge:l="+l+",m="+m+",r="+r+",array"+Arrays.toString(array));
	}
	
	public static void mergeSort(int [] array,int l,int r) {
		if (r>l) {
			int m = l + (r-l)/2;
			mergeSort(array,l,m);
			mergeSort(array,m+1,r);
			merge(array,l,m,r);
		}
	}
	
	public static void main(String args[]) {
		int [] unsorted = new int[] {10,23,4,8,6,1};
		System.out.println(Arrays.toString(unsorted	));
		mergeSort(unsorted,0,unsorted.length-1);
		System.out.println(Arrays.toString(unsorted));
		
		
	}
}
