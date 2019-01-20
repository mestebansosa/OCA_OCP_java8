package java8.OCP.C7_Concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreams {
	// Serial stream: the results are ordered, with only one entry being processed at a time.
	// Parallel stream: capable of processing results concurrently, using multiple threads.
	// By default, the number of threads available in a parallel stream is related to
	// the number of available CPUs in your environment.
	
	public int processRecord(int input) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		return input + 1;
	}

	public long processAllDataSerial(List<Integer> data) {
		return data.stream().map(a -> processRecord(a)).count();
	}

	public long processAllDataParallel(List<Integer> data) {
		return data.parallelStream().map(a -> processRecord(a)).count();
	}

	public static void main(String[] args) {
		Supplier<Stream<Integer>> serialStreamIntegers = () -> Arrays.asList(1, 2, 3, 4, 5, 6).stream();
		Supplier<Stream<Integer>> parallelStreamIntegers = () -> Arrays.asList(1, 2, 3, 4, 5, 6).parallelStream();
		
		// Creating Parallel Streams
		// - parallel(), You just call it on an existing stream. It is an intermediate operation.
		Stream<Integer> serialStream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
		Stream<Integer> parallelStream = serialStream.parallel();

		// parallelStream(). The Collection interface includes a method parallelStream()
		// that can be called on any collection and returns a parallel stream.
		Stream<Integer> parallelStream2 = Arrays.asList(1, 2, 3, 4, 5, 6).parallelStream();

		// Processing Tasks in Parallel
		serialStreamIntegers.get().forEach(s -> System.out.print(s + " ")); // ordered
		// forEach() operation on a parallel stream is equivalent to
		// submitting multiple Runnable lambda expressions to a pooled thread executor
		parallelStreamIntegers.get().forEach(s -> System.out.print(s + " ")); // disorder
		// forEachOrder() forces order (cost of performance) in both, serial and parallel 
		parallelStreamIntegers.get().forEachOrdered(s -> System.out.print(s + " ")); // ordered
		System.out.println();

		// Using ParallelStreams: example of serial and parallel
		ParallelStreams calculator = new ParallelStreams();
		// Define the data
		List<Integer> data = new ArrayList<Integer>();
		for(int i=0; i<1000; i++) data.add(i);
		// Process the data serial
		long start = System.currentTimeMillis();
		System.out.println(calculator.processAllDataSerial(data));
		double time = (System.currentTimeMillis() - start)/1000.0;
		System.out.println("\nTasks completed in serial: "+time+" seconds");
		// Process the data parallel
		start = System.currentTimeMillis();
		System.out.println(calculator.processAllDataParallel(data));
		time = (System.currentTimeMillis() - start)/1000.0;
		System.out.println("\nTasks completed in parallel: "+time+" seconds");
		
		// When using streams, you should avoid any lambda expressions that can produce side effects.
		
		// Avoiding Stateful Operations.
		// It strongly recommended avoiding Stateful Operations when using Parallel Streams. 
		// A stateful lambda expression is one whose result depends on any state that
		// might change during the execution of a pipeline.
		// Any time you are working with a collection with a parallel stream, it is
		// recommended that you us a concurrent collection.
		List<Integer> data2 = Collections.synchronizedList(new ArrayList<>());
		parallelStreamIntegers.get()
				.map(i -> {data2.add(i);return i;}) // AVOID STATEFUL LAMBDA EXPRESSIONS!
				.forEachOrdered(i -> System.out.print(i + " "));
		System.out.println();
		for (Integer e : data2) {
			System.out.print(e + " ");
		}
		
		// Processing Parallel Reductions
		// Performing Order-Based Tasks
		System.out.println();
		System.out.println(serialStreamIntegers.get().findAny().get()); // Consistently outputs 1
		System.out.println(parallelStreamIntegers.get().findAny().get()); // Any value
		// calling skip(5).limit(2).findFirst() will return the same result on ordered serial and parallel.
		
		// Creating unordered Streams
		serialStreamIntegers.get().unordered();
		// This method does not actually reorder the elements; it just tells the JVM that if an
		// order-based stream operation is applied, the order can be ignored
		// on parallel streams, the results can greatly improve performance:
		serialStreamIntegers.get().unordered().parallel();
		
		// Combining Results with reduce()
		// the reduce() method parameters: identity, accumulator, and the third parameter the combiner
		// Requirements for Parallel Reduction with reduce():
		//    identity must be be defined such that for all elements in the stream u, 
		//        combiner.apply(identity, u) is equal to u.
		//    accumulator operator op must be associative and stateless
		//    combiner operator must be associative and stateless and compatible with the identity
		// For parallel is recommended to use the three-argument version or reduce()
		System.out.println(parallelStreamIntegers.get()
				.reduce(0,(a,b) -> (a-b))); // NOT AN ASSOCIATIVE ACCUMULATOR
		
		// Combing Results with collect()
		// Idem like with reduce()
		// Using the One-Argument collect() Method
		Stream<String> stream = Stream.of("w", "o", "l", "f").parallel();
		Set<String> set = stream.collect(Collectors.toSet());
		System.out.println(set); // [f, w, l, o]
		// Requirements for Parallel Reduction with collect():
		//    The stream is parallel.
		//    The parameter of the collect operation has the 
		//         Collector.Characteristics.CONCURRENT
		//    Either the stream is unordered, or the collector has the 
		//         Collector.Characteristics.UNORDERED.
		// The Collectors class includes two methods for retrieving collectors
		// that are both UNORDERED and CONCURRENT, Collectors.toConcurrentMap() and
		// Collectors.groupingByConcurrent(), and performing parallel reductions efficiently
		Stream<String> ohMy = Stream.of("lions", "tigers", "bears").parallel();
		ConcurrentMap<Integer, String> map = ohMy
				.collect(Collectors.toConcurrentMap(String::length, k -> k, (s1, s2) -> s1 + "," + s2));
		System.out.println(map); // {5=lions,bears, 6=tigers}
		System.out.println(map.getClass()); // java.util.concurrent.ConcurrentHashMap
		
		ohMy = Stream.of("lions", "tigers", "bears").parallel();
		ConcurrentMap<Integer, List<String>> map2 = ohMy.collect(
		Collectors.groupingByConcurrent(String::length));
		System.out.println(map2); // {5=[lions, bears], 6=[tigers]}
	}
}
