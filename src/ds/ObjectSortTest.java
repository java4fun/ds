package ds;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;


class ObjectSortTest {
	class Player implements Comparator<Player> {
		String name;
		int score;
		public Player(String name, int score) {
			this.name = name;
			this.score = score;
		}
		
		@Override
		public int compare(Player a, Player b) {

			if (a.score == b.score) {
				return a.name.compareTo(b.name);
			}
			return b.score - a.score;  // reverse order by score
		}
		
	}

	class Circle implements Comparable<Circle> {
		
		public double radius;
		
		public Circle(double r) {
			this.radius = r;
		}
		
		public Circle() {
			this(1);
		}

		@Override
		public int compareTo(Circle o) {
			if (this.radius > o.radius) return 1;
			if (this.radius == o.radius) return 0;
			return -1;
		}
		
		@Override
		public String toString() {
			return "This Circle is of radius: " + this.radius;
		}
		
	}
	
	@Test
	void testSortCircle() {
		//fail("Not yet implemented");
	}

	@Test
	// 2D Array of games of (luck, importance), 
	// find Max luck balance given K loss of important games
	void testSortArray() {
		int[][] contests = 
				{
				{6,1},
				{5,0},
				{4,1},
				{2,0}
				};
		System.out.println(luckBalance(2, contests));
	}
	
	static int luckBalance (int k, int[][] contests) {
		int luckBalance = 0;
		Arrays.sort(contests, new Comparator<int[]> () {
			@Override
			public int compare(int[] a, int[] b) {
				return -1 * Integer.compare(a[0], b[0]);
			}
			
		});
		
		for (int i = 0; i < contests.length; i++) {
			int luck = contests[i][0];
			int importance = contests[i][1];
			
			if(importance == 1 && k > 0) {
				k--; luckBalance += luck;        // lose to get luck point
			} else if (importance == 1 && k == 0) {
				luckBalance -= luck;  // has to win, can't lose
			}
			if (importance == 0) luckBalance += luck;  // lose to get luck point
		}
		
		return luckBalance;
	}
}
