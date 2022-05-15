import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;


class Demo1Test {

	// 110. Is Binary Tree Balanced
	boolean balanced = true;
	boolean isBinaryTreeBalanced(TreeNode root) {
		height(root);
		return balanced;
	}
	private int height(TreeNode root) {
		if (root == null) return 0;
		int leftHeight = height(root.left);
		int rightHeight = height(root.right);
		
		// is tree balanced?
		if (Math.abs(leftHeight-rightHeight) > 1) balanced = false;
		
		return 1 + Math.max(leftHeight, rightHeight);
	}
	
	
	
	// 104. Max Depth Of Binary Tree
	// height()
	private int maxDepthOfBT (TreeNode root) {
		if (root == null) return 0;
		return 1 + Math.max(maxDepthOfBT(root.left), maxDepthOfBT(root.right)) ;
	}
	
	
	// 94. Binary Tree InOrder traversal
	void inOrder(TreeNode root) {
		if (root == null) return;
		// PreOrder:  System.out.println(root.data + ", ");
		inOrder(root.left);
		System.out.println(root.data + ", ");
		inOrder(root.right);
		// PostOrder:  System.out.println(root.data + ", ");
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
		
		while (!stack.isEmpty()) {
			TreeNode cur = stack.pop();
			System.out.println(cur.data + " ");
			
			//LIFO:  push right first -> then it will pop last
			if (cur.right != null) stack.push(cur.right);
			if (cur.left != null) stack.push(cur.left);
		}
	}
	
	
	
	// 102. Binary Tree Level Order Traversal (Top Down)
	List<List<Integer>> levelOrderTraversal(TreeNode root) {
		List<List<Integer>> result = new LinkedList<>();
		if (root == null) return result;
		
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		
		while (!q.isEmpty()) {
			int n = q.size();
			List<Integer> levelList = new LinkedList<>();
			
			for (int i = 0; i < n; i++) {
				TreeNode node = q.remove();
				levelList.add(node.data);   //OR: System.out.println(cur.data + " ");
				if (node.left != null) q.add(node.left);
				if (node.right != null) q.add(node.right);
			}
			// !!!ATTENTION!!!
			//result.add(0, levelList);  // BOTTOM UP
			result.add(levelList);
		}
		return result;
	}
	
	
	
	// 107. Binary Tree Level Order Traversal II (Bottom Up)
	// Same as levelOrderTraversal, except result list (always insert to front of the list) 
	List<List<Integer>> levelOrderBottomUp(TreeNode root) { 
		List<List<Integer>> result = new LinkedList<>();
		if (root == null) return result;
		
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		
		while (!q.isEmpty()) {
			int n = q.size();
			List<Integer> levelList = new LinkedList<>();
			for (int i = 0; i < n; i++) {
				TreeNode node = q.remove();
				levelList.add(node.data);
				if (node.left != null) q.add(node.left);
				if (node.right != null) q.add(node.right);
			}
			
			// !!!ATTENTION!!!
			result.add(0, levelList);
		}
		return result;
	}


