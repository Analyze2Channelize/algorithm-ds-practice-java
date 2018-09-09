package edu.saurabh.stacks;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LinkedQueue<ITEM> implements Iterable<ITEM> {
    private Node<ITEM> first;     // start of queue
    private Node<ITEM> last;	// end of queue
    private int n;                // size of the stack

    // helper linked list class
    private static class Node<ITEM> {
        private ITEM item;
        private Node<ITEM> next;
    }
    public LinkedQueue() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void enqueue(ITEM item) {
        Node<ITEM> oldLast = last;
        last = new Node<ITEM>();
        last.item = item;
        last.next = null;
        if(isEmpty()) {
        	first = last;
        }else {
        	oldLast.next = last;
        }
        
        n++;
    }

    public ITEM dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        ITEM item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        if(isEmpty()) last =null;
        return item;                   // return the saved item
    }
    public ITEM peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (ITEM item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }
       

    public Iterator<ITEM> iterator() {
        return new ListIterator<ITEM>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<ITEM> implements Iterator<ITEM> {
        private Node<ITEM> current;

        public ListIterator(Node<ITEM> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public ITEM next() {
            if (!hasNext()) throw new NoSuchElementException();
            ITEM item = current.item;
            current = current.next; 
            return item;
        }
    }


    public static void main(String[] args) {
    	LinkedQueue<String> queue = new LinkedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
            	queue.enqueue(item);
            else if (!queue.isEmpty())
                StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }
}


