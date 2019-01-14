package java8.OCP.C4_FunctionalProgramming;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Stream_creation {
	public static void main(String[] args) {

		// A stream in Java is a sequence of data. A stream pipeline is the operations that run on a stream to produce a result.
		/*
		 There are three parts to a stream pipeline:
         - Source: Where the stream comes from.
         - Intermediate operations: Transforms the stream into another one. There can be as few
           or as many intermediate operations as you’d like. Since streams use lazy evaluation, the
           intermediate operations do not run until the terminal operation runs.
         - Terminal operation: Actually produces a result. Since streams can be used only once, 
           the stream is no longer valid after a terminal operation completes.
		 */
		
		// Threre are few ways to create finite streams
		Stream<String> empty = Stream.empty(); // count = 0
		Stream<Integer> singleElement = Stream.of(1); // count = 1
		Stream<Integer> fromArray = Stream.of(1, 2, 3); // count = 3		
		List<String> list = Arrays.asList("a", "b", "c");
		Stream<String> fromList = list.stream();
		Stream<String> fromListParallel = list.parallelStream();  // isn't worth for small streams.
		
		// creation infinite streams
		Stream<Double> randoms = Stream.generate(Math::random);
		Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);	
		// randoms.forEach(System.out::println); Will print forever
	}
}
