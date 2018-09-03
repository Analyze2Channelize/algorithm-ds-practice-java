package edu.saurabh.sorting;

import java.util.Arrays;

public class InsertionSort {

	public static void sortInPlace(Integer [] array) {
		
		int length = array.length;
		for (int j =1 ; j< length;j++) {
			Integer key = array[j];
			int i = j-1;
			while(i >= 0 && array[i] > key)
			{
				array[i+1] = array[i];
				i = i-1;
			}
			array[i+1] = key;
			
		}
		
	}
	
	public static void main(String args[]) {
		Integer [] unsorted = new Integer[] {10,23,4,8,6,1};
		System.out.println(Arrays.toString(unsorted));
		sortInPlace(unsorted);
		System.out.println(Arrays.toString(unsorted));
		
		
	}
}
