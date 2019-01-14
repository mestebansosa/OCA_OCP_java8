package java8.OCP.C4_FunctionalProgramming;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stream_terminalOperations {
	public static void main(String[] args) {

		// A Stream should be operated on (invoking an intermediate or terminal stream
		// operation) only once. A Stream implementation may throw IllegalStateException
		// (the stream has already been operated upon or closed) if it detects that the
		// Stream is being reused.
		// Solution:
		// https://www.baeldung.com/java-stream-operated-upon-or-closed-exception
		Supplier<Stream<String>> streamSupplierAnimals = () -> Stream.of("monkey", "gorilla", "bonobo");
		Supplier<Stream<String>> streamSupplierWolf = () -> Stream.of("w", "o", "l", "f");
		Supplier<Stream<String>> ohMy = () -> Stream.of("lions", "tigers", "bears");


		// count(). 
		//   Reduction. Hang on an infinite stream. Returns long.
		Stream<Integer> fromArray = Stream.of(1, 2, 3); // count = 2
		System.out.println(fromArray.count()); // 3

		// min() and max(). 
		//   Reduction. Hang on an infinite stream. Returns Optional<T>
		Optional<String> min = streamSupplierAnimals.get().min((s1, s2) -> s1.length() - s2.length());
		min.ifPresent(System.out::println); // monkey

		// Since the stream is empty, the comparator is never called and no value is
		// present in the Optional.
		Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
		System.out.println(minEmpty.isPresent()); // false

		// findAny() and findFirst(). 
		//   No reduction. Works on an infinite stream. Returns Optional<T>
		// Return an element of the stream unless the stream is empty. 
		// If the stream is empty, they return an empty Optional.
		// findAny() is useful when you are working with a parallel stream.
		streamSupplierAnimals.get().findAny().ifPresent(System.out::println); // monkey
		Stream<String> infinite = Stream.generate(() -> "chimp");
		infinite.findAny().ifPresent(System.out::println); // chimp

		// allMatch() , anyMatch() and noneMatch(). 
		//   No reduction. Maybe or not work on an infinite stream. Returns boolean
		List<String> list = Arrays.asList("monkey", "2", "chimp");
		Stream<String> infinite2 = Stream.generate(() -> "chimp");
		Predicate<String> pred = x -> Character.isLetter(x.charAt(0));
		System.out.println(list.stream().anyMatch(pred)); // true
		System.out.println(list.stream().allMatch(pred)); // false
		System.out.println(list.stream().noneMatch(pred)); // false
		System.out.println(infinite2.anyMatch(pred)); // true

		// forEach(). 
		//   No reduction. Hang on an infinite stream. Returns void
		streamSupplierAnimals.get().forEach(System.out::println); // monkeygorillabonobo

		// reduce()
		//   Reduction. Hang on an infinite stream. Returns Varies
		// Reduction. 3 ways
		// Sum, min, max, average, and string concatenation are special cases of reduction
		// reduce() way 1, with identity and accumulator
		System.out.println(streamSupplierWolf.get().reduce("", (string, toAdd) -> string + toAdd)); // wolf
		System.out.println(streamSupplierWolf.get().reduce("", (string, toAdd) -> string.concat(toAdd))); // wolf
		System.out.println(streamSupplierWolf.get().reduce("", String::concat)); // wolf

		Stream<Integer> stream2 = Stream.of(3, 5, 6);
		System.out.println(stream2.reduce(1, (a, b) -> a * b)); // 90

		// reduce() way 2, ony with accumulator so returns optional
		BinaryOperator<Integer> op = (a, b) -> a * b;
		Stream<Integer> empty = Stream.empty();
		Stream<Integer> oneElement = Stream.of(3);
		Stream<Integer> threeElements = Stream.of(3, 5, 6);
		empty.reduce(op).ifPresent(System.out::print); // no output
		oneElement.reduce(op).ifPresent(System.out::print); // 3
		threeElements.reduce(op).ifPresent(System.out::print); // 90

		// reduce() way 3 parallel, with identity, accumulator and combiner
		BinaryOperator<Integer> op2 = (a, b) -> a * b;
		Stream<Integer> stream3 = Stream.of(3, 5, 6);
		System.out.println(stream3.reduce(1, op2, op2)); // 90

		// collect().
		//   Reduction. Hang on an infinite stream. Returns Varies
		// Mutable Reduction. We use the same mutable object while accumulating so it is
		// more efficient than a regular reduction
		// collect() way 1
		StringBuilder stringBuilder = streamSupplierWolf.get().collect(StringBuilder::new, StringBuilder::append,
				StringBuilder::append);
		System.out.println(stringBuilder); // wolf
		TreeSet<String> treeSet = streamSupplierWolf.get().collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
		System.out.println(treeSet); // [f, l, o, w]

		// collect() way 2. Using Collectors (implement Collector Interface)
		System.out.println(streamSupplierWolf.get().collect(Collectors.toCollection(TreeSet::new))); // [f, l, o, w]
		System.out.println(streamSupplierWolf.get().collect(Collectors.toSet())); // [f, w, l, o]

		// grouping/partitioning collectors
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
