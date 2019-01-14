package java8.OCP.C4_FunctionalProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Stream_withPrimitives_operations {
	public static void main(String[] args) {

		// Using Primitive Optional with Primitive Streams
		// OptionalDouble, OptionalInt and OptionalLong
		List<Integer> list = Arrays.asList(1,2,3);
		IntStream ints = list.stream().flatMapToInt(x -> IntStream.of(x));
		OptionalDouble avg = ints.average();
		System.out.println(avg.getAsDouble());
		
		// min, max, avg, count and sum
		LongStream longs = LongStream.of(5, 10);
		long sum = longs.sum();
		System.out.println(sum); // 15
		
		DoubleStream doubles = DoubleStream.generate(() -> Math.PI).limit(10);
		OptionalDouble min = doubles.min(); // runs infinitely
		System.out.println(min.getAsDouble()); // 15
		
		// Summarizing Statistics.
		// Five operations in one (min, max, avg, count and sum) when visiting a stream once time.
		IntStream ints2 = IntStream.of(5, 10, 15, 20, 25, 30);
		IntSummaryStatistics stats = ints2.summaryStatistics();
		if (stats.getCount() == 0) throw new RuntimeException();
		System.out.println(stats.getMax() - stats.getMin());
		
		// Functional Interfaces for Primitives
		// BooleanSupplier is a separate type.
		BooleanSupplier b1 = () -> true;
		BooleanSupplier b2 = () -> Math.random() > .5;
		System.out.println(b1.getAsBoolean());
		System.out.println(b2.getAsBoolean());
		
		// Common functional interfaces for primitives
		/* 
		 - Generics are gone from some of the interfaces, since the type name tells us what primitive
		   type is involved. In other cases, such as IntFunction, only the return type generic is needed.
		 - The single abstract method is often, but not always, renamed to reflect the primitive
			type involved.
		 - BiConsumer, BiPredicate, and BiFunction are not develop in java8.
		 */
		DoubleSupplier ds = () -> 1;
		IntSupplier is = () -> 1;
		LongSupplier ls = () -> 1;
		ds.getAsDouble();
		is.getAsInt();
		ls.getAsLong();
		
		DoubleConsumer dc = a -> System.out.println(a); // or System.out::println
		dc.accept(1); // the same method for IntConsumer and LongConsumer
		
		DoublePredicate dp = a -> a > 3;
		dp.test(4); // the same method for IntPredicate and LongPredicate
		
		DoubleFunction<Integer> df = s -> 5; // Only R as T is a double.
		df.apply(1); // the same method for IntFunction and LongFunction
		
		DoubleUnaryOperator duo = a -> a + 5; // UnaryOperator has the same type that is double
		DoubleBinaryOperator dbo = (a,b) -> a + b; // BinaryOperator has the same type that is double
		IntUnaryOperator iuo = a -> a + 5; 
		IntBinaryOperator ibo = (a,b) -> a + b; 
		LongUnaryOperator luo = a -> a + 5; 
		LongBinaryOperator lbo = (a,b) -> a + b; 
		duo.applyAsDouble(3);
		dbo.applyAsDouble(5, 9);
		iuo.applyAsInt(5);
		ibo.applyAsInt(5, 9);
		luo.applyAsLong(5);
		lbo.applyAsLong(5, 9);
		
		// Primitive-specific functional interfaces
		ToDoubleFunction<String> tdf = s -> Double.valueOf(s);
		ToDoubleBiFunction<String, Integer> tdbf = (s,i) -> Double.valueOf(s) + i;
		ToIntFunction<String> tif = s -> Integer.valueOf(s);
		tdf.applyAsDouble("567");
		tdbf.applyAsDouble("567",567);
		tif.applyAsInt("567");
		
		// Linking Streams.
		List<String> cats = new ArrayList<>();
		cats.add("Annie");
		cats.add("Ripley");		
		Stream<String> stream = cats.stream();
		cats.add("KC");
		System.out.println(stream.count()); // output is 3, so both are linked
		


	}
}
