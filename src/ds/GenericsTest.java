package ds;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


class GenericsTest {
	
	// 1. Generic Method
	public static <T> List<T> arrayToList(T[] array, List<T> list) {
		for (T obj : array) {
			list.add(obj);
		}
		return list;
	}
	
	// 2. Varargs
	// only 1 method, which can take any # of args
	public static void printShoppingList(String... items) {
		System.out.println("Shopping List:");
		for (int i  = 0; i < items.length; i++) {
			System.out.println(i+1 + ": " + items[i]);
		} 
	}

	
	// 3. substitution principle
	class Building {
	    public int numberOfRooms = 7;

	    @Override
	    public String toString() {
	        return ("building");
	    }

	    public int getNumberOfRooms() {
	        return numberOfRooms;
	    }

	    public void setNumberOfRooms(int numberOfRooms) {
	        this.numberOfRooms = numberOfRooms;
	    }
	}
	class Office extends Building {        
	    
	    @Override
	    public String toString() {
	        return ("office");
	    }
    }
	class House extends Building {
	    public int numberOfRooms = 10;

	    @Override
	    public String toString() {
	        return ("house");
	    }
	            
	    public int getNumberOfRooms() {
	        return numberOfRooms;
	    }
	    
	    public void setNumberOfRooms(int numberOfRooms) {
	        this.numberOfRooms = numberOfRooms;
	    }
	}
	
	static void build(Building building) {
		System.out.println("Constructing a new " + building.toString());
	}
	
	// Substitution usage in the test() => Only one way WORKS
	static void printBuildings(List<Building> buildings) {
		for (int i = 0; i < buildings.size(); i++) {
			System.out.println(i + 1 + ": " + buildings.get(i).toString());
		}
	}
	
	
    // we can use wildcards: (List<? extends Building> buildings)
    // dynamic binding
	// A. In variable: extends
	static void printBuildings2(List<? extends Building> buildings) {
		for (int i = 0; i < buildings.size(); i++) {
			System.out.println(i + 1 + ": " + buildings.get(i).toString());
		}
	}
	
    // B. Out variable: super
    static void addHouseToList(List<? super House> buildings) {
        //buildings.add(new House()); // House or its sub-class (child class)
        System.out.println();
    }
	
	@Test
	void test() {
		//fail("Not yet implemented");
		
		final Character [] charArray = {'h', 'e', 'l', 'l', 'o'};
		final Integer[] intArray = {1,2,3,4,5};
		
		// 1. Generic Method
		List<Character> charList = arrayToList(charArray, new ArrayList<>())  ;
		List<Integer> intList = arrayToList(intArray, new ArrayList<>());
		
		System.out.println(intList.get(0));
		System.out.println(charList.get(0));
		
		
		// 2. Varargs
		printShoppingList("Bread", "Milk", "Eggs", "Bananas");
		

		// 3. Substitution principle
        Building building = new Building();        
        Office office = new Office();
        build(building);
        build(office);
        
        List<Building> buildings = new ArrayList();
        buildings.add(building);
        buildings.add(office);
        printBuildings(buildings);
        
        List<Office> offices = new ArrayList();
        offices.add(office);
        offices.add(new Office());
        //printBuildings(offices);  // Compile ERROR
        printBuildings2(offices); // Wildcard WORKS
        
        
        // 4. Wildcards (extends vs super)
        // List of houses
        List<House> houses = new ArrayList();
        houses.add(new House());
        houses.add(new House());
        printBuildings2(houses);  // IN
        
        addHouseToList(houses);	 	// OUT: house     
        addHouseToList(buildings);   // OUT: buildings, which is super of House     
		
	}

	
	
}
