package edu.saurabh.strings;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.saurabh.stacksNQueues.LinkedQueue;

public class TernarySearchTries<Value> {

	private Node<Value> root;
	private int n;

	private static class Node<Value>{
		private Value value;
		private char  character;
		private Node<Value> left,middle,right;

	}

	public TernarySearchTries() {
	}

	public void put(String key, Value val) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if(!contains(key)) n++;
		root = put(root,key,val,0);
	}

	private Node<Value> put(Node<Value> x, String key, Value val,int d) {

		char c = key.charAt(d);

		if(x==null) { x = new Node<Value>(); x.character= c;}

		if(c < x.character) { x.left = put(x.left,key,val,d);}

		else if (c > x.character) {x.right = put(x.right,key,val,d);}

		else if(d < key.length()-1) {x.middle = put(x.middle,key,val,d+1);}

		else { x.value=val;}

		return x;

	}

	public boolean contains(String key) {
		return get(key) !=null;
	}

	public Value get(String key) {

		if (key == null) throw new IllegalArgumentException("first argument to put() is null");

		if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");

		Node<Value> x = get(root,key,0);

		if(x==null) return null;

		return x.value;
	}

	private Node<Value> get(Node<Value> x, String key,int d){

		if(x==null) return null;

		char c = key.charAt(d);

		if(c < x.character) return get(x.left,key,d);

		else if(c > x.character) return get(x.right,key,d);

		else if(d < key.length() -1) return get(x.middle,key,d+1);

		else return x;
	}

	public Iterable<String> keys(){
		LinkedQueue<String> queue = new LinkedQueue<String>();
		collect(root, new StringBuilder(), queue);
		return queue;
	}

	public  Iterable<String> keysWithPrefix(String prefix){

		LinkedQueue<String> results = new LinkedQueue<>();

		Node<Value> x = get(root,prefix,0);

		if(x==null) return results;

		if(x.value !=null) results.enqueue(prefix.toString());

		collect(x.middle,new StringBuilder(prefix),results);

		return results;

	}

	private void collect(Node<Value> x, StringBuilder prefix, LinkedQueue<String> results){

		if(x==null) return;

		collect(x.left, prefix,results);

		if(x.value !=null) results.enqueue(prefix.toString()+x.character);

		// append current character to prefix only while going down middle path, as middle path is always chosen on match
		collect(x.middle,prefix.append(x.character),results);
		prefix.deleteCharAt(prefix.length() - 1);

		collect(x.right, prefix, results);

	}

	/**
	 * Returns the string in the symbol table that is the longest prefix of query
	 */

	public String longestPrefixOf(String query) {
		if (query == null) {
			throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
		}
		int length = 0;
		Node<Value> x = root;
		int i =0;

		while(x!=null && i< query.length()) {
			char c = query.charAt(i);

			if(c < x.character) x = x.left;

			else if(c>  x.character) x = x.right;

			else {
				i++;
				// on character match,  if there is a value then update length of key
				if(x.value !=null) length = i;
				x = x.middle;
			}
		}
		return query.substring(0, length);

	}

	public int size() {
		return n;
	}

	public Iterable<String> keysThatMatch(String pattern) {
		Queue<String> queue = new Queue<String>();
		collect(root, new StringBuilder(), 0, pattern, queue);
		return queue;
	}

	private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
		if (x == null) return;
		char c = pattern.charAt(i);
		if (c == '.' || c < x.character) collect(x.left, prefix, i, pattern, queue);
		if (c == '.' || c == x.character) {
			if (i == pattern.length() - 1 && x.value != null) queue.enqueue(prefix.toString() + x.character);
			if (i < pattern.length() - 1) {
				collect(x.middle, prefix.append(x.character), i+1, pattern, queue);
				prefix.deleteCharAt(prefix.length() - 1);
			}
		}
		if (c == '.' || c > x.character) collect(x.right, prefix, i, pattern, queue);
	}

	public static void main(String[] args) {

		// build symbol table from standard input
		TernarySearchTries<Integer> st = new TernarySearchTries<Integer>();
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			st.put(key, i);
		}

		// print results
		if (st.size() < 100) {
			StdOut.println("keys(\"\"):");
			for (String key : st.keys()) {
				StdOut.println(key + " " + st.get(key));
			}
			StdOut.println();
		}

		StdOut.println("longestPrefixOf(\"shellsort\"):");
		StdOut.println(st.longestPrefixOf("shellsort"));
		StdOut.println();

		StdOut.println("longestPrefixOf(\"shell\"):");
		StdOut.println(st.longestPrefixOf("shell"));
		StdOut.println();

		StdOut.println("keysWithPrefix(\"shor\"):");
		for (String s : st.keysWithPrefix("shor"))
			StdOut.println(s);
		StdOut.println();

		StdOut.println("keysThatMatch(\".he.l.\"):");
		for (String s : st.keysThatMatch(".he.l."))
			StdOut.println(s);
	}
}
