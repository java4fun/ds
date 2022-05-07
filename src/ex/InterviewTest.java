package ex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class InterviewTest {

	@Test
	// 1. Static 
	void testStatic() {
		Dice d = new Dice();
		// The sidesOfDice should be accessed in a static way
		int sides = d.sidesOfDice + Dice.sidesOfDice;
	}
	
	
	
	@Test
	// 2. OPTIONAL
	void testOptional() {

        Optional<String> emptyOpt = Optional.empty();
        Optional<String> nameOpt = Optional.of("Kathryn");

//        if (emptyOpt.isPresent()) {
//            System.out.println("Hi " + nameOpt.get());
//        } else {
//            System.out.println("Hi");
//        }

        // No need to check isPresent()
        System.out.println("Hi " + nameOpt.orElse(""));
        System.out.println("Hi " + emptyOpt.orElse(""));
	}
	
	
	
	@Test
	// 3. New Switch (java 14)
	void testNewSwitch() {
		
		Season season = Season.Spring;
		
		// java 14 Only
//        String weather = switch(season) {
//	        case Spring, Summer -> "Sunny";
//	        case Fall -> "Rainy";
//	        case Winter -> "Snowy";
//	        default -> {
//	            System.out.println("Invalid season");
//	            yield "Invalid weather";
//	        }
//	    };
//
//    System.out.println(weather);
	}
    enum Season {
        Spring, Summer, Fall, Winter
    }
    
    
    
    
    
	@Test
	// 4. String Manipulation 
	void testStringManipulation() {
		
		// 1. STRING CONCAT
        String firstName = "Shelly";
        String lastName = "Parker";

        String name = firstName + " " + lastName;
        System.out.println(name);

        String concatName = (firstName.concat(" ").concat(lastName));

        StringBuilder stringBuilder = new StringBuilder("abc");
        stringBuilder.append("def");
        stringBuilder.append("ghi");
        stringBuilder.insert(0, "xyz");
        stringBuilder.delete(3, 6);
        String alpha = stringBuilder.toString();
        System.out.println(alpha);

        StringBuffer stringBuffer = new StringBuffer("abc");
        stringBuffer.append("def");
        stringBuffer.append("ghi");
        stringBuffer.insert(0, "xyz");
        stringBuffer.delete(3, 6);
        String beta = stringBuffer.toString();
        System.out.println(beta);
        
        
        
        // 2. NORMALIZE STRING INPUT
        String panda = "  Panda";
        String fish = "  fish  ";
        String horse = "Horse   ";
        String cat = " CAT";
        String nothing = "   ";

        List<String> animals = List.of(panda, fish, horse, cat, nothing);
        animals.stream().forEach(s -> System.out.println(s.trim()));

        List<String> trimmed = animals.stream()
                .map(s -> s.trim())
                .collect(Collectors.toList());

        List<String> realAnimals = trimmed.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        List<String> normalizedAnimalsNames = realAnimals.stream()
                .map(s -> s.toLowerCase())
                .collect(Collectors.toList());

        System.out.println("Normalized Animal Names " + normalizedAnimalsNames);  
        
        
        
        // 3. Access data from string
        // 0 1 2 3 4 5
        // A P P L E S
        String apples = "Apples";

        char firstCharacter = apples.charAt(0);
        System.out.println(firstCharacter);

        System.out.println(apples.indexOf('e'));

        String sub = apples.substring(2, 4);
        System.out.println(sub);

        System.out.println(apples.contains("App"));

        char[] applesArray = apples.toCharArray();
        int index = 4;
        System.out.println(applesArray[4]);
        
        
        
        // 4. String functions
        String text = "The giant panda has an insatiable appetite for bamboo. " +
                "A typical animal eats half the day—a full 12 out of every 24 " +
                "hours—and relieves itself dozens of times a day. It takes 28 " +
                "pounds of bamboo to satisfy a giant panda's daily dietary needs. " +
                "Pandas will sometimes eat birds or rodents as well.";

        String[] sentences = text.split("\\. ");

        System.out.println("The text has " + sentences.length + " sentences.");

        String[] words = text.split(" |-");
        System.out.println("The text has " + words.length + " words.");

        System.out.println(Arrays.asList(words));

        System.out.println("The text has " + text.length() + " characters.");

        
        
        
        // 5. String Equality
        String literalA = "abc";
        String literalB = "abc";
        String literalC = "c";

        String objectA = new String("abc");
        String objectB = new String("abc");
        String objectC = new String("c");

        System.out.println(literalA.equals(objectA));  // true
        System.out.println(literalA.equals(objectB));  // true
        System.out.println(literalA.equals(literalB)); // true

        System.out.println(literalA.equals(literalC));   // false
        System.out.println(literalA.equals(objectC));    // false
	}
    
	
	
	
	
	
	// 5. Palindrome
    public static boolean palindromeCheckerTraditional(String original) {
        String normalized = original.toLowerCase();
        StringBuilder reversed = new StringBuilder();

        // String reversed = StringUtils.reverse(normalized);
        // String reversed = new StringBuilder(normalized).reverse().toString();

        for (int i = normalized.length() - 1; i >= 0; i--) {
            reversed.append(normalized.charAt(i));
        }

        return normalized.equals(reversed.toString());
    }
    public static boolean palindromeCheckStreams(String original) {
        String normalized = original.toLowerCase();

        return IntStream.range(0, normalized.length() / 2)
                .allMatch(i ->
                        normalized.charAt(i) ==
                                normalized.charAt(normalized.length() -i -1));
    }
    public static boolean palindromeCheckPointer(String original) {
        String normalized = original.toLowerCase();
        int forward = 0;
        int backward = normalized.length() - 1;
        while (forward <= backward) {
        	if (normalized.charAt(forward) != normalized.charAt(backward)) {
        		return false;
        	}
        	forward++; backward--;
        }
        return true;
    }
    
	@Test
	void testPalindrome() {
        String s1 = "maDam";
        String s2 = "raceCar";
        String s3 = "aBba";
        String s4 = "swims";
        String s5 = "wifI";

        System.out.println(palindromeCheckerTraditional(s1));
        System.out.println(palindromeCheckerTraditional(s2));
        System.out.println(palindromeCheckerTraditional(s3));
        System.out.println(palindromeCheckerTraditional(s4));
        System.out.println(palindromeCheckerTraditional(s5));

        System.out.println();

        System.out.println(palindromeCheckStreams(s1));
        System.out.println(palindromeCheckStreams(s2));
        System.out.println(palindromeCheckStreams(s3));
        System.out.println(palindromeCheckStreams(s4));
        System.out.println(palindromeCheckStreams(s5));
        
        System.out.println();

        System.out.println(palindromeCheckPointer(s1));
        System.out.println(palindromeCheckPointer(s2));
        System.out.println(palindromeCheckPointer(s3));
        System.out.println(palindromeCheckPointer(s4));
        System.out.println(palindromeCheckPointer(s5));
	}
	// 5. Palindrome II (can delete 1 char)
	// use two pointer(LEFT, RIGHT)
	
	
	
	
	
	
	
	
	// 6. Count Vowels and Consonants
    public static void findNumberOfVowelsAndConsonants(String input) {
        int vowelsCount = 0;
        int consonantCount = 0;
        String vowels = "aeiouy";
        String normalized = input.toLowerCase().trim();
        
        char[] normalizedArray = normalized.toCharArray();
        for (char c : normalizedArray) {
            if (vowels.indexOf(c) != -1) {
                vowelsCount++;
            } else if (c != ' '){
                consonantCount++;
            }
        }

        System.out.println("There are " + vowelsCount + " vowels in " + input);
        System.out.println("There are " + consonantCount + " consonants in " + input);
        System.out.println();
    }

    public static void findNumberOfVowelsAndConsonantsStreams(String input) {
        String vowels = "aeiouy";
        String normalized = input.toLowerCase().trim();

        List<Integer> letters = normalized.chars()
                .filter(Character::isAlphabetic)
                .boxed()
                .collect(Collectors.toList());

        long vowelsCount = letters.stream()
                .filter(c -> vowels.indexOf(c) != -1)
                .count();

        long consonantCount = letters.stream().count() - vowelsCount;

        System.out.println("There are " + vowelsCount + " vowels in " + input);
        System.out.println("There are " + consonantCount + " consonants in " + input);
        System.out.println();
    }
    
    
	@Test
	void testVowelsAndConsonants() {
        String s1 = "HellO";
        String s2 = " there is a quiet Mouse";
        String s3 = "I am happy    ";

        findNumberOfVowelsAndConsonants(s1);
        findNumberOfVowelsAndConsonants(s2);
        findNumberOfVowelsAndConsonants(s3);

        findNumberOfVowelsAndConsonantsStreams(s1);
        findNumberOfVowelsAndConsonantsStreams(s2);
        findNumberOfVowelsAndConsonantsStreams(s3);
	}
	
	
	
	
	
	
	
	// 7. Maximum Product of 2 numbers in a Array
    public static int maximumProduct(int[] arr) {

        int length = arr.length;
        int max = Integer.MIN_VALUE;

        if (length < 2) {
            System.out.println("No maximum exists, returning sentinel value");
            return max;
        }

        for (int i = 0; i < length; i++) {
            for (int j = i+1; j < length; j++) {
                if (arr[i] * arr[j] > max) {
                    max = arr[i] * arr[j];
                }
            }
        }

        return max;
    }

    public static int maximumProduct2(int[] arr) {

        int length = arr.length;

        if (length < 2) {
            System.out.println("No maximum exists, returning sentinel value");
            return Integer.MIN_VALUE;
        }

        Arrays.sort(arr);

        int maxProduct = arr[length - 2] * arr[length - 1];
        int minProduct = arr[0] * arr[1];

        if (maxProduct > minProduct) {
            return maxProduct;
        } else {
            return minProduct;
        }
    }

    public static int maximumProduct3(int[] arr) {
        int length = arr.length;

        if (length < 2) {
            System.out.println("No maximum exists, returning sentinel value");
            return Integer.MIN_VALUE;
        }

        int max1 = arr[0];
        int max2 = Integer.MIN_VALUE;

        int min1 = arr[0];
        int min2 = Integer.MAX_VALUE;

        for (int i = 1; i < length; i++) {
            if (arr[i] < min1) {
                min2 = min1;
                min1 = arr[i];
            } else if (arr[i] < min2) {
                min2 = arr[i];
            } else if (arr[i] > max1) {
                max2 = max1;
                max1 = arr[i];
            } else if (arr[i] > max2) {
                max2 = arr[i];
            }
        }

        int maxProduct = max1 * max2;
        int minProduct = min1 * min2;

        if (maxProduct > minProduct) {
            return maxProduct;
        } else {
            return minProduct;
        }
    }
    
	@Test
	void testMaxProductOfTwoNumbers() {
        int[] t1 = {5, 3, 2, 5, 7, 0, 1};
        int[] t2 = {-2, -1, -3, 4, 8, 0};
        int[] t3 = {-20, -10, 3, 9, -8};

        System.out.println(maximumProduct(t1));
        System.out.println(maximumProduct(t2));
        System.out.println(maximumProduct(t3));

        System.out.println();

        System.out.println(maximumProduct2(t1));
        System.out.println(maximumProduct2(t2));
        System.out.println(maximumProduct2(t3));

        System.out.println();

        System.out.println(maximumProduct3(t1));
        System.out.println(maximumProduct3(t2));
        System.out.println(maximumProduct3(t3));
	}
	
	
	
	
	
	// 8. Delete middle node in a Linked List
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
	
    public static void printLinkedList(Node head) {
        while(head != null) {
            System.out.print(head.getData() + ", ");
            head = head.getNext();
        }
        System.out.println("END");
    }

    public Node<Integer> buildNode(int data) {
        Node node = new Node();
        node.setData(data);
        node.setNext(null);
        return node;
    }
    
    
    public static Node deleteMiddle(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Node slow = head;
        Node fast = head;

        Node previous = null;

        while(fast != null && fast.getNext() != null) {
            fast = fast.getNext().getNext();
            previous = slow;
            slow = slow.getNext();
        }

        // Delete middle node
        previous.setNext(slow.getNext());

        return head;
    }
    
	@Test
	void testDeleteMiddleNodeInLinkedList() {
        Node<Integer> head = buildNode(8);
        Node<Integer> nodeA = buildNode(3);
        Node<Integer> nodeB = buildNode(18);
        Node<Integer> nodeC = buildNode(12);
        Node<Integer> nodeD = buildNode(1);

        head.setNext(nodeA);
        nodeA.setNext(nodeB);
        nodeB.setNext(nodeC);
        nodeC.setNext(nodeD);

        printLinkedList(head);
        deleteMiddle(head);
        printLinkedList(head);
        deleteMiddle(head);
        printLinkedList(head);
        deleteMiddle(head);
        printLinkedList(head);
        deleteMiddle(head);
        printLinkedList(head);
        deleteMiddle(head);
        printLinkedList(head);
	}
	
	
	
	
	
	
	// 9. Generate Binary Numbers
    public static void printBinary(int n) {
        if (n <= 0) {
            System.out.println("Nothing to print\n");
            return;
        }

        for (int i = 1; i <= n; i++) {
            System.out.println(Integer.toBinaryString(i));
        }

        System.out.println();
    }

    public static void printBinaryUsingQueue(int n) {
        if (n <= 0) {
            System.out.println("Nothing to print\n");
            return;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);

        for (int i = 1; i <= n; i++) {
            Integer current = queue.remove();
            System.out.println(current);

            queue.add(current * 10);
            queue.add(current * 10 + 1);
        }

        System.out.println();
    }
    
	@Test
	void testGenerateBinaryNumbers() {
      printBinary(6);
      printBinary(-9);
      printBinary(0);
      printBinary(2);
      printBinary(10);

//      printBinaryUsingQueue(6);
//      printBinaryUsingQueue(-9);
//      printBinaryUsingQueue(0);
//      printBinaryUsingQueue(2);
//      printBinaryUsingQueue(10);
	}
	
	
	
	
	// 10. Matching Parentheses
    public static boolean matchingParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> blockSymbols = Map.of(')','(',
                ']', '[',
                '>','<');

        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);

            if (blockSymbols.containsValue(current)) {
                stack.push(current);
                continue;
            }

            if (blockSymbols.containsKey(current) &&
                    (stack.isEmpty() ||
                            blockSymbols.get(current) != stack.pop())) {
                return false;
            }
        }

        return stack.isEmpty();
    }
	
	@Test
	void testMatchingParentheses() {
        String t1 = "(<[ ]>)";
        String t2 = "[<incre>ment]";
        String t3 = "<increment>";
        String t4 = "()incre<>ment<>[]";

        String t5 = "<increment(";
        String t6 = "[)incr]ement(";
        String t7 = "<i(ncr>e)ment";
        String t8 = "(<increment>";

        System.out.println(matchingParentheses(t1));
        System.out.println(matchingParentheses(t2));
        System.out.println(matchingParentheses(t3));
        System.out.println(matchingParentheses(t4));

        System.out.println(matchingParentheses(t5));
        System.out.println(matchingParentheses(t6));
        System.out.println(matchingParentheses(t7));
        System.out.println(matchingParentheses(t8));
	}
	
	
	
	
	
	// 11. Polymorphism
	class NonNegativeArrayList extends ArrayList<Integer> {

	    public NonNegativeArrayList(Integer... numbers) {
	        super(Arrays.stream(numbers)
	                .filter(n -> n >= 0)
	                .collect(Collectors.toList()));
	    }
	    public NonNegativeArrayList(Collection<Integer> list) {
	        super(list.stream()
	                .filter(n -> n >= 0)
	                .collect(Collectors.toList()));
	    }
	    
	    @Override
	    public void add(int index, Integer element) {
	        if (element >= 0) {
	            super.add(index, element);
	        }
	    }

	    @Override
	    public boolean add(Integer element) {
	        if (element >= 0) {
	            return super.add(element);
	        } else {
	            return false;
	        }
	    }

	    @Override
	    public boolean addAll(Collection<? extends Integer> c) {
	        return super.addAll(c.stream()
	                .filter(n -> n >= 0)
	                .collect(Collectors.toList()));
	    }

	    @Override
	    public boolean addAll(int index, Collection<? extends Integer> c) {
	        return super.addAll(index, c.stream()
	                .filter(n -> n >= 0)
	                .collect(Collectors.toList()));
	    }
	}
	
	@Test
	void testPolymorphism() {
        HashSet<Integer> hashSet = new HashSet() {{
            add(1);
            add(-3);
            add(10);
            add(0);
        }};

        ArrayList<Integer> arrayList = new ArrayList<>() {{
            add(-17);
            add(11);
            add(48);
            add(-8);
            add(-5);
        }};

        
        List<Integer> hashNonNegativeList = new NonNegativeArrayList(hashSet);
        List<Integer> arrayNonNegativeList = new NonNegativeArrayList(arrayList);
        List<Integer> nonNegativeArrayList = new NonNegativeArrayList(-10, 2, 3, -2, 0);

        System.out.println(hashNonNegativeList);
        System.out.println(arrayNonNegativeList);
        System.out.println(nonNegativeArrayList);
	}
	
}








// HELPER class
class Dice {
	static int sidesOfDice = 6;
	int faceValue = 0;
	
	public int roll () {
		Random r = new Random();
		this.faceValue = r.nextInt(sidesOfDice) + 1;
		return this.faceValue;
	}
	
	public static void changeSidesOfrDice(int newSides) {
		Dice.sidesOfDice = newSides;
	}
	
	public int getFaceValue() {
		return this.faceValue;
	}
}

