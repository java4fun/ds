import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.IntStream;



class Demo2Test {

	// 5. Palindrome
	boolean palindromeCheckerPointer(String original) {
		String s = original.toLowerCase();
		int left =  0;
		int right = s.length() -1;
		while (left <= right) {
			if (s.charAt(left) != s.charAt(right)) return false;
			left++; right--;
		}
		return true;
	}
	
	boolean palindromeCheckerStream(String original) {
		String s = original.toLowerCase();
		return IntStream
				.range(0, s.length()/2)
				.allMatch(i -> s.charAt(i) == s.charAt(s.length()-1-i));
	}
	
	
	
	// 6. Count Vowels and Consonants
	void findNumberOfVowelsAndConsonants(String input) {
		String s = input.toLowerCase().trim();
		String vowels = "aoieuy";
		int cntVowel = 0; int cntConsonant =0;
		
		char[] chars = s.toCharArray();
		for(char c : chars) {
			if (vowels.indexOf(c) != -1 ) {
				cntVowel++;
			} else if (c != ' ') {
				cntConsonant++;
			}
		}
			
        System.out.println("There are " + cntVowel + " vowels in " + input);
        System.out.println("There are " + cntConsonant + " consonants in " + input);
	}
	
	
	
	
	// 7. Maximum Product of 2 numbers in a Array
	int maxProduct(int[] a) {
		int length = a.length;
		
		if (length < 2) {
            System.out.println("No maximum exists, returning sentinel value");
            return Integer.MIN_VALUE;
		}
		
		Arrays.sort(a);
		int leftProduct = a[0] * a[1];
		int rightProduct = a[length-1] * a[length-2];
		return Math.max(leftProduct, rightProduct);
	}
	
