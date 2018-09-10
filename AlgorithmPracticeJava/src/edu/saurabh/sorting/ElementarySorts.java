package edu.saurabh.sorting;

import edu.princeton.cs.algs4.StdRandom;

public class ElementarySorts {


	private class Point implements Comparable<Point>{
		private int x;
		private int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point that) {
			if (that.x > this.x) return -1;
			if (that.x < this.x) return 1;
			if (that.y > this.y) return -1;
			if (that.y < this.y) return 1;
			return 0;
		}

	}

	public static int countIntersection(Point[] a, Point[] b) {
		ShellSort.sort(a);
		ShellSort.sort(b);

		int i = 0;
		int j = 0;
		int count = 0;

		while (i < a.length && j < b.length) {
			if (a[i].compareTo(b[j]) == 0) {
				count++;
				i++;
				j++;
			}
			else if (a[i].compareTo(b[j]) < 0) i++;
			else j++;
		}
		return count;
	}

	public static boolean isPerm(Integer[] a, Integer[] b) {
		if (a.length != b.length) return false;
		ShellSort.sort(a);
		ShellSort.sort(b);

		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) return false;
		}
		return true;
	}

	public static void knuthUniformShuffle(Object[] a) {
		int length = a.length;
		for(int i=0;i<length;i++) {
			int r = i+ StdRandom.uniform(length-i); // between i and n-1
			Object temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}

	private ElementarySorts() {}



}
