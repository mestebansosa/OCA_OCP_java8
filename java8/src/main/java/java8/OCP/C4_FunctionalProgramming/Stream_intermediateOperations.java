package java8.OCP.C4_FunctionalProgramming;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stream_intermediateOperations {
	public static void main(String[] args) {

		// A Stream should be operated on (invoking an intermediate or terminal stream
		// operation) only once. A Stream implementation may throw IllegalStateException
		// (the stream has already been operated upon or closed) if it detects that the
		// Stream is being reused.
		// Solution:
		// https://www.baeldung.com/java-stream-operated-upon-or-closed-exception
		Supplier<Stream<String>> streamSupplierAnimals = () -> Stream.of("monkey", "gorilla", "bonobo");
		Supplier<Stream<String>> streamSupplierWolf = () -> Stream.of("w", "o", "l", "f");
		Supplier<Stream<Integer>> infinite = () -> Stream.iterate(1, x -> x + 1);


		// Unlike a terminal operation, intermediate operations deal with infinite
		// streams simply by returning an infinite stream. Since elements are produced
		// only as needed, this works fine.
		// Intermediate operations return a Stream.
		
		// filter(). returns a Stream with elements that match a given expression.
		streamSupplierAnimals.get().filter(x -> x.startsWith("m")).forEach(System.out::println); // monkey

		// distint(). returns a stream with duplicate values removed.
		Stream<String> s = Stream.of("duck", "duck", "duck", "goose");
		s.distinct().forEach(System.out::println); // duckgoose

		// limit() and skip(). make a Stream smaller.
		// Stream<Integer> s2 = Stream.iterate(1, n -> n + 1);
		Stream.iterate(1, n -> n + 1).skip(5).limit(2).forEach(System.out::print); // 67

		// map(). transforms the data. One-to-one mapping from the elements in the
		// stream to the elements of the next step in the stream.
		streamSupplierAnimals.get().map(String::length).forEach(System.out::print); // 676
		streamSupplierAnimals.get().map(x -> x.length()).forEach(System.out::print); // 676

		// flatMap(). takes each element in the stream and makes any elements it
		// contains top-level elements in a single stream. This is helpful when you want
		// to remove empty elements from a stream or you want to combine a stream of
		// lists
		List<String> zero = Arrays.asList();
		List<String> one = Arrays.asList("Bonobo");
		List<String> two = Arrays.asList("Mama Gorilla", "Baby Gorilla");
		Stream<List<String>> animals = Stream.of(zero, one, two);
		animals.flatMap(l -> l.stream()).forEach(System.out::println);

		// sorted(). Java uses natural ordering unless we specify a comparator
		Stream<String> sort = Stream.of("brown-", "bear-");
		sort.sorted().forEach(System.out::print); // bear-brown-
		Stream<String> sort2 = Stream.of("brown-", "bear-");
		// sort2.sorted(Comparator.reverseOrder); // DOES NOT COMPILE. No ()
		sort2.sorted(Comparator.reverseOrder()).forEach(System.out::print); // brown-bear-

		// peek(). Use it to output the contents of the stream without changing it, as it goes by. Debug.
		Stream<String> stream = Stream.of("black bear", "brown bear", "grizzly");
		long count = stream.filter(bear -> bear.startsWith("g")).peek(System.out::println).count(); // grizzly
		System.out.println(count); // 1
		
		// The pipeline.
		// we wanted to get the first two names alphabetically that are four characters long.
		List<String> list = Arrays.asList("Toby", "Anna", "Leroy", "Alex");
		list.stream().sorted().filter(x -> x.length() == 4).limit(2).forEach(System.out::println);
		
		// It actually hangs until you kill the program or it		
/*		Stream.generate(() -> "Elsa").filter(n -> n.length() == 4).peek(System.out::println).sorted().limit(2)
				.forEach(System.out::println);*/		 
		
		// This one prints Elsa twice.
		Stream.generate(() -> "Elsa").filter(n -> n.length() == 4).limit(2).sorted().forEach(System.out::println);

		// This one hangs as well until we kill the program.
/*		Stream.generate(() -> "Olaf Lazisson").filter(n -> n.length() == 4).limit(2).sorted()
				.forEach(System.out::println);
*/		
		//Stream<Integer> infinite = Stream.iterate(1, x -> x + 1);
		infinite.get().limit(5)
		.filter(x -> x % 2 == 1)
		.forEach(System.out::print); // 135
		
		infinite.get().limit(5)
		.peek(System.out::print)
		.filter(x -> x % 2 == 1)
		.forEach(System.out::print); // 11233455
		
		infinite.get().filter(x -> x % 2 == 1)
		.peek(System.out::print)
		.limit(5)
		.forEach(System.out::print); // 1133557799.
		
		// Printing a stream. Four ways.
		s.forEach(System.out::println);
		System.out.println(s.collect(Collectors.toList()));
		s.peek(System.out::println).count();
		s.limit(5).forEach(System.out::println);		
	}
}
