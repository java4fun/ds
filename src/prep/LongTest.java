package prep;

import java.math.BigInteger;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.junit.jupiter.api.Test;


public class LongTest {


	// Stream && Lambda (functional)
	// 1. create stream
	static Integer[] intArray = {1,2,3,4,5,6,7,8,9,10};
	static List<Integer> intList = new ArrayList<> (Arrays.asList(intArray));

	static String[] shopArray = {"apple", "banana", "cherry", "coffee"};
	static String[] shopArray2 = new String[] {"apple", "banana", "cherry", "coffee"};
	static List<String> shopList = List.of("apple", "banana", "cherry", "coffee"); // immutable
	static Stream<String> shopListStream = shopList.stream();

	static Stream<String> shopStream = Stream.of("apple", "banana", "cherry", "coffee"); 
	static Stream<String> shopStream2 = Arrays.stream(shopArray);

	@Test
	void testStreamAndLambda() {

	// Predicate -> test(?), Consumer -> accept(?), Supplier -> get(),  Function -> apply(x)
	// stream -> forEach, anyMatch, map, filter, reduce(0, BinaryOperator<> func(acc, x))
	//***** Collectors.toSet(), joining(), counting(), groupingBy, partitioningBy *****
	// build-in function: IntFunction, BiFunction, UnaryOperator, BinaryOperator, etc

	}

    // Custom Function:
    @FunctionalInterface
    interface GreetingFunction {
    	void sayGreeting(String msg);
    }
    @FunctionalInterface
    interface Calculate {
    	int calc(int x, int y);
    }
	// define Tri args func interface
    @FunctionalInterface
    interface TriFunction<T, U, V, R> {
    	R apply(T t, U u, V v);
    }

    // define No arg function
    interface NoArgFunction<R> {
    	R apply();
    }

    // Used by DataLoader
    class Person {
    	private String name;
    	private Integer age;
    	public Person(String name, Integer age) {
    		this.name = name;
    		this.age = age;
    	}
    }	

	// 15. Calculation as functional interface
	public static void CalculateFunctions() {
		Calculate add = (a, b) -> a + b;
		Calculate diff = (a, b) -> Math.abs(a-b);
		Calculate divide = (a, b) -> (b != 0 ? a/b : 0);

        System.out.println(add.calc(3,2));
        System.out.println(diff.calc(5,10));
        System.out.println(divide.calc(5, 0));
	}


	// 16. Collector and Stream API
	public static void StreamApiWithCollector() {
		List<String> names = Arrays.asList("Paul", "Jane", "Sam", "Adam", "David");
		List<String> animals = Arrays.asList("dog", "cat", "panda", "tiger", "puma");

		// Sort: prior to Java 8
		Collections.sort(names, new Comparator<String>() {

			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});

		// re-factor 1: lambda with return block
		Collections.sort(names, (String a, String b) -> {
			return b.compareTo(a);
		});

		// re-factor 2: NO return block
		Collections.sort(names, (String a, String b) -> b.compareTo(a));


		// re-factor 3: Not type declaration (*** Preferred ***)
		Collections.sort(names, (a, b) -> b.compareTo(a));



		System.out.println(names);

	}


	/*
	 * HACKERRANK - algorithm
	 * 
	 */
	// 5. Palindrome
	// two runner close in
	boolean palindromeCheckerPointer(String orig) {
		String s = orig.trim().toLowerCase();
		int left = 0, right = s.length()-1;
		while(left < right) {
			if (s.charAt(right) != s.charAt(left)) return false;
			left++; right--;
		}
		return true;
	}
	boolean palindromeCheckerStream(String orig) {
		String s = orig.trim().toLowerCase();
		return IntStream.range(0, s.length()/2)
				.allMatch(i -> s.charAt(i) == s.charAt(s.length()-i-1));
	}

	// LC: 5. Longest Palindromic Substring (Sliding window):
	// use start, end to track the range: 2 cases: "racecar", "aabbaa"
	// https://youtu.be/y2BD4MJqV20
	// O(n^2)
	public String longestPalindromeSubstring2(String s) {
		if (s == null || s.length() < 1) return "";
		int start = 0, end = 0;   // track the longest sub
		for (int i = 0; i < s.length(); i++) {
			// racecar
			int len1 = expandFromMiddle(s, i, i);
			// aabbaa
			int len2 = expandFromMiddle(s, i, i+1);
			int len = Math.max(len1, len2);
			if (len > end - start) {
				start = i - (len-1)/2;
				end = i + len/2;   // does it work for both case 1 and case 2
			}
		}
		return s.substring(start, end + 1);
	}
	private int expandFromMiddle(String s, int left, int right) {
		if (s == null || left > right) return 0;

		while (left >=0 && right < s.length()
			&& s.charAt(right) == s.charAt(left)) {
			left--; right++;
		}
		return right - left - 1; 
	}
	@Test
	void testLongestPalindromeSubstring() {
		System.out.println(longestPalindromeSubstring2("racecar"));
		System.out.println(longestPalindromeSubstring2("aabbaa"));
	}


	// 6. Count Vowels and Consonants  =>  String vowels = "aeiouy";


	// 7. Max product of 2 numbers in a Array   => "SORT it" => leftProduct, rightProduct


