package java8.OCP.C4_FunctionalProgramming;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Collecting_results {
	public static void main(String[] args) {
		Supplier<Stream<String>> ohMy = () -> Stream.of("lions", "tigers", "bears");

		// collect() terminal operation use Collectors
		
		// Predefined collectors: grouping/partitioning collectors
		/*
		averagingDouble(ToDoubleFunction f)
		counting() Counts number of elements
		groupingBy(Function f, Supplier s, Collector dc) 
		   Creates a map grouping by the specified function with the type and downstream collector
		joining(CharSequence cs) Creates a single String using cs as a delimiter between elements.		

		maxBy(Comparator c)
		minBy(Comparator c) Finds the largest/smallest elements
		mapping(Function f, Collector dc) Adds another level of collectors
		partitioningBy(Predicate p, Collector dc) 
		  Creates a map grouping by the specified predicate with the optional downstream collector
		
		summarizingDouble(ToDoubleFunction f) Calculates average, min, max, and so on
		summingDouble(ToDoubleFunction f) Calculates the sum for our three core primitive types
		toList()
		toSet() Creates an arbitrary type of list or set
		toCollection(Supplier s) Creates a Collection of the specified type
		toMap(Function k, Function v)

        toMap(Function k, Function v, BinaryOperator m, Supplier s)
          Creates a map using functions to map the keys, values, an merge function, and a type
		*/
		
		System.out.println(ohMy.get().collect(Collectors.joining(", "))); // lions, tigers, bears
				
		System.out.println(ohMy.get().filter(s -> s.startsWith("t"))
				.collect(Collectors.toCollection(TreeSet::new))); // [tigers]
		
		// A Collector goes into collect(). It doesn't do anything on its own.
		Double result = ohMy.get().collect(Collectors.averagingInt(String::length));
		System.out.println(result); // 5.333333333333333
		
		// Collecting into Maps
		// When creating a map, you need to specify two functions. The first function tells the
		// collector how to create the key. The second function tells the collector how to create 
		// the value. In our example, we use the
		Map<String, Integer> map = ohMy.get().collect(Collectors.toMap(s -> s, String::length));
		System.out.println(map); // {lions=5, bears=5, tigers=6}
		// You can rewrite s -> s as Function.identity().
		Map<String, Integer> map2 = ohMy.get().collect(Collectors.toMap(Function.identity(), String::length));
		System.out.println(map2); // {lions=5, bears=5, tigers=6}
		
		// create a comma-separated String with the animal names.
		Map<Integer, String> map3 = ohMy.get().collect(Collectors.toMap(
		String::length, k -> k, (s1, s2) -> s1 + "," + s2));
		System.out.println(map3); // {5=lions,bears, 6=tigers}
		System.out.println(map3.getClass()); // class. java.util.HashMap
		
		// the code return a TreeMap instead. No problem. Add a constructor reference as a parameter:
		TreeMap<Integer, String> treeMap = ohMy.get()
				.collect(Collectors.toMap(String::length, k -> k, (s1, s2) -> s1 + "," + s2, TreeMap::new));
		System.out.println(treeMap); // // {5=lions,bears, 6=tigers}
		System.out.println(treeMap.getClass()); // class. java.util.TreeMap
		
		// Collecting Using groupingBy, partitioningBy, and Mapping
		// Collecting Using groupingBy
		Map<Integer, List<String>> mapOhMy = ohMy.get().collect(Collectors.groupingBy(String::length));
		System.out.println(mapOhMy); // {5=[lions, bears], 6=[tigers]}
		
		Map<Integer, Set<String>> mapSet = ohMy.get()
				.collect(Collectors.groupingBy(String::length, Collectors.toSet()));
		System.out.println(mapSet); // {5=[lions, bears], 6=[tigers]}
		
		// We can even change the type of Map returned through yet another parameter:
		TreeMap<Integer, Set<String>> treeMapSet = ohMy.get()
				.collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()));
		System.out.println(treeMapSet); // {5=[lions, bears], 6=[tigers]}
		
		// Collecting Using partitioningBy.
		// Partitioning is a special case of grouping. With partitioning, there are only two possible
		// groups—true and false. Partitioning is like splitting a list into two parts.
		Map<Boolean, List<String>> mapPartition = ohMy.get().collect(Collectors.partitioningBy(s -> s.length() <= 5));
		System.out.println(mapPartition); // {false=[tigers], true=[lions, bears]}

		Map<Integer, Long> mapCounting = ohMy.get()
				.collect(Collectors.groupingBy(String::length, Collectors.counting()));
		System.out.println(mapCounting); // {5=2, 6=1}
		
		// Collecting Using Mapping
		// lets us go down a level and add another collector.
		// we wanted to get the first letter of the first animal alphabetically of each length.
		Map<Integer, Optional<Character>> mapMinBy = ohMy.get().collect(
				Collectors.groupingBy(
				String::length,
				Collectors.mapping(s -> s.charAt(0),
				Collectors.minBy(Comparator.naturalOrder()))));
				System.out.println(mapMinBy); // {5=Optional[b], 6=Optional[t]}	
		// Note: if you have import static to make code shorter, 
		// there will be groupingBy, mapping, and minBy without Collectors.
		
				
		// Debugging Complicated Generics
		// When working with collect(), there are often many levels of generics, making compiler
	    // errors unreadable. Here are three useful techniques for dealing with this situation:
		// - Start over with a simple statement and keep adding to it. 
		// - Extract parts of the statement into separate statements. 
		// - Use generic wildcards for the return type of the final statement, for example, Map<?,?>.
	}
}
