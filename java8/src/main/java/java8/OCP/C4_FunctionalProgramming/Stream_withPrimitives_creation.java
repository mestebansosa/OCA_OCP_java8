package java8.OCP.C4_FunctionalProgramming;

import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Stream_withPrimitives_creation {
	public static void main(String[] args) {

		// with no primitives
		Stream<Integer> stream = Stream.of(1, 2, 3);
		System.out.println(stream.reduce(0, (s, n) -> s + n));
		
		// with primitives
		// IntStream: Used for the primitive types int, short, byte, and char
		// LongStream: Used for the primitive type long
		// DoubleStream: Used for the primitive types double and float
		Stream<Integer> stream2 = Stream.of(1, 2, 3);
		System.out.println(stream2.mapToInt(x -> x).sum());
		
		// Create finite stream
		DoubleStream empty = DoubleStream.empty();
		DoubleStream oneValue = DoubleStream.of(3.14);
		DoubleStream varargs = DoubleStream.of(1.0, 1.1, 1.2);
		oneValue.forEach(System.out::println);
		varargs.forEach(System.out::println);
		
		// Create infinite stream
		DoubleStream random = DoubleStream.generate(Math::random);
		// DoubleStream random2 = new Random().doubles(); This should be the same.
		DoubleStream fractions = DoubleStream.iterate(.5, d -> d / 2);
		random.limit(3).forEach(System.out::println);
		fractions.limit(3).forEach(System.out::println);
		
		// we wanted a stream with the numbers from 1 through 5
		IntStream count = IntStream.iterate(1, n -> n+1).limit(5);
		count.forEach(System.out::println);
		IntStream range = IntStream.range(1, 6);
		range.forEach(System.out::println);
		IntStream rangeClosed = IntStream.rangeClosed(1, 5);
		rangeClosed.forEach(System.out::println);
		
		// The final way to create a primitive stream is by mapping from another stream type.
		Stream<String> objStream = Stream.of("penguin", "fish");
		IntStream intStream = objStream.mapToInt(s -> s.length()); // Note: it uses mapTo*() as parameter		
		// using flatMapTo*()
		List<Integer> list = Arrays.asList(1,2,3);
		IntStream ints = list.stream().flatMapToInt(x -> IntStream.of(x));
		System.out.println(ints.average().getAsDouble());
	}
}
