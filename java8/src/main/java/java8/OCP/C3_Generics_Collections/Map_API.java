package java8.OCP.C3_Generics_Collections;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Map_API {
    public static void main(String[] args) {
    	// maps are: HashMap, HashTable, TreeMap
    	// HashMap O(1) constant time, uses hashCode(). You lose the order of insertion.
    	// TreeMap O(logN) log time. You do not lose the order of insertion.

    	// all data structures allow nulls except these:
    	//      TreeMap: no null keys, HashTable: no null keys nor values
    	Map<String, String> favorites = new HashMap<>();
    	favorites.put("Jenny", "Bus Tour");    	
    	favorites.put("Jenny", "Tram");
    	System.out.println(favorites); // {Jenny=Tram}
    	
    	// Using New Java 8 Map APIs
		// putIfAbsent: If the specified key is not already associated with a value (or
		// is mapped to null) associates it with the given value and returns null, else
		// returns the current value.
    	favorites.put("Jenny", "Bus Tour");
    	favorites.put("Tom", null);
    	System.out.println(favorites.putIfAbsent("Jenny", "Tram")); // returns Bus Tour
    	System.out.println(favorites.putIfAbsent("Sam", "Tram")); // returns null
    	System.out.println(favorites.putIfAbsent("Tom", "Tram")); // returns nul
    	System.out.println(favorites); // {Tom=Tram, Jenny=Bus Tour, Sam=Tram}
    	// handle simple replacements.
    	
    	// The merge() method allows adding logic to the problem of what to choose.
    	BiFunction<String, String, String> mapper = (v1, v2) -> v1.length() > v2.length() ? v1: v2;
    	BiFunction<String, String, String> mapperNull = (v1, v2) -> null;
    	favorites.clear();
    	favorites.put("Jenny", "Bus Tour");
    	favorites.put("Tom", "Tram");
    	favorites.put("Sam", null);
    	favorites.put("SamToBeDeleted", "toBeDeleted");
    	
    	String jenny = favorites.merge("Jenny", "Skyride", mapper); // if "Bus Tour" > skyride does not change
    	String tom = favorites.merge("Tom", "Skyride", mapper); // Tom's value changed to Skyride
    	String sam1 = favorites.merge("Sam", "Skyride", mapper); // here the mapper is not called as it is not needed.
    	String sam2 = favorites.merge("SamToBeDeleted", "Skyride", mapperNull); // here the element is removed.
    	
    	System.out.println(favorites); // {Tom=Skyride, Jenny=Bus Tour, Sam=Skyride}
    	System.out.println(jenny); // Bus Tour
    	System.out.println(tom); // Skyride   
    	System.out.println(sam1); // Skyride   
    	System.out.println(sam2); // null   
    	
    	// computeIfPresent() calls the BiFunction if the requested key is found.
    	Map<String, Integer> counts = new HashMap<>();
    	counts.put("Paco", 1);
    	
    	BiFunction<String, Integer, Integer> biFunction = (k, v) -> v + 1;
    	Integer paco = counts.computeIfPresent("Paco", biFunction);
    	Integer sam = counts.computeIfPresent("Sam", biFunction);
    	System.out.println(counts); // {Paco=2}
    	System.out.println(paco); // 2
    	System.out.println(sam); // null

    	// For computeIfAbsent(), the functional interface runs only when the key isn’t present or is null:
    	counts.clear();
    	counts.put("Jenny", 15);
    	counts.put("Tom", null);
    	Function<String, Integer> function = (k) -> 1;
    	counts.computeIfAbsent("Jenny", function); // 15
    	counts.computeIfAbsent("Sam", function); // 1
    	counts.computeIfAbsent("Tom", function); // 1
    	
    	// Choosing the right collection type
    	/*
    	Which class do you choose when					Answer (single
    	you want ____________							best type) Reason
    	
    	to pick the top zoo map off a stack of maps		ArrayDeque
    	
    	to sell tickets to people in the order in 
    	which they appear in line  and tell them 
    	their position in line							LinkedList
    	
    	to write down the first names of all of the 
    	elephants (The elephants do not have unique 
    	first names.)									ArrayList 
    	
    	to list the unique animals that
    	you want to see at the zoo today				HashSet 
    	
    	to list the unique animals that you want to 
    	see at the zoo today in alphabetical order		TreeSet
    	
    	to look up animals based on a unique identifier	HashMap
    	 */
    	
    }			
}
