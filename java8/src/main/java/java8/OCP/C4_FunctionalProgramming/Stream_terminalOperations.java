package java8.OCP.C4_FunctionalProgramming;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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


		/*
		 Terminal stream operations
			Method 			WhatHappensforInfiniteStreams	ReturnValue	Reduction
			allMatch()		Sometimes terminates 			boolean 	No
			anyMatch()
			noneMatch()
				
			collect() 		Does not terminate 				Varies 		Yes
			count() 		Does not terminate 				long 		Yes
			findAny()		Terminates 						Optional<T>	No
			findFirst()
				
			forEach() 		Does not terminate 				void 		No
			min()/max() 	Does not terminate 				Optional<T>	Yes
			reduce()	 	Does not terminate 				Varies 		Yes
		 */
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
		// Reduction. 3 ways (parameters: identity, accumulator and combiner)
		// Sum, min, max, average, and string concatenation are special cases of reduction
		// 		Integer sum = integers.reduce(0, (a, b) -> a+b);
		// 		Integer sum = integers.reduce(0, Integer::sum);
		// reduce() way 1, with identity and accumulator
		System.out.println(streamSupplierWolf.get().reduce("", (string, toAdd) -> string + toAdd)); // wolf
		System.out.println(streamSupplierWolf.get().reduce("", (string, toAdd) -> string.concat(toAdd))); // wolf
		System.out.println(streamSupplierWolf.get().reduce("", String::concat)); // wolf

		Stream<Integer> stream2 = Stream.of(3, 5, 6);
		System.out.println(stream2.reduce(1, (a, b) -> a * b)); // 90

		// reduce() way 2, ony with accumulator. When you don’t specify an identity, 
		//   an Optional is returned because there might not be any data.
		BinaryOperator<Integer> op = (a, b) -> a * b;
		Stream<Integer> empty = Stream.empty();
		Stream<Integer> oneElement = Stream.of(3);
		Stream<Integer> threeElements = Stream.of(3, 5, 6);
		empty.reduce(op).ifPresent(System.out::print); // no output
		oneElement.reduce(op).ifPresent(System.out::print); // 3
		threeElements.reduce(op).ifPresent(System.out::print); // 90

		// reduce() way 3, processing in parallel, with identity, accumulator and combiner
		BinaryOperator<Integer> op2 = (a, b) -> a * b;
		Stream<Integer> stream3 = Stream.of(3, 5, 6);
		System.out.println(stream3.reduce(1, op2, op2)); // 90

		// collect(). Two ways. Parameters: supplier, accumulator and combiner
		//   Reduction. Hang on an infinite stream. Returns Varies
		// Mutable Reduction. We use the same mutable object while accumulating so it is
		// more efficient than a regular reduction
		// collect() way 1
		StringBuilder stringBuilder = streamSupplierWolf.get().collect(StringBuilder::new, StringBuilder::append,
				StringBuilder::append);
		System.out.println(stringBuilder); // wolf
		TreeSet<String> treeSet = streamSupplierWolf.get().collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
		System.out.println(treeSet); // [f, l, o, w]

		// collect() way 2. Using Collectors (implement Collector Interface).
		// A Collector goes into collect(). It doesn't do anything on its own. See Collection_results.java
		System.out.println(streamSupplierWolf.get().collect(Collectors.toCollection(TreeSet::new))); // [f, l, o, w]
		System.out.println(streamSupplierWolf.get().collect(Collectors.toSet())); // [f, w, l, o]

	}
}
