package edu.saurabh.trees;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.saurabh.stacksNQueues.LinkedQueue;

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
		return size() ==0;

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
		assert check();

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
		assert check();

	}

	private Node delete(Node x, Key key) {

		if(x==null) return null;
		if(key.compareTo(x.key) <0) {
			x.left = delete(x.left,key);
		}else if(key.compareTo(x.key) >0) {
			x.right = delete(x.right,key);
		}else {
			// Hibbard deletion
			Node t = x;
			x = min(t.right);// minimum element of right subtree
			x.right = deleteMin(t.right);// parent of subtree after deleting min
			x.left = t.left;//original left of node to be deleted
		}
		x.size = size(x.left) + size(x.right) + 1;
		return x;

	}

	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		root = deleteMin(root);
		assert check();
	}

	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		root = deleteMax(root);
		assert check();
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

	/*return key whose rank is k, (k+1)st smallest key */
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

	/*largest key smaller than or equal to given key*/
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

	/*smallest key larger than or equal to given key*/
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
	/*number of keys less than given key*/
	public int rank(Key key) {
		// number of keys less than key
		if (key == null) throw new IllegalArgumentException("argument to rank() is null");
		return rank(root,key); 
	}

	private int rank(Node x, Key key) {
		if(x==null) return 0;
		int cmp = key.compareTo(x.key);
		if(cmp < 0)  return rank(x.left,key);
		else if(cmp>0) return 1+size(x.left)+rank(x.right,key);
		else return size(x.left);
	}

	/*number of keys between lo and hi*/
	public int size(Key lo,Key hi) {
		if (lo == null) throw new IllegalArgumentException("first argument to size() is null"); 
		if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

		if (lo.compareTo(hi) > 0) return 0;
		if (contains(hi)) return rank(hi) - rank(lo) + 1;
		else              return rank(hi) - rank(lo);

	}

	/*all keys in the tree*/
	public Iterable<Key> keys(){
		return keys(min(),max());

	}

	public Iterable<Key> keys(Key lo,Key hi){
		if (lo == null) throw new IllegalArgumentException("first argument to keys() is null"); 
		if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");
		LinkedQueue<Key> queue = new LinkedQueue<Key>();
		keys(root, queue, lo, hi);
		return queue;
	}

	private void keys(Node x , LinkedQueue<Key> queue,Key lo, Key hi) {
		if(x==null) return;
		int cmplo = lo.compareTo(x.key);
		int cmphi = hi.compareTo(x.key);

		if(cmplo < 0) keys(x.left,queue,lo,hi);// lo is less than current node, go further left
		if(cmplo <=0 && cmphi >=0) queue.enqueue(x.key); // lo is less or equal to current node and greater/equal to hi
		if(cmphi > 0 ) keys(x.right,queue,lo,hi);// hi is greater than current node, go further right

	}

	public int height() {
		return height(root);
	}

	private int height(Node x) {
		if(x==null) return -1;
		return 1 + Math.max(height(x.left),height(x.right));

	}


	public Iterable<Key> inOrder(){
		LinkedQueue<Key> queue = new LinkedQueue<>();
		inOrder(root,queue);
		return queue;

	}

	private void inOrder(Node x,LinkedQueue<Key> queue){

		if(x==null) return; //back to root, whenever we get a null link
		inOrder(x.left,queue);// go left
		queue.enqueue(x.key);// enqueue key whenever we are coming up a left path
		inOrder(x.right,queue);// go right
		return;// don't enqueue,just go back up root when we are coming up a right path
	}

	public Iterable<Key> levelOrder(){
		LinkedQueue<Key> keys = new LinkedQueue<>();
		LinkedQueue<Node> nodes = new LinkedQueue<>();
		nodes.enqueue(root);
		while(!nodes.isEmpty()) {
			Node x = nodes.dequeue();
			if(x==null) continue;
			keys.enqueue(x.key);
			nodes.enqueue(x.left);
			nodes.enqueue(x.right);
		}
		return keys;
	}

	public boolean isBST() {
		return isBST(root,null,null);// initially both max and min are unknown, so null
	}

	private boolean isBST(Node x, Key min,Key max ) {
		if(x==null) return true;
		if(min!=null && x.key.compareTo(min) <=0) return false; // key in right child less than parent, so not a BST. Not executed When traversing left path because min is null
		if(max!=null && x.key.compareTo(max) >=0) return false; // key in left child greater than parent, so not a BST.Not executed When traversing right path because max is null
		return isBST(x.left,min,x.key) && isBST(x.right,x.key,max); //for left path,send min as null and max as parent key. for right path,send max as null and min as parent key
	}



	public boolean isSizeConsistent() {
		return isSizeConsistent(root);

	}

	private boolean isSizeConsistent(Node x) {
		if(x==null) return true;
		if(x.size!=size(x.left)+1+size(x.right)) return false;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);

	}

	public boolean isRankConsistent() {
		for (int i = 0; i < size(); i++)
			if (i != rank(select(i))) return false;
		for (Key key : keys())
			if (key.compareTo(select(rank(key))) != 0) return false;
		return true;

	}


	private boolean check() {
		if (!isBST())            StdOut.println("Not in symmetric order");
		if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
		if (!isRankConsistent()) StdOut.println("Ranks not consistent");
		return isBST() && isSizeConsistent() && isRankConsistent();
	}

	public static void main(String[] args) { 
		BinarySearchTree<String, Integer> st = new BinarySearchTree<String, Integer>();
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			st.put(key, i);
		}
		StdOut.println("Level order");

		for (String s : st.levelOrder())
			StdOut.println(s + " " + st.get(s));

		StdOut.println("All keys");

		for (String s : st.keys())
			StdOut.println(s + " " + st.get(s));

		StdOut.println("Inorder");

		for (String s : st.inOrder())
			StdOut.println(s + " " + st.get(s));



	}
}
