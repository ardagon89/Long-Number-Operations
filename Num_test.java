/*Authors : 
----------------- 
1) sxa190016 - Shariq Ali
2) nxs190026 - Navanil Sengupta
3) axs190140 - Abhigyan Sinha
4) epm180002 - Enakshi Mandal */

package axs190140;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Num  implements Comparable<Num> {

    static long defaultBase = 1000000000;  // Change as needed
    long base = defaultBase;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) {
    	int num_digits;
    	if(s.charAt(0) == '-') //checking number is negative or not
    	{
    		this.isNegative = true;
    		s = s.substring(1);		// taking only the magnitude
    	}
    	else
    	{
    		this.isNegative = false; // number is positive
    	}
    	// calculating the number of digits
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

    //setting the part for initializing a number object in long format.
    public Num(long x) {
    	//Number is negative
    	if(x < 0)
    	{
    		this.isNegative = true;
    		x *= -1;
    	}
    	// Number is positive
    	else
    	{
    		this.isNegative = false;
    	}
    	// calculating the number of digits
    	int num_digits = String.valueOf(this.base).length()-1;
    	this.len = (int) Math.ceil((double)String.valueOf(x).length()/num_digits);
    	this.arr = new long[this.len];
    	
    	for(int i=0; i < this.len; i++)
    	{
    		this.arr[i] = x % this.base;
    		x /= this.base;
    	}
    }
    
    //defining a blank constructor to define new Num
    private Num()
    {
    	
    }
    
    //Defining Num constructor
    public Num(Num x)
    {
    	this.isNegative = x.isNegative;
    	this.len = x.len;
    	this.base = x.base;
    	this.arr = x.arr.clone();
    }
    
    //Defining add constructor for adding two numbers
    public static Num add(Num a, Num b) {
    	 Num result;
    	 
    	 // if a is negative and b is positive then subtract a from b
    	if (a.isNegative == true && b.isNegative == false)
    	{
    		a.isNegative = false;
    		result = subtract(b, a);
    		a.isNegative = true;
    		return result;
    	}
    	
    	//if a is positive and b is negative subtract b from a
    	else if (a.isNegative == false && b.isNegative == true)
    	{
    		b.isNegative = false;
    		result = subtract(a, b);
    		b.isNegative = true;
    		return result;
    	}
    	// if both a & b are negative add a & b
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
    	
    	// both are positive just add with carry
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

    // subtracting 2 numbers
    public static Num subtract(Num a, Num b) {
    	Num result;
    	
    	// if both are negative then just subtract b from a by keeping isNegative as false as -a -(-b) = -a + b
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
    	//if a is negative and b is not negative then just add a & b by considering b as negative as -a -(+b) = -(a+b)
    	else if(a.isNegative && !b.isNegative)
    	{
    		b.isNegative = true;
    		result = add(a, b);
    		b.isNegative = false;
    	}
    	
    	// if b is negative and a is positive then just add a & b, as a -(-b) = a + b
    	else if (!a.isNegative && b.isNegative)
    	{
    		b.isNegative = false;
    		result = add(a, b);
    		b.isNegative = true;
    	}
    	// if both a & b are positive
    	else
    	{
    		if(b.compareTo(a)==1 || (b.compareTo(a) == 0)) // checking if b is greater than a
    		{
    			result = subtract(b, a);
    			result.isNegative = true;
    		}
    		else // if a is greater than b
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

    //computing the product of a & b
    public static Num product(Num a, Num b) {
    	Num result = new Num();

		// Getting the magnitude of product = |a| * |b|
		result = result.mag_product(a, b);

		// Both a & b are negative or both are +ve so the result is +ve
		if (a.isNegative == b.isNegative) {
			result.isNegative = false;
		} // or any of the following cases :  ( - * +) or (- * +)
		else {
			result.isNegative = true;
		} 
		return result;
    }

    // helper function for computing product
    private Num mag_product(Num a, Num b)
    {
    	Num result = new Num();
    	
    	// taking the greater length among a & b
    	int n = a.compareTo(b) == 1 || a.compareTo(b) == 0?a.len:b.len;
    	
    	// both a & b = 0, so 0 * 0
    	if(a.len == 1 && a.arr[0] == 0 || b.len == 1 && b.arr[0] == 0)
    	{
    		result.len = 1;
    		result.arr = new long[result.len];
    		result.arr[0] = 1;
    		return result;
    	}
    	
    	// if the numbers to be multiplied are single digits eg : 8 * 5
    	if( a.len == 1 && b.len == 1)
    	{
    		long mul = a.arr[0] * b.arr[0];
    		long carry = mul/base;
    		if( carry == 0) // single digit answer eg: 2*2 = 4
    		{
    			result.len = 1;
    			result.arr = new long[result.len];
    			result.arr[0] = mul;
    			return result;
    		}
    		else { // double digit answer eg 4*8 = 32
    			result.len = 2;
    			result.arr = new long[result.len];
    			result.arr[0] = mul % base;
    			result.arr[1] = mul / base;
    			return result;
    		}
    	}
    	
    	// using karatsuba's algorithm to compute the product
    	else {
    		Num Xl = new Num();
    		Num Xr = new Num();
    		Num Yl = new Num();
    		Num Yr = new Num();
    		
    		//Num res; 
    		int zeros = 0;
    		//Dividing the numbers into 2 half therefore fh : first half & sh : second half
    		int fh = n/2; int sh = n/2 -fh;
    		Xl.len = Yl.len = fh; //left part of both the numbers
    		Xr.len = Yr.len = sh; // right part of both the numbers
    		Xl.arr = new long[fh]; Yl.arr = new long[fh]; // initializing arrays with required length
    		Xr.arr = new long[sh]; Yr.arr = new long[sh];
    		
    		if(a.len != b.len)
    		{
    			zeros = a.len - b.len; // calculating the number of zeros to be padded
    			if(zeros < 0)   // this means that b is lengthier than a
    			{
    				padZeros(a,b.len - a.len); // pad up a with 0 to cover up the length deficit
    				partition(a, Xr, Xl, b, Yr, Yl);  // divide both the numbers into first half and second half
    			}
    			else {
    				padZeros(b,zeros); // else a is lengthier so pad up b with 0s
    				partition(a, Xr, Xl, b, Yr, Yl); // divide both the numbers into first half and second half
    			}
    		}
    		else {
				partition(a, Xr, Xl, b, Yr, Yl); // both are of same length, just need to be partitioned into fh and sh.
			}
    		// Calling using Recursion
    		Num prod1 = new Num();
    		prod1 = mag_product(Xr, Yr);

    		// Calling using Recursion
    		Num prod2 = new Num();
    		prod2 = mag_product(Xl, Yl);

    		// Calling using Recursion
    		Num prod3 = new Num();
    		prod3 = mag_product(add(Xl, Xr), add(Yl, Yr));

    		// CONQUER STEP:
    		// a * b = shiftZeros(r1,(n/1+n/2)) + r2 + shiftZeros(r3-r2-r1, n/2)
    		Num sum = add(prod1, prod2);
    		Num r4 = subtract(prod3, sum);

    		Num f1 = shiftZeros(prod1, (n / 2 + n / 2));
    		Num f2 = shiftZeros(r4, n / 2);

    		Num temp = add(f1, prod2);
    		result = add(temp, f2);
    	}
    	return result;
    }

    private Num padZeros(Num a, int zeros) {
		// No padding needed
		if (zeros == 0) return a;
		
		Num padded = new Num();
		int pLen = a.len + zeros; // NOTE: default number in java is ZERO* ;)
		padded.arr = new long[pLen];
		int index = 0;
		
		// Copying each element from front until there is one in a.arr
		for (long e : a.arr) {
			padded.arr[index] = e;
			index++;
		}
		// Appropriate Sign and length of paddedNum
		padded.isNegative = a.isNegative;
		padded.len = pLen;

		return padded;
	}

    // helper function for partitioning the numbers in Karatsuba's algorithm
    private void partition(Num a, Num a1, Num a2, Num b, Num b1, Num b2) {
		int i = 0;
		// considering the longer number
		int n = a.compareTo(b) == 0 || a.compareTo(b) == 1? a.len : b.len;
		//partitioning length
		int l2 = n/2;
		// Consider a = 347812 = [2,1,8,7,4,3]. So, l2 = 3.
		// a1 = 123 = [7,4,3] - upper half and a2 = 45 = [2,1,8] - lower half
		for (i=0; i<l2; i++) {
			a2.arr[i] = a.arr[i]; // filling the lower half of a into a2
			b2.arr[i] = b.arr[i]; // filling the lower half of b into b2

			a1.arr[i] = a.arr[l2 + i];// filling the upper half of a into a1
			b1.arr[i] = b.arr[l2 + i];// filling the upper half of b into b1
		}
		// When n is Odd number
		if (n % 2 != 0) {
			// a1, b1 would have one more digit than a2, b2 (Case: l1 = l2+1)
			a1.arr[i] = a.arr[l2 + i];
			b1.arr[i] = b.arr[l2 + i];
		}
	}

    
    private Num shiftZeros(Num a, int d) {
		Num out = new Num();
		
		out.len = a.len + d; // NOTE: default number in java is ZERO* ;)
		out.arr = new long[out.len];
		out.isNegative = a.isNegative; // Appropriate sign

		int lengthA = a.len;
		
		// Copying digits from a to out
		for (int i=0; i<lengthA; i++) {
			out.arr[d + i] = a.arr[i]; // assigned to index: d to (a.len + d - 1)  
		}
		
		return out;
	}
    
    // Use divide and conquer
    public static Num power(Num x, long n) {
    	Num out = new Num();
		Num one = new Num(1);
		Num zero = new Num(0);
		Num minusOne = new Num(-1);
		
		// When n = 0 , means power is 0.
		if (n == 0) {
			if (x.compareTo(zero) == 0) {
				throw new ArithmeticException("power(0,0) is Undefined!");
			} else {
				return one;
			}
		}
		// When n is < 0 means power is Negative.
		if (n < 0) {

			if (x.compareTo(zero) == 0)
				throw new ArithmeticException("power(0,Negative) is Undefined!");
			
			if (x.isNegative && x.compareTo(one) == 0) {
				if (n % 2 == 0) return one; // a == 1 & even number of negative 1 therefore positive result
				else return minusOne; // a == 1 & odd number of negative 1 therefore negative result
			}
			
			if (x.compareTo(one) == 0) return one; // a == 1 is raised to power any positive integer is 1.
				
			if (x.compareTo(zero) > 0 && x.isNegative) {
				if (n % 2 == 0) return zero; // -ve number raised to -ve power(even) will return positive number
				else return minusOne; //-ve number raised to -ve(odd) power will return negative number
				
			}
			
			if (x.compareTo(zero) > 0) return zero; // positive number raised to a negative power will return a fraction hence
			// an integer value equal to 0.

		}

		// When power is Positive
		if (n > 0) {

			if (x.compareTo(zero) == 0) return zero; //a is 0 so 0 ^ pos_power = 0
			
			if (x.compareTo(one) == 0 && x.isNegative) { // a = -1
				if (n % 2 == 0) return one; // -1 ^ even_pow = 1
				else return minusOne; // -1 ^ odd_pow = -1;
			}
			
			if (x.compareTo(one) == 0) return one; // 1 ^ power(any number) = 1

			// When a is Negative
			if (x.compareTo(zero) > 0 && x.isNegative) {
				if (n % 2 == 1) out.isNegative = true; // a < 0 ^ odd power = -ve number
				out = out.power_Helper(x, n);      // compute the negative number
				return out;               // return the answer
			}
			
			// When a is Positive
			else return out.power_Helper(x, n); // Compute and return the positive number
		}
		return out;   // for the function to work
    }
    
    //Helper function to compute the power of the number
    private Num power_Helper(Num x, long n) {

		if (n == 0) return new Num(1); // power = 0, so a ^ 0 = 1

		if (n == 1) return x; // a^1 = a   
		
		Num halfPower = power_Helper(x, n/2); // saving (4 - 1) recursive calls

		// When n is Positive and EVEN
		if (n % 2 == 0)
			return product(halfPower, halfPower);

		// When n is Positive and ODD
		else
			return product(x, product(halfPower, halfPower));
	}

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
    	Num out = new Num();
		Num zero = new Num(0);
		Num one = new Num(1);
		Num minusOne = new Num(-1);
		Num two = new Num(2);

		// Division by ZERO
		if (b.compareTo(zero) == 0)
			throw new ArithmeticException("Connot divide by ZERO.");

		// Numerator is ZERO
		if (a.compareTo(zero) == 0)
			return zero;

		// Denominator is one/ minusOne
		if (b.compareTo(one) == 0) {
			// assigning correct sign to 'out'
			out.isNegative = (b.isNegative)? !a.isNegative: a.isNegative; 

			out.len = a.len;
			out.arr = new long[out.len]; // Copying a to out
			int i=0;
			for (long e : a.arr) { out.arr[i++] = e; }
			return out;
		}

		// When division is by 2
		if (b.compareTo(two) == 0) {
			out.isNegative = (b.isNegative) ? !a.isNegative : a.isNegative;
			out = a.by2();
			return out;
		}

		// |a| < |b|, causing division either ZERO or minusOne
		if (a.compareTo(b) < 0) {
			if (a.isNegative == b.isNegative) return zero;
			else return minusOne;
		}

		// |a| == |b|
		if (a.compareTo(b) == 0) {
			if (a.isNegative == b.isNegative) return one;
			else return minusOne;
		}

		// Binary Search begins
		Num low = new Num(1);
		Num high = new Num();

		// Copying a to high
		// high.isNegative = a.isNegative; // DONT NEED THIS*
		high.len = a.len;
		high.arr = new long[high.len];

		int i = 0;
		for (long e : a.arr) { high.arr[i++] = e; }

		// mid = (low + high) / 2.
		Num mid = new Num();

		while (low.compareTo(high) < 0) {

			Num sum = out.add(low, high);
			mid = sum.by2();

			if (mid.compareTo(low) == 0)
				break; // When mid = low

			Num prod = out.mag_product(mid, b);

			if (prod.compareTo(a) == 0)
				break; // When mid*b = a

			else if (prod.compareTo(a) > 0) 
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
    public static Num mod(Num a, Num b) {
    	Num zero = new Num(0);
		Num one = new Num(1);

		// When Undefined modulo operation
		if (a.isNegative || b.isNegative || b.compareTo(zero) == 0)
			throw new ArithmeticException("Undefined mod!");

		// NOTE: b wouldn't be minusOne
		if (b.compareTo(one) == 0)
			return a;

		Num quotient = divide(a, b);
		Num product = product(quotient, b);

		Num remainder = subtract(a, product);

		return remainder;
    }

    // Use binary search
    public static Num squareRoot(Num a) {
    	Num zero = new Num(0);
		Num one = new Num(1);
		
		// When a is NEGATIVE
		if (a.isNegative)
			throw new ArithmeticException("squareRoot(Negatives) is undefined!"); 

		if (a.compareTo(zero) == 0)
			return zero; // When a is ZERO

		if (a.compareTo(one) == 0)
			return zero; // When a is ONE

		Num low = new Num(0);
		Num high = new Num();
		
		// Copying a to high
		high.isNegative = a.isNegative;
		high.len = a.len;
		high.arr = new long[high.len];

		int i = 0;
		for (long e : a.arr) { high.arr[i++] = e; }

		Num mid = new Num();
		Num sum = new Num(0);
		
		// Quite similar to divide
		while (low.compareTo(high) < 0) {

			sum = add(low, high);
			mid = sum.by2();

			if (low.compareTo(mid) == 0)
				return mid;

			Num prod = product(mid, mid);

			if (prod.compareTo(a) == 0)
				return mid;

			else if (prod.compareTo(a) < 0)
				low = mid;
			else
				high = mid;
		}
		return mid;
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
    
    private Num trimZeros(Num x) {
		Num a = new Num();
		a.isNegative = x.isNegative; // sign as it was

		int aLen = x.len; // initialized
		
		// reduce by 1 every-time you have a leading zero
		while (x.arr[aLen - 1] == 0) {
			aLen--;
			// When all zeros, eventually ended up making aLen = 0
			if (aLen == 0) {
				aLen++;
				break;
			}
		}
		// Initialize and returning appropriately
		a.arr = new long[aLen];
		for (int i = 0; i < aLen; i++) {
			a.arr[i] = x.arr[i];
		}
		a.len = aLen;
		return a;
	}
    
    
    public Num convertBase(int newBase) {
    	Num nB = new Num(newBase);
		Num zero = new Num(0);
		
		Num out = new Num();
		out.isNegative = this.isNegative;
		
		// len1 = this.len = log_thisBase (N) = log(N)/log(thisBase)
		// len2 = log_newBase (N) = log(N)/log(newBase)
		// So, len2 = len1 * (log_newBase (thisBase)) = len1 * log(thisBase)/ log(newBase)
		
		double lgB1 = Math.log10(this.base);
		double lgB2 = Math.log10(newBase);
		
		double l2 = (lgB1 / lgB2) * this.len;
		out.len = (int) l2 + 1; // Safe side + 1, will trimZeros later
		
		out.arr = new long[out.len];
		int index=0;
		
		// Copying this number to thisCopy
		Num thisCopy = new Num(); 
		thisCopy.len = this.len;
		thisCopy.arr = new long[this.len];
		for (long e: this.arr) thisCopy.arr[index++] = e;
		index = 0;
		
		// Computing each digit using mod-divide methods
		while (thisCopy.compareTo(zero) > 0) {
			Num r = mod(thisCopy, nB);
			String s = r.toString();
			out.arr[index] = Long.parseLong(s);
			Num q = divide(thisCopy, nB);
			thisCopy = q; index++;
		}
		
		out = zero.trimZeros(out);
		out.base = newBase;
		return out;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
    	Num half = new Num();
		long carry = 0;
		Num two = new Num(2);
		Num zero = new Num(0);
		
		if (this.compareTo(two) < 0) 
			return zero;  
		
		// When the most significant digit = 1, the half.len = this.len - 1
		if (this.arr[len - 1] == 1) {
			half.len = this.len - 1;
			carry = 1; // Planning to start from index = half.len - 1 = this.len - 2, WITH a carry :)
		}
		// When most significant digit > 1
		else half.len = this.len;

		half.isNegative = this.isNegative;
		half.arr = new long[half.len];

		// Assigning correct digits of half.arr from index = most to least significant
		int index = half.len - 1;

		while (index > -1) {
			long sum = 0; // to store the proper digit to be halved.

			// When there exits a carry
			if (carry == 1) {
				sum = this.arr[index] + base;
			} else {
				sum = this.arr[index];
			}

			half.arr[index] = sum / 2; // the proper halved digit
			carry = (sum % 2 == 1) ? 1 : 0;

			index--;
		}
		return half;
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
    	Num out = new Num();
		if (expr.length == 0) return null;

		Stack<String> operand = new Stack<>();
		Set<String> uniqueOperators = new HashSet<>();
		String n1, n2;
		int i = 0;
		
		uniqueOperators.add("+");
		uniqueOperators.add("-");
		uniqueOperators.add("*");
		uniqueOperators.add("/");
		uniqueOperators.add("^");
		uniqueOperators.add("%");

		while (i < expr.length) {
			if (!uniqueOperators.contains(expr[i])) 
				operand.push(expr[i]);

			else {
				n1 = operand.pop();
				n2 = operand.pop();
				//System.out.println(evaluate(n1, n2, expr[i]).toString());
				operand.push(out.evaluate(n1, n2, expr[i]).toString());
			}
			i++;
		}
		//System.out.println(operand.peek());
		out = new Num(operand.pop());
		return out;
    }

    // Parse/evaluate an expression in infix and return resulting number
    // Input expression is a string, e.g., "(3 + 4) * 5"
    // Tokenize the string and then input them to parser
	// Implementing this method correctly earns you an excellence credit
    public static Num evaluateExp(String expr) {
    	System.out.println("Input:"+expr);
    	ArrayList<String> postfix = new ArrayList<String>();
    	Stack<String> operators = new Stack<String>();
    	StringBuilder sb = new StringBuilder();
    	Map<String, Integer> precedence = new HashMap<String, Integer>();
    	precedence.put("(", 1);
    	precedence.put(")", 1);
    	precedence.put("+", 2);
    	precedence.put("-", 2);
    	precedence.put("*", 3);
    	precedence.put("/", 3);
    	precedence.put("%", 3);
    	precedence.put("^", 4);

    	for(int i=0; i< expr.length(); i++) {
    		if(expr.charAt(i) >= '0' && expr.charAt(i) <= '9')
    		{
    			sb.append(expr.charAt(i));
    			if(i == expr.length()-1)
    			{
    				postfix.add(sb.toString());
    				sb.setLength(0);
    			}
    		}
    		else
    		{
    			if(sb.length() > 0)
    			{
    				postfix.add(sb.toString());
    				sb.setLength(0);
    			}
    			if(expr.charAt(i) != ' ')
    			{
    				if(expr.charAt(i) == ')')
    				{
						while(!operators.peek().equals("("))
    					{
    						postfix.add(operators.pop());
    					}
    					operators.pop();					
    				}
    				else
    				{
    					while((!operators.isEmpty())
    							&& 
    							(!operators.peek().equals("("))
    							&& 
    							(!String.valueOf(expr.charAt(i)).equals("("))
    							&&
    							(precedence.get(operators.peek()).compareTo(precedence.get(String.valueOf(expr.charAt(i)))) > 0
								||
								(precedence.get(operators.peek()).equals(precedence.get(String.valueOf(expr.charAt(i))))
										&& (!String.valueOf(expr.charAt(i)).equals("^")))))
    					{
    						postfix.add(operators.pop());
    					}
        				operators.add(String.valueOf(expr.charAt(i)));
    				}
    			}
    		}
    		//System.out.println(String.valueOf(expr.charAt(i))+" "+postfix+" "+operators);
    	}
    	while(!operators.isEmpty())
    	{
    		postfix.add(operators.pop());
    	}
    	System.out.println("Output:"+postfix);
	return evaluatePostfix(postfix.toArray(new String[postfix.size()]));
    }
    
    
    @SuppressWarnings("deprecation")
	private Num evaluate(String s1, String s2, String operator) {

		Num result = null, num1 = null, num2 = null;
		long num3 = 0;

		if (operator.equals("^")) {
			num1 = new Num(s2);
			num3 = new Long(s1);
		} else {
			num1 = new Num(s1);
			num2 = new Num(s2);
		}

		switch(operator) {
		case "+":
			result = add(num2, num1);
			break;
		case "-":
			result = subtract(num2, num1);
			break;
		case "*":
			result = product(num2, num1);
			break;
		case "/":
			result = divide(num2, num1);
			break;
		case "^":
			result = power(num1, num3);
			break;
		case "%":
			result = mod(num2, num1);
			break;
		}
		
		return result;
	}


    public static void main(String[] args) {
	Num x = new Num(3000L);
	Num y = new Num("800");
	Num z = Num.add(x, y);
	Num c = Num.power(y, 2);
	System.out.println("Sum: "+z);
	System.out.println("Sub: "+Num.subtract(x, y));
	System.out.println("Compare: "+x.compareTo(y));
	System.out.println("Product : " + Num.product(x, y));
	System.out.println("Power raised to 2 :" + c);
	System.out.println("Division result : " + Num.divide(x, y));
	System.out.println("Mod result :" + Num.mod(x, y));
	System.out.println("Squre Root result :" + Num.squareRoot(x));
	System.out.println("Base change result to 8 :" + x.convertBase(8));
//	Num a = Num.power(x, 8);
//	System.out.println(a);
//	if(z != null) z.printList();
	String[] pf = new String[] {"3", "4", "2", "*", "1", "5", "-", "2", "3", "^", "^", "/", "+"};
	System.out.println("The result of the postfix notation is : " + Num.evaluatePostfix(pf));
	System.out.println("The result of the infix notation is : " + Num.evaluateExp("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3"));
    }
}
