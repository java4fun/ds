package ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class StreamTest {
	static Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	static List<Integer> listOfIntegers = new ArrayList<>(Arrays.asList(intArray));
	
	
	
	@Test
	// 0. Create stream
	void testStreamConstruction() {
        Stream<String> shoppingStream = Stream.of("apples", "bananas", "cherries", "coffee");
        // Array
        String[] shopArray = new String[]{"apples", "bananas", "cherries", "coffee"};
        String[] shoppingArray = {"apples", "bananas", "cherries", "coffee"};   // Simple way
        Stream<String> shopArrayStream = Arrays.stream(shoppingArray);
        // Lists
        List<String> shoppingList = List.of("apples", "bananas", "cherries", "coffee");
        Stream<String> shoppingListStream = shoppingList.stream();
        
		System.out.println("*****Stream.of()****************************");		

        // For loop in one line
        shoppingList.stream().forEach(System.out::println);
        shoppingList.parallelStream().forEach(System.out::println);
        
        // Match
        boolean isOnList = shoppingList.stream()
                .anyMatch(item -> item.contains("apples"));
        
		System.out.println(isOnList);
	}
	
	
	
	@Test
	// 1. Map (Function)
	void testMap() {
		Function<Integer, Integer> timesTwo = x -> x * 2;
		List<Integer> doubled = listOfIntegers
				.stream()
				.map(timesTwo)
				.collect(Collectors.toList());
		System.out.println("*****map()****************************");		
		System.out.println(doubled);
	}
	
	@Test
	// 2. Filter (Predicate)
	void testFilter() {
		Predicate<Integer> isEven = x -> x % 2 == 0;
		
		List<Integer> evens = listOfIntegers
				.stream()
				.filter(isEven)
				.collect(Collectors.toList());
		System.out.println("*****filter()****************************");
		System.out.println(evens);
		
		
        String[] wordsArr = { "hello", "functional", "programming", "is", "cool" };
        List<String> words = new ArrayList<>(Arrays.asList(wordsArr));

        Function<Integer, Predicate<String>> createLengthTest = (minLength) -> {
            return (str) -> str.length() > minLength;
        };

        Predicate<String> isLongerThan3 = createLengthTest.apply(3);
        Predicate<String> isLongerThan10 = createLengthTest.apply(10);

        List<String> longWords = words
                .stream()
                .filter(isLongerThan10)
                .collect(Collectors.toList());

        System.out.println(longWords);

		
	}

	
	@Test
	// 3. Reduce:
	void testReduce() {
		BinaryOperator<Integer> getSum = (acc, x) -> {
			Integer result = acc + x;
			return result;
		};
		
		Integer sum = listOfIntegers
				.stream()
				.reduce(0, getSum);
		
		System.out.println("*****reduce()****************************");
		System.out.println(sum);
		
	}

	
	@Test
	// 4. Collect:
	void testCollect() {
        String[] wordsArray = {"hello", "apple", "functional", "programming", "is", "cool"};
        List<String> words = new ArrayList<>(Arrays.asList(wordsArray));
        
        Set<String> wordSet = words
        		.stream()
        		.filter(w -> w.length() > 5)
        		.collect(Collectors.toSet());
		System.out.println("*****Collect()()****************************");
        System.out.println(wordSet);

        
        String longWords = words
        		.stream()
        		.filter(w -> w.length() > 5)
        		.collect(Collectors.joining(", "));
        System.out.println(longWords);
        
        
        Long howManyWords = words
                .stream()
                .filter((w) -> w.length() > 5)
                .collect(Collectors.counting());
        System.out.println(howManyWords);
        
        
        
        // GroupingBy due to multiples
        Map<Integer, List<String>> wordLengthMap = words
        		.stream()
        		.filter(w -> w.length() > 5)
        		.collect(Collectors.groupingBy(w -> w.length()));
        System.out.println(wordLengthMap);
        
        
        
        // Partition to 2 (Predicate: true, false)
        Map<Boolean, List<String>> wordLengthMap2 = words
        		.stream()
        		.collect(Collectors.partitioningBy(w -> w.length() > 5));
        System.out.println(wordLengthMap2);
        
        
	}

	
	@Test
	// 5. Combine List Functions
	void testCombine() {
        Employee[] employeesArr = {
                new Employee("John", 34, "developer", 80000f),
                new Employee("Hannah", 24, "developer", 95000f),
                new Employee("Bart", 50, "sales executive", 100000f),
                new Employee("Sophie", 49, "construction worker", 40000f),
                new Employee("Darren", 38, "writer", 50000f),
                new Employee("Nancy", 29, "developer", 75000f),
        };
        List<Employee> employees = new ArrayList<>(Arrays.asList(employeesArr));
        
        Float totalDeveloperSalaries = employees
        		.stream()
        		.filter(e -> e.jobTitle == "developer")
        		.map(d -> d.salary)
        		.reduce(0f,  (acc, x) -> acc + x);
        
        Long numberOfDevelopers = employees
                .stream()
                .filter((employee) -> employee.jobTitle == "developer")
                .collect(Collectors.counting());
        
        Float averageDeveloperSalary = totalDeveloperSalaries / numberOfDevelopers;
        System.out.println(averageDeveloperSalary);
	}

	
	@Test
	// 6. Parallel Streams:
	void testParallel() {
        String[] wordsArray = {"hello", "apple", "functional", "programming", "is", "cool"};
        List<String> words = new ArrayList<>(Arrays.asList(wordsArray));
        
        List<String> processedWords = words
                .parallelStream()
                .map((word) -> {
                    System.out.println("Uppercasing " + word);
                    return word.toUpperCase();
                })
                .map((word) -> {
                    System.out.println("Adding exclamation point to " + word);
                    return word + "!";
                })
                .collect(Collectors.toList());
        System.out.println(processedWords);
	}

	
	@Test
	// 7. More Test for Stream:
	void testMoreMapReduce() {
        Person[] peopleArr = {
                new Person("Brandon", 23),
                new Person("Hank", 43),
                new Person("Jenna", 33),
                new Person("Veronica", 56),
                new Person("Jack", 27),
        };
        List<Person> people = new ArrayList<>(Arrays.asList(peopleArr));


        // 1. Get a list of all the people's name
        List<String> peopleNames = people
                .stream()
                .map((person) -> person.name)
                .collect(Collectors.toList());

        System.out.println(peopleNames);






        Car[] carsArr = {
                new Car("Chevy", "red", 45000f),
                new Car("Ford", "blue", 23000f),
                new Car("Toyota", "grey", 14000f),
                new Car("Lamborghini", "blue", 150000f),
                new Car("Renault", "blue", 150000f),
        };
        List<Car> cars = new ArrayList<>(Arrays.asList(carsArr));

        
        // 2. Get a list of all the blue cars
        List<Car> blueCars = cars
                .stream()
                .filter((car) -> car.color == "blue")
                .collect(Collectors.toList());

        System.out.println(blueCars);


        
        


        Employee[] employeesArr = {
                new Employee("John", 34, "developer", 80000f),
                new Employee("Hannah", 24, "developer", 95000f),
                new Employee("Bart", 50, "sales executive", 100000f),
                new Employee("Sophie", 49, "construction worker", 40000f),
                new Employee("Darren", 38, "writer", 50000f),
                new Employee("Nancy", 29, "developer", 75000f),
        };
        List<Employee> employees = new ArrayList<>(Arrays.asList(employeesArr));

        // 3. Find the sum of all of the employees' salaries
        Float sumOfSalaries = employees
                .stream()
                .map((employee) -> employee.salary)
                .reduce(0f, (acc, x) -> acc + x);

        System.out.println(sumOfSalaries);	
	}
	
	
	
	static class Employee {
        public final String name;
        public final Integer age;
        public final String jobTitle;
        public final Float salary;

        public Employee(String name, Integer age, String jobTitle, Float salary) {
            this.name = name;
            this.age = age;
            this.jobTitle = jobTitle;
            this.salary = salary;
        }
    }
	
    static class Person {
        public final String name;
        public final Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }

    static class Car {
        public final String make;
        public final String color;
        public final Float price;

        public Car(String make, String color, Float price) {
            this.make = make;
            this.color = color;
            this.price = price;
        }
    }
}
