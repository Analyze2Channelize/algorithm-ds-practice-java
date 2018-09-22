package edu.saurabh.stacksNQueues;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

//using a double linked list and hashmap
public class LRUCache<Item> implements Iterable<Item>{


	private Map<Integer,Node<Item>> keyToNode;
	private Node<Item> head, tail;
	private static final int MAX_CACHE_CAPACITY= 7;

	public LRUCache() {
		keyToNode = new HashMap<>();
	}


	private static class Node<Item> {
		private int key;
		private Item value;
		private Node<Item> next;
		private Node<Item> prev;

		public Node(int key,Item value) {
			this.key = key;
			this.value = value;
		}

	}

	private void addAtHead(Node n) {
		n.next = head;
		n.prev = null;
		if(head !=null) {
			head.prev = n;
		}
		head = n;
		if(tail == null) {
			tail = n;
		}


	}

	private void removeNode(Node n) {
		if(n.next != null) {
			n.next.prev = n.prev;
		}else {
			tail = n.prev;
		}

		if(n.prev !=null) {
			n.prev.next = n.next;
		}else {
			head = n.next;
		}
	}

	public Item get(int key) {
		if(keyToNode.containsKey(key)) {
			Node<Item> n = keyToNode.get(key);
			removeNode(n);
			addAtHead(n);
			return n.value;
		}
		return null;


	}

	public void delete(int key) {

	}

	public void put(int key, Item value) {

		if(keyToNode.containsKey(key)) {
			Node<Item> n = keyToNode.get(key);
			n.value=value;
			removeNode(n);
			addAtHead(n);
		}else {
			Node<Item> n = new Node<>(key,value);

			if(keyToNode.size() >=MAX_CACHE_CAPACITY) {
				// remove the least recently used entry
				removeNode(tail);
				keyToNode.remove(tail.key);
				addAtHead(n);
				keyToNode.put(key, n);
			}else {
				addAtHead(n);
				keyToNode.put(key, n);
			}
		}

	}

	@Override
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(head);
	}

	class ListIterator<Item> implements Iterator<Item>{

		private Node<Item> current;

		ListIterator(Node<Item> current){
			this.current = current;
		}

		@Override
		public boolean hasNext() {
			return current!=null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.value;
			current = current.next; 
			return item;
		}

	}
	
	private static void printCacheContents(LRUCache<Integer> lrucache) {
		Iterator<Integer> it = lrucache.iterator();
		while(it.hasNext()) {
			System.out.print(it.next()+" ");
		}
		System.out.println();
	}


	public static void main(String[] args) {
		LRUCache<Integer> lrucache = new LRUCache<>();
		lrucache.put(1, 1);
		lrucache.put(10, 10);
		lrucache.put(15, 15);
		lrucache.put(11, 11);
		lrucache.put(12, 12);
		lrucache.put(18, 18);
		lrucache.put(13, 13);
		
		System.out.println("Initial cache");
		printCacheContents(lrucache);

		System.out.println(lrucache.get(1));
		System.out.println(lrucache.get(10));
		System.out.println(lrucache.get(15));
		
		System.out.println("After getting 3 elements");
		printCacheContents(lrucache);
		
		lrucache.put(97, 97);
		lrucache.put(98, 98);
		lrucache.put(99, 99);
		
		System.out.println("After add 3 new elements to force 3 evictions");
		printCacheContents(lrucache);

	}



}
