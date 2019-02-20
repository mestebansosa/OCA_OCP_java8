package java8.OCP.C3_Generics_Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Comparator_Comparable {

	public static class Duck implements Comparable<Duck> {
		private String name;
		private int id;

		public Duck(String name, int id) {
			this.name = name;
			this.id = id;
		}

		public int getId() { 
			return id;
		}

		public String toString() { // use readable output
			return name + " " + id;
		}

		/*
		 There are three rules to know:
         0 is returned when the current object is equal to the argument to compareTo().
         <0 is returned when the current object is smaller than the argument to compareTo().
         >0 is returned when the current object is larger than the argument to compareTo().
		 */
		public int compareTo(Duck d) {
			// return name.compareTo(d.name); // call String's compareTo
			return id - d.id; // ascending
		}
		
		// You are strongly encouraged to make your Comparable classes consistent with equals()
		public boolean equals(Object obj) {
			if (!(obj instanceof Duck)) {
				return false;
			}
			Duck other = (Duck) obj;
			return this.id == other.id;
		}
	}
	
	
	// Comparator with multiple fields
	public class Squirrel {
		private int weight;
		private String species;

		public Squirrel(String theSpecies) {
			if (theSpecies == null)
				throw new IllegalArgumentException();
			species = theSpecies;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public String getSpecies() {
			return species;
		}
	}

	// normal way of doing
	public class MultiFieldComparator implements Comparator<Squirrel> {
		public int compare(Squirrel s1, Squirrel s2) {
			int result = s1.getSpecies().compareTo(s2.getSpecies());
			if (result != 0)
				return result;
			return s1.getWeight() - s2.getWeight();
		}
	}
	
	// Using the Default and static methods of Comparator.
	// Multiple fields		
	public class ChainingComparator implements Comparator<Squirrel> {
		public int compare(Squirrel s1, Squirrel s2) {
			Comparator<Squirrel> c = Comparator.comparing(s -> s.getSpecies());
			c = c.thenComparingInt(s -> s.getWeight());
			return c.compare(s1, s2);
		}
	}

	public static void main(String[] args) {
		// Numbers sort before letters and uppercase letters sort before lowercase letters.

		// Comparable
		// It is a functional interface but its does not make any sense using lambdas.
		/*
		 	public interface Comparable<T> { public int compareTo(T o); }
		 */
		List<Duck> ducks = new ArrayList<>();
		ducks.add(new Duck("Quack", 1));
		ducks.add(new Duck("Puddles", 2));
		Collections.sort(ducks); // sort by name
		System.out.println(ducks); // [Puddles, Quack]
		
		// Comparator
		// Sometimes you want to sort an object that did not implement Comparable, or
		// you want to sort objects in different ways at different times.
		// They let you separate sort order from the object to be sorted.
		// Comparator is a Functional interface and in this case it is better with lambdas.
		
		// Note: interface can be instantiated in case we use anonymous inner class in the code.
		Comparator<Duck> byId = new Comparator<Duck>() {
			public int compare(Duck d1, Duck d2) {
				return d1.getId() - d2.getId();
			}
		};
		// with a lambda expression
		Comparator<Duck> byId2 = (d1, d2) -> d1.getId() - d2.getId();
		
		// Searching and Sorting
		// expects the Comparable.compareTo()
		Collections.sort(ducks); 
		Collections.binarySearch(ducks, new Duck("Quack", 1));
		// No Comparable.compareTo(), using Comparator.compare()
		Collections.sort(ducks, byId); 
		Collections.binarySearch(ducks, new Duck("Quack", 1), byId2);
		
	}
}
