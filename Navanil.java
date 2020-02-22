package nxs190026;



import java.util.HashSet;
import java.util.Stack;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class Num  implements Comparable<Num> {

    static long defaultBase = 1000000000;  // Change as needed
    long base = defaultBase;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    private boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) {
    	int num_digits;
    	if(s.charAt(0) == '-')
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
    }

    public Num(long x) {
    	if(x < 0)
    	{
    		this.isNegative = true;
    		x *= -1;
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
    }
    
    public Num(Num x)
    {
    	this.isNegative = x.isNegative;
    	this.len = x.len;
    	this.base = x.base;
    	this.arr = x.arr.clone();
    }
    
    public Num() {
    	
    }

    public static Num add(Num a, Num b) {
    	Num result;
    	if (a.isNegative == true && b.isNegative == false)
    	{
    		a.isNegative = false;
    		result = subtract(b, a);
    		a.isNegative = true;
    		return result;
    	}
    	else if (a.isNegative == false && b.isNegative == true)
    	{
    		b.isNegative = false;
    		result = subtract(a, b);
    		b.isNegative = true;
    		return result;
    	}
    	else if (a.isNegative == true && b.isNegative == true)
    	{
    		a.isNegative = false;
    		b.isNegative = false;
    		result = add(a, b);
    		result.isNegative = true;
    		a.isNegative = true;
    		b.isNegative = true;
    		return result;
    	}
    	else
    	{
    		Num smaller, bigger;
    		if(a.len >= b.len)
    		{
    			bigger = new Num(a);
    			smaller = b;
    		}
    		else
    		{
    			bigger = new Num(b);
    			smaller = a;
    		}
    		long carry = 0;
    		for(int i = 0; i < bigger.len; i++)
    		{
    			if(i < smaller.len)
    			{
    				bigger.arr[i] += (smaller.arr[i]);
    			}
    			bigger.arr[i] += carry;
    			carry = bigger.arr[i]/bigger.base;
    			bigger.arr[i] = bigger.arr[i] % bigger.base;
    		}
    		if(carry > 0)
    		{
    			long[] temp = new long[bigger.len+1];
    			temp[bigger.len] = carry;
    			System.arraycopy(bigger.arr, 0, temp,  0, bigger.len);
    			bigger.arr = temp;
    			bigger.len = temp.length;
    		}
    		return bigger;
    	}
    }

    public static Num subtract(Num a, Num b) {
    	Num result;
    	if(a.isNegative && b.isNegative)
    	{
    		a.isNegative = false;
    		b.isNegative = false;
    		result = subtract(a, b);
    		if(result.compareTo(new Num(0)) != 0)
    		{
    			result.isNegative = !result.isNegative;
    		}
    		a.isNegative = true;
    		b.isNegative = true;
    	}
    	else if(a.isNegative && !b.isNegative)
    	{
    		b.isNegative = true;
    		result = add(a, b);
    		b.isNegative = false;
    	}
    	else if (!a.isNegative && b.isNegative)
    	{
    		b.isNegative = false;
    		result = add(a, b);
    		b.isNegative = true;
    	}
    	else
    	{
    		if((b.len > a.len))
    		{
    			result = subtract(b, a);
    			result.isNegative = true;
    		}
    		else
    		{
    			Num smaller;
    			result = new Num(a);
    			smaller = b;
        		long borrow = 0;
        		for(int i = 0; i < result.len; i++)
        		{
        			if(i < smaller.len)
        			{
        				if (result.arr[i] >= smaller.arr[i]+borrow)
        				{
        					result.arr[i] -= (smaller.arr[i]+borrow);
        					borrow = 0;
        				}
        				else
        				{
        					result.arr[i] += result.base;
        					result.arr[i] -= (smaller.arr[i]+borrow);
        					borrow = 1;
        				}
        			}
        			else
        			{
        				if (result.arr[i] >= borrow)
        				{
        					result.arr[i] -= (borrow);
        					borrow = 0;
        				}
        				else
        				{
        					result.arr[i] += result.base;
        					result.arr[i] -= (borrow);
        					borrow = 1;
        				}
        			}
        		}
        		if(borrow > 0)
        		{
        			result.isNegative = true;
        			result.arr[result.len-1] = result.base - result.arr[result.len-1];
        		}
    		}
    	}
    	int i = result.len - 1;
    	while(i>0 && result.arr[i] == 0)
    	{
    		i--;
    	}
    	if(i < result.len - 1)
    	{
    		long [] temp = new long[i+1];
    		System.arraycopy(result.arr, 0, temp, 0, temp.length);
    		result.arr = temp;
    		result.len = temp.length;
    	}
	return result;
    }

    public static Num product(Num a, Num b) {
	return null;
    }

    // Use divide and conquer
    public static Num power(Num a, long n) {
	return null;
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) throws ArithmeticException {
    //Navanil
    	
    	Num res = new Num();
    	Num ini_zero = new Num(0);
    	Num ini_one = new Num(1);
    	Num ini_two = new Num(2);
    	Num ini_minus_one = new Num(-1);
    	
    	// denominator by zero
    	if (b.compareTo(ini_zero) == 0) {
    		throw new ArithmeticException("Division by zero.");
    	}
    	
    	//numerator is zero
    	if(a.compareTo(ini_zero) == 0)
    	{
    		return ini_zero;
    	}
    		
    	//denominator is 1 or -1
    	if(b.compareTo(ini_one) == 0)
    	{
    		if(b.isNegative == true)
    		{
    			res.isNegative = !a.isNegative;
    		}
    		else {
    			res.isNegative = a.isNegative;
    		}
    		
    		
    		res.len = a.len;
    		res.arr = new long[res.len];
    		int i = 0;
    		
    		for(long x: a.arr)
    		{
    			res.arr[i++] = x;
    			return res;
    		}
    	}
    	
    	//division by 2
    	if(b.compareTo(ini_two) == 0)
    	{
	    	if(b.isNegative)
	    	{
	    		res.isNegative = !a.isNegative;
	    	}
	    	else {
	    		res.isNegative = a.isNegative;
	    	}
	    	
	    	res = a.by2();
	    	return res;
    	}
    	
    	// |a| == |b|
    			if (a.compareTo(b) == 0) {
    				if (a.isNegative == b.isNegative) return res;
    				else return ini_minus_one;
    			}

    			// Binary Search begins
    			Num low = new Num(1);
    			Num high = new Num();

    			// Copying a to high
    			high.len = a.len;
    			high.arr = new long[high.len];

    			int i = 0;
    			for (long x : a.arr) 
    			{ 
    				high.arr[i++] = x; 
    			}

    			// mid = (low + high) / 2.
    			Num mid = new Num();

    			while (low.compareTo(high) < 0) {

    				Num sum = res.add(low, high);
    				mid = sum.by2();

    				if (mid.compareTo(low) == 0)
    					break; // When mid = low

    				Num product = res.mag_product(mid, b);

    				if (product.compareTo(a) == 0)
    					break; // When mid*b = a

    				else if (product.compareTo(a) > 0) 
    					high = mid;
    				else
    					low = mid;
    			}

    			// When a, b has different signs
    			if (a.isNegative != b.isNegative)
    				mid.isNegative = true;

    			return mid;
  
    }

    // return a%b
    //Navanil
    public static Num mod(Num a, Num b) throws ArithmeticException {
    	
    	Num ini_zero = new Num(0);
    	Num ini_one = new Num(1);
    	
    	
    	// When Undefined modulo operation
    	if (a.isNegative || b.isNegative || b.compareTo(ini_zero) == 0)
    	{
    		throw new ArithmeticException("Undefined modulo operation!");
    	}
    	
    	if(b.compareTo(ini_one) == 0)
    	{
    		return a;
    	}
    	
    	Num q = divide(a,b);
    	Num prod = mag_product(q,b);
    	
    	Num rem = subtract(a, prod);
    		
		return rem;
    	
    	
    }

    // Use binary search
    public static Num squareRoot(Num a) throws ArithmeticException {
    	
    	Num ini_zero = new Num(0);
    	Num ini_one = new Num(1);
    	
    	if (a.isNegative)
    	{
    		throw new ArithmeticException("Square root of negative number is undefined");
    	}
    	
    	if(a.compareTo(ini_zero) == 0)
    	{
    		return ini_zero;
    	}
    	
    	if(a.compareTo(ini_one) == 0)
    	{
    		return ini_zero;
    	}
    	
    	Num low = new Num(0);
    	Num high = new Num();
    	
    	
    	
    	
    	
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
    	int result = 0;
    	if(this.isNegative && !other.isNegative)
    	{
    		result = -1;
    	}
    	else if(!this.isNegative && other.isNegative)
    	{
    		result = 1;
    	}
    	else if(!this.isNegative && !other.isNegative)
    	{
    		if(this.len == other.len)
    		{
    			int i = this.len - 1;
    			while(i>=0 && this.arr[i]==other.arr[i])
    			{
    				i--;
    			}
    			if(i==-1)
    			{
    				result = 0;
    			}
    			else
    			{
    				if(this.arr[i]<other.arr[i])
    				{
    					result = -1;
    				}
    				else
    				{
    					result = 1;
    				}
    			}
    		}
    		else if(this.len < other.len)
    		{
    			result = -1;
    		}
    		else
    		{
    			result = 1;
    		}
    	}
    	else
    	{
    		if(this.len == other.len)
    		{
    			int i = this.len - 1;
    			while(i>=0 && this.arr[i]==other.arr[i])
    			{
    				i--;
    			}
    			if(i==-1)
    			{
    				result = 0;
    			}
    			else
    			{
    				if(this.arr[i]<other.arr[i])
    				{
    					result = 1;
    				}
    				else
    				{
    					result = -1;
    				}
    			}
    		}
    		else if(this.len < other.len)
    		{
    			result = 1;
    		}
    		else
    		{
    			result = -1;
    		}
    	}
	return result;
    }
    
    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
    	System.out.print(this.base+": ");
    	for(int i=0;i<this.len;i++)
		{
    		System.out.print(this.arr[i]+" ");
		}
    	System.out.println();
    }
    
    // Return number to a string in base 10
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.isNegative ? "-" : "");
    	sb.append(String.valueOf(this.arr[this.len-1]));
    	for(int i = this.len-2; i>=0; i--)
    	{
    		String num = String.valueOf(this.arr[i]);
    		int len_diff = String.valueOf(this.base).length() - 1 - num.length() ;
    		if(len_diff > 0)
    		{
    			for(int j=0; j<len_diff; j++)
    			{
    				sb.append("0");
    			}
    		}
    		sb.append(num);
    	}
	return sb.toString();
    }

    public long base() { return base; }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
	return null;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
    	Num res = new Num();
	return res;
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
	Num x = new Num(-1L);
	Num y = new Num("999999999999999999");
	Num z = Num.add(x, y);
	System.out.println("Sum: "+z);
	System.out.println("Sub: "+Num.subtract(x, y));
	System.out.println("Compare: "+x.compareTo(y));
	Num a = Num.power(x, 8);
	System.out.println(a);
	if(z != null) z.printList();
    }
}