	//100. Same Binary Tree
	boolean isSameBinaryTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) return true;
		if (p == null || q == null) return false;
		if (p.data != q.data) return false;
		
		return isSameBinaryTree(p.left, q.left) && isSameBinaryTree(p.right, q.right);
	}
	
	
	// 101. Symmetric Binary Tree 
	boolean isSymmetricBinaryTree (TreeNode root) {
		if (root == null) return true;
		return symmetricHelper(root.left, root.right);
	}
	boolean symmetricHelper(TreeNode left, TreeNode right) {
		if (left == null && right == null) return true;
		if (left == null || right == null) return false;
		if (left.data != right.data) return false;
		
		return symmetricHelper(left.right, right.left) &&
				symmetricHelper(left.left, right.right);
	}
	
	
	
	//98. Validate Binary Search Tree (BST)
	// Recursive
	
	
	// Insert data into BST(root)
	static Node insertNodeBST(Node root, int d) {
		if (root == null) return new Node(d);
		Node cur;
		if (d <= root.data) {
			cur = insertNodeBST(root.left, d);
			root.left = cur;
		} else {
			cur = insertNodeBST(root.right, d);
			root.right = cur;
		}
		return root;
	}
	
	
	// LinkedList: find merge node (two lists intercept)
	// 1: 1 -> 2 -> 3 -> 7 -> 8
	// 2:      5 -> 6 -> 7 -> 8
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
		return cur1.data;
	}
	
	
	// LinkedList: get data pos backwards from the Tail
	int getNodeFromListTail (ListNode head, int pos) {
		// use head to do loop run
		// use 2nd node runner to delay tailPos step to start
		// when head run to the end, the delayed pointer is at the correct pos

		ListNode delayPointer = head;
		int count =0;
		
		while(head != null) {
			head = head.next;
			if (count < pos) {
				count++;
			} else {
				delayPointer = delayPointer.next;
			}
		}
		// head reaches list end
		return delayPointer.data;
	}
	
	
	// Recursion: Print Binary
	void printBinary(int n) {
		if (n > 0) {
			printBinary(n/2);
			System.out.printf("%d", n%2);
		}
	}
	
	
	
	
	// Stream && Lambda (functional)
	// 1. create stream
	static Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	static List<Integer> intList = new ArrayList<>(Arrays.asList(intArray));
	static Stream<String> shopStream = Stream.of("apples", "bananas", "cherries", "coffee");
	static String[] shopArray = new String[]{"apples", "bananas", "cherries", "coffee"};
	static Stream<String> shopArrayStream = Arrays.stream(shopArray);
	static List<String> shopList = List.of("apples", "bananas", "cherries", "coffee");
	static Stream<String> shopListStream = shopList.stream();
	
	@Test
	void testStreamAndLambda() {
		
		// 2. For loop in one line
		shopList.stream().forEach(System.out::println);
		
		
		// 3. Match
		boolean isOnList = shopList.stream()
				.anyMatch(item -> item.contains("apples"));
		
		// 4. Map
		Function <Integer, Integer> timesTwo = x -> x *2;
		List<Integer> doubled = intList
				.stream()
				.map(timesTwo)
				.collect(Collectors.toList());
		
		
		// 5. Filter using Predicate
		Predicate<Integer> isEven = x -> x%2 == 0;
		List<Integer> evens = intList
				.stream()
				.filter(isEven)
				.collect(Collectors.toList());
		//!!! Collectors.toSet(), joining(), counting(), groupingBy, partitioningBy !!!
		
		// 6. Reduce
		BinaryOperator<Integer> getSum = (acc, x) -> acc + x;
		Integer sum = intList
				.stream()
				.reduce(0, getSum);
		
		
		// 7. Combine Functions
//        List<Employee> employees = new ArrayList<>(Arrays.asList(employeesArr));
//        
//        Float totalDeveloperSalaries = employees
//        		.stream()
//        		.filter(e -> e.jobTitle == "developer")
//        		.map(d -> d.salary)
//        		.reduce(0f,  (acc, x) -> acc + x);
//        
//        Long numberOfDevelopers = employees
//                .stream()
//                .filter((employee) -> employee.jobTitle == "developer")
//                .collect(Collectors.counting());
//        
//        Float averageDeveloperSalary = totalDeveloperSalaries / numberOfDevelopers;
		
// ===== 8.  Basics of Functional Interface ======
		// Predicate: test()
		Predicate<String> stringLenLessThan10 = (s) -> s.length() < 10;
		System.out.println(stringLenLessThan10.test("Apples"));
	    
		// Consumer: accept()
		Consumer <String> stringConsumer = (s) -> System.out.println(s.toLowerCase());
		stringConsumer.accept("ABCDEFG");
		
        Consumer<String> hello = name -> System.out.println("Hello, " + name);
        for (String name : Arrays.asList("Duke", "Mickey", "Minnie")) {
            hello.accept(name);
        }
		
		// Function: apply()
		Function<Integer, String> int2StringConverter = (i) -> Integer.toString(i);
		System.out.println(int2StringConverter.apply(26));
		
		// Supplier: get()
		Supplier <String> stringSupplier = () -> "Java is fun";
		System.out.println(stringSupplier.get());
		
		// BiFunction: apply()
		BiFunction<String, String, String> concat = (a, b) -> a + b;
		String sentence = concat.apply("today is ", "a great day");
		System.out.println(sentence);
		
		// Custom Function:
		GreetingFunction greeting = message -> System.out.println("Java programming " + message);
		greeting.sayMessage("rocks with lambda expression");

		// ===== 9. build-in functions =====
		// IntFunction:
		IntFunction<String> intToString = num -> Integer.toString(num);
		// Or using :: (functional reference to a method)
		IntFunction<String> intToString2 = Integer::toString;
		System.out.println("expected value 3, actual value: " +
                intToString.apply(123).length());

        // Lambda (functional reference to an instance method)
        Consumer<String> print = System.out::println;
        print.accept("Coming to you directly from a lambda...");

		// Lambda (functional reference to a Constructor)
		Function<String, BigInteger> newBigInt = BigInteger::new;
        System.out.println("expected value: 123456789, actual value: "+
                newBigInt.apply("123456789"));	
		
        
        // Below two are the same using static method concat()
        UnaryOperator<String> greet = x -> "Hello, ".concat(x);
        System.out.println(greet.apply("World"));
        UnaryOperator<String> makeGreeting = "Hello, "::concat;
        System.out.println(makeGreeting.apply("Peggy"));
        
        
        // BiFunction (Built-in),  
        // TriFunction (Self defined above), 
        // NoArgFunction (Self defined above)
		System.out.println("*****BiFunction, TriFunction, NoArgFunction******************");
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
            System.out.println(add.apply(32, 32));

        TriFunction<Integer, Integer, Integer, Integer> addThree = (x, y, z) -> x + y + z;
            System.out.println(addThree.apply(54, 3, 4));

        NoArgFunction<String> sayHello = () -> "Hello!";
            System.out.println(sayHello.apply());
            
            
        // 10. Functions as Data
        final Boolean IS_DEVELOPMENT = false;
        //DataLoader dataLoader = new DataLoader(IS_DEVELOPMENT);
		//System.out.println("*****Functions as Data******************");
        //System.out.println(dataLoader.loadPerson.apply());




        // 11. Passing Function as Arguments
		System.out.println("*****Function as Arguments******************");
        System.out.println(MeMath.combine2And3(MeMath::add));
        System.out.println(MeMath.combine2And3(MeMath::subtract));
        System.out.println(MeMath.combine2And3((x, y) -> 2 * x + 2 * y));




        // 12. Returning function
        NoArgFunction<NoArgFunction<String>> createGreeter = () -> () -> "Hello functional!";
        NoArgFunction<String> greeter = createGreeter.apply();
		System.out.println("*****Returning function******************");
        System.out.println(greeter.apply());

        Function<Integer, Integer> times2 = MyMath.createMultiplier(2);
        Function<Integer, Integer> timesThree = MyMath.createMultiplier(3);
        Function<Integer, Integer> timesFour = MyMath.createMultiplier(4);
        System.out.println(timesTwo.apply(6));
        System.out.println(timesThree.apply(6));
        System.out.println(timesFour.apply(6));


        // 13. closure: can access local vars included in the function
        NoArgFunction<NoArgFunction<String>> createAGreeter = () -> {
            String name = "Shaun";
            return () -> "Hello, " + name;
        };
        //NoArgFunction<String> greeter = createAGreeter.apply();
		System.out.println("*****closure******************");
        //System.out.println(greeter.apply());



        // 14. Higher Order Function
        BiFunction<Float, Float, Float> divide = (x, y) -> x / y;
        Function<BiFunction<Float, Float, Float>, BiFunction<Float, Float, Float>> secondArgIsntZeroCheck =
                (func) -> (x, y) -> {
                    if (y == 0f) {
                        System.out.println("Error: second argument is zero!");
                        return 0f;
                    }
                    return func.apply(x, y);
                };
        BiFunction<Float, Float, Float> divideSafe = secondArgIsntZeroCheck.apply(divide);
		System.out.println("*****Higher Order Function******************");
        System.out.println(divideSafe.apply(10f, 2f));	
        
        
        // 15. NORMALIZE STRING INPUT
        String panda = "  Panda";
        String fish = "  fish  ";
        String horse = "Horse   ";
        String cat = " CAT";
        String nothing = "   ";

        List<String> animals = List.of(panda, fish, horse, cat, nothing);
        animals.stream().forEach(s -> System.out.println(s.trim()));
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
		
		// re-factor 2: Not type declaration (Preferred)
		Collections.sort(names, (a, b) -> b.compareTo(a));
		
		System.out.println(names);
			
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
}

