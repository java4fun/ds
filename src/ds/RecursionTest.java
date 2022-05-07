package ds;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RecursionTest {

	
	
	@Test
	void testPowerAndFactorial() {
		
		System.out.println("*******power()**********************");
		System.out.println(power(2, 4));
		System.out.println(power(2, 5));
		System.out.println(power(3, 4));
		
		System.out.println(factorial(4));
	}
	private static double power(double x, int n) {
		double y;
		if (n == 0) return 1.0;
		y = power(x, n/2);
		y = y * y;
		
		if (n % 2 == 0) return y;
		
		return x * y;
	}
	public long factorial(int n) {
		if (n == 0) return 1;
		return n * factorial(n-1);
	}
	
	
	
	
	
	
	@Test
	void testFibonacci() {
		System.out.println("*******fibonacci()**********************");
		System.out.println(fib(3));
		System.out.println(fib(4));
		System.out.println(fib(5));
		System.out.println(fib(6));
	}
	private static int fib (int n) {
		if (n == 1 || n == 2) return 1;
		return fib(n - 1) + fib(n - 2);
	}
	
	
	
	
	
	
	
	@Test
	void testDecimalToBinary() {
		System.out.println("*******printBinary()**********************");
		printBinary(2); System.out.println();
		printBinary(3); System.out.println();
		printBinary(4); System.out.println();
		printBinary(5); System.out.println();
		
		
	}
	private static void printBinary (int n) {
		if (n > 0) {
			printBinary(n/2);
			System.out.printf("%d", n%2);
		}
	}
	
}
