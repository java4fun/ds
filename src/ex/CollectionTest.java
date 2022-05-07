package ex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import org.junit.jupiter.api.Test;

class CollectionTest {

	static Collection<String> collection = Arrays.asList("red", "orange", "yellow", "green",
	        "blue", "indigo", "violet");
	
    static ArrayList<String> groceries = new ArrayList<>(Arrays.asList("milk", 
            "bread", "cheese"));
    static List<String> groceries2 = new ArrayList<>(Arrays.asList("sugar", 
            "flour", "baking soda"));
	static List colorList = Arrays.asList(collection.toArray());
	
	
	
	@Test
	void testIterator() {
		//fail("Not yet implemented");
		Iterator<String> it = collection.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	
	@Test
	void testListIterator() {
		//fail("Not yet implemented");
		ListIterator<String> it = colorList.listIterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	@Test
	void testQueue() {
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= 10; i++) {
			queue.add(i);
		}

		for (Iterator iterator = queue.iterator(); iterator.hasNext();) {
			System.out.println((Integer) iterator.next());
			
		}
	}	
}
