package edu.saurabh.stacksNQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] a; 
	private int n;

	@SuppressWarnings("unchecked")
	public RandomizedQueue()  {
		a = (Item[]) new Object[2];
		n = 0;
	}
	public boolean isEmpty()  {
		return n==0;
	}
	public int size()     {
		return n;
	}


	private void resize(int capacity) {
		assert capacity >= n;
		a = java.util.Arrays.copyOf(a, capacity);
	}

	public void enqueue(Item item)   {
		if(item==null) throw new IllegalArgumentException();
		if (n == a.length) resize(2*a.length);    // double size of array if necessary
		a[n] = item;
		n++;
	}
	public Item dequeue()    {
		if(isEmpty()) throw new NoSuchElementException();	
		int randomIndex = StdRandom.uniform(n);
		Item item = a[randomIndex];
		if(randomIndex == n-1) {
			a[randomIndex] = null;
		}else {
			//leave no holes in the array, move nulls to end of array
			a[randomIndex] = a[n-1];
			a[n-1] = null;
		}
		n--;
		return item;
	}

	public Item sample()   {
		if(isEmpty()) throw new NoSuchElementException();	
		int random = StdRandom.uniform(n);
		Item item = a[random];
		return item;
	}

	public Iterator<Item> iterator()    {
		return new ListIterator<Item>();
	}

	@SuppressWarnings("hiding")
	private class ListIterator<Item> implements Iterator<Item> {
		int i =0;
		int[] indices;
		public ListIterator() {
			indices = new int[n];
			for(int j=0;j<n;j++) {
				indices[j] = j;
			}
			StdRandom.shuffle(indices);
		}

		public boolean hasNext() {
			return i < n;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("unchecked")
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			return (Item) a[indices[i++]];
		}
	}
	public static void main(String[] args)  {
		RandomizedQueue<Integer> s = new RandomizedQueue<>();
		s.enqueue(10);
		s.enqueue(20);
		s.enqueue(30);
		s.enqueue(40);
		s.enqueue(50);
		System.out.println(s.dequeue());
		System.out.println(s.sample());
		Iterator<Integer> it1 = s.iterator();
		while(it1.hasNext()) {
			System.out.print(it1.next()+",");
		}
		System.out.println();
		Iterator<Integer> it2 = s.iterator();
		while(it2.hasNext()) {
			System.out.print(it2.next()+",");
		}
	}
}
