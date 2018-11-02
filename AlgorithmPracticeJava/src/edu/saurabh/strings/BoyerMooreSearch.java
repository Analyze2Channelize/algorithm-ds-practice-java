package edu.saurabh.strings;

import edu.princeton.cs.algs4.StdOut;

public class BoyerMooreSearch {

	private int R;
	private int[] right;
	private char[] pattern;
	private String pat;

	public BoyerMooreSearch(String pattern) {

		this.R = 256;
		this.pat = pattern;

		right = new int[R];
		for(int i=0;i<R;i++) {
			right[i] =-1;
		}
		// pre-processing, determine rightmost location of each character in pattern, -1 means character not in pattern
		for(int i=0;i<pattern.length();i++) {
			right[pattern.charAt(i)] = i;
		}

	}

	public BoyerMooreSearch(char[] pattern,int radix) {

		this.R = radix;
		this.pattern = new char[pattern.length];
		for(int i=0;i<pattern.length;i++) {
			this.pattern[i] = pattern[i];
		}
		right = new int[R];
		for(int i=0;i<R;i++) {
			right[i] =-1;
		}
		for(int i=0;i<pattern.length;i++) {
			right[pattern[i]] = i;
		}

	}

	public int search(String txt) {
		int m = pat.length();
		int n = txt.length();
		int skip;
		for(int i=0;i<= n-m;i +=skip) {
			skip =0;
			for(int j=m-1;j>=0;i--) {

				if(pat.charAt(j) != txt.charAt(i+j)) {
					// max used , so that's we don't go back by getting negative value
					skip = Math.max(1, j-right[txt.charAt(i+j)]);
					break;
				}
				if(skip ==0) return i;
			}
		}

		return n;
	}

	public int search(char[] text) {
		int m = pattern.length;
		int n = text.length;
		int skip;
		for (int i = 0; i <= n - m; i += skip) {
			skip = 0;
			for (int j = m-1; j >= 0; j--) {
				if (pattern[j] != text[i+j]) {
					skip = Math.max(1, j - right[text[i+j]]);
					break;
				}
			}
			if (skip == 0) return i;    // found
		}
		return n;                       // not found
	}

	public static void main(String[] args) {
		String pat = "Sax";
		String txt = "SaurabhSaxena";
		char[] pattern = pat.toCharArray();
		char[] text    = txt.toCharArray();

		BoyerMooreSearch boyermoore1 =  new BoyerMooreSearch(pat);
		BoyerMooreSearch boyermoore2 = new BoyerMooreSearch(pattern, 256);
		int offset1 = boyermoore1.search(txt);
		int offset2 = boyermoore2.search(text);

		// print results
		StdOut.println("text:    " + txt);

		StdOut.print("pattern: ");
		for (int i = 0; i < offset1; i++)
			StdOut.print(" ");
		StdOut.println(pat);

		StdOut.print("pattern: ");
		for (int i = 0; i < offset2; i++)
			StdOut.print(" ");
		StdOut.println(pat);
	}

}
