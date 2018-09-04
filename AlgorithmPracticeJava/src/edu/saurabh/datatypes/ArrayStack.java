package edu.saurabh.datatypes;

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<E> {
	private Object[] elements ;
	private static final int  DEFAULT_INITIAL_CAPACITY = 16;
	private int size=0;

	public ArrayStack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}
	public void push(Object e) {
		ensureCapacity();
		elements[size++]=e;
	}
	private void ensureCapacity() {
		if(elements.length == size) {
			elements = Arrays.copyOf(elements, 2*size+1);
		}
	}

	public boolean isEmpty() {
		return size==0;
	}

	@SuppressWarnings("unchecked")
	public  E pop() {
		if(size==0) {
			throw new EmptyStackException();
		}

		E top =  (E)(elements[--size]);
		elements[size] = null;
		return top;
	}

	public static void main(String[] args) {
		ArrayStack<String> stack = new ArrayStack<String>();
		stack.push("S");
		stack.push("K");
		while (!stack.isEmpty())
			System.out.println(stack.pop().toUpperCase());
	}
}
