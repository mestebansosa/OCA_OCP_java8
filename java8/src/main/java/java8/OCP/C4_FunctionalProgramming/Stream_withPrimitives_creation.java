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
		// doing the same but converting the Stream<Integer> to an IntStream and 
		// asked the IntStream to calculate the sum for us.
		Stream<Integer> stream2 = Stream.of(1, 2, 3);
		System.out.println(stream2.mapToInt(x -> x).sum());
		
		// with primitives
		// IntStream: Used for the primitive types int, short, byte, and char
		// LongStream: Used for the primitive type long
		// DoubleStream: Used for the primitive types double and float
		IntStream stream3 = IntStream.of(1, 2, 3);
		System.out.println(stream3.sum());
		
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
		/*
		 Mapping methods between types of streams
			Source			ToCreate	ToCreate		ToCreate	ToCreate
			StreamClass		Stream		DoubleStream	IntStream	LongStream
			--------		------		-----------		----------	---------
			Stream 			map 		mapToDouble 	mapToInt 	mapToLong
			DoubleStream	mapToObj 	map 			mapToInt 	mapToLong
			IntStream 		mapToObj 	mapToDouble 	map 		mapToLong
			LongStream 		mapToObj 	mapToDouble 	mapToInt 	map

		 Function parameters when mapping between types of streams
			Source			ToCreate		ToCreate			ToCreate			ToCreate
			StreamClass		Stream			DoubleStream		IntStream			LongStream
			--------		------			-----------			----------			---------
			Stream 			Function 		ToDoubleFunction 	ToIntFunction 		ToLongFunction
			DoubleStream	DoubleFunction 	DoubleUnaryOperator	DoubleToIntFunction DoubleToLongFunction
			IntStream 		IntFunction 	IntToDoubleFunction IntUnaryOperator	IntToLongFunction
			LongStream 		LongFunction 	LongToDoubleFunctio LongToIntFunction 	LongUnaryOperator
	
		 */
		Stream<String> objStream = Stream.of("penguin", "fish");
		IntStream intStream = objStream.mapToInt(s -> s.length()); // Note: it uses mapToInt(ToIntFunction)		

		// You can also create a primitive stream from a Stream using 
		//  flatMapToInt(), flatMapToDouble(), or flatMapToLong().
		List<Integer> list = Arrays.asList(1,2,3);
		IntStream ints = list.stream().flatMapToInt(x -> IntStream.of(x));
		System.out.println(ints.average().getAsDouble());
	}
}
