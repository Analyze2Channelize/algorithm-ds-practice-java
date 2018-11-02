package edu.saurabh.strings;

import java.math.BigInteger;
import java.util.Random;

public class RabinKarpSearch {

	private String pat;      
	private long patHash;    
	private int m;     
	private long q;   
	private int R;         
	private long RM;

	public RabinKarpSearch(String pat) {

		this.pat = pat;
		R = 256;
		m = pat.length();
		q = longRandomPrime();

		// precompute R^(m-1) % q for use in removing leading digit
		RM = 1;
		for (int i = 1; i <= m-1; i++)
			RM = (R * RM) % q;
		patHash = hash(pat, m);
	}

	private long longRandomPrime() {
		BigInteger random = BigInteger.probablePrime(32, new Random());
		return random.longValue();
	}

	//Horner's method for hash calculation
	private long hash(String pat,int length) {

		long hash=0;

		for(int i=0;i<length;i++) {
			hash = ((R*hash) + pat.charAt(i)) % q;
		}

		return hash;
	}

	private boolean check(String txt, int txtStartIndex) {
		for (int patIndex = 0; patIndex < m; patIndex++) 
			if (pat.charAt(patIndex) != txt.charAt(txtStartIndex + patIndex)) 
				return false; 
		return true;
	}

	public int search(String txt) {

		int n = txt.length();

		long txtHash = hash(txt,m);

		if(txtHash == patHash && check(txt,0)) { return 0;}

		for(int i=m;i< n;i++) {
			// Remove leading digit, add trailing digit, check for match. 
			txtHash = (txtHash + q - RM*txt.charAt(i-m) % q) % q; 
			txtHash = (txtHash*R + txt.charAt(i)) % q;

			int offset = i - m + 1;
			if ((patHash == txtHash) && check(txt, offset))
				return offset;
		}
		return n;
	}

}
