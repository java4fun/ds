package ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

class LCTest {

	@Test
	// Reverse Integer
	void testReverseInteger() {
		System.out.println("*********reverseInteger()**********************");
		//123 => 321,  -123=>-321,  120=>21
		System.out.println(reverseInteger(-123));
	}
	int reverseInteger(int x) {
		int reversed = 0;
		int pop;
		
		while ( x != 0) {
			pop = x % 10;
			x /= 10;
			
			if (reversed > Integer.MAX_VALUE/10 || reversed == Integer.MAX_VALUE/10 && pop > 7) return 0;
			if (reversed < Integer.MIN_VALUE/10 || reversed == Integer.MIN_VALUE/10/10 && pop < -8) return 0;
			reversed = reversed * 10 + pop;
		}
		return reversed;
	}

	@Test
	// Palindrome Number
	void testPalindromeNumber() {
		System.out.println("*********isPalindromeInteger()**********************");
		//121 => true,  -123=>false,  120=>false
		System.out.println(isPalindromeInteger(-123));
		System.out.println(isPalindromeInteger(121));
	}
	boolean isPalindromeInteger(int x) {
		if (x == 0) return true;
		if (x < 0 || x % 10 == 0) return false;
		
		int reversed = 0;
		int pop;
		while (x > reversed) {  // DO ONLY Half way
			pop = x % 10;
			x /= 10;
			reversed = reversed * 10 + pop;
		}
		if (x == reversed || x == reversed / 10) return true;
		return false;
	}
	
	
	@Test
	// Array contains duplicate
	void testArrayContainsDuplicate() {
		System.out.println("*********arrayContainsDuplicate()**********************");

		System.out.println(arrayContainsDuplicate(new int[]{1,2,3}));
		System.out.println(arrayContainsDuplicate(new int[]{1,2,2}));
	}
	boolean arrayContainsDuplicate (int[] nums) {
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < nums.length; i++) {
			if (set.contains(nums[i])) return true;
			set.add(nums[i]);
		}
		return false;
	}
	
	@Test
	// Generate Pascal Triangle
	void testGeneratePascalTriangle() {
		System.out.println("*********generatePascalTriangle()**********************");

		System.out.println(generatePascalTriangle(2));
		System.out.println(generatePascalTriangle(3));
	}
	List<List<Integer>> generatePascalTriangle(int numRows) {
		List<List<Integer>> triangle = new ArrayList<>();
		if (numRows == 0) return triangle;
		
		List<Integer> firstRow = new ArrayList<>();
		firstRow.add(1);
		triangle.add(firstRow);
		
		for (int i = 1; i < numRows; i++) {  // 2nd Row to Last
			List<Integer> prevRow = triangle.get(i-1);
			List<Integer> row = new ArrayList<>();
			
			row.add(1);
			for (int j = 1; j < i; j++) {    // Row i has i elements
				row.add(prevRow.get(j-1) + prevRow.get(j));
			}
			row.add(1);
			triangle.add(row);
		}
		return triangle;
	}
	
	@Test
	// Binary Search Sorted Array
	// First Bad Version (call API isBadVersion(n)) ==> similar solution using binary search
	void testBinarySearchSortedArray() {
		System.out.println("*********binarySearchSortedArray()**********************");
	}
	int binarySearchSortedArray(int[] nums, int target) {
		if (nums.length == 0) return -1;
		int left = 0;
		int right = nums.length -1;
		
		while (left <= right) {
			int mid = (left + right)/2;
			
			if (nums[mid] == target) {
				return nums[mid];
			} else if (nums[mid] > target) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return -1;  // NOT Found
	}
	
	
	@Test
	// Reverse String in Char array
	void testReverseString() {
		System.out.println("*********reverseString()**********************");
	}
	void reverseString (char[] s) {
		int left = 0;
		int right = s.length-1;
		while (left <= right) {
			char tmp = s[left];
			s[left] = s[right];
			s[right] = tmp;
			left++; right--;
		}
	}
	
	@Test
	// Keys and Rooms: Stack
	void testCanVisitAllRooms() {
		System.out.println("*********canVisitAllRooms()**********************");
	}
	boolean canVisitAllRooms(List<List<Integer>> rooms) {
		boolean[] seen = new boolean [rooms.size()];
		seen[0] = true;

		Stack<Integer> keys = new Stack();
		keys.add(0);  // we have keys to room 0 
		
		while (!keys.isEmpty()) {
			int curKey = keys.pop();
			for(int newKey : rooms.get(curKey)) {
				if(!seen[newKey]) {
					seen[newKey] = true;
					keys.add(newKey);
				}
			}
		}
		
		for (boolean visited : seen) {
			if (!visited) return false;
		}
		return true;
	}
	
	
	@Test
	void testSortedArraySquares() {
		System.out.println("*********sortedArraySquares()**********************");
	}
	// sorted Array Squares
	// IN:  [-4,-1,0,3,10]
	// OUT: [0,1,9,16,100]
	int[] sortedArraySquares(int[] A) {
		// use negativePointer and positivePointer
		int N = A.length;
		
		int sortedSqures[] = new int[N];
		
		// traverse in two directions

		
		return sortedSqures;
	}
	
	
	@Test
	void testMaxArea() {
		System.out.println("*********maxArea()**********************");	
	}
	// Container with Most Water
	int maxArea(int[] heights) {
		int maxArea = 0;
		int left = 0;
		int right = heights.length - 1;
		
		while (left < right) {
			// draw lines in order to see the move
		}
		return maxArea;
	}
	
	@Test
	void testIsNStraightHand() {
		System.out.println("*********isNStraightHand()**********************");	
	}
	boolean isNStraightHand(int[] hand, int W) {
		TreeMap<Integer, Integer> cardCnt = new TreeMap<>();
		for (int card : hand) {
			if (cardCnt.containsKey(card)) 
				cardCnt.replace(card, cardCnt.get(card) + 1);
			else cardCnt.put(card, 1);
		}
		
		while (cardCnt.size() > 0) {
			int firstCard = cardCnt.firstKey();
			for (int i = firstCard; i < firstCard + W; i++) {
				if (!cardCnt.containsKey(i)) return false;
				
				int cnt = cardCnt.get(i);
				if (cnt == 1) {
					cardCnt.remove(i);
				} else {
					cardCnt.replace(i, cardCnt.get(i) - 1);
				}
			}
		}
				
		return true;
	}
	
	
	@Test
	void testPeakIndexInMountainArray() {
		System.out.println("*********peakIndexInMountainArray()**********************");	
	}
	// peak Index In Mountain Array
	int peakIndexInMountainArray(int[] A) {
		int i = 0;
		while (A[i] < A[i+1]) i++;  // assume peak always existed
		return i;
	}
	
	@Test
	void testReverseOnlyLetters() {
		System.out.println("*********reverseOnlyLetters()**********************");
	}
	// reverse only letters
	// ab-cd => dc-ba  
	String reverseOnlyLetters(String S) {
		// loop 1: use Stack to store only letter chars
		
		// loop 2: use StringBuilder to build the new reversed String
		StringBuilder sb = new StringBuilder();
		
		return sb.toString();
	}
	
	@Test
	void testSpiralMatrix() {
		System.out.println("*********spiralMatrix()**********************");
	}
	
	// 1. retrieve spiral matrix of (m, n)
	// 2. generate spiral matrix of (n, n) (1,2,3,4,5,6,7,8,9....)
	List<Integer> spiralMatrix(int[][] matrix) {
		List<Integer> r = new ArrayList<>();
		if (matrix.length == 0) return r;
		
		int rowBegin=0, colBegin = 0;
		int rowEnd = matrix.length -1; 
		int colEnd = matrix[0].length -1;
		
		while (rowBegin <= rowEnd && colBegin <= colEnd) {
			for (int i= colBegin; i <= colEnd; i++) {
				r.add(matrix[rowBegin][i]);
			}
			rowBegin++;
			for (int i = rowBegin; i <=rowEnd; i++) {
				r.add(matrix[i][colEnd]);
			}
			colEnd--;
			
			if (rowBegin <= rowEnd) {
				for (int i=colEnd; i >= colBegin; i--) {
					r.add(matrix[rowEnd][i]);
				}
			}
			rowEnd--;
			
			if (colBegin <= colEnd) {
				for (int i = rowEnd; i <= rowBegin; i--) {
					r.add(matrix[i][colBegin]);
				}
			}
			colBegin++;
		}
		return r;
	}
	
	@Test
	void testLongestOnes() {
		System.out.println("*********longestOnes()**********************");
	}
	// Max Consecutive Ones III with k flips
	// Use Sliding window
	// L     R
	// 1111000111110   k=2
	// slide and expand L R, finally return R-L 
	int longestOnes(int[] A, int K) {
		return 0;
	}
	
	@Test
	void findSingleNumber() {
		System.out.println("*********findSingleNumber()**********************");
	}
	// find Single Number (in a array, only ONE exist, everything else is paired)
	int findSingleNumber (int[] nums) {
		int r = 0;
		
		for (int i = 0; i < nums.length; i++) {
			r ^= nums[i];
		}
		return r;
	}
	
	@Test
	void testFindCommonChars() {
		System.out.println("*********findCommonChars()**********************");
	}
	// Find common chars from a list of LowerCase strings
	// IN: ["bella", "label", "roller"]
	// OUT: ["e", "l", "l"]  -> "l" repeats twice in each string
	List<String> findCommonChars(String[] S) {
		List<String> commonChars = new ArrayList<>();
		int[] minFrequencies = new int[26];
		Arrays.fill(minFrequencies, Integer.MAX_VALUE);
		
		for(String curString : S) {
			int[] charFrequencies = new int[26];
			for (char c : curString.toCharArray()) {
				charFrequencies[c-'a']++;  // all string are lower case
			}
			
			for (int i = 0; i < 26; i++) {
				minFrequencies[i] = Math.min(minFrequencies[i], charFrequencies[i]);
			}
		}
		
		for (int i = 0; i < 26; i++) {
			while (minFrequencies[i]>0) {
				commonChars.add("" + (char)(i+'a'));
				minFrequencies[i]--;
			}
		}
		
		return commonChars;
	}
	
	@Test
	void testMaxProfit() {
		System.out.println("*********maxProfit()**********************");
	}
	// Best time to buy and sell stocks (1 buy/sell Only)
	int maxProfit(int[] prices) {
		int minVal = Integer.MAX_VALUE;
		int maxProfit = 0;
		for (int i = 0; i < prices.length; i++) {
			if (prices[i] < minVal) {
				minVal = prices[i];
			} else if (prices[i] - minVal > maxProfit) {
				maxProfit = prices[i] - minVal;
			}
		}
		return maxProfit;
	}
	// Best time to buy and sell stocks (can have more than 1 trade)
	int maxProfit2(int[] prices) {
		return 0;
	}
	
	@Test
	void testRotateString() {
		System.out.println("*********rotateString()**********************");
	}
	// Is String B a rotated string of A
	// IN: A= "abcde"  B="cdeab"  return true
	boolean rotateString(String A, String B) {
		return (A.length() == B.length() && (A+A).contains(B));
	}
	
	@Test
	void testFizzBuzz() {
		System.out.println("*********fizzBuzz()**********************");
	}
	// Fizz Buzz
	List<String> fizzBuzz(int n) {
		List<String> result = new ArrayList<>();
		for (int i = 1, fizz=0, buzz=0; i <= n; i++) {
			fizz++; buzz++;
			if (fizz == 3 && buzz == 5) {
				fizz=0; buzz=0;
				result.add("FizzBuzz");
			} else if (fizz == 3) {
				fizz=0;
				result.add("Fizz");
			} else if (buzz == 5) {
				buzz = 0;
				result.add("Buzz");
			} else {
				result.add(Integer.toString(i));
			}
		}
		return result;
	}
	
	@Test
	void testLengthOfLongestSubstring() {
		System.out.println("*********lengthOfLongestSubstring()**********************");
	}
	// Length of the Longest SubString
	int lengthOfLongestSubstring(String s) {
		int p1=0, p2 = 0;
		int max =0;
		HashSet<Character> set = new HashSet<>();
		while (p2 < s.length()) {
			if(!set.contains(s.charAt(p2))) {
				set.add(s.charAt(p2));
				p2++;
				max=Math.max(set.size(), max);
			} else {
				set.remove(s.charAt(p1));  
				p1++;
			}
		}
		
		return max;
	}
	String removeVowels(String s) {
		return s.replaceAll("[aeiou]", "");
	}
	
	@Test
	void testMostCommonWord() {
		System.out.println("*********mostCommonWord()**********************");
	}
	// Most Common Word excluding banned words
	String mostCommonWord(String paragraph, String[] banned) {
		HashSet<String> bannedWords = new HashSet<String>();
		HashMap<String, Integer> wordCount= new HashMap<>();
		
		for(String ban : banned) {
			bannedWords.add(ban);
		}
		String[] words = paragraph.toLowerCase().split("\\W+"); // \W not word character
		
		for (String word : words) {
			if (!bannedWords.contains(word)) {
				wordCount.put(word, wordCount.getOrDefault(word,0)+1);
			}
		}
		
		int max = 0;
		String result = "";
		for(String word : wordCount.keySet()) {
			if (wordCount.get(word) > max) {
				max = wordCount.get(word);
				result = word;
			}
		}
		return result;
	}
	
	@Test
	void testNextGreaterElement() {
		System.out.println("*********nextGreaterElement()**********************");
	}
	// Next Greater Element I
	// IN: A=[4,1,2],  B=[1,3,4,2]
	// OUT: [-1, 3, -1]
	int[] nextGreaterElement(int[] A, int[] B) {
		HashMap<Integer, Integer> nextGreatest = new HashMap();
		Stack<Integer> stack = new Stack<>();
		
		// build next greater map with stack as temp storage
		for (Integer i : B) {
			while (!stack.isEmpty() && stack.peek() < i) {
				nextGreatest.put(stack.pop(), i);
			}
			stack.push(i);
		}
		
		// modify A to store the next greater value
		for(int i =0; i< A.length; i++) {
			A[i] = nextGreatest.getOrDefault(A[i], -1); 
		}
		return A;
	}
	 
	@Test
	void testNextGreaterElement2() {
		System.out.println("*********nextGreaterElement2()**********************");
	}
	// Next Greater Element I
	// IN: [1,2,1]  => [1,2,1,  1,2,1]
	// OUT: [2, -1, 2]
	int[] nextGreaterElement2(int[] A) {
		int n = A.length;
		int[] outA = new int[n];
		Arrays.fill(outA, -1);
		
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < n*2; i++) {
			while (!stack.isEmpty() && A[stack.peek()] < A[i%n]) {
				outA[stack.pop()] = A[i%n];
			}
			
			if (i < n) stack.push(i);
		}
		
		return outA;
	}
	
	@Test
	void testSortColors() {
		System.out.println("*********sortColors()**********************");
	}
	// sort in-place (0, 1, 2, RED, WHITE, BLUE)
	// IN [2,0,2,1,1,0]
	// OUT[0,0,1,1,2,2]
	void sortColors(int[] colors) {
		// SWAP:
		// 0 to the front
		// 2 to the end
		// 1 in the middle
		if (colors.length == 0 || colors.length == 1) return;
		
		int start = 0;
		int end = colors.length-1;
		int index = 0;
		while (index <= end && start < end) {
			if (colors[index] == 0) {
				
			} else if (colors[index] == 2) {
				
			} else { // 1
				index++;
			}
		}
	}
	
	@Test
	void testLenonadeChange() {
		System.out.println("*********lenonadeChange()**********************");
	}
	// lemonade change
	boolean lenonadeChange(int[] bills) {
		int fives = 0;
		int tens = 0;
		
		for (int b : bills) {
			if (b == 5) {
				fives++;
			} else if (b == 10) {
				fives--;
			} else {  // 20
				if (tens > 0) {
					tens--;
					fives--;
				} else {
					fives -=3;
				}
			}
			if (fives < 0) return false;
		}
		return true;
	}
	
	@Test
	void testRemoveAdjacentDuplicate() {
		System.out.println("*********removeAdjacentDuplicate()**********************");
	}
	//remove Adjacent Duplicate
	// "abbaca"  =>  "ca"
	String removeAdjacentDuplicate(String s) {
		char[] stack = new char[s.length()];
		int i = 0;
		for (int j = 0; j < s.length(); j++) {
			char curChar = s.charAt(j);
			if (i>0 && stack[i-1] == curChar) {
				i--;  // will override the spot (kind of a pop())
			} else {
				stack[i] = curChar;
				i++;
			}
		}
		
		return new String(stack, 0, i);
	}
	
	@Test
	void testCharacterReplacement() {
		System.out.println("*********characterReplacement()**********************");
	}
	//
	// Longest Repeating Character Replacement: 
	// can replace same char K times
	// All UPPER case
	int characterReplacement(String s, int k) {
		int n = s.length();
		int[] charCnt = new int[26];
		int maxLength = 0;
		int maxCount = 0;
		
		// sliding window
		int p1, p2;
		// TBD:
		
		return maxLength;
	}
	
	@Test
	void testCanPermutatePalindrome() {
		System.out.println("*********canPermutatePalindrome()**********************");
	}
	// Palindrome permutation
	boolean canPermutatePalindrome(String s) {
		int[] charCount = new int[128];
		for(int i=0; i<s.length(); i++) {
			charCount[s.charAt(i)]++;
		}
		
		int count = 0;
		for (int i = 0; i < 128; i++) {
			count += charCount[i]%2;  // 0 paired, 1 -> 1 left over
		}
		return count <= 1; // 0 or 1 left over
	}
	
}
