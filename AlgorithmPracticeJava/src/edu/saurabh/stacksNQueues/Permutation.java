package edu.saurabh.stacksNQueues;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {

	
	public static void main(String[] args) {
		
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rdq = new RandomizedQueue<>();
		 while (!StdIn.isEmpty()) {
	            String item = StdIn.readString();
	            rdq.enqueue(item);
	            
	        }
		 for(int i=0;i<k;i++) {
			 System.out.println(rdq.dequeue());
		 }
	}

}