	// 163. Missing Ranges (find missing ranges for sorted array)
	private String getRange(int down, int up) {
		return down == up ? String.valueOf(up) : down + "->" + up;
	}
	public List<String> findMissingRanges(int[] nums, int lower, int upper) {
		List<String> ranges = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			// lower/left boundary
			if (nums[i] < lower) continue;   // ignore, skip
			if (nums[i] == lower) {
				lower++;  // move to next
				continue;
			}
			// otherwise: nums[i] > lower  ==> add range
			ranges.add(getRange(lower, nums[i] - 1));  
			lower = nums[i] + 1;
		}
		// upper/right boundary: add remaining range
		if (lower <= upper) {
			ranges.add(getRange(lower, upper));
		}
		return ranges;
	}


	// 169. Majority Element
	// Given an array nums of size n, return the majority element.
	// the majority element  appears more  than (n/2) times
	// Assumption: array not empty, it has majority
	// Input: nums = [3,2,3]  =>  Output: 3
	// https://youtu.be/cLuLy6dy7n8
	// https://youtu.be/bg6r_fgtpMQ  =>  O(n)
	public int majorityElement(int[] nums) {
		int count = 0;
		Integer candidate = null;
		for (int num : nums) {
			if (count == 0) candidate = num;

			count += (num == candidate ? 1 : -1);
		}
		return candidate;
	}
	public int majorityElement2(int[] nums) {
		if (nums.length == 1) return nums[0];
		Map<Integer, Integer> map = new HashMap<>();
		for (int i : nums) {
			if (map.containsKey(i) && map.get(i) + i > nums.length/2) return i;
			else
				map.put(i, map.getOrDefault(i, 0) + 1);
		}

		return -1;  // Assumption array has the majority, only 1 can be more than n/2
	}



	// 189. Rotate Array  (TRICK: reverse 3 times)
	// Given an array, rotate the array to the right by k steps, where k is non-negative
	// Input: nums = [1,2,3,4,5,6,7], k = 3
	// Output: [5,6,7,1,2,3,4]
	// 1. reverse ALL			7 6 5 4 3 2 1
	// 2. reverse k elements    5 6 7 4 3 2 1
	// 3. reverse the rest		5 6 7 1 2 3 4
	public void rotate2(int[] nums, int k) {
		int n = nums.length;
		k = k%n; // in case k > n  (ex. k=9, n=7, we only need rotate 2 times, NOT 9 times)
		reverse(nums, 0, n);
		reverse(nums, 0, k-1);
		reverse(nums, k, n-1);
	}
	private void reverse(int[] nums, int start, int end) {
		while (start < end) {
			int tmp = nums[start]; nums[start] = nums[end]; nums[end] = tmp;
			start++; end--;
		}
	}


	// 10. Matching parentheses
	// STACK
	boolean matchingParentheses(String s) {
		Map<Character, Character> matchMap = Map.of(')', '(',   ']', '[',   '}', '{');
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (matchMap.containsValue(c)) {
				stack.push(c);
				continue;
				// skip the matching logic
			}

			if (matchMap.containsKey(c) // AND none in stack, or the next is not a match
					&& (stack.isEmpty() || matchMap.get(c) != stack.pop())) {
				return false;
			}
		}

		return stack.isEmpty(); // Nothing left, so ALL matched
	}


	// 11. Container With Most Water
	// two pointers
	int maxArea2(int[] heights) {
		int maxArea = 0;
		int left = 0;
		int right = heights.length - 1;

		while (left < right) {
			// draw lines in order to see the move
			if (heights[left] <= heights[right]) {
				maxArea = Math.max(maxArea, heights[left] * (right - left));
				left++;
			} else {
				maxArea = Math.max(maxArea, heights[right] * (right - left));
				right--;
			}
		}
		return maxArea;
	}


	// 14. Longest Common Prefix
	// Input: strs = ["flower","flow","flight"]
	// Output: "fl"
	// Set Prefix to the 1st element, then remove from the end as we loops, the remaining part will be the answer
	public String longestCommonPrefix(String[] strs) {
		if (strs.length == 0) return "";
		String prefix = strs[0];  // the 1st element as prefix
		for (int i = 1; i < strs.length; i++) {
			while (strs[i].indexOf(prefix) != 0) {
				prefix = prefix.substring(0, prefix.length()-1);
			}
		}
		return prefix;
	}


	// 1. Two Sum (unique array)
	// MAP to keep track
	public int[] twoSum(int [] nums, int target) {
		int[] ans = new int[2];
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (map.containsKey(target - nums[i])) {
				ans[1] = i;
				ans[0] = map.get(target - nums[i]);
				return ans;
			}
			map.put(nums[i], i);
		}
		return ans; // NOT FOUND [0, 0]
	}
	// 1.1. Two Sum 2 (Sorted array)
	// two runners close in from both ends
	public int[] twoSum2(int [] nums, int target) {
		int left = 0, right = nums.length;
		while (left <= right) {
			if (nums[left] + nums[right] == target) {
				return new int[] {left, right};
			} else if (nums[left] + nums[right] < target) {
				left++;
			} else {
				right--;
			}
		}
		return new int[] {left, right};
	}


	// 217.  (Array) Contains duplicate
	// EASY: HashSet
	boolean hasDuplicate(int [] a) {
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			if (set.contains(a[i])) return true;
			set.add(a[i]);
		}
		return false;
	}


	// 118. Generate Pascal Triangle 
	//   [[1], 
	//  [1, 1], 
	// [1, 2, 1]]
	List<List<Integer>> generatePascalTriangle(int n) {
		List<List<Integer>> triangle = new ArrayList<>();
		if (n == 0) return triangle;

		List<Integer> firstRow = new ArrayList<>();
		firstRow.add(1); triangle.add(firstRow);

		List<Integer> prevRow = firstRow;
		for (int i = 1; i < n; i++) {  // 2nd to last
			List<Integer> curRow = new ArrayList<>();

			curRow.add(1);  // starts with 1 
			for (int j = 1; j < i; j++) { // row i has ONLY i elements
				curRow.add(prevRow.get(j-1) + prevRow.get(j));
			}
			curRow.add(1); // and ends with 1
			triangle.add(curRow);
			prevRow = curRow;
		}
		return triangle;
	}




	// 704. Binary Search (Sorted Array)
	// Binary Search sorted Array: return the index or -1 if NOT FOUND
	int binarySearchSortedArray(int[] a, int target) {
		if (a.length == 0) return -1;

		int left = 0, right = a.length -1;
		while (left <= right) {
			int mid = (left + right)/2;
			if (a[mid] == target)
				return mid;  // return index
			else if (a[mid] > target) 
				right = mid - 1;
			else
				left = mid + 1;
		}

		return -1;  // NOT FOUND
	}




	// 796. Rotate String
	// Is String B a rotated string of A
	// IN: A= "abcde"  B="cdeab"  return true
	// A+A contains B
	boolean rotateString(String A, String B) {
		return A.length() == B.length() && (A+A).contains(B);
	}



	// 33. Search in Rotated Sorted Array
	// Input: nums = [4,5,6,7, 0,1,2], target = 0
	// Output: 4 (index)
	// Input: nums = [4,5,6,7, 0,1,2], target = 3
	// Output: -1 (not found)
	public int search(int[] nums, int target) {

		// find index of smallest num, above example is 0
		// figure out which side to search
		// Binary search that side
		return -1;
	}




	// 34. Find First and Last Position of Element in Sorted Array
	// Input: nums = [5,7,7,8,8,10], target = 8 =>	Output: [3,4]
	// Input: nums = [5,7,7,8,8,10], target = 6 ==>  Output: [-1,-1]
	// Input: nums = [], target = 0  => 	Output: [-1,-1]
	public int[] searchRange(int[] nums, int target) {
		return new int[] { binarySearchForMinIdx(nums, target), binarySearchForMaxIdx(nums, target) };
	}
	// findStartingIndex()
	private int binarySearchForMinIdx(int[] nums, int target) {
		return -1; 
	}
	// findEndingIndex
	private int binarySearchForMaxIdx(int[] nums, int target) {
		return -1;
	}



	// 48. Rotate Image
	public void rotate(int[][] matrix) {
		int n = matrix.length;
		for (int i = 0; i < (n + 1) / 2; i++) {
			for (int j = 0; j < n / 2; j++) {
				int temp = matrix[n - 1 - j][i];
				matrix[n - 1 - j][i] = matrix[n - 1 - i][n - j - 1];
				matrix[n - 1 - i][n - j - 1] = matrix[j][n - 1 - i];
				matrix[j][n - 1 - i] = matrix[i][j];
				matrix[i][j] = temp;
			}
		}
	}


	// 49. Group Anagrams (amazon)
	// https://youtu.be/ptgykfAEax8
	// sort, track and group
	public List<List<String>> groupAnagrams2(String[] strs) {
		List<List<String>> groupedAnagrams = new ArrayList<>();
		HashMap<String, List<String>> map = new HashMap<>();
		for (String s : strs) {
			char[] charArray = s.toCharArray();
			Arrays.sort(charArray);
			String sorted = new String(charArray);

			if (!map.containsKey(sorted)) {
				// init if not exist
				map.put(sorted, new ArrayList<>());
			}
			map.get(sorted).add(s); // add the original string
		}
		groupedAnagrams.addAll(map.values());
		return groupedAnagrams;
	}


	// 53. Maximum Subarray (max sum contiguous sub-array)
	// Given an integer array nums, find the contiguous subarray 
	// (containing at least one number) which has the largest sum and return its sum.
	// Input: nums = [-2,1,-3,4,-1,2,1,-5,4]  =>  Output: 6
	// Explanation: [4,-1,2,1] has the largest sum = 6.
	// memorize prev sum + one additional
	// Better solution: Sliding window, drop anything negative
	public int maxSubArray(int[] nums) {
		int currSum = 0;
		int maxSum = Integer.MIN_VALUE;
		for (int num : nums) {
			currSum += num;
			if (num > currSum) {
				currSum = num;
			}
			maxSum = Math.max(maxSum, currSum);
		}
		return maxSum;
	}



	// 54. Spiral Matrix
	// 1. retrieve spiral matrix of (m, n)
	// 2. generate spiral matrix of (n, n) (1,2,3,4,5,6,7,8,9....)
	// https://youtu.be/BdQ2AkaTgOA
	List<Integer> spiralMatrix(int[][] A) {  
		List<Integer> r = new ArrayList<>();
		if(A.length == 0) return r;

		// row, column
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


	// 55. Jump Game
	// nums = [2,3,1,1,4] -> true
	// nums = [3,2,1,0,4] -> false
	// https://youtu.be/Zb4eRjuPHbM
	// BACKWARDS to keep track of the lastGoodIndex
	public boolean canJump2(int[] nums) {
		int lastGoodIndex = nums.length -1;
		for (int i = nums.length-1; i>=0; i--) {
			if (i + nums[i] >= lastGoodIndex) {
				lastGoodIndex = i;
			}
		}
		return lastGoodIndex == 0; // if we are at the 1st element, we are GOOD
	}



	// 56. Merge Intervals
	// Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
	// Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
	// Output: [[1,6],[8,10],[15,18]]
	// https://youtu.be/qKczfGUrFY4
	public int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, 
				Comparator.comparingInt((int[] o) -> o[0])
				.thenComparingInt(o -> o[1]));
		List<int[]> mergedIntervals = new ArrayList<>();
		int idx = 0;
		while (idx < intervals.length) {
			int currentStart = intervals[idx][0];
			int currentEnd = intervals[idx][1];
			idx++;
			while (idx < intervals.length && intervals[idx][0] <= currentEnd) {
				currentEnd = Math.max(intervals[idx][1], currentEnd);
				idx++;
			}
			mergedIntervals.add(new int[] { currentStart, currentEnd });
		}

		// convert to 2D array to return
		int[][] result = new int[mergedIntervals.size()][2];
		for (int i = 0; i < mergedIntervals.size(); i++) {
			result[i] = mergedIntervals.get(i);
		}
		return result;
	}



	// 62. Unique Paths
	// Robot move from top left corner to botton right color of 2D grid
	// https://youtu.be/6qMFjFC9YSc
	// DP
	public int uniquePaths2(int m, int n) {
		int[][] dp = new int[m][n];
		for (int i = 0; i < dp.length; i++) {
			dp[i][0] = 1; // 1st column
		}
		for (int i = 0; i < dp[0].length; i++) {
			dp[0][i] = 1; // 1st row
		}

		for (int i = 1; i < dp.length; i++) {
			for (int j = 1; j < dp[i].length; j++) {
				dp[i][j] = dp[i-1][j] + dp[i][j-1];
			}
		}
		return dp[m-1][n-1];
	}




	// 70. Climbing Stairs
	// You are climbing a staircase. It takes n steps to reach the top.
	//	Each time you can either climb 1 or 2 steps. \
	// Q:  In how many distinct ways can you climb to the top?
	// https://youtu.be/uHAToNgAPaM
	// DP: bottom up (base case: 1, 2 steps)
	public int climbStairs(int n) {
		if (n == 1) {
			return 1;
		}
		int[] dp = new int[n + 1];   // we don't use index 0
		dp[1] = 1;
		dp[2] = 2;
		for (int i = 3; i <= n; i++) {
			dp[i] = dp[i - 1] + dp[i - 2];
		}
		return dp[n]; // index [0, 1, 2, ... n] => n+1 elements
	}




	// 75. Sort Colors
	// Given an array nums with n objects colored red, white, or blue, sort them
	// in-place so that objects of the same color are adjacent, with the colors in
	// the order red, white, and blue.
	// Input: nums = [2,0,2,1,1,0]
	// Output: [0,0,1,1,2,2]
	// swap 0 to start index, swap 2 to end index (keep track), 
	public void sortColors2(int[] nums) {
		if(nums.length == 0 || nums.length == 1) return;
		int start = 0, end = nums.length-1;
		int index = 0;
		while (index <= end && start < end) {
			if (nums[index] == 0) {
				nums[index] = nums[start]; // save start to index
				nums[start] = 0;
				start++;
				index++;  // index start from head, so it only moves up
			} else if (nums[index] == 2) {
				nums[index] = nums[end]; // save end to index
				nums[end] = 2;
				end--;
			} else {
				index++;
			}
		}
	}



	// 88. Merge Sorted Array
	// https://youtu.be/C4oBXLr3zos
	// Assumption: nums1 have enough space m+n to hold result
	public void mergeSorted(int[] nums1, int m, int[] nums2, int n) {
		// end idx of num1, end idx of num2, backwards runner
		int p1 = m-1, p2 = n-1, i = m+n-1;
		while (p2 > 0) {
			if (p1 > 0 && nums1[p1] > nums2[p2] ) {
				nums1[i--] = nums1[p1--];
			} else {
				nums1[i--] = nums1[p2--];
			}
		}
	}



	// 121. Best Time to Buy and Sell Stock (1 buy, 1 sell)
	// Input: prices = [7,1,5,3,6,4]
	// Output: 5
	public int maxProfit(int[] prices) {
		int minPrice = prices[0];
		int maxProfit = 0;
		for (int price : prices) {
			if (price < minPrice) {
				minPrice = price;
			}
			maxProfit = Math.max(maxProfit, price - minPrice);
		}
		return maxProfit;
	}

	// 122. Best Time to Buy and Sell Stock II (multiple buy sell)
	// Input: prices = [7,1,5,3,6,4]
	// Output: 7
	public int maxProfit2(int[] prices) {
		int stockBuyPrice = Integer.MAX_VALUE;
		int profit = 0;
		for (int i = 0; i < prices.length; i++) {
			if (prices[i] > stockBuyPrice) {
				profit += prices[i] - stockBuyPrice;
				stockBuyPrice = prices[i];
			} else {
				stockBuyPrice = prices[i];
			}
		}
		return profit;
	}



	// 128. Longest Consecutive Sequence
	// Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
	// You must write an algorithm that runs in O(n) time.
	// Input: nums = [100,4,200,1,3,2]   => 	Output: 4
	// SORT => brute force  => O(n log n)
	// Use Set: Start sequence has NO left neighbor => set ( we can check it has left neighbor)
	// keep check the right neighbor to expand the seq
	public int longestConsecutive(int[] nums) {
		Map<Integer, Integer> map = new HashMap<>();
		int maxLength = 0;
		for (int num : nums) {
			if (map.containsKey(num)) {
				continue;
			}
			int left = map.containsKey(num - 1) ? map.get(num - 1) : 0;
			int right = map.containsKey(num + 1) ? map.get(num + 1) : 0;
			int sum = left + right + 1;
			maxLength = Math.max(maxLength, sum);
			map.put(num, sum);
			map.put(num - left, sum);
			map.put(num + right, sum);
		}
		return maxLength;
	}


	// 841. Keys and Rooms: Stack
	// DFS (Stack), BFS (Queue)
	// https://youtu.be/oYeGiShGn2k
	boolean canVisitAllRooms2(List<List<Integer>> rooms) {
		Set <Integer> visited = new HashSet<>();
		visited.add(0);

		Stack<Integer> stack = new Stack<>();
		stack.add(0);
		while (!stack.isEmpty()) {
			List<Integer> keys = rooms.get(stack.pop());
			for(int key : keys) {
				if (!visited.contains(key)) {
					visited.add(key);
					stack.add(key);
				}
			}
		}
		return visited.size() == rooms.size();
	}




	// 28. Implement strStr()
	// Input: haystack = "hello", needle = "ll"
	// Output: 2 (index)
	public int strStr(String haystack, String needle) {
		for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
			if (haystack.substring(i, i + needle.length()).equals(needle)) {
				return i;
			}
		}
		return -1;
	}



	// 2. Add Two Numbers ( stored in linked list in reverse order)
	// Input: l1 = [2,4,3], l2 = [5,6,4]
	// Output: [7,0,8]
	//	Explanation: 342 + 465 = 807.
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode root = new ListNode(-1);
		ListNode curr = root;
		int carry = 0;
		while (l1 != null || l2 != null || carry != 0) {
			int currSum = carry;
			if (l1 != null && l2 != null) {
				currSum += l1.data + l2.data;
				l1 = l1.next;
				l2 = l2.next;
			} else if (l1 != null && l2 == null) {
				currSum += l1.data;
				l1 = l1.next;
			} else if (l1 == null && l2 != null) {
				currSum += l2.data;
				l2 = l2.next;
			}
			carry = currSum > 9 ? 1 : 0;
			currSum %= 10;
			curr.next = new ListNode(currSum);
			curr = curr.next;
		}
		return root.next;
	}



	// 3. Longest Substring Without Repeating Character
	// slideing window (start, end)
	// Same: Nick White 
	public int lengthOfLongestSubstring2(String s) {
		int start =0, end = 0;
		int max = 0;
		int size = s.length();
		Set<Character> set = new HashSet<>();
		while (end < size) {
			if (!set.contains(s.charAt(end))) {
				set.add(s.charAt(end));
				end++;
				max = Math.max(max, set.size());
			} else {
				set.remove(s.charAt(start));
				start++;
			}
		}

		return max;
	}






	/*
	 * LIST Section
	 */


	// 141. Linked List Cycle (hasCycle)
	public boolean hasCycle(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				return true;
			}
		}
		return false;
	}



	// 206. Reverse Linked List
	public ListNode reverseList(ListNode head) {
		ListNode prev = null;
		ListNode next = null;
		ListNode curr = head;
		while (curr != null) {
			next = curr.next;
			curr.next = prev;  // reverse
			prev = curr;    // reposition
			curr = next; // move to next
		}
		return prev;
	}


	// 8. Delete middle node in a linkedlist (Assumption --> middle node exists (Odd list))
	// use slow and fast runner
	ListNode deleteMiddleNode(ListNode head) {
		if (head == null || head.next == null) return head; // Nothing to delete
		ListNode slow = head;
		ListNode fast = head;
		ListNode prev = null;
		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			prev = slow;
			slow = slow.next;
		}
		// Now slow points to middle
		prev.next = slow.next;
		return head;
	}


	// 160. Intersection of two linked list
	// LinkedList: find merge node (two lists intercept)
	// findMergeNode, findInterceptNode
	// 1: 1 -> 2 -> 3 
	//                -> 7 -> 8
	// 2:      5 -> 6
	// 1: 1 -> 2 -> 3 -> 7 -> 8 -> 5 -> 6 -> 7 -> 8  (length 9)
	// 2: 5 -> 6 -> 7 -> 8 -> 1 -> 2 -> 3 -> 7 -> 8  (length 9)
	int findMergeNode(ListNode head1, ListNode head2) {
		ListNode cur1 = head1;
		ListNode cur2 = head2;
		while (cur1 != cur2) {
			if (cur1 == null)
				cur1 = head2;
			else 
				cur1 = cur1.next;

			if (cur2 == null)
				cur2 = head1;
			else
				cur2 = cur2.next;
		}
		// cur1 == cur2  (eventually)
		return cur1.data;
	}
	// https://youtu.be/CPXIkMWNn5Q
	ListNode getIntersectionNode(ListNode head1, ListNode head2) {
		Set<ListNode> visited = new HashSet<>();
		while (head1 != null) {
			visited.add(head1);
			head1 = head1.next;
		}
		while (head2 != null) {
			if(visited.contains(head2)) {
				return head2;
			}
			head2 = head2.next;
		}
		return null; // NOT FOUND
	}


	// 19. Remove Nth Node From End of List
	// Similar

	// LinkedList: get data position backwards from the TAIL
	int getNodeFromListTail (ListNode head, int pos) {
		ListNode delayRunner = head;
		ListNode cur = head;

		while (cur != null) {
			cur = cur.next;
			if (pos > 0) 
				pos--;
			else 
				delayRunner = delayRunner.next;
		}
		// cur reach end node
		return delayRunner.data;
	}


	// 21. Merge Two Sorted Lists
	public ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
		if (l1 == null || l2 == null) 
			return l1 == null ? l2 : l1;
		// AVOID null check by adding 1st node, the result will SKIP it
		ListNode tmp = new ListNode(0);
		ListNode cur = tmp;
		// case 1
		while (l1 != null && l2 !=null) {
			if (l1.data < l2.data) {
				cur.next = l1;
				l1 = l1.next;
			} else {
				cur.next = l2;
				l2 = l2.next;
			}
			cur = cur.next;
		}
		// case 2
		if (l1 != null) {
			cur.next = l1;
			l1 = l1.next;
		}
		// case 3
		if (l2 != null) {
			cur.next = l2;
			l2 = l2.next;
		}
		return tmp.next;
	}


	// 26. Remove Duplicates from Sorted list
	// just delete node or SKIP a duplicate node


	// 26. Remove Duplicates from Sorted Array
	public int removeDuplicates(int[] nums) {
		int j = 1;  // track the position to add the next unique number
		// 112 2333
		// 123 2333
		for (int i = 0; i < nums.length - 1; i++) {
			if (nums[i] != nums[i+1]) {
				nums[j] = nums[i+1];
				j++;  // j keep track of the position for the next unique number
			}
		}
		return j;
	}






	class ListNode {
		int data;
		ListNode next;
		ListNode (int d) {
			this.data = d;
		}
	}
	class Node<D> {

	    private D data;
	    private Node next;

	    public D getData() {
	        return this.data;
	    }

	    public void setData(D data) {
	        this.data = data;
	    }

	    public Node getNext() {
	        return this.next;
	    }

	    public void setNext(Node next) {
	        this.next = next;
	    }
	}



	/*
	 * Knowledge Test
	 */

	// 1. Static

	// 2. Optional

	// 4. String Manipulation,  String functions,  String equality

	// 5. Object Sort with Comparator(compare: a, b) & Comparable (compareTo: o): Ex. Player, Circle

	// 6. Generics: a. generic method, b. var args ... =>[]
	//		substitution principle,  Building --> Office,  House
	// 	IN   => printBuilding (List<? extends Building> buildings)
	//  OUT  => addHouseToList(List<? super House> houses)





	/*
	 * 
	 *  TREE Data Structure
	 *  
	 */

	// 110. Is Binary Tree Balanced - recursive using height()
	boolean balanced = true;
	private int height (TreeNode root) {
		if (root == null) return 0;
		int lHeight = height(root.left);
		int rHeight = height(root.right);

		// add this line
		if (Math.abs(rHeight-lHeight) > 1) balanced = false;

		return 1 + Math.max(lHeight, rHeight);
	}
	public boolean isBTBalanced (TreeNode root) {
		height(root);
		return balanced;
	}

	// 104. Max Depth (or Height) Of Binary Tree - Similar to height()
	private int maxDepthOfBT (TreeNode root) {
		if (root == null) return 0;
		return 1 + Math.max(maxDepthOfBT(root.left), maxDepthOfBT(root.right)) ;
	}


	// 94. Binary Tree InOrder (PreOrder, PostOrder) traversal
	void inOrder(TreeNode root) {
		if (root == null) return;
		// PreOrder:  System.out.println(root.data + ", ");
		inOrder(root.left);
		System.out.println(root.val + ", ");
		inOrder(root.right);
		// PostOrder:  System.out.println(root.data + ", ");
	}

	// ***** inOrderIterative *****
	// https://youtu.be/WZwNoTm_9d8
	void inOrderIterative(TreeNode root) {
		if (root == null) return;
		Stack<TreeNode> stack = new Stack<>();
		TreeNode cur = root;
		while (cur != null || !stack.isEmpty()) {
			// All the way to the left leaf
			while (cur != null) {
				stack.push(cur);
				cur = cur.left;
			}

			// Now start pop
			cur = stack.pop();
			visit(cur);
			cur = cur.right;	
		}
	}
	private void visit(TreeNode node) {
		System.out.println(node.val);
	}


	// ***** preOrderIterative  *****
	void preOrderIterative(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode cur = stack.pop();
			visit(cur);

			//LIFO:  push right first -> then it will pop last
			if (cur.right != null) stack.push(cur.right);
			if (cur.left != null) stack.push(cur.left);			
		}
	}


	// 102. Binary Tree Level Order Traversal (Top Down)
	// 107. Binary Tree Level Order Traversal II (Bottom Up)
	// QUEUE:  https://youtu.be/kI5L2s_tKMY
	List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> res = new LinkedList<>();
		if (root == null) return res;

		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty() ) {

			// get current level size n, & loop to print out all of them
			List<Integer> levelList = new LinkedList<>();
			int n = queue.size();		
			for (int i = 0; i < n; i++) {
				TreeNode node = queue.remove();
				levelList.add(node.val);  // OR use sysout()

				// Also add the children to queue: left first
				if (node.left != null) queue.add(node.left);
				if (node.right != null) queue.add(node.right);
			}

			// !!!ATTENTION!!!
			//res.add(0, levelList);  // BOTTOM UP
			res.add(levelList);
		}
		return res;
	}




	//100. Same Binary Tree
	boolean isSameBinaryTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) return true;
		if (p == null || q == null) return false;
		if (p.val != q.val) return false;

		return isSameBinaryTree(p.left, q.left) 
			&& isSameBinaryTree(p.right, q.right);
	}


	// 101. Symmetric Binary Tree 
	boolean isSymmetricBinaryTree (TreeNode root) {
		if (root == null) return true;
		return isSymmetric(root.left, root.right);
	}
	private boolean isSymmetric(TreeNode left, TreeNode right) {
		// BASE condition
		if (left == null && right == null) return true;
		if (left == null || right == null) return false;
		if (left.val != right.val) return false;

		return isSymmetric(left.left, right.right)
			&& isSymmetric(left.right, right.left);
	}



	//98. Validate Binary Search Tree (BST)
	// Recursive: meet 3 conditions:
    // 1. left < node
    // 2. right > node
    // 3. subtree meet 1 & 2
    // https://youtu.be/Z_-h_mpDmeg
    public boolean isValidBST(TreeNode root) {
    	return validate(root, null, null);
    }
    private boolean validate(TreeNode cur, Integer max, Integer min) {
    	if (cur == null ) {
    		return true;  // reach the leaf, should always be true if we can reach that far
    	} else if (max != null && cur.val >= max || min != null && cur.val <= min) {
    		return false;
    	} else {
    		return validate(cur.left, cur.val, min) 
    			&& validate(cur.right, max, cur.val);
    	}
    }







	// Insert data into BST(root)
	static BstNode insertNodeBST(BstNode root, int d) {
		if (root == null) return new BstNode(d);
		BstNode cur;
		if (d <= root.val) {
			cur = insertNodeBST(root.left, d);
			root.left = cur;
		} else {
			cur = insertNodeBST(root.right, d);
			root.right = cur;
		}
		return root;
	}





	/*
	 * 
	 *  TEST Tree Data Structure
	 */

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int d) {
			val = d;
		}
	}

	// Actually this is a BST (Abstract Data Structure)
	// https://github.com/eugenp/tutorials/blob/master/data-structures/src/main/java/com/baeldung/tree/BinaryTree.java	
	class BinaryTree {
		TreeNode root;

		// add
		public void add(int d) {
			root = addRecursive(root, d);
		}
		private TreeNode addRecursive(TreeNode cur, int d) {
			if (cur == null) return new TreeNode(d);

			if (d < cur.val) 
				cur.left = addRecursive(cur.left, d);
			else 
				cur.right = addRecursive(cur.right, d);
			return cur;
		}

		// isEmpty
		public boolean isEmpty() {
			return root == null;
		}

		// getSize()
		public int getSize() {
			return getSizeRecursive(root);
		}
		private int getSizeRecursive(TreeNode cur) {
			return cur == null ? 0 : getSizeRecursive(cur.left) + getSizeRecursive(cur.right);
		}


		// containsNode()
		public boolean containsNode(int d) { 
			return containsNodeRecursive(root, d);
		}
		private boolean containsNodeRecursive(TreeNode cur, int d) {
			if (cur == null) return false;
			if (cur.val == d) return true;

			return cur.val >= d ? containsNodeRecursive(cur.left, d) :  containsNodeRecursive(cur.right, d);
		}


		// delete()
		public void delete(int d) {
			root = deleteRecursive(root, d);
		}
		private TreeNode deleteRecursive(TreeNode cur, int d) {
			// TODO
			// I. equal  (no child, 1 child, 2 children

			// II. left

			// III. right


			return cur;
		}
	    private int findSmallestValue(TreeNode root) {
	        return root.left == null ? root.val : findSmallestValue(root.left);
	    }


	    // inOrder() recursive
	    // public void inOrder()  ==> start from root node
	    public void inOrder(TreeNode node) {
	    	if (node == null) return;
    		inOrder(node.left);
    		visit(node.val);
    		inOrder(node.right);
	    }
	    // preOrder() recursive
	    public void preOrder(TreeNode node) {
	    	if (node == null) return;
    		visit(node.val);
    		preOrder(node.left);
    		preOrder(node.right);
	    }	    
	    // postOrder() recursive
	    public void postOrder(TreeNode node) {
	    	if (node != null) return;
    		postOrder(node.left);
    		postOrder(node.right);
    		visit(node.val);
	    } 
	    // levelOrder()
	    public void levelOrder() {
	        if (root == null) return;

	        Queue<TreeNode> queue = new ArrayDeque<> ();
	        queue.add(root);

	        while (!queue.isEmpty()) {
	        	TreeNode node = queue.remove();
	        	visit(node.val);

	        	// add left, right to queue
	        	if (node.left != null) queue.add(node.left);
	        	if (node.right != null) queue.add(node.right);
	        }
	    }



	    // Iterative Traversal: inOrderIterative, preOrderIterative, postOrderIterative
	    public void inOrderIterative() {
	        Stack<TreeNode> stack = new Stack<>();
	        TreeNode current = root;

	        while (current != null || !stack.isEmpty()) {
	            while (current != null) {
	                stack.push(current);
	                current = current.left;
	            }

	            current = stack.pop();
	            visit(current.val);
	            current = current.right;
	        }
	    }

	    public void preOrderIterative() {
	        Stack<TreeNode> stack = new Stack<>();
	        TreeNode current = root;
	        stack.push(root);

	        while (current != null && !stack.isEmpty()) {
	            current = stack.pop();
	            visit(current.val);

	            // LIFO: push right first -> then it will pop last
	            if (current.right != null)  stack.push(current.right);
	            if (current.left != null)   stack.push(current.left);
	        }
	    }

	    // ***** HARD ******
	    public void postOrderIterative() {
	        Stack<TreeNode> stack = new Stack<>();
	        TreeNode prev = root;
	        TreeNode current = root;
	        stack.push(root);

	        while (current != null && !stack.isEmpty()) {
	            current = stack.peek();
	            boolean hasChild = (current.left != null || current.right != null);
	            boolean isPrevLastChild = (prev == current.right || (prev == current.left && current.right == null));

	            if (!hasChild || isPrevLastChild) {
	                current = stack.pop();
	                visit(current.val);
	                prev = current;
	            } else {
	                if (current.right != null) {
	                    stack.push(current.right);
	                }
	                if (current.left != null) {
	                    stack.push(current.left);
	                }
	            }
	        }   
	    }    


	    // helper method
	    private void visit(int d) {
	        System.out.print(" " + d);        
	    }
	}


	// createBinaryTree
	private BinaryTree createBinaryTree() {
        BinaryTree bt = new BinaryTree();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

        return bt;
    }


	@Test
	// add sorted array to BST
	void testAddSorted () {
		int[] nums = {1,2,3,4,5,6,7,8,9, 10	};
		BstNode root = BstNode.addSorted(nums, 0, 9);
		root.inOrder();
	}










}	



