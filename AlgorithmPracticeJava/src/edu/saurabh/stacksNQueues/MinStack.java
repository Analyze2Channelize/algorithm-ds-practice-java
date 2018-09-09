package edu.saurabh.stacksNQueues;

import java.util.NoSuchElementException;

public class MinStack<ITEM extends Comparable<ITEM>> {

	private LinkedStack<ITEM> valueStack;
	private LinkedStack<ITEM> minStack;
	
	
	public MinStack() {
		valueStack = new LinkedStack<>();
		minStack = new LinkedStack<>();
	}
	
	
	public ITEM pop() {
		if (isEmpty()) throw new NoSuchElementException("underflow");
		minStack.pop();
		return valueStack.pop();
	}

	public ITEM getMin() {
		return minStack.peek();
	}
	public boolean isEmpty() {
		return valueStack.isEmpty() ;
	}
	
	public ITEM peek() {
		if (isEmpty()) throw new NoSuchElementException("underflow");
		return valueStack.peek();
		
	}
	
	public void push(ITEM item) {
		valueStack.push(item);
		if(minStack.isEmpty()) {
			minStack.push(item);
		}else {
			ITEM currentMin = minStack.peek();
			if(currentMin.compareTo(item) >0) {
				minStack.push(item);
			}else {
				minStack.push(currentMin);
			}
		}
	}
	
	public int size() {
		return valueStack.size();
	}
	
	 public static void main(String[] args) 
	    {
		 	MinStack<Integer> s = new MinStack<>();
	        s.push(10);
	        s.push(20);
	        s.push(30);
	        System.out.println(s.getMin());
	        s.push(5);
	        System.out.println(s.getMin());
	    }

}
