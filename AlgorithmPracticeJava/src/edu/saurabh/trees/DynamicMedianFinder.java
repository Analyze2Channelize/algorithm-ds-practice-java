package edu.saurabh.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdRandom;

public class DynamicMedianFinder {

	private final MinPQ<Integer> minPQ;
	private final MaxPQ<Integer> maxPQ;

	public DynamicMedianFinder() {
		minPQ = new MinPQ<>();
		maxPQ = new MaxPQ<>();
	}

	public float median() {
		int minSize = minPQ.size();
		int maxSize = maxPQ.size();
		if (minSize == 0 && maxSize == 0) {
			return 0;
		}
		if (minSize > maxSize) {
			return minPQ.min();
		}
		if (minSize < maxSize) {
			return maxPQ.max();
		}
		return (minPQ.min()+maxPQ.max())/2F;
	}

	public void insert(int element) {
		float median = median();
		if (element > median) {
			minPQ.insert(element);
		} else {
			maxPQ.insert(element);
		}
		balanceHeap();
		printQueues();
		
	}

	private void printQueues() {
		Iterator<Integer> maxIt = maxPQ.iterator();
		List<Integer> maxQElements = new ArrayList<>();
		while(maxIt.hasNext()){
			maxQElements.add(maxIt.next());
		}
		Collections.sort(maxQElements);
		for(Integer i : maxQElements) {
			System.out.print(i+" ");
		}
		Iterator<Integer> minIt = minPQ.iterator();
		while(minIt.hasNext()){
			System.out.print(minIt.next()+" ");
		}
		System.out.println("|Median:"+median());
	}

	private void balanceHeap() {
		int minSize = minPQ.size();
		int maxSize = maxPQ.size();
		if (minSize > maxSize + 1) {
			maxPQ.insert(minPQ.delMin());
		}
		if (maxSize > minSize + 1) {
			minPQ.insert( maxPQ.delMax());
		}
	}




	public static void main(String[] args) {

		DynamicMedianFinder dMean = new DynamicMedianFinder();
		List<Integer> usedNumbers = new ArrayList<>();
		for(int i=0;i<10;i++) {
			int item = getNewUniqueRandom(usedNumbers,100);
			dMean.insert(item);
		}

	}

	private static int getNewUniqueRandom(List<Integer> usedNumbers,int N) {
		while(true) {
			int item = StdRandom.uniform(N);
			if(!usedNumbers.contains(item)) {
				usedNumbers.add(item);
				return item;
			}
		}
	}

}