/*
 *  BST Node - with helper methods
 *  
 *  BST: leverage the helper methods of BstNode
 */

class BstNode {
	int val;
	BstNode left, right;
	BstNode(int val) {
		this.val = val;
	}

	// **** Helper methods **************
	// INSERT
	public void insert(int d) {
		if (d < this.val) {
			if (left == null) 
				left = new BstNode(d);
			else
				left.insert(d);
		} else {
			if (right == null)
				right = new BstNode(d);
			else
				right.insert(d);
		}
	}

	// ADD: or BstNode add()
	public void add(int d) {
		addRecursive(this, d);
	}
	private BstNode addRecursive(BstNode cur, int d) {
		if (cur == null) return new BstNode(d);  // Similar to insert() above
		if (d <= cur.val) 
			cur.left = addRecursive(cur.left, d);
		else 
			cur.right = addRecursive(cur.right, d);
		return cur;
	}


	// FIND
	public BstNode find(Integer d) {
		if (this.val == d) return this;
		if (d < this.val) {
			if (left != null) return left.find(d);
		} else {
			if (right != null) return right.find(d);
		}
		// NOT FOUND
		return null;
	}

	private void visit(BstNode node) {
		System.out.println(node.val + " ");
	}


	// InOrder traversal
	// PreOrder, PostOrder similar
	public void inOrder() {
		if (left != null) left.inOrder();
		visit(this);
		if (right != null) right.inOrder();
	}



