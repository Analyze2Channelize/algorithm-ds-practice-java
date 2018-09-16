package edu.saurabh.trees;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinHeapPriorityQueue<Key> implements Iterable<Key> {


	private Key[] keys;
	private int N;
	private Comparator<Key> comparator;

	public MinHeapPriorityQueue() {
		this(1);
	}

	public MinHeapPriorityQueue(int initCapacity, Comparator<Key> comparator) {
		this.comparator = comparator;
		keys = (Key[]) new Object[initCapacity + 1];
		N = 0;
	}

	public MinHeapPriorityQueue(int max) {
		keys = (Key[])new Object[max+1];
		N = 0;

	}

	public MinHeapPriorityQueue(Key[] items) {
		keys = (Key[])new Object[items.length+1];
		N = items.length;
		// first populate heap in items order
		for(int i=0;i<N;i++) {
			keys[i+1] = items[i];
		}
		// then one by one sink all root nodes(left half of array), the right half of array always contains leaves 
		for(int k=N/2; k>=1;k--) {
			sink(k);
		}
		assert isMinHeap();
	}

	public void insert(Key v) {
		if (N == keys.length - 1) resize(2 * keys.length);
		keys[++N] = v;
		swim(N);
		assert isMinHeap();
	}

	public Key min() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		return keys[1];
	}

	// helper function to double the size of the heap array
	private void resize(int capacity) {
		assert capacity > N;
		Key[] temp = (Key[]) new Object[capacity];
		for (int i = 1; i <= N; i++) {
			temp[i] = keys[i];
		}
		keys = temp;
	}

	public Key delMin() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		Key k = keys[1];
		exchg(1,N--);
		sink(1);
		keys[N+1] = null;
		if ((N > 0) && (N == (keys.length - 1) / 4)) resize(keys.length / 2);
		assert isMinHeap();
		return k;
	}

	private void swim(int k) {
		while(k>1 && greater(k/2,k)) {
			exchg(k,k/2);
			k = k/2;
		}
	}

	private void exchg( int i , int j) {
		Key temp = keys[i];
		keys[i] = keys[j];
		keys[j] = temp;
	}

	private boolean isMinHeap() {
		return isMinHeap(1);
	}

	// is subtree of pq[1..n] rooted at k a min heap?
	private boolean isMinHeap(int k) {
		if (k > N) return true;
		int left = 2*k;
		int right = 2*k + 1;
		if (left  <= N && greater(k,left) )  return false;
		if (right <= N && greater(k,right)) return false;
		return isMinHeap(left) && isMinHeap(right);
	}

	private void sink(int k) {


		while(2*k <= N) {
			int j = 2*k;
			// choose the smaller child
			if(j < N && greater(j,j+1)) j++;
			// if parent is equal or smaller than smallest child, we are done.
			if(!greater(k,j)) break;
			// otherwise exchange parent with smaller child
			exchg(k,j);
			// the,jump down one level
			k = j;

		}

	}

	public boolean isEmpty() {
		return N==0;
	}

	public int size() {
		return N;
	}

	
	private boolean greater(int i, int j) {
		if (comparator == null) {
			return ((Comparable<Key>) keys[i]).compareTo(keys[j]) > 0;
		}
		else {
			return comparator.compare(keys[i], keys[j]) > 0;
		}
	}


	public static void main(String[] args) {
		Integer[] items = new Integer[] {100,76,4,26,78,45,11,90};
		MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>(items);
		System.out.println("Minimum:"+pq.min());
		Iterator<Integer> it = pq.iterator();
		while(it.hasNext()) {
			System.out.print(it.next()+" ");
		}
	}


	@Override
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<Key> {
		// create a new pq
		private MinHeapPriorityQueue<Key> copy;

		// add all items to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			if (comparator == null) copy = new MinHeapPriorityQueue<Key>(size());
			else                    copy = new MinHeapPriorityQueue<Key>(size(), comparator);
			for (int i = 1; i <= N; i++)
				copy.insert(keys[i]);
		}

		public boolean hasNext()  { return !copy.isEmpty();                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public Key next() {
			if (!hasNext()) throw new NoSuchElementException();
			return copy.delMin();
		}
	}

}
