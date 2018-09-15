package edu.saurabh.sorting;

public class KSmallestInUnionOf2Arrays {

	/* takes time proportional to O(k)*/	
	public static Comparable kSmallestLinear(Comparable[] A,Comparable[] B,int k) {

		int i = 0;
		int j = 0;
		assert A.length + B.length >= k;
		while (true) {
			if (i < A.length) { 
				while (j == B.length|| A[i].compareTo(B[j])<=0) {
					i++;
					if (i + j == k)
						return A[i-1]; 
					if (i == A.length) 
						break;   
				}
			}
			if (j < B.length) {
				while (i == A.length|| B[j].compareTo(A [i])<=0) {
					j++;
					if (i + j == k)
						return B[j-1]; 
					if (j == B.length)
						break;
				}
			}
		}
	}

	/*takes time O(lg n+lg m)*/
	int findKthSmallestLogarithmic(int A[], int m, int B[], int n, int k) {
		  assert(m >= 0); assert(n >= 0); assert(k > 0); assert(k <= m+n);
		  
		  int i = (int)((double)m / (m+n) * (k-1));
		  int j = (k-1) - i;
		 
		  assert(i >= 0); assert(j >= 0); assert(i <= m); assert(j <= n);
		  // invariant: i + j = k-1
		  // Note: A[-1] = -INF and A[m] = +INF to maintain invariant
		  int Ai_1 = ((i == 0) ? Integer.MIN_VALUE : A[i-1]);
		  int Bj_1 = ((j == 0) ? Integer.MIN_VALUE : B[j-1]);
		  int Ai   = ((i == m) ? Integer.MAX_VALUE : A[i]);
		  int Bj   = ((j == n) ? Integer.MAX_VALUE : B[j]);
		 
		  if (Bj_1 < Ai && Ai < Bj)
		    return Ai;
		  else if (Ai_1 < Bj && Bj < Ai)
		    return Bj;
		 
		  assert((Ai > Bj && Ai_1 > Bj) || 
		         (Ai < Bj && Ai < Bj_1));
		 
		  // if none of the cases above, then it is either:
		  if (Ai < Bj)
		    // exclude Ai and below portion
		    // exclude Bj and above portion
		    return findKthSmallestLogarithmic(A, m-i-1, B, j, k-i-1);
		  else /* Bj < Ai */
		    // exclude Ai and above portion
		    // exclude Bj and below portion
		    return findKthSmallestLogarithmic(A, i, B, n-j-1, k-j-1);
	}


	public static void main(String[] args) {

		Integer[] a = new Integer[] {7,5,1,3,8};
		MergeSort.sort(a);
		Integer[] b = new Integer[] {10,11,9,7,1};
		MergeSort.sort(b);
		int k = 4;
		Comparable kSmallest1 = KSmallestInUnionOf2Arrays.kSmallestLinear(a,b,k);
		System.out.println(k+"th smallest:"+kSmallest1);
		Comparable kSmallest2 = KSmallestInUnionOf2Arrays.kSmallestLinear(a,b,k);
		System.out.println(k+"th smallest:"+kSmallest2);


	}


}