	// largest()
	public Integer largest() {
		if (right == null) return this.val;
		return right.largest();
	}

	// smallest()
	public Integer smallest() {
		if (left == null) return this.val;
		return left.smallest();
	}


	// isLeaf()
	public boolean isLeaf() {
		return left == null && right == null;
	}


	// numOfLeafNodes 
	public int numOfLeafNodes() {
		if (isLeaf()) return 1;
		return (left == null ? 0 : left.numOfLeafNodes()) 
				+ (right == null ? 0 : right.numOfLeafNodes());
	}


	// height()
	public int height() {
		if (isLeaf()) return 1;
		return 1 + Math.max(left == null ? 0 : left.height(), 
				right == null ? 0 : right.height());
	}



	// **** addSorted() - add sorted array to BstNode and return root node
	public static BstNode addSorted(int [] nums, int start, int end) {
		if (start > end) return null;  // base condition

		int mid = (start+end)/2;
		BstNode midNode = new BstNode(nums[mid]);
		midNode.left = addSorted(nums, start, mid-1);
		midNode.right = addSorted(nums, mid+1, end);
		return midNode;
	}

}


/*
 *  BST - based on the above BstNode
 */

class Bst {
	private BstNode root;

	public void insert(int d) {
		if (root == null) 
			root = new BstNode(d);
		else
			root.insert(d);
	}

	public void add(int d) {
		if (root == null) 
			root = new BstNode(d);
		else
			root.add(d);
	}

	public void inOrder() {
		if (root == null) return;
		root.inOrder();
	}

	public BstNode find(int d) {
		if (root == null) return null;
		return root.find(d);
	}

	public Integer smallest() {
		if (root == null) return null;
		return root.smallest();
	}
	public int height() {
		if (root == null) return 0;
		return root.height();
	}

	public int numOfLeafNodes() {
		if (root == null) return 0;
		return root.numOfLeafNodes();
	}

	public static Bst createBstFromSorted(int[] nums) {
		Bst bst = new Bst();
		if (nums != null && nums.length > 0) 
			bst.root = BstNode.addSorted(nums, 0, nums.length-1);
		return bst;
	}

}