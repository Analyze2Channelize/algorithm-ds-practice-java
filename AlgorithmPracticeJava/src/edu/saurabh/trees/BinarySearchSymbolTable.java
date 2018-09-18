package edu.saurabh.trees;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.saurabh.stacksNQueues.LinkedQueue;

public class BinarySearchSymbolTable<Key extends Comparable<Key>,Value> {

	private Key[] keys;
	private Value[] vals;
	private int n = 0;

	public BinarySearchSymbolTable() {
		this(2);
	}

	public BinarySearchSymbolTable(int capacity) { 
		keys = (Key[]) new Comparable[capacity]; 
		vals = (Value[]) new Object[capacity]; 
	}   

    private void resize(int capacity) {
        assert capacity >= n;
        Key[]   tempk = (Key[])   new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }
	
	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n==0;

	}

	public boolean contains(Key key) {
		return get(key)!=null;

	}

	public Value get(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null"); 
		if (isEmpty()) return null;
		int i = rank(key);
		if(i < n && keys[i].compareTo(key) == 0  ) return vals[i];
		return null;

	}

	public int rank(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to rank() is null"); 
		int lo =0,hi = n-1;
		while(lo<=hi) {
			int mid = lo + (hi-lo)/2;
			int cmp = key.compareTo(keys[mid]);
			if(cmp < 0) {
				hi = mid-1;
			}else if(cmp>0) {
				lo =  mid+1;
			}else {
				return mid;
			}
		}
		return lo;

	}

	public void put(Key key, Value value) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null"); 
		if (value == null) {
			delete(key);
			return;
		}
		int i = rank(key);

		if(i < n && keys[i].compareTo(key) == 0  ) {
			vals[i] = value;
			return;
		}

		if (n == keys.length) resize(2*keys.length);
		// make space, shift right
		for(int j =n;j>i;j--) {
			keys[j] = keys[j-1];
			vals[j] = vals[j-1];
		}
		keys[i] = key;
		vals[i] = value;
		n++;
	}

	public void delete(Key key) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null"); 
		int i = rank(key);
		if(i==n || key.compareTo(keys[i])!=0) return;

		// fill space, shift left
		for(int j =i;j<n-1;j++) {
			keys[j] = keys[j+1];
			vals[j] = vals[j+1];
		}
		n--;
		keys[n]= null;
		vals[n] = null;
		// resize if 1/4 full
        if (n > 0 && n == keys.length/4) resize(keys.length/2);


	}

	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		delete(min());
	}

	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		delete(max());

	}

	public Key min() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		return keys[0];

	}

	public Key max() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
		return keys[n-1];

	}

	public Key select(int k) {
		return keys[k];

	}

	public Key floor(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to ceiling() is null"); 
		int i = rank(key);
		if(i<n && keys[i].compareTo(key)== 0 ) return keys[i];
		if(i==0) return null;
		else return keys[i-1];

	}

	public Key ceiling(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to ceiling() is null"); 
		int i = rank(key);
		if(i<n && keys[i].compareTo(key)== 0 ) return keys[i];
		if(i==n) return null;
		else return keys[i];


	}

	public int size(Key lo,Key hi) {
		if (lo == null) throw new IllegalArgumentException("first argument to size() is null"); 
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null"); 

        if (lo.compareTo(hi) > 0) return 0;
		if(contains(hi)) return rank(hi)- rank(lo)+1;
		else return rank(hi)- rank(lo);

	}

	public Iterable<Key> keys(){
		return keys(min(),max());

	}

	public Iterable<Key> keys(Key lo,Key hi){
		if (lo == null) throw new IllegalArgumentException("first argument to keys() is null"); 
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null"); 
        LinkedQueue<Key> queue = new LinkedQueue<>();
        if (lo.compareTo(hi) > 0) return queue;
        for(int i=rank(lo);i<rank(hi);i++) {
        	queue.enqueue(keys[i]);
        }
        if(contains(hi)) queue.enqueue(keys[rank(hi)]);
		return queue;

	}

	public static void main(String[] args) { 
		BinarySearchSymbolTable<String, Integer> st = new BinarySearchSymbolTable<String, Integer>();
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			st.put(key, i);
		}
		for (String s : st.keys())
			StdOut.println(s + " " + st.get(s));
	}



}
