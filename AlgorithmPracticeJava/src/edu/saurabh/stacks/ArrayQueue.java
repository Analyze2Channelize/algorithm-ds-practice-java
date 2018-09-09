package edu.saurabh.stacks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ArrayQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int head;
    private int tail;
    private int n;            // number of elements on stack


    @SuppressWarnings("unchecked")
	public ArrayQueue() {
        a = (Item[]) new Object[2];
        n = 0;
        head =0;
        tail = 0;
    }

    public boolean isEmpty() {
        return n==0;
    }

 
    public int size() {
        return n;
    }


    @SuppressWarnings("unchecked")
	private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[(head + i) % a.length];
        }
        a = temp;
        head = 0;
        tail  = n;
    }


    public void enqueue(Item item) {
        if (n == a.length) resize(2*a.length);    // double size of array if necessary
    	a[tail++] = item;
    	if(tail ==a.length) tail =0;
    	n++;
    	System.out.println(Arrays.toString(a));
       
    }


    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[head];
        a[head] = null;                              // to avoid loitering
        head++;
        n--;
        if(head == a.length) head = 0;
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length/2);
        System.out.println(Arrays.toString(a));
        return item;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[head];
    }

 
    public Iterator<Item> iterator() {
        return new ArrayQueueIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayQueueIterator implements Iterator<Item> {
    	private int i =0;
        public boolean hasNext() {
            return head < tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = a[(i + head) % a.length];
            i++;
            return item;
        }
    }



    public static void main(String[] args) {
    	ArrayQueue<String> queue = new ArrayQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on stack)");
    }
}

