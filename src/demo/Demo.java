package demo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

class Demo {

	// 5. Palindrome
	// 2 pointers close in from both side
	boolean palindromeCheckerPointer(String original) {
		String s = original.toLowerCase().trim();
		int left = 0;
		int right = s.length() -1;
		while (left <= right) {
			if (s.charAt(left) != s.charAt(right)) return false;
			left++; right--;
		}
		return true;
	}
	boolean palindromeCheckerStream(String original) {
		String s = original.toLowerCase();
		return IntStream.range(0, s.length()/2)
				.allMatch(i -> s.charAt(i) == s.charAt(s.length()-i-1));
	}
	
	
	// 6. Count Vowels and Consonants
	// char[], just count:  vowels = "aoiouy";
	
	
	
	// 7. Maximum Product of 2 numbers in a Array
	// Arrays.sort(a);
	// return Math.max(leftProduct, rightProduct);
	
	
	
	// 8. Delete middle node in a Linked List
	// two runner: slow = head; fast = head;  prev = null;
	// prev.next = slow.next
	
	
	// 9. Generate Binary Numbers
	// Integer.toBinaryString(i)
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
	// matchMap.containsValue(), containsKey()
	boolean matchingParentheses(String s) {
		Map<Character, Character> matchMap = Map.of(')','(',']','[','}','{');

		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < s.length(); i++) { 
			char c = s.charAt(i);
			if(matchMap.containsValue(c)) {
				stack.push(c);
				continue;
			}
			if (matchMap.containsKey(c) 
					&& (stack.isEmpty() || stack.pop() != matchMap.get(c)))
					return false;
		}
		
