package ds;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;


// 2. Custom functional interface
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
//4. used by DataLoader
class DataLoader {
 public final NoArgFunction<Person> loadPerson;

 public DataLoader(Boolean isDevelopment) {
     loadPerson = isDevelopment
             ? this::loadPersonFake
             : this::loadPersonReal;
 }

 private Person loadPersonReal() {
     System.out.println("Loading person...");
     return new Person("Real Person", 30);
 }

 private Person loadPersonFake() {
     System.out.println("Returning fake person object...");
     return new Person("Fake Person", 100);
 }
}

class LambdaTest {

	// 1. the basics of lambda
	static void FunctionBasics() {
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
	}
	

	// 3. Runnable simplified
	public static void RunnableSimplified() {
		// a. Anonymous Inner class: RUNNABLE
		Runnable r1 = new Runnable() {
			@Override 
			public void run() {
				System.out.println("run 1");
			}
		};
		
		// b. Lambda
		Runnable r2 = () -> System.out.println("run 2");
		
		// Start both thread
		r1.run();
		r2.run();
	}
	
	// 4. build-in functions
	public static void BuildInFunctions() {
		// IntFunction:
		IntFunction<String> intToString = num -> Integer.toString(num);
		// Or using :: (functional reference to a method)
		IntFunction<String> intToString2 = Integer::toString;
		System.out.println("expected value 3, actual value: " +
                intToString.apply(123).length());

		// Lambda (functional reference to a Constructor)
		Function<String, BigInteger> newBigInt = BigInteger::new;
        System.out.println("expected value: 123456789, actual value: "+
                newBigInt.apply("123456789"));	
		
        // Lambda (functional reference to an instance method)
        Consumer<String> print = System.out::println;
        print.accept("Coming to you directly from a lambda...");
        
        // Below two are the same using static method concat()
        UnaryOperator<String> greeting = x -> "Hello, ".concat(x);
        System.out.println(greeting.apply("World"));
        UnaryOperator<String> makeGreeting = "Hello, "::concat;
        System.out.println(makeGreeting.apply("Peggy"));
        
	}
	
	// 5. Calculation as functional interface
	public static void CalculateFunctions() {
		Calculate add = (a, b) -> a + b;
		Calculate diff = (a, b) -> Math.abs(a-b);
		Calculate divide = (a, b) -> (b != 0 ? a/b : 0);
		
        System.out.println(add.calc(3,2));
        System.out.println(diff.calc(5,10));
        System.out.println(divide.calc(5, 0));
	}
	
	// 6. Collector and Stream API
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
	
	
	@Test
	void testLambda() {
		//fail("Not yet implemented");
		System.out.println("*****FunctionBasics()******************");
		FunctionBasics();
		
		System.out.println("*****RunnableSimplified()******************");
		RunnableSimplified();
		
		System.out.println("*****BuildInFunctions()*******************");
		BuildInFunctions();
		
		System.out.println("*****CalculateFunctions()******************");
		CalculateFunctions();
		
		System.out.println("*****StreamApiWithCollector()*****************");
		StreamApiWithCollector();
		
	}

	
	
	@Test
	// From Shawn's training
	void testFunc() {

        // 1. The functional interface
        Function<Integer, Integer> myTriple = MyMath::triple;
        Integer result = myTriple.apply(5);
		System.out.println("*****The functional interface******************");
        System.out.println(result);



        // 2. The functional interface
        Function<Integer, Integer> absoluteValue = (x) -> {
            if (x < 0) {
                return -x;
            } else {
                return x;
            }
        };
        // SHORT:   (x) -> x < 0 ? -x : x;
		System.out.println("*****The functional interface******************");
        System.out.println(absoluteValue.apply(-100));

        // 3. BiFunction (Built-in),  
        // TriFunction (Self defined above), 
        // NoArgFunction (Self defined above)
		System.out.println("*****BiFunction, TriFunction, NoArgFunction******************");
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
            System.out.println(add.apply(32, 32));

        TriFunction<Integer, Integer, Integer, Integer> addThree = (x, y, z) -> x + y + z;
            System.out.println(addThree.apply(54, 3, 4));

        NoArgFunction<String> sayHello = () -> "Hello!";
            System.out.println(sayHello.apply());



        // 4. Functions as Data
        final Boolean IS_DEVELOPMENT = false;
        DataLoader dataLoader = new DataLoader(IS_DEVELOPMENT);
		System.out.println("*****Functions as Data******************");
        System.out.println(dataLoader.loadPerson.apply());




        // 5. Passing Function as Arguments
		System.out.println("*****Function as Arguments******************");
        System.out.println(MeMath.combine2And3(MeMath::add));
        System.out.println(MeMath.combine2And3(MeMath::subtract));
        System.out.println(MeMath.combine2And3((x, y) -> 2 * x + 2 * y));




        // 6. Returning function
        NoArgFunction<NoArgFunction<String>> createGreeter = () -> () -> "Hello functional!";
        NoArgFunction<String> greeter = createGreeter.apply();
		System.out.println("*****Returning function******************");
        System.out.println(greeter.apply());

        Function<Integer, Integer> timesTwo = MyMath.createMultiplier(2);
        Function<Integer, Integer> timesThree = MyMath.createMultiplier(3);
        Function<Integer, Integer> timesFour = MyMath.createMultiplier(4);
        System.out.println(timesTwo.apply(6));
        System.out.println(timesThree.apply(6));
        System.out.println(timesFour.apply(6));


        // 7. closure: can access local vars included in the function
        NoArgFunction<NoArgFunction<String>> createAGreeter = () -> {
            String name = "Shaun";
            return () -> "Hello, " + name;
        };
        //NoArgFunction<String> greeter = createAGreeter.apply();
		System.out.println("*****closure******************");
        //System.out.println(greeter.apply());



        // 8. Higher Order Function
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
		
	}
}


//5. Passing Function as Arguments
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
