package edu.saurabh.datatypes;

import java.util.EmptyStackException;

public class LinkedListStack<T> {

	private static class LinkedNode<T>{
		T item;
		LinkedNode<T> next;

		private LinkedNode(T value){
			this.item = value;
			next = null;
		}

		private LinkedNode(T value,LinkedNode<T> reference){
			this.item = value;
			next = reference;
		}
	}

	protected  LinkedNode<T> top ;

	public void push(T e) {
		top = new LinkedNode<T>(e,top);
	}

	public  T pop() {
		if (empty()) {
			throw new EmptyStackException();
		}

		T result = top.item;
		top = top.next;
		return result;
	}
	public  T peek() {
		if (empty()) {
			throw new EmptyStackException();
		}

		return top.item;
	}

	public boolean empty() {
		return (top == null);
	}

	public String toString() {
		if (empty()) {
			return "Empty Stack";
		} else {
			return recursiveToString(top);
		}
	}

	private String recursiveToString(LinkedNode<T> startNode) {
		if (startNode == null) {
			return "";
		}
		String separator = "";
		if (startNode != top) {  // add :: after each item (but not at start)
			separator = " :: ";
		}
		return separator + startNode.item + recursiveToString(startNode.next);
	}
	 public static void main(String[] args) {
		 LinkedListStack<String> s = new LinkedListStack<String>();

			System.out.println("before pushing anything, " + s);
			s.push("hello");
			s.push("world");
			System.out.println("after pushing hello and world, " + s);
			System.out.println("pop returns " + s.pop());
			System.out.println("after popping, " + s);
			LinkedListStack<Integer> si = new LinkedListStack<Integer>();
			// push 100 values
			for (int i = 0; i < 100; i++) {
			    si.push(i);
			}
			// now pop them and make sure the same values are returned
			// in LIFO order
			for (int i = 99; i >= 0; i--) {
			    Integer returned = si.pop();
			    if (! returned.equals(i)) {
				System.out.println("error: pop returns " + returned +
						   ", expected " + i);
			    }
			}
			s.push("a");
			s.push("beautiful");
			s.push("day");
			System.out.println("after pushing 'a beautiful day', " + s);
			System.out.println("pop returns " + s.pop());
			System.out.println("pop returns " + s.pop());
			System.out.println("pop returns " + s.pop());
			System.out.println("pop returns " + s.pop());
			System.out.println("after popping, " + s);
		

		    }
}