		return stack.isEmpty();
	}
	
	
	// 11. Reverse Integer
	//123 => 321,  -123=>-321,  120=>21
	// reversed = reversed * 10 + pop;
	int reverseInteger(int x) {
		int reversed = 0;
		int pop;
		while (x !=0) {
			pop = x % 10;
			x = x/10;
			reversed = reversed * 10 + pop;
		}
		return reversed;
	}
	
	
	// 12. Palindrome Number
	//121 => true,  -123=>false,  120=>false
	// similar to the above, only do half way, and CHECK result
	
	
	
	
	// 13. Array contains duplicate
	// HashSet<Integer> set = new HashSet<>();
	
	
	
	// 14. Generate Pascal Triangle
	// List<List<Integer>> triangle
	// List<Integer> firstRow
	// List<Integer> prevRow 
	// List<Integer> curRow
	
	
	
	// 15. Binary Search Sorted Array: return the Index or -1 if NOT FOUND
	// First Bad Version (call API isBadVersion(n))
	//     similar solution using binary search

	
	
	// 16. Reverse String in Char array
	// 2 runners: left, right, SWAP
	
	
	
	
	// 17. Keys and Rooms: Stack
	boolean canVisitAllRooms(List<List<Integer>> rooms) {
		boolean[] seen = new boolean[rooms.size()];
		seen[0] = true;
		
		Stack<Integer> roomKeys = new Stack<>();
		roomKeys.add(0);
		
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
	
	
	// 18. Is String B a rotated string of A
	// IN: A= "abcde"  B="cdeab"  return true
	// A+A contains B
	boolean rotateString(String A, String B) {
		return A.length() == B.length() 
				&& (A+A).contains(B);
	}
	
	
	
	
	// 19. Best time to buy and sell stocks (1 buy/sell Only)
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
	
	
	
	// 104. Max Depth Of Binary Tree (Height)
	private int heightOfBT(TreeNode root) {
		if (root == null) return 0;
		return 1 + Math.max(heightOfBT(root.left), heightOfBT(root.right));
	}
	
	
	// 110. Is Binary Tree Balanced
	// similar to above: check leftHeight-rightHeigh > 1 ==> balanced = false

	
	
	// 94. Binary Tree InOrder traversal
	void inOrder(TreeNode root) {
		if (root == null) return;
		
		inOrder(root.left); 
		System.out.println(root.data + " ");
		inOrder(root.right);
	}
	
	void inOrderIterative(TreeNode root) {
		if (root == null) return;
		Stack<TreeNode> stack = new Stack<>();
		TreeNode cur = root;
		while (cur != null || !stack.isEmpty()) {
			while (cur != null) {
				stack.push(cur);
				cur=cur.left;
			}
			cur = stack.pop();
			System.out.println(cur.data + " ");
			cur = cur.right;
		}
	}
	
	void preOrderIterative(TreeNode root) {
		if (root == null) return;
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		
		while(!stack.isEmpty()) {
			TreeNode cur = stack.pop();
			System.out.println(cur.data + " ");
			
			if (cur.right != null) stack.push(cur.right);
			if (cur.left != null) stack.push(cur.left);
		}
	}
	
	
	
	// 102. Binary Tree Level Order Traversal (Top Down)
	// Queue
	// 
	List<List<Integer>> levelOrderTraversal(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		if (root == null) return result;
		
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		
		while (!q.isEmpty()) {
			int n = q.size();
			List<Integer> level = new LinkedList<>();
			
			for (int i = 0; i < n; i++) {
				TreeNode node = q.remove();
				level.add(node.data);
				if (node.left != null) q.add(node.left);
				if (node.right != null) q.add(node.right);
				
				
			}
			result.add(level);
		}
	
		return result;
	}
	
	
	
	
	//100. Same Binary Tree
	boolean isSameBinaryTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) return true;
		if (p == null || q == null) return false;
		if (p.data != q.data) return false;
		
		return isSameBinaryTree(p.left, q.left)
				&& isSameBinaryTree(p.right, q.right);
	}
	
	
	
	// 101. Symmetric Binary Tree 
	// same concept as above
	boolean isSymmetricBinaryTree (TreeNode root) {
		if (root == null) return true;
		return symmetricHelper(root.left, root.right);
	}
	boolean symmetricHelper(TreeNode left, TreeNode right) {
		if (left == null && right == null ) return true;
		if (left == null || right == null ) return false;
		if (left.data != right.data) return false;
		
		return symmetricHelper(left.right, right.left)
				&& symmetricHelper(left.left, right.right);
	}
	
	
	
	
	//98. Validate Binary Search Tree (BST)
	// Recursive
	
	
	
	// Insert data into BST(root)
	static Node inserNodeBST (Node root, int d) {
		if (root == null) root = new Node(d);
		
		Node cur;
		if (d <= root.data) {
			cur = inserNodeBST(root.left, d);
			root.left = cur;
		} else {
			cur = inserNodeBST(root.right, d);
			root.right = cur;
		}
		
		return root;
	}
	
	
	// LinkedList: find merge node (two lists intercept)
	int findMergeNode(ListNode head1, ListNode head2) {
		ListNode cur1 = head1;
		ListNode cur2 = head2;
		
		while (cur1 != cur2) {
			if (cur1 == null) 
				cur1 = head2;
			else 
				cur1 = cur1.next;
			
			if (cur2 != null)
				cur2 = head1;
			else 
				cur2 = cur2.next;
		}
		
		return cur1.data;
	}
	
	
	
	// LinkedList: get data pos backwards from the Tail
	// use head to do loop run
	// use 2nd node runner to delay tailPos step to start
	// when head run to the end, the delayed pointer is at the correct pos

	
	// Recursion: Print Binary
	void printBinary(int n) {
		if (n > 0) {
			printBinary(n/2);
			System.out.printf("%d", n%2);
		}
	}
	
	
	
}






@FunctionalInterface
interface GreetingFunction {
	void sayMessage(String message);
}

@FunctionalInterface
interface Calculate {
	int calc(int x, int y);
}

//define NO arg functional interface
interface NoArgFunction<R> {
	R apply();
}

//define Tri Arg functional interface
interface TriFunction<T, U, V, R> {
	R apply(T t, U u, V v);
}

//Used by DataLoader
class Person {
	private String name;
	private Integer age;

	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
}

