package edu.saurabh.stacksNQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;



public class Deque<Item> implements Iterable<Item>{

	private Node<Item> head; 
	private Node<Item> tail;
	private int size;

	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
		private Node<Item> previous;
	}

	public Deque()  {
		head = null;
		tail = null;
		size = 0;
	}
	public boolean isEmpty()     {
		return size==0;
	}
	public int size()       {
		return size;
	}
	public void addFirst(Item item)  {
		if(item==null) throw new IllegalArgumentException();
		Node<Item> oldHead = head;
		Node<Item> newNode = new Node<Item>();
		newNode.item=item;
		if(isEmpty()) {
			head = newNode;
			tail = newNode;
		}else {
			newNode.next = oldHead;
			head.previous = newNode;
			head = newNode;
		}
		size++;
	}
	public void addLast(Item item)      {
		if(item==null) throw new IllegalArgumentException();
		Node<Item> oldTail = tail;
		Node<Item> newNode = new Node<Item>();
		newNode.item=item;
		if(isEmpty()) {
			head = newNode;
			tail = newNode;
		}else {
			oldTail.next = newNode;
			newNode.previous = oldTail;
			tail = newNode;
		}
		size++;
	}
	public Item removeFirst()    {
		if(isEmpty()) throw new NoSuchElementException();	
		Node<Item> nodeToRemove = head;
		if(size==1) {
			head = tail = null;
		}else {
			head.next.previous = null;
			head = head.next;	
		}
		size--;
		nodeToRemove.next = null;
		return nodeToRemove.item;
	}
	public Item removeLast()        {
		if(isEmpty()) throw new NoSuchElementException();
		Node<Item> nodeToRemove = tail;
		if(size==1) {
			head = tail = null;
		}else {
			tail.previous.next = null;
			tail= tail.previous;
		}
		size--;
		nodeToRemove.previous=null;
		return nodeToRemove.item;
	}

	public Iterator<Item> iterator() {
		return new ListIterator<Item>(head);
	}
	public static void main(String[] args)   {
		Deque<Integer> s = new Deque<>();
		s.addFirst(10);
		s.addFirst(20);
		s.addLast(30);
		s.addLast(40);
		Iterator<Integer> it1 = s.iterator();
		while(it1.hasNext()) {
			System.out.print(it1.next()+",");
		}
		System.out.println();
		System.out.println(s.removeLast());
		System.out.println(s.removeFirst());
		System.out.println("Size:"+s.size());
		Iterator<Integer> it2 = s.iterator();
		while(it2.hasNext()) {
			System.out.print(it2.next()+",");
		}
		
	}

	@SuppressWarnings("hiding")
	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next; 
			return item;
		}
	}



}
