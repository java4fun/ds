package easy;

import java.util.*;
import java.util.stream.Collectors;

/*
 * https://leetcode.com/problem-list/top-interview-questions/
 */
public class Easy {

	// 1. Two Sum
	public int[] twoSum(int[] nums, int target) {
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
		return ans; // not found [ 0, 0]
	}
	// 1. Two Sum 2 (sorted array)

	
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
				currSum += l1.val + l2.val;
				l1 = l1.next;
				l2 = l2.next;
			} else if (l1 != null && l2 == null) {
				currSum += l1.val;
				l1 = l1.next;
			} else if (l1 == null && l2 != null) {
				currSum += l2.val;
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
	public int lengthOfLongestSubstring(String s) {
		int start = 0;
		int end = 0;
		int n = s.length();
		Map<Character, Integer> map = new HashMap<>();
		int maxLength = 0;
		while (end < n) {
			char c = s.charAt(end++);
			map.put(c, map.getOrDefault(c, 0) + 1);
			while (start <= end && map.get(c) > 1) {
				map.put(s.charAt(start), map.getOrDefault(s.charAt(start++), 0) - 1);
			}
			maxLength = Math.max(maxLength, end - start);
		}
		return maxLength;
	}

	// 4. Median of Two Sorted Arrays ( O(log (m+n)) ) -- > HARD
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int n = nums1.length + nums2.length;
		ArrayList<Integer> arr = new ArrayList<>();

		int i = 0;
		int j = 0;
		int k = 0;
		int limit = (n) / 2 + 1;

		while (i < nums1.length && j < nums2.length && k < limit) {
			if (nums1[i] < nums2[j]) {
				arr.add(nums1[i]);
				i++;
			} else {
				arr.add(nums2[j]);
				j++;
			}
			k++;
		}

		if (i < nums1.length) {
			while (i < nums1.length && k < limit) {
				arr.add(nums1[i]);
				i++;
				k++;
			}
		} else {
			while (j < nums2.length && k < limit) {
				arr.add(nums2[j]);
				j++;
				k++;
			}
		}

		k--;

		return n % 2 == 0 ? (double) (arr.get(k - 1) + arr.get(k)) / 2.0 : (double) arr.get(k);
	}

	
	// 5. Longest Palindromic Substring
	public boolean isPalindrome(String s, int begin, int end) {
		if (begin < 0)
			return false;
		while (begin < end) {
			if (s.charAt(begin++) != s.charAt(end--))
				return false;
		}
		return true;
	}

	public String longestPalindrome(String s) {
		String res = "";
		int currLength = 0;
		for (int i = 0; i < s.length(); i++) {
			if (isPalindrome(s, i - currLength - 1, i)) {
				res = s.substring(i - currLength - 1, i + 1);
				currLength += 2;
			} else if (isPalindrome(s, i - currLength, i)) {
				res = s.substring(i - currLength, i + 1);
				currLength += 1;
			}
		}
		return res;
	}
	
	// 7. Reverse Integer
	public int reverse(int x) {
		int result = 0;

		while (x != 0) {
			int tail = x % 10;
			int newResult = result * 10 + tail;
			if ((newResult - tail) / 10 != result) {
				return 0;
			}
			result = newResult;
			x = x / 10;
		}

		return result;
	}

	// 8. String to Integer (atoi)
	public int myAtoi(String s) {
		int idx = 0;
		int n = s.length();
		while (idx < n && s.charAt(idx) == ' ') {
			idx++;
		}
		int sign = 1;
		if (idx < n && (s.charAt(idx) == '+' || s.charAt(idx) == '-')) {
			sign = s.charAt(idx++) == '-' ? -1 : 1;
		}
		long num = 0;
		while (idx < n && Character.isDigit(s.charAt(idx))) {
			num = num * 10 + Character.getNumericValue(s.charAt(idx++));
			if (sign == 1 && num > Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			} else if (sign == -1 && num * sign < Integer.MIN_VALUE) {
				return Integer.MIN_VALUE;
			}
		}
		return (int) (num * sign);
	}

	// 10. Regular Expression Matching
	// 11. Container With Most Water
	public int maxArea(int[] height) {
		int maximumArea = 0;
		int leftIdx = 0;
		int rightIdx = height.length - 1;
		while (leftIdx < rightIdx) {
			int maxHeight = Math.min(height[leftIdx], height[rightIdx]);
			int currArea = maxHeight * (rightIdx - leftIdx);
			maximumArea = Math.max(currArea, maximumArea);
			if (maxHeight == height[leftIdx]) {
				leftIdx++;
			} else {
				rightIdx--;
			}
		}
		return maximumArea;
	}

	// 13. Roman to Integer
	public int romanToInt(String s) {
		String[] keys = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
		int[] values = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		int val = 0;
		int idx = 0;
		int n = s.length();
		while (idx < n) {
			char c = s.charAt(idx);
			if (idx + 1 < n && map.containsKey(s.substring(idx, idx + 2))) {
				val += map.get(s.substring(idx, idx + 2));
				idx += 2;
			} else {
				val += map.get(String.valueOf(c));
				idx++;
			}
		}
		return val;
	}

	// 14. Longest Common Prefix
	// Input: strs = ["flower","flow","flight"]
	// Output: "fl"
	// Set Prefix to the 1st element, then remove from the end as we loops, the remaining part will be the answer
	public String longestCommonPrefix(String[] strs) {
		int minLength = Integer.MAX_VALUE;
		int minLengthIdx = -1;
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].length() < minLength) {
				minLength = strs[i].length();
				minLengthIdx = i;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs[minLengthIdx].length(); i++) {
			for (String str : strs) {
				if (str.charAt(i) != strs[minLengthIdx].charAt(i)) {
					return sb.toString();
				}
			}
			sb.append(strs[minLengthIdx].charAt(i));
		}
		return sb.toString();
	}

	// 15. 3Sum
	// Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] 
	// such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
	// Note: the solution set must not contain duplicate triplets.
	// return ALL set, but NO duplicates
	public List<List<Integer>> threeSum(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> result = new ArrayList<>();
		for (int i = 0; i < nums.length && nums[i] <= 0; i++) {
			if (i == 0 || nums[i - 1] != nums[i]) {
				Set<Integer> set = new HashSet<>();
				for (int j = i + 1; j < nums.length; j++) {
					int target = -1 * (nums[i] + nums[j]);
					if (set.contains(target)) {
						result.add(Arrays.asList(nums[i], nums[j], target));
						while (j + 1 < nums.length && nums[j] == nums[j + 1]) {
							j++;
						}
					}
					set.add(nums[j]);
				}
			}
		}
		return result;
	}

	// 17. Letter Combinations of a Phone Number
	public List<String> letterCombinations(String digits) {
		if (digits.length() == 0) {
			return new ArrayList<>();
		}
		String[] strs = { "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
		Map<Integer, String> map = new HashMap<>();
		for (int i = 2; i <= 9; i++) {
			map.put(i, strs[i - 2]);
		}
		List<String> list = new ArrayList<>();
		helper(digits, 0, digits.length(), new StringBuilder(), map, list);
		return list;
	}

	private void helper(String digits, int idx, int n, StringBuilder sb, Map<Integer, String> map, List<String> list) {
		if (idx == n) {
			if (sb.length() == n) {
				list.add(sb.toString());
			}
		} else {
			for (int i = idx; i < n; i++) {
				int digit = Character.getNumericValue(digits.charAt(i));
				for (char c : map.get(digit).toCharArray()) {
					sb.append(c);
					helper(digits, i + 1, n, sb, map, list);
					sb.deleteCharAt(sb.length() - 1);
				}
			}
		}
	}

	// 19. Remove Nth Node From End of List
	public ListNode removeNthFromEnd(ListNode head, int n) {
		if (head == null || n == 0) {
			return head;
		}
		ListNode slow = head;
		int count = 0;
		while (count < n) {
			slow = slow.next;
			count++;
		}
		if (slow == null) {
			return head.next;
		}
		ListNode fast = head;
		while (slow.next != null) {
			slow = slow.next;
			fast = fast.next;
		}
		fast.next = fast.next.next;
		return head;
	}

	// 20. Valid Parentheses
	// map = {')', '(', ']', '['
	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<>();
		for (char c : s.toCharArray()) {
			if (c == '[' || c == '{' || c == '(') {
				stack.push(c);
			} else {
				if (stack.isEmpty()) {
					return false;
				}
				char popped = stack.pop();
				if ((c == ')' && popped == '(') || (c == ']' && popped == '[') || (c == '}' && popped == '{')) {
					continue;
				}
				return false;
			}
		}
		return stack.isEmpty();
	}

	// 21. Merge Two Sorted Lists
	public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		if (list1 == null || list2 == null) {
			return list1 == null ? list2 : list1;
		}
		ListNode prev = null;
		ListNode head = null;
		while (list1 != null && list2 != null) {
			if (list1.val <= list2.val) {
				prev = list1;
				if (head == null) {
					head = list1;
				}
				list1 = list1.next;
			} else {
				ListNode node = list2;
				list2 = list2.next;
				if (prev == null) {
					node.next = list1;
					prev = node;
					head = prev;
				} else {
					prev.next = node;
					node.next = list1;
					prev = node;
				}
			}
		}
		if (list2 != null) {
			prev.next = list2;
		}
		if (list1 != null) {
			prev.next = list1;
		}
		return head;
	}

	// 22. Generate Parentheses
	// Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
	// Input: n = 3
	// Output: ["((()))","(()())","(())()","()(())","()()()"]
	Set<String> set;

	public List<String> generateParenthesis(int n) {
		set = new HashSet<>();
		StringBuilder sb = new StringBuilder();
		helper(n, 0, 0, sb);
		return new ArrayList<>(set);
	}

	private void helper(int n, int open, int close, StringBuilder sb) {
		if (sb.length() == 2 * n) {
			set.add(sb.toString());
		} else {
			if (open <= close) {
				sb.append('(');
				helper(n, open + 1, close, new StringBuilder(sb.toString()));
			} else {
				sb.append(')');
				helper(n, open, close + 1, new StringBuilder(sb.toString()));
				sb.deleteCharAt(sb.length() - 1);
				if (open < n) {
					sb.append('(');
					helper(n, open + 1, close, new StringBuilder(sb.toString()));
				}
			}
		}
	}

	// 23. Merge k Sorted Lists (HARD)
	public ListNode mergeKLists(ListNode[] lists) {
		PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
		for (ListNode listNode : lists) {
			if (listNode != null) {
				pq.add(listNode);
			}
		}
		ListNode dummy = new ListNode(0);
		ListNode curr = dummy;
		while (!pq.isEmpty()) {
			ListNode removed = pq.remove();
			curr.next = new ListNode(removed.val);
			curr = curr.next;
			if (removed.next != null) {
				pq.add(removed.next);
			}
		}
		return dummy.next;
	}

	// 26. Remove Duplicates from Sorted Array
	public int removeDuplicates(int[] nums) {
		int start = 0;
		int end = 0;
		int n = nums.length;
		while (end < n) {
			int curr = nums[end];
			while (end < n && nums[end] == curr) {
				end++;
			}
			nums[start++] = curr;
		}
		return start;
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

	// 29. Divide Two Integers

	
	// 33. Search in Rotated Sorted Array
	// Input: nums = [4,5,6,7, 0,1,2], target = 0
	// Output: 4 (index)
	// Input: nums = [4,5,6,7, 0,1,2], target = 3
	// Output: -1 (not found)
	public int search(int[] nums, int target) {
		int start = 0;
		int end = nums.length - 1;
		while (start <= end) {
			int mid = (start + end) / 2;
			if (nums[mid] == target) {
				return mid;
			}
			if (nums[mid] >= nums[start]) {
				if (nums[start] <= target && nums[mid] > target) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			} else {
				if (nums[end] >= target && nums[mid] < target) {
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			}
		}
		return -1;
	}

	// 34. Find First and Last Position of Element in Sorted Array
	// Input: nums = [5,7,7,8,8,10], target = 8 =>	Output: [3,4]
	// Input: nums = [5,7,7,8,8,10], target = 6 ==>  Output: [-1,-1]
	// Input: nums = [], target = 0  => 	Output: [-1,-1]
	public int[] searchRange(int[] nums, int target) {
		return new int[] { binarySearchForMinIdx(nums, target), binarySearchForMaxIdx(nums, target) };
	}

	private int binarySearchForMinIdx(int[] nums, int target) {
		int leftIdx = 0;
		int rightIdx = nums.length - 1;
		int idx = -1;
		while (leftIdx <= rightIdx) {
			int mid = (leftIdx + rightIdx) / 2;
			if (nums[mid] == target) {
				idx = mid;
				rightIdx = mid - 1;
			} else if (nums[mid] > target) {
				rightIdx = mid - 1;
			} else {
				leftIdx = mid + 1;
			}
		}
		return idx;
	}

	private int binarySearchForMaxIdx(int[] nums, int target) {
		int leftIdx = 0;
		int rightIdx = nums.length - 1;
		int idx = -1;
		while (leftIdx <= rightIdx) {
			int mid = (leftIdx + rightIdx) / 2;
			if (nums[mid] == target) {
				idx = mid;
				leftIdx = mid + 1;
			} else if (nums[mid] > target) {
				rightIdx = mid - 1;
			} else {
				leftIdx = mid + 1;
			}
		}
		return idx;
	}

	// 36. Valid Sudoku

	// 38. Count and Say
	// "33 222 5 1"
	// say "23321511"
	public String countAndSay(int n) {
		return rec(n, "1");
	}

	private String rec(int n, String s) {
		if (n == 1) {
			return s;
		}
		StringBuilder sb = new StringBuilder();
		int idx = 0;
		while (idx < s.length()) {
			char c = s.charAt(idx);
			int count = 0;
			while (idx < s.length() && s.charAt(idx) == c) {
				idx++;
				count++;
			}
			sb.append(count).append(c);
		}
		return rec(n - 1, sb.toString());
	}

	// 41. First Missing Positive
	// 42. Trapping Rain Water
	// 44. Wildcard Matching

	
	// 46. Permutations
	// Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
	// Input: nums = [1,2,3]
	// Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> ans = new ArrayList<>();
		Set<Integer> used = new HashSet<>();
		permuteHelper(nums, nums.length, ans, used, new ArrayList<>());
		return ans;
	}

	private void permuteHelper(int[] nums, int length, List<List<Integer>> ans, Set<Integer> used,
			ArrayList<Integer> curr) {
		if (curr.size() == length) {
			ans.add(new ArrayList<>(curr));
			return;
		}

		for (int i = 0; i < length; i++) {
			if (!used.contains(nums[i])) {
				used.add(nums[i]);
				curr.add(nums[i]);
				permuteHelper(nums, length, ans, used, curr);
				used.remove(nums[i]);
				curr.remove(curr.size() - 1);
			}
		}
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

	// 49. Group Anagrams - Categorize Strings by Count - Leetcode 49
	public List<List<String>> groupAnagrams(String[] strs) {
		return new ArrayList<>(Arrays.stream(strs).collect(Collectors.groupingBy(Easy::getCodedString)).values());
	}
	private static String getCodedString(String s) {
		return s.chars().mapToObj(c -> (char) c).sorted().map(Object::toString).collect(Collectors.joining());
	}

	// 50. Pow(x, n)

	// 53. Maximum Subarray
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

	// 55. Jump Game
	// nums = [2,3,1,1,4] -> true
	// nums = [3,2,1,0,4] -> false
	// https://youtu.be/Zb4eRjuPHbM
	public boolean canJump(int[] nums) {
		int reachable = 0;
		for (int i = 0; i < nums.length; ++i) {
			if (i > reachable)
				return false;
			reachable = Math.max(reachable, i + nums[i]);
		}
		return true;
	}

	// 56. Merge Intervals
	// Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
	// Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
	// Output: [[1,6],[8,10],[15,18]]
	// https://youtu.be/qKczfGUrFY4
	public int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, Comparator.comparingInt((int[] o) -> o[0]).thenComparingInt(o -> o[1]));
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
		int[][] result = new int[mergedIntervals.size()][2];
		for (int i = 0; i < mergedIntervals.size(); i++) {
			result[i] = mergedIntervals.get(i);
		}
		return result;
	}

	
	// 62. Unique Paths
	// Robot move from top left corner to botton right color of 2D grid
	// https://youtu.be/6qMFjFC9YSc
	public int uniquePaths(int m, int n) {
		Integer[][] dp = new Integer[m][n];
		return helper(0, 0, m, n, dp);
	}

	private int helper(int currX, int currY, int m, int n, Integer[][] dp) {
		if (currX == m - 1 && currY == n - 1) {
			return 1;
		}
		if (currX >= m || currY >= n) {
			return 0;
		}
		if (dp[currX][currY] != null) {
			return dp[currX][currY];
		}
		dp[currX][currY] = helper(currX + 1, currY, m, n, dp) + helper(currX, currY + 1, m, n, dp);
		return dp[currX][currY];
	}

	// 66. Plus One
	// Input: digits = [4,3,2,1]  =>  Output: [4,3,2,2]
	// Input: digits = [9]  => 	Output: [1,0]
	public int[] plusOne(int[] digits) {
		int carry = 1;
		int n = digits.length;
		for (int i = n - 1; i >= 0; i--) {
			int temp = digits[i] + carry;
			if (temp <= 9) {
				digits[i] = temp;
				return digits;
			}
			digits[i] = temp % 10;
		}
		int[] newDigits = new int[n + 1];
		newDigits[0] = 1;
		for (int i = 1; i < n + 1; i++) {
			newDigits[i] = digits[i - 1];
		}
		return newDigits;
	}

	// 69. Sqrt(x)
	public int mySqrt(int x) {
		if (x < 2) {
			return x;
		}
		int start = 2;
		int end = x / 2;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			long num = (long) mid * mid;
			if (num > x) {
				end = mid - 1;
			} else if (num < x) {
				start = mid + 1;
			} else {
				return mid;
			}
		}
		return end;
	}

	// 70. Climbing Stairs
	// You are climbing a staircase. It takes n steps to reach the top.
	// Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
	// https://youtu.be/uHAToNgAPaM
	// DP: bottom up (base case: 1, 2 steps)
	public int climbStairs(int n) {
		if (n == 1) {
			return 1;
		}
		int[] dp = new int[n + 1];
		dp[1] = 1;
		dp[2] = 2;
		for (int i = 3; i <= n; i++) {
			dp[i] = dp[i - 1] + dp[i - 2];
		}
		return dp[n];
	}

	// 73. Set Matrix Zeroes
	// Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's.
	// You must do it in place.

	
	// 75. Sort Colors
	// Given an array nums with n objects colored red, white, or blue, sort them
	// in-place so that objects of the same color are adjacent, with the colors in
	// the order red, white, and blue.
	// Input: nums = [2,0,2,1,1,0]
	// Output: [0,0,1,1,2,2]
	public void sortColors(int[] nums) {
		int zeroIdx = 0;
		int twoIdx = nums.length - 1;
		int currIdx = 0;
		while (currIdx <= twoIdx) {
			if (nums[currIdx] == 0) {
				swap(nums, currIdx++, zeroIdx++);
			} else if (nums[currIdx] == 2) {
				swap(nums, currIdx, twoIdx--);
			} else {
				currIdx++;
			}
		}
	}
	private void swap(int[] nums, int idxOne, int idxTwo) {
		int temp = nums[idxTwo];
		nums[idxTwo] = nums[idxOne];
		nums[idxOne] = temp;
	}

	
	
	// 76. Minimum Window Substring => HARD
	// Input: s = "ADOBECODE BANC", t = "ABC"
	// Output: "BANC"
	public String minWindow(String s, String t) {
		Map<Character, Integer> map = new HashMap<>();
		for (char c : t.toCharArray()) {
			map.put(c, map.getOrDefault(c, 0) + 1);
		}
		int count = map.size();
		int start = 0;
		int end = 0;
		int minWindowLength = Integer.MAX_VALUE;
		int minWindowStart = 0;
		int minWindowEnd = 0;
		while (end < s.length()) {
			char c = s.charAt(end++);
			if (map.containsKey(c)) {
				map.put(c, map.get(c) - 1);
				if (map.get(c) == 0) {
					count--;
				}
			}
			while (count == 0 && start < end) {
				if (end - start < minWindowLength) {
					minWindowLength = end - start;
					minWindowStart = start;
					minWindowEnd = end;
				}
				char temp = s.charAt(start++);
				if (map.containsKey(temp)) {
					map.put(temp, map.get(temp) + 1);
					if (map.get(temp) == 1) {
						count++;
					}
				}
			}
		}
		return s.substring(minWindowStart, minWindowEnd);
	}

	// 78. Subsets (Power set)
	// Given an integer array nums of unique elements, 
	// return all possible subsets (the power set).
	// The solution set must not contain duplicate subsets. Return the solution in any order.
	public List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		helper(nums, 0, new ArrayList<>(), result);
		return new ArrayList<>(result);
	}

	private void helper(int[] nums, int currIdx, List<Integer> currSubset, List<List<Integer>> result) {
		result.add(new ArrayList<>(currSubset));
		if (currIdx >= nums.length) {
			return;
		}
		for (int i = currIdx; i < nums.length; i++) {
			currSubset.add(nums[i]);
			helper(nums, i + 1, currSubset, result);
			currSubset.remove(currSubset.size() - 1);
		}
	}

	// 79. Word Search
	// 84. Largest Rectangle in Histogram => HARD

	// 88. Merge Sorted Array
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		int endIdxOne = m - 1;
		int endIdxTwo = n - 1;
		int currIdx = m + n - 1;
		while (endIdxOne >= 0 || endIdxTwo >= 0) {
			if (endIdxOne >= 0 && endIdxTwo >= 0) {
				if (nums1[endIdxOne] > nums2[endIdxTwo]) {
					nums1[currIdx--] = nums1[endIdxOne--];
				} else {
					nums1[currIdx--] = nums2[endIdxTwo--];
				}
			} else if (endIdxOne >= 0 && endIdxTwo < 0) {
				nums1[currIdx--] = nums1[endIdxOne--];
			} else {
				nums1[currIdx--] = nums2[endIdxTwo--];
			}
		}
	}
	
	
	
	// 91. Decode Ways
	// 94. Binary Tree Inorder Traversal

	// 98. Validate Binary Search Tree
	// 101. Symmetric Tree
	// 102. Binary Tree Level Order Traversal
	// 103. Binary Tree Zigzag Level Order Traversal

	// 104. Maximum Depth of Binary Tree

	// 105. Construct Binary Tree from Preorder and Inorder Traversal

	// 108. Convert Sorted Array to Binary Search Tree

	// 116. Populating Next Right Pointers in Each Node

	// 118. Pascal's Triangle

	
	
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

	
	// 124. Binary Tree Maximum Path Sum => HARD


	// 125. is String a Valid Palindrome

	// 127. Word Ladder => HARD

	// 128. Longest Consecutive Sequence
	// Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
	// You must write an algorithm that runs in O(n) time.
	// Input: nums = [100,4,200,1,3,2]   => 	Output: 4
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

	// 130. Surrounded Regions => Medium

	// 131. Palindrome Partitioning
	// Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.
	// Input: s = "aab"
	// Output: [["a","a","b"],["aa","b"]]
	public List<List<String>> partition(String a) {
		List<List<String>> ans = new ArrayList<>();
		helper(ans, new ArrayList<String>(), a, 0);
		return ans;
	}

	private void helper(List<List<String>> ans, List<String> temp, String a, int idx) {
		if (idx == a.length()) {
			ans.add(new ArrayList<>(temp));
			return;
		}
		for (int i = idx; i < a.length(); i++) {
			String sb = a.substring(idx, i + 1);

			if (isPalindrome(sb)) {
				temp.add(sb);
				helper(ans, temp, a, i + 1);
				temp.remove(temp.size() - 1);
			}
		}
	}

	private boolean isPalindrome(String s) {
		return new StringBuilder(s).reverse().toString().equals(s);
	}
	
	// 134. Gas Station
	

	// 136. Single Number
	// Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
	public int singleNumber(int[] nums) {
		int xor = nums[0];
		for (int i = 1; i < nums.length; i++) {
			xor ^= nums[i];
		}
		return xor;
	}

	// 138. Copy List with Random Pointer
	public Node copyRandomList(Node head) {
		if (head == null) {
			return head;
		}
		Node curr = head;
		/*
		 * List A->B->C Expected outcome: A->A`->B->B`->C->C` Where node marked with `
		 * is copied node
		 */
		while (curr != null) {
			Node newNode = new Node(curr.val);
			newNode.next = curr.next;
			curr.next = newNode;
			curr = newNode.next;
		}
		curr = head;
		/*
		 * List A->A`->B->B`->C->C` | | | (Random pointers) C A B Expected outcome:
		 * A->A`->B->B`->C->C` | | | | | | C C` A A` B B`
		 */
		while (curr != null) {
			curr.next.random = curr.random != null ? curr.random.next : null;
			curr = curr.next.next;
		}
		Node oldCurr = head;
		Node newCurr = head.next;
		Node newHead = head.next; // Retain a pointer to copied list's head
		// Separate merged lists into original list & copied list.
		while (oldCurr != null) {
			oldCurr.next = oldCurr.next.next;
			newCurr.next = newCurr.next != null ? newCurr.next.next : null;
			oldCurr = oldCurr.next;
			newCurr = newCurr.next;
		}
		return newHead;
	}

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
	// 146. LRU Cache
	// 148. Sort List

	// 152. Maximum Product Subarray
	public int maxProduct(int[] nums) {
		int currMax = nums[0];
		int currMin = nums[0];
		int maxProd = currMax;
		for (int i = 1; i < nums.length; i++) {
			int temp = currMax;
			currMax = Math.max(Math.max(temp * nums[i], currMin * nums[i]), nums[i]);
			currMin = Math.min(Math.min(temp * nums[i], currMin * nums[i]), nums[i]);
			maxProd = Math.max(maxProd, currMax);
		}
		return maxProd;
	}

	// 155. Min Stack
	class MinStack {
		private final Stack<int[]> stack;

		public MinStack() {
			this.stack = new Stack<>();
		}

		public void push(int val) {
			int min = stack.isEmpty() ? val : Math.min(val, stack.peek()[1]);
			stack.push(new int[] { val, min });
		}

		public void pop() {
			stack.pop();
		}

		public int top() {
			return stack.peek()[0];
		}

		public int getMin() {
			return stack.peek()[1];
		}
	}

	/**
	 * Your MinStack object will be instantiated and called as such: MinStack obj =
	 * new MinStack(); obj.push(val); obj.pop(); int param_3 = obj.top(); int
	 * param_4 = obj.getMin();
	 */

	// 160. Intersection of Two Linked Lists (Merge Node)
	
	

	// 163. Missing Ranges
	public List<String> findMissingRanges(int[] nums, int lower, int upper) {
		List<String> ranges = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] < lower) {
				continue;
			}
			if (nums[i] == lower) {
				lower++;
				continue;
			}
			ranges.add(getRange(lower, (nums[i] - 1)));
			lower = nums[i] + 1;
		}
		if (lower <= upper) {
			ranges.add(getRange(lower, upper));
		}
		return ranges;
	}

	private String getRange(int down, int up) {
		return down == up ? String.valueOf(up) : down + "->" + up;
	}

	// 169. Majority Element
	// Given an array nums of size n, return the majority element.
	// Input: nums = [3,2,3]  =>  Output: 3
	public int majorityElement(int[] nums) {
		int count = 0;
		Integer candidate = null;
		for (int num : nums) {
			if (count == 0) {
				candidate = num;
			}
			count += num == candidate ? 1 : -1;
		}
		return candidate;
	}

	// 179. Largest Number
	//Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
	//Since the result may be very large, so you need to return a string instead of an integer.
	// Input: nums = [10,2]  =>   Output: "210"

	// 189. Rotate Array
	// Given an array, rotate the array to the right by k steps, where k is non-negative
	// Input: nums = [1,2,3,4,5,6,7], k = 3
	// Output: [5,6,7,1,2,3,4]
	public void rotate(int[] nums, int k) {
	    int n = nums.length;
	    k %= n;
	    int count = 0;
	    for (int i = 0; count < n; i++) {
	      int currIdx = i;
	      int prevValue = nums[i];
	      do {
	        int nextIdx = (currIdx + k) % n;
	        int tempValue = nums[nextIdx];
	        nums[nextIdx] = prevValue;
	        prevValue = tempValue;
	        currIdx = nextIdx;
	        count++;
	      } while (i != currIdx);
	    }
	  }
	
	// 190. Reverse Bits
	
	// 198. House Robber
	
	// 200. Number of Islands
	

	// 206. Reverse Linked List
	public ListNode reverseList(ListNode head) {
		ListNode prev = null;
		ListNode next = null;
		ListNode curr = head;
		while (curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}

	// 207. Course Schedule
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		int[] indegree = new int[numCourses];
		for (int[] prerequisite : prerequisites) {
			map.computeIfAbsent(prerequisite[1], k -> new HashSet<>()).add(prerequisite[0]);
			indegree[prerequisite[0]]++;
		}
		Queue<Integer> queue = new LinkedList<>();
		Set<Integer> taken = new HashSet<>();
		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0) {
				queue.add(i);
				taken.add(i);
			}
		}
		while (!queue.isEmpty()) {
			int removed = queue.remove();
			for (Integer dependentCourse : map.getOrDefault(removed, new HashSet<>())) {
				indegree[dependentCourse]--;
				if (indegree[dependentCourse] == 0) {
					taken.add(dependentCourse);
					queue.add(dependentCourse);
				}
			}
		}
		return taken.size() == numCourses;
	}

	// 210. Course Schedule II
	
	
	// 215. Kth Largest Element in an Array
	
	// 217. Contains Duplicate
	
	
	// 234. Palindrome Linked List

	// 237. Delete Node in a Linked List

}

class Node {
    int val;
    Node next;
    Node random;
    Node(int val) {this.val = val;}
}
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) {this.val = val;}
}
