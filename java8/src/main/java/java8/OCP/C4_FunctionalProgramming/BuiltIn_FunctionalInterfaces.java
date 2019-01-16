package java8.OCP.C4_FunctionalProgramming;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BuiltIn_FunctionalInterfaces {
	
	@FunctionalInterface
	public interface Sprint {
		void sprint(String animal); // Abstract method: public abstract void sprint(String animal)
		// public void sprint2(Animal animal); Have to have only one abstract method.

		default void doYes() {
		}; // Can have default methods, as they have implementation, so no abstract

		static void doUtil() {
		}; // Can have static methods

		int count = 0; // Members: public static final int count = 0;
	}
	
	// Creating your own functional interface. No problem.
	interface TriFunction<T, U, V, R> {
		R apply(T t, U u, V v);
	}

	public static class ExceptionCaseStudy {
		private static List<String> create() throws IOException {
			throw new IOException();
		}
		
		private static List<String> createSafe() {
			try {
				return ExceptionCaseStudy.create();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
	
	public static void main(String[] args) {
		/* 
		Common functional interfaces
		FunctionalInterfaces Parameters ReturnType SingleAbstractMethod
	    -------------------- ---------- ---------- --------------------
		Supplier<T>          0          T          get
		Consumer<T>          1 (T)      void       accept
		BiConsumer<T, U>     2 (T, U)   void       accept
		Predicate<T>         1 (T)      boolean    test
		BiPredicate<T, U>    2 (T, U)   boolean    test
		Function<T, R>       1 (T)      R          apply
		BiFunction<T, U, R>  2 (T, U)   R          apply
		UnaryOperator<T>     1 (T)      T          apply
		BinaryOperator<T>    2 (T, T)   T          apply
		
		Runnable             0          void       run
		*/
		
		// A Supplier is used when you want to generate or supply (return) values
		// without taking any input.
		Supplier<LocalDate> s1 = LocalDate::now;
		Supplier<LocalDate> s2 = () -> LocalDate.now();
		LocalDate d1 = s1.get();
		LocalDate d2 = s2.get();
		System.out.println(d1);
		System.out.println(d2);
		// Often used when constructing new objects.
		Supplier<StringBuilder> s3 = () -> new StringBuilder();
		System.out.println(s3.get());
		Supplier<ArrayList<String>> s4 = ArrayList<String>::new;
		ArrayList<String> a1 = s4.get();	
		
		// You use a Bi/Consumer when you want to do something with 1 or 2 parameters
		// but not return anything
		Consumer<String> c1 = System.out::println;
		Consumer<String> c2 = x -> System.out.println(x);
		c1.accept("Annie");
		c2.accept("Annie");

		Map<String, Integer> map = new HashMap<>();
		BiConsumer<String, Integer> b1 = map::put;
		BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);
		b1.accept("chicken", 7);
		b2.accept("chick", 1);
		System.out.println(map);

		// Bi/Predicate is often used when filtering or matching.
		// Predicate is commonly understood to be a boolean-valued function 'P: X? {true, false}'
		Predicate<String> p1 = String::isEmpty;
		Predicate<String> p2 = x -> x.isEmpty();
		System.out.println(p1.test(""));
		System.out.println(p2.test(""));

		BiPredicate<String, String> biPred1 = String::startsWith;
		BiPredicate<String, String> biPred2 = (string, prefix) -> string.startsWith(prefix);
		System.out.println(biPred1.test("chicken", "chick"));
		System.out.println(biPred2.test("chicken", "chick")); // note that test returns a boolean

		// more methods in functional interfaces. Default methods for chaining
		Predicate<String> egg = s -> s.contains("egg");
		Predicate<String> brown = s -> s.contains("brown");
		Predicate<String> brownEggs = egg.and(brown);
		Predicate<String> otherEggs = egg.and(brown.negate());

		// Bi/Function is responsible for turning one parameter into a value of a
		// potentially different type and returning it.
		Function<String, Integer> f1 = String::length;
		Function<String, Integer> f2 = x -> x.length();
		System.out.println(f1.apply("cluck")); // 5
		System.out.println(f2.apply("cluck")); // 5
		BiFunction<String, String, String> biFunc1 = String::concat;
		BiFunction<String, String, String> biFuuc2 = (string, toAdd) -> string.concat(toAdd);

		// UnaryOperator and BinaryOperator are a special cases of a function. They
		// require all type parameters to be the same type. A UnaryOperator transforms
		// its value into one of the same type.
		UnaryOperator<String> u1 = String::toUpperCase;
		UnaryOperator<String> u2 = x -> x.toUpperCase();
		System.out.println(u1.apply("chirp")); // CHIRP
		System.out.println(u2.apply("chirp"));

		BinaryOperator<String> bOper1 = String::concat;
		BinaryOperator<String> bOper2 = (string, toAdd) -> string.concat(toAdd);
		System.out.println(bOper1.apply("baby ", "chick")); // baby chick
		System.out.println(bOper2.apply("baby ", "chick")); // baby chick
		
		// identify the error
		// Function<List<String>> ex1 = x -> x.get(0); // DOES NOT COMPILE, has no return type
		// UnaryOperator<Long> ex2 = (Long l) -> 3.14; // DOES NOT COMIPLE, return double.
		// Predicate ex4 = String::isEmpty; // DOES NOT COMPILE, where is the type <>
		
		
		// Checked Exceptions and Functional Interfaces
		// The functional interfaces do not declare checked exceptions. This is normally OK.
		// However, it is a problem when working with methods that declare checked exceptions.
		// Both does not compile
		//   ExceptionCaseStudy.create().stream().count();	
		//   Supplier<List<String>> s = ExceptionCaseStudy::create; // DOES NOT COMPILE
		// Two solutions:
		Supplier<List<String>> s = () -> {
			try {
				return ExceptionCaseStudy.create();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		};
		// second solution
		// Now we can use the safe wrapper in our Supplier without issue:
		Supplier<List<String>> safe = ExceptionCaseStudy::createSafe;
	}
}