	// 8. Delete middle node in a Linked List
	ListNode deleteMiddleNode(ListNode head) {
		if (head == null || head.next == null) return head;  // nothing to delete
		
		ListNode slow = head;
		ListNode fast = head;
		ListNode prev = null;
		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			prev = slow;
			slow = slow.next;
		}
		prev.next = slow.next;
		return head;
	}
	
	
	
	// 9. Generate Binary Numbers
	// Integer.toBinaryString(i)
	// Queue
	void printBinaryUsingQueue(int n) {
		if (n <= 0) return;  // nothing to print
		
		Queue<Integer> queue = new LinkedList<>();
		queue.add(1);
		
		for(int i = 1; i <= n; i++) {
			Integer current = queue.remove();
			System.out.println(current);
			queue.add(current * 10);
			queue.add(current * 10 + 1);
		}
	}

	
	
	
	// 10. Matching Parentheses
	// Stack
	boolean matchingParentheses(String s) {
		Map<Character, Character> matchMap = Map.of(')','(',']','[','}','{');
		
		Stack<Character> stack = new Stack<>();
		
		for (int i = 0; i < s.length(); i++ ) {
			char c = s.charAt(i);
			if(matchMap.containsValue(c)) {
				stack.push(c);
				continue;
			}
			
			if(matchMap.containsKey(c) // AND none in stack, or the next is not a match
					&& (stack.isEmpty() || matchMap.get(c) != stack.pop())) {
				return false;
			}
		}
		
		return stack.isEmpty();
	}
	
	
	
	
	// Reverse Integer
	//123 => 321,  -123=>-321,  120=>21
	int reverseInteger(int x) {
		int reversed = 0;
		int pop;
		while (x != 0 ) {
			pop = x % 10;
			x /= 10;
			reversed = reversed * 10 + pop;
		}
		return reversed;
	}
	
	
	
	// Palindrome Number
	//121 => true,  -123=>false,  120=>false
	// similar to the above, only do half way, and CHECK result
	boolean isPalindromeInteger(int x) {
		if (x == 0) return true;
		if (x < 0 || x % 10 == 0) return false;
		
		int reversed = 0;
		int pop;
		while ( x > reversed) {  // DO ONLY HALF
			pop = x % 10;
			x /= 10;
			reversed = reversed * 10 + pop;
		}
		
		if (x == reversed || x == reversed / 10) return true;
		return false;
	} 
	
	
	
	// Array contains duplicate
	boolean arrayContainsDuplicate(int [] a) {
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			if (set.contains(a[i])) return true;
			set.add(a[i]);
		}
		return false;
	}
	
	
	
	// Generate Pascal Triangle
	List<List<Integer>> generatePascalTriangle(int n) {
		List<List<Integer>> triangle = new ArrayList<>();
		if (n == 0 ) return triangle;
		
		List<Integer> firstRow = new ArrayList<>();
		firstRow.add(1);
		triangle.add(firstRow);
		
		List<Integer> prevRow = firstRow;
		for (int i = 1; i < n; i++) {  // 2nd to last
			List<Integer> curRow = new ArrayList<>();
			
			curRow.add(1);
			for (int j = 1; j < i; j++) { // row i has ONLY i elements
				curRow.add(prevRow.get(j-1) + prevRow.get(j));
			}
			curRow.add(1);
			
			triangle.add(curRow);
			prevRow = curRow;
		}
		return triangle;
	}
	
	
	// Binary Search Sorted Array: return the Index or -1 if NOT FOUND
	// First Bad Version (call API isBadVersion(n))
	//     similar solution using binary search
	int binarySearchSortedArray(int[] a, int target) {
		if (a.length == 0) return -1;
		
		int left = 0;
		int right = a.length -1;
		while (left <= right) {
			int mid = (left+right)/2;
			if (a[mid] == target) 
				return mid;  // return the index
			else if (a[mid] > target) 
				right = mid -1;
			else 
				left = mid + 1;
		}
		
		return -1; // Not found
	}
	
	
	
	// Reverse String in Char array
	void reverseString (char[] s) {
		int left = 0;
		int right = s.length -1;
		
		while(left <= right) {
			char tmp = s[left];
			s[left] = s[right];
			s[right] = tmp;
			left++; right--;
		}
	}
	
	
	// Keys and Rooms: Stack
	boolean canVisitAllRooms(List<List<Integer>> rooms) {
		boolean[] seen = new boolean[rooms.size()];
		seen[0] = true;
		
		Stack<Integer> roomKeys = new Stack<>();
		roomKeys.add(0);   // we have key to room 0
		
		while (!roomKeys.isEmpty()) {
			int curKey = roomKeys.pop();
			for(int newKey : rooms.get(curKey)) {
				if (!seen[newKey]) {
					seen[newKey] = true;
					roomKeys.add(newKey);
				}
			}
		}
		
		for(boolean visited : seen) {
			if (!visited) return false;
		}
		
		return true;
	}
	
	
	
	// sorted Array Squares
	// IN:  [-4,-1,0,3,10]
	// OUT: [0,1,9,16,100]
	// start from the center, run both direction
	int[] sortedArraySquares(int[] A) {
		// use negativePointer and positivePointer
		int N = A.length;
		int sortedSqures[] = new int[N];
		
		// traverse in two directions
		
		return sortedSqures;
	}
	
	
	
	// Container with Most Water
	// two pointers
	int maxArea(int[] heights) {
		int maxArea = 0;
		int left = 0;
		int right = heights.length - 1;
		
		while (left < right) {
			// draw lines in order to see the move
		}
		return maxArea;
	}
	
	
	
	// peak Index In Mountain Array
	int peakIndexInMountainArray(int[] A) {
		int i = 0;
		while (A[i] < A[i+1]) i++;  // assume peak always existed
		return i;
	}
	
	
	
	// reverse only letters
	// ab-cd => dc-ba  
	String reverseOnlyLetters(String S) {
		// loop 1: use Stack to store only letter chars
		
		// loop 2: use StringBuilder to build the new reversed String
		StringBuilder sb = new StringBuilder();
		
		return sb.toString();
	}
	
	
	
	// 1. retrieve spiral matrix of (m, n)
	// 2. generate spiral matrix of (n, n) (1,2,3,4,5,6,7,8,9....)
	List<Integer> spiralMatrix(int[][] A) {  
		List<Integer> r = new ArrayList<>();
		if(A.length == 0) return r;
		
		int rBegin = 0, cBegin = 0;
		int rEnd = A.length -1;
		int cEnd = A[0].length -1;
		
		while (rBegin <= rEnd && cBegin <= cEnd) {
			for (int i = cBegin; i <= cEnd; i++) {
				r.add(A[rBegin][i]);
			}
			rBegin--;
			
			for (int i = rBegin; i <= rEnd; i++) {
				r.add(A[i][cEnd]);
			}
			cEnd++;	
			
			if (rBegin <= rEnd) {
				//
			}
			rEnd--;
			
			
			// One more
		}
		
		return r;
	}

	
	// Max Consecutive Ones III with k flips
	// Use Sliding window
	
	
	// find Single Number (in a array, only ONE exist, everything else is paired)
	// bit XOR (^)
	
	
	// Best time to buy and sell stocks (1 buy/sell Only)
	// Find a min, then find the max price diff
	int maxProfit(int[] prices) {
		int min = Integer.MAX_VALUE;
		int maxProfit = 0;
		
		for (int i = 0; i < prices.length; i++) {
			if (prices[i] < min) {
				min = prices[i];
			} else if (prices[i] - min > maxProfit) {
				maxProfit = prices[i] - min;
			}
		}
		
		return maxProfit;
	}
	
	
	
	// Is String B a rotated string of A
	// IN: A= "abcde"  B="cdeab"  return true
	// A+A contains B
	boolean rotateString(String A, String B) {
		return A.length() == B.length() && (A+A).contains(B);
	}
	
	
	
	
	
}



