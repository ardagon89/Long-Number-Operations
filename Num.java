
// Starter code for lp1.
// Version 1.0 (Monday, Jan 27).

// Change following line to your NetId
package sxa190016;

public class Num  implements Comparable<Num> {

    static long defaultBase = 1000000000;  // Change as needed
    long base = defaultBase;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) {
    	int num_digits;
    	if(s.substring(0, 1) == "-")
    	{
    		this.isNegative = true;
    		s = s.substring(1);
    	}
    	else
    	{
    		this.isNegative = false;
    	}
    	num_digits = String.valueOf(this.base).length()-1;
    	double x = (double)s.length()/num_digits;
    	this.len = (int)Math.ceil(x);
    	this.arr = new long[this.len];
    	StringBuilder sb = new StringBuilder(s);
    	for(int i=0; i<this.len; i++)
    	{
    		if(sb.length()>num_digits)
    		{
    			this.arr[i] = Long.parseLong(sb.substring(sb.length()-num_digits));
    			sb.setLength(sb.length()-num_digits);
    		}
    		else
    		{
    			this.arr[i] = Long.parseLong(sb.toString());
    		}
    	}
    	for(int i=0; i<this.arr.length; i++)
    	{
        	System.out.println(this.arr[i]);
    	}
    }

    public Num(long x) {
    	if(x < 0)
    	{
    		this.isNegative = true;
    	}
    	else
    	{
    		this.isNegative = false;
    	}
    	int num_digits = String.valueOf(this.base).length()-1;
    	this.len = (int) Math.ceil((double)String.valueOf(x).length()/num_digits);
    	this.arr = new long[this.len];
    	
    	for(int i=0; i < this.len; i++)
    	{
    		this.arr[i] = x % this.base;
    		x /= this.base;
    	}

    	for(int i=0; i<this.arr.length; i++)
    	{
        	System.out.println(this.arr[i]);
    	}
    }

    public static Num add(Num a, Num b) {
    	if (a.isNegative == true && b.isNegative == false)
    	{
    		a.isNegative = false;
    		return subtract(b, a);
    	}
    	else if (a.isNegative == false && b.isNegative == true)
    	{
    		b.isNegative = false;
    		return subtract(a, b);
    	}
    	else if (a.isNegative == true && b.isNegative == true)
    	{
    		a.isNegative = false;
    		b.isNegative = false;
    		Num result = add(a, b);
    		result.isNegative = true;
    		return result;
    	}
    	else
    	{
    		Num result, smaller;
    		if(a.len >= b.len)
    		{
    			result = a;
    			smaller = b;
    		}
    		else
    		{
    			result = b;
    			smaller = a;
    		}
    		int len = a.len >= b.len ? a.len : b.len;
    		for(int i = 0; i <= len; i++)
    		{
    			
    		}
    		return result;
    	}
    }

    public static Num subtract(Num a, Num b) {
	return null;
    }

    public static Num product(Num a, Num b) {
	return null;
    }

    // Use divide and conquer
    public static Num power(Num a, long n) {
	return null;
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
	return null;
    }

    // return a%b
    public static Num mod(Num a, Num b) {
	return null;
    }

    // Use binary search
    public static Num squareRoot(Num a) {
	return null;
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
	return 0;
    }
    
    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
    }
    
    // Return number to a string in base 10
    public String toString() {
	return null;
    }

    public long base() { return base; }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
	return null;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
	return null;
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
	return null;
    }

    // Parse/evaluate an expression in infix and return resulting number
    // Input expression is a string, e.g., "(3 + 4) * 5"
    // Tokenize the string and then input them to parser
	// Implementing this method correctly earns you an excellence credit
    public static Num evaluateExp(String expr) {
	return null;
    }


    public static void main(String[] args) {
	Num x = new Num(23372036854775807L);
	Num y = new Num("8456452342035456456");
	Num z = Num.add(x, y);
	System.out.println(z);
	Num a = Num.power(x, 8);
	System.out.println(a);
	if(z != null) z.printList();
    }
}