class TreeNode {
	int data;
	TreeNode left;
	TreeNode right;
	TreeNode (int d) {
		this.data = d;
	}
}

class ListNode {
	int data;
	ListNode next;
	ListNode (int d) {
		data = d;
	}
}

// BST Node
class Node {
	int data;
	Node left, right;
	Node (int data) {
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
		if (isLeaf()) return 1;
		int leftHeight = 0;
		int rightHeight = 0;
		
		if (left != null) leftHeight = left.height();
		if (right != null) rightHeight = right.height();
		
		return 1 + Math.max(leftHeight, rightHeight);
	}
	
	Integer largest() {
		if (right == null) return this.data;
		return right.largest();
	}
	Integer smallest() {
		if (left == null) return this.data;
		return left.smallest();
	}
	
	int numOfLeafNodes() {
		if (isLeaf()) return 1;
		int leftLeaves = 0;
		int rightLeaves = 0;
		if (left != null) leftLeaves = left.numOfLeafNodes();
		if (right != null) rightLeaves = right.numOfLeafNodes();
		return leftLeaves + rightLeaves;
	}
	
	public static Node addSorted(int[] data, int start, int end) {
		if (end >= start) {
			int mid = (start+end)/2;
			Node newNode = new Node(data[mid]);
			newNode.left = addSorted(data, start, mid-1);
			newNode.right = addSorted(data, mid+1, end);
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
		if (root == null) return;
		root.traverseInOrder();
	}
	
	public Node find(int d) {
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
	
	int numOfLeafNodes() {
		if (root == null) return 0;
		return root.numOfLeafNodes();
	}
	
	public static BST createFromSortedArray(int[] data) {
		BST bst = new BST();		
		if (data != null && data.length > 0) {
			bst.root = Node.addSorted(data, 0, data.length-1);
		}
		return bst;
	}
}

// For Functional/Lambda
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