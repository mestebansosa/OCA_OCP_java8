package java8.OCP.C2_DesignPattern_Principles;

import java.util.function.Predicate;

public class FunctionalInterface_Lambda {
	
	// defining a Functional interface
	@FunctionalInterface
	public interface Sprint {
		public void sprint(String animal);
	}
	
	// The following three FI are valid.
	public interface Run extends Sprint {}

	public interface SprintFaster extends Sprint {
		public void sprint(String animal);
	}

	public interface Skip extends Sprint {
		public default int getHopCount(String kangaroo) {return 10;}
		public static void skip(int speed) {}
	}
	
	// Predicate FI
	private static void print(String animal, Predicate<String> trait) {
		if (trait.test(animal))
			System.out.println(animal);
	}
	
	public static void main(String[] args) {
		// Java defines a functional interface as an interface that contains a single abstract method.
		// Functional interfaces are used as the basis for lambda expressions.
		// A lambda expression is a block of code that gets passed around, like an anonymous method.
		
		// @FunctionalInterface Annotation is not required, but it is recommended.
		// another developer may treat any interface you create that has only one method as
		print(new String("fish"), a -> a.charAt(0) == 'f');
		print(new String("fish"), a -> ! a.isEmpty());
		
		// Lambda uses deferred execution.
		// A lambda expression is a block of code that gets passed around, like an anonymous method.
		// It can be consumed by a functional interface whose abstract method has the
		// same number of parameters and compatible data types.
		// syntax: parameters, arrow, body
		// The parentheses () in parameters, can be omitted in a lambda expression if there is exactly
		// one input parameter and the type is not explicitly stated in the expression.
		// In the body, with {} is mandatory the return and the ;
		/*
			() -> new String()  // consumed by a functional interface with no parameter and return a String object
			d -> {return d.quack();} // with {} is mandatory the return and the ;
			(Duck d) -> d.quack() // with parameter type is mandatory the ()
			(Animal a, Duck d) -> d.quack()	
		*/
	
		// parameters
		/*
			() -> true // 0 parameters
			a -> {return a.startsWith("test");} // 1 parameter
			(String a) -> a.startsWith("test") // 1 parameter
			(int x) -> {} // 1 parameter
			(int y) -> {return;} // 1 parameter
			(a, b) -> a.startsWith("test") // 2 parameters
			(String a, String b) -> a.startsWith("test") // 2 parameters
		*/

		// Invalid lambdas. Parameters with ()
		/*
			Duck d -> d.quack() // DOES NOT COMPILE
			a,d -> d.quack() // DOES NOT COMPILE
			Animal a, Duck d -> d.quack() // DOES NOT COMPILE
	
			a, b -> a.startsWith("test") // DOES NOT COMPILE, (a, b) -> a.startsWith("test")
			c -> return 10; // DOES NOT COMPILE, c -> { return 10; }
			a -> { return a.startsWith("test") } // DOES NOT COMPILE, a -> { return a.startsWith("test"); }
	
			(int y, z) -> {int x=1; return y+10; } // DOES NOT COMPILE
			(String s, z) -> { return s.length()+z; } // DOES NOT COMPILE
			(a, Animal b, c) -> a.getName() // DOES NOT COMPILE	
		*/

		// Java doesn’t allow us to re‐declare a local variable
		/*
			(a, b) -> { int a = 0; return 5;} // DOES NOT COMPILE
		*/

		// From Chapter 4.
		// Lambda expressions can access static variables, instance variables, 
		// effectively final method parameters, and effectively final local variables.
		// A lambda can’t access private variables in another class. 
	
	}	
	
}
