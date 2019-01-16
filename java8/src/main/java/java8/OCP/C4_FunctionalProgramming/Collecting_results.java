package java8.OCP.C4_FunctionalProgramming;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
		
		// A Collector goes into collect(). It doesn't do anything on its own.
		Double result = ohMy.get().collect(Collectors.averagingInt(String::length));
		System.out.println(result); // 5.333333333333333
		
		// Collecting into Maps
		Map<String, Integer> map = ohMy.get().collect(Collectors.toMap(s -> s, String::length));
		System.out.println(map); // {lions=5, bears=5, tigers=6}
		
		// Collecting Using Grouping, Partitioning, and Mapping
		Map<Integer, List<String>> mapOhMy = ohMy.get().collect(
		Collectors.groupingBy(String::length));
		System.out.println(mapOhMy); // {5=[lions, bears], 6=[tigers]}
		
		Map<Integer, Set<String>> mapSet = ohMy.get().collect(
		Collectors.groupingBy(String::length, Collectors.toSet()));
		System.out.println(mapSet); // {5=[lions, bears], 6=[tigers]}
		
		Map<Boolean, List<String>> mapPartition = ohMy.get().collect(
		Collectors.partitioningBy(s -> s.length() <= 5));
		System.out.println(mapPartition); // {false=[tigers], true=[lions, bears]}
		
		Map<Integer, Long> mapCounting = ohMy.get().collect(Collectors.groupingBy(
		String::length, Collectors.counting()));
		System.out.println(mapCounting); // {5=2, 6=1}
		
		// we wanted to get the first letter of the first animal alphabetically of each length.
		Map<Integer, Optional<Character>> mapMinBy = ohMy.get().collect(
				Collectors.groupingBy(
				String::length,
				Collectors.mapping(s -> s.charAt(0),
				Collectors.minBy(Comparator.naturalOrder()))));
				System.out.println(mapMinBy); // {5=Optional[b], 6=Optional[t]}		
	}
}
