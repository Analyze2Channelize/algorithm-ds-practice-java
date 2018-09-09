package edu.saurabh.stacksNQueues;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LinkedStack<ITEM> implements Iterable<ITEM> {
    private Node<ITEM> first;     // top of stack
    private int n;                // size of the stack
    int numOfPushPop;

    // helper linked list class
    private static class Node<ITEM> {
        private ITEM item;
        private Node<ITEM> next;
    }
    public LinkedStack() {
        first = null;
        n = 0;
        numOfPushPop=0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void push(ITEM item) {
        Node<ITEM> oldfirst = first;
        first = new Node<ITEM>();
        first.item = item;
        first.next = oldfirst;
        n++;
        numOfPushPop++;
    }

    public ITEM pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        ITEM item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        numOfPushPop++;
        return item;                   // return the saved item
    }
    public ITEM peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
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
        return new ListIterator<ITEM>(first,numOfPushPop);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<ITEM> implements Iterator<ITEM> {
        private Node<ITEM> current;
        private int numOfPushPopAtStartOfIteration;

        public ListIterator(Node<ITEM> first,int numOfPushPop) {
            current = first;
            this.numOfPushPopAtStartOfIteration = numOfPushPop;
        }

        public boolean hasNext() {
        	if(numOfPushPopAtStartOfIteration != numOfPushPop) throw new ConcurrentModificationException();
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public ITEM next() {
        	if(numOfPushPopAtStartOfIteration != numOfPushPop) throw new ConcurrentModificationException();
            if (!hasNext()) throw new NoSuchElementException();
            ITEM item = current.item;
            current = current.next; 
            return item;
        }
    }


    public static void main(String[] args) {
    	LinkedStack<String> stack = new LinkedStack<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                stack.push(item);
            else if (!stack.isEmpty())
                StdOut.print(stack.pop() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}


