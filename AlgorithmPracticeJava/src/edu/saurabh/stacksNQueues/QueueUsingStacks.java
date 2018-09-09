package edu.saurabh.stacksNQueues;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QueueUsingStacks<ITEM> {

	private LinkedStack<ITEM> enqueueStack;
	private LinkedStack<ITEM> dequeueStack;
	
	
	public QueueUsingStacks() {
		enqueueStack = new LinkedStack<>();
		dequeueStack = new LinkedStack<>();
	}
	
	public ITEM dequeue() {
		 if (isEmpty()) throw new NoSuchElementException("underflow");
		if(dequeueStack.isEmpty()) {
			transfer();
		}
		return dequeueStack.pop();
	}

	private void transfer() {
		while(!enqueueStack.isEmpty()) {
			ITEM item = enqueueStack.pop();
			dequeueStack.push(item);
		}
	}
	
	public boolean isEmpty() {
		return dequeueStack.isEmpty() && enqueueStack.isEmpty() ;
	}
	
	public ITEM peek() {
		if (isEmpty()) throw new NoSuchElementException("underflow");
		if(dequeueStack.isEmpty()) {
			transfer();
		}
		return dequeueStack.peek();
		
	}
	
	public void enqueue(ITEM item) {
		enqueueStack.push(item);
	}
	
	public int size() {
		return enqueueStack.size() + dequeueStack.size();
	}
	
	public static void main(String[] args) {
		QueueUsingStacks<String> queue = new QueueUsingStacks<String>();
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
