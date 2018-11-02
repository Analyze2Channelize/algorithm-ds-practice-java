package edu.saurabh.strings;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.saurabh.stacksNQueues.LinkedQueue;

public class TrieSymbolTable<Value> {

	public static final int R = 256;
	private Node root;
	private int n;

	private static class Node{
		private Object value;
		private Node[] next = new Node[R];
	}

	public TrieSymbolTable() {
	}

	public void put(String key, Object value) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (value == null) delete(key);
		else root = put(root,key,value,0);
	}

	private Node put(Node x, String key, Object value, int d) {

		if(x==null) x = new Node();

		if(d==key.length()) { 
			if(x.value == null) n++;
			x.value = value; 
			return x; }

		char c = key.charAt(d);

		x.next[c] = put(x.next[c],key,value,d+1);

		return x;
	}

	public Value get(String key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		Node x = get(root,key,0);
		if(x==null) return null;
		return (Value)x.value;
	}

	private Node get(Node x, String key,int d) {

		if(x==null) return null;

		if(d == key.length())  return x;

		char c = key.charAt(d);

		return get(x.next[c],key,d+1);
	}

	public void delete(String key) {
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");
		delete(root,key,0);
	}

	private Node delete(Node x, String key, int d) {
		if(x==null) return null;

		if(d == key.length()) {
			if(x.value !=null) n--;
			x.value = null;
		}else {
			char c = key.charAt(d);
			x.next[c] = delete(x.next[c],key,d+1);
		}
		for(int i=0;i<R;i++) {
			if(x.next[i] !=null) {
				return x;
			}
		}
		return null;
	}

	public boolean contains(String key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	}

	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Iterable<String> keys() {
		return keysWithPrefix("");
	}

	public Iterable<String> keysWithPrefix(String prefix) {
		LinkedQueue<String> results = new LinkedQueue<>();
		Node x = get(root,prefix,0);
		collect(x,new StringBuilder(prefix),results);
		return results;
	}

	private void collect(Node x, StringBuilder prefix,LinkedQueue<String> results) {
		// whenever the parent's child is null, do nothing
		if (x==null) return;
		// if the child has a value, current prefix forms a key(child character has already been added to prefix before calling).
		if(x.value !=null) results.enqueue(prefix.toString());
		//for each child, evaluate all its R children in Depth-first fashion 
		for(int c=0;c<R;c++) {
			prefix.append(c);
			collect(x.next[c],prefix,results);// DFS traversal down the tree
			prefix.deleteCharAt(prefix.length()-1);
		}
	}
	
	public Iterable<String> keysThatMatch(String pattern) {
		LinkedQueue<String> results = new LinkedQueue<>();
		collect(root,new StringBuilder(),pattern,results);
		return results;
	}

	private void collect(Node x, StringBuilder prefix, String pattern, LinkedQueue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.value != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
	
	public String longestPrefixOf(String query) {
		int length = longestPrefix(root,query,0,-1);
		if(length==-1) return null;
		return query.substring(0, length);
	}
	
	private int longestPrefix(Node x, String query,int d, int length) {
		if ( x == null) return length;
		if(x.value !=null) length = d;
		if(d==query.length() ) return length;
		char c = query.charAt(d);
		return longestPrefix(x.next[c],query,d+1,length);
	}

	public static void main(String[] args) {

		TrieSymbolTable<Integer> st = new TrieSymbolTable<Integer>();
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

		StdOut.println("longestPrefixOf(\"quicksort\"):");
		StdOut.println(st.longestPrefixOf("quicksort"));
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