class TreeNode {
	int data;
	TreeNode left;
	TreeNode right;

	TreeNode(int d) {
		this.data = d;
	}
}

class ListNode {
	int data;
	ListNode next;

	ListNode(int d) {
		data = d;
	}
}

//BST Node
class Node {
	int data;
	Node left, right;

	Node(int data) {
		this.data = data;
	}

// BST Insert
	void insert(int d) {
		if (d <= this.data) {
			if (left == null) {
				left = new Node(d);
			} else {
				left.insert(d);
			}
		} else if (d > this.data) {
			if (right == null) {
				right = new Node(d);
			} else {
				right.insert(d);
			}
		}
	}

// BST find
	Node find(Integer data) {
		if (this.data == data)
			return this;
		if (data < this.data && left != null)
			return left.find(data);
		if (right != null)
			return right.find(data);
		return null;
	}

// BST traverse InOrder
	void traverseInOrder() {
		if (left != null) {
			left.traverseInOrder();
		}
		System.out.println(this.data + " ");
		if (this.right != null) {
			right.traverseInOrder();
		}
	}

// BST isLeaf
	boolean isLeaf() {
		return left == null && right == null;
	}

// BST height
	int height() {
		if (isLeaf())
			return 1;
		int leftHeight = 0;
		int rightHeight = 0;

		if (left != null)
			leftHeight = left.height();
		if (right != null)
			rightHeight = right.height();

		return 1 + Math.max(leftHeight, rightHeight);
	}

	Integer largest() {
		if (right == null)
			return this.data;
		return right.largest();
	}

	Integer smallest() {
		if (left == null)
			return this.data;
		return left.smallest();
	}

	int numOfLeafNodes() {
		if (isLeaf())
			return 1;
		int leftLeaves = 0;
		int rightLeaves = 0;
		if (left != null)
			leftLeaves = left.numOfLeafNodes();
		if (right != null)
			rightLeaves = right.numOfLeafNodes();
		return leftLeaves + rightLeaves;
	}

	public static Node addSorted(int[] data, int start, int end) {
		if (end >= start) {
			int mid = (start + end) / 2;
			Node newNode = new Node(data[mid]);
			newNode.left = addSorted(data, start, mid - 1);
			newNode.right = addSorted(data, mid + 1, end);
			return newNode;
		}
		return null;
	}
}

class BST {
	private Node root;

	public void insert(int d) {
		if (root == null)
			root = new Node(d);
		else
			root.insert(d);
	}

	public void traveseInOrder() {
		if (root == null)
			return;
		root.traverseInOrder();
	}

	public Node find(int d) {
		if (root == null)
			return null;
		return root.find(d);
	}

	public Integer smallest() {
		if (root == null)
			return null;
		return root.smallest();
	}

	public int height() {
		if (root == null)
			return 0;
		return root.height();
	}

	int numOfLeafNodes() {
		if (root == null)
			return 0;
		return root.numOfLeafNodes();
	}

	public static BST createFromSortedArray(int[] data) {
		BST bst = new BST();
		if (data != null && data.length > 0) {
			bst.root = Node.addSorted(data, 0, data.length - 1);
		}
		return bst;
	}
}

//For Functional/Lambda
class MeMath {
	public static Integer add(Integer x, Integer y) {
		return x + y;
	}

	public static Integer subtract(Integer x, Integer y) {
		return x - y;
	}

	public static Integer combine2And3(BiFunction<Integer, Integer, Integer> combineFunc) {
		return combineFunc.apply(2, 3);
	}
}

class MyMath {
	public static Integer triple(Integer x) {
		return x * 3;
	}

	public static Integer timesTwo(Integer x) {
		return x * 2;
	}

	public static Integer timesThree(Integer x) {
		return x * 3;
	}

	public static Integer timesFour(Integer x) {
		return x * 4;
	}

	// Returning function
	public static Function<Integer, Integer> createMultiplier(Integer y) {
		return (Integer x) -> x * y;
	}
}