package edu.saurabh.trees;

import java.util.NoSuchElementException;

public class BinarySearchTree<Key extends Comparable<Key>,Value>  {

	private Node root;

	private class Node{
		private Key key;
		private Value val;
		private Node left,right;
		private int size;
		public Node(Key key, Value val, int size) {
			this.key = key;
			this.val = val;
			this.size = size;
		}
	}

	public BinarySearchTree() {}


	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if(x==null) return 0;
		return x.size;
	}

	public boolean isEmpty() {
		return root==null;

	}

	public boolean contains(Key key) {
		return get(key)!=null;

	}

	public Value get(Key key) {
		return get(root,key);

	}

	private Value get(Node x, Key key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		if(x==null) return null;
		int cmp = key.compareTo(x.key);
		if(cmp <0 ) return get(x.left,key);
		else if(cmp>0) return get(x.right,key);
		else return x.val;
	}


	public void put(Key key, Value value) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null"); 
		if(value==null) {
			delete(key);
			return;
		}
		root = put(root,key,value);

	}

	private Node put(Node x, Key key,Value val) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null"); 
		if(x==null) return new Node(key,val,1);
		int cmp = key.compareTo(x.key);
		if(cmp < 0) {
			x.left = put(x.left,key,val);
		}else if(cmp > 0) {
			x.right = put(x.right,key,val);
		}else {
			x.val = val;
		}
		x.size = size(x.left)+size(x.right)+1;
		return x;

	}

	public void delete(Key key) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null"); 
		if(!contains(key)) throw new NoSuchElementException("No such element"); 
		root = delete(root,key);

	}

	private Node delete(Node x, Key key) {

		if(key.compareTo(x.key) <0) {
			x.left = delete(x.left,key);
		}else if(key.compareTo(x.key) >0) {
			x.right = delete(x.right,key);
		}else {
			// Hibbard deletion
			Node t = x;
			x = min(t.right);// minimum element of right subtree
			x.right = deleteMin(t.right);// root of subtree after deleting min
			x.left = t.left;//original left of node to be deleted
		}
		x.size = size(x.left) + size(x.right) + 1;
		return x;

	}

	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		root = deleteMin(root);
	}

	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		root = deleteMax(root);
	}

	private Node deleteMin(Node x) {
		if(x.left==null) return x.right;
		x.left = deleteMin(x.left);
		x.size = size(x.left)+size(x.right)+1;
		return x;
	}

	private Node deleteMax(Node x) {

		if(x.right==null) return x.left;
		x.right = deleteMax(x.right);
		x.size = size(x.left)+size(x.right)+1;
		return x;

	}

	public Key min() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		return min(root).key;

	}

	private Node min(Node x) {
		if(x.left==null) return x;
		else return min(x.left);
	}

	public Key max() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		return max(root).key;
	}

	private Node max(Node x) {
		if(x.right == null) return x;
		else return max(x.right);
	}

	public Key select(int k) {
		if (k < 0 || k >= size()) {
			throw new IllegalArgumentException("argument to select() is invalid: " + k);
		}
		Node x = select(root, k);
		return x.key;
	}

	private Node select(Node x, int k) {
		if (x == null) return null; 
		int t = size(x.left); 
		if      (t > k) return select(x.left,  k); 
		else if (t < k) return select(x.right, k-t-1); 
		else            return x;

	}

	public Key floor(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to floor() is null");
		if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
		Node x = floor(root, key);
		if (x == null) return null;
		else return x.key; 
	}

	private Node floor(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0) return x;
		if (cmp <  0) return floor(x.left, key);
		Node t = floor(x.right, key); 
		if (t != null) return t;
		else return x; 
	}

	public Key ceiling(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
		if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
		Node x = ceiling(root, key);
		if (x == null) return null;
		else return x.key;
	}

	private Node ceiling(Node x,Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0) return x;
		if (cmp < 0) { 
			Node t = ceiling(x.left, key); 
			if (t != null) return t;
			else return x; 
		} 
		return ceiling(x.right, key); 
	}

	public int rank(Key key) {
		// number of keys less than key
		if (key == null) throw new IllegalArgumentException("argument to rank() is null");
		return rank(root,key); 
	}

	public int rank(Node x, Key key) {
		if(x==null) return 0;
		int cmp = key.compareTo(x.key); 
		if      (cmp < 0) return rank( x.left,key); 
		else if (cmp > 0) return 1 + size(x.left) + rank(x.right,key); 
		else              return size(x.left); 
	}

	public int size(Key lo,Key hi) {
		if (lo == null) throw new IllegalArgumentException("first argument to size() is null"); 
		if (hi == null) throw new IllegalArgumentException("second argument to size() is null");
		return 0; 

	}

	public Iterable<Key> keys(){
		return keys(min(),max());

	}

	public Iterable<Key> keys(Key lo,Key hi){
		if (lo == null) throw new IllegalArgumentException("first argument to keys() is null"); 
		if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");
		return null; 

	}


}
