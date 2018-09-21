package edu.saurabh.stacksNQueues;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Document search. 
 * Design an algorithm that takes a sequence of N document words 
 * and a sequence of M query words and find the shortest interval 
 * in which the M query words appear in the document in the order given. 
 * The length of an interval is the number of words in that interval.
 * 
 */
public class MultiwordSearch {



	public static void main(String[] args) {
		// read in document words
		String[] words = StdIn.readAllStrings();
		// one queue per query word
		Queue<Integer> [] queues = (Queue<Integer>[]) new Queue[args.length];

		for(int i =0;i<args.length;i++) {
			queues[i] = new Queue<Integer>();
		}

		//for each query word, create a sorted list of the indices where it appears in the document
		for(int i=0;i<words.length;i++) {
			for(int j=0;j<args.length;j++) {
				if(words[i].equals(args[j])) queues[j].enqueue(i);
			}
		}

		boolean done =false;
		int bestlo = -1;
		int besthi = words.length;
		while(!queues[0].isEmpty()) {
			int lo = queues[0].dequeue();
			int hi = lo;

			for(int j=1;j<args.length;j++) {

				// skip all those indices which are less than/equal to the current hi index
				while(!queues[j].isEmpty() && queues[j].peek() <=hi) queues[j].dequeue();

				if(queues[j].isEmpty()) {
					// we couldn't find this query word at a higher index, so stop the current loop
					done = true;
					break;
				}else {
					//update hi to the index of the furthest match we have seen so far
					hi = queues[j].peek();//peek don't dequeue , index will be required in next repeat cycle
				}
			}
			//we are done with all the words in current cycle, so update the best interval
			if(!done && hi-lo< besthi -bestlo) {
				besthi = hi;
				bestlo = lo;
			}
			// repeat the exercise again from the next position of the first query word(given by queues[0].deque),may be we can find a smaller interval
		}

		if(bestlo >=0) {
			for (int i = bestlo; i <= besthi; i++)
				StdOut.print(words[i] + " ");
			StdOut.println();
		}else {
			StdOut.println("NOT FOUND");
		}
	}

}
