package java8.OCP.C3_Generics_Collections;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;

public class Generics {
	
	// Generic Class
	// Behind the scenes, the compiler replaces all references to T in Crate with Object.
	public class Crate<T> {
		private T contents;

		public T emptyCrate() {
			return contents;
		}

		public void packCrate(T contents) {
			this.contents = contents;
		}
	}
	
	// Generic Methods
	public <T> Crate<T> ship(T t) {
		System.out.println("Preparing " + t);
		return new Crate<T>();
	}	
	
	public static <T> T identity(T t) { return t; }
	// public static T noGood(T t) { return t; } // DOES NOT COMPILE

	// Generic Interfaces
	public interface Shippable<T> {
		void ship(T t);
	}
	// There are three ways a class can approach implementing this interface:
	// The first is to specify the generic type in the class.
	class ShippableRobotCrate implements Shippable<Robot> {
		public void ship(Robot t) {
		}
	}

	// The next way is to create a generic class.
	class ShippableAbstractCrate<U> implements Shippable<U> {
		public void ship(U t) {
		}
	}
	
	// The final way is to not use generics at all. This is the old way of writing
	// code. It generates a compiler warning about Shippable being a raw type, but
	// it does compile.
	class ShippableCrate implements Shippable {
		public void ship(Object t) {
		}
	}
	
    public static void main(String[] args) {
    	// without generics, it easy to get a ClassCastException
    	// legacy code is the older code.
    	// Java presents a compiler warning for older code. Note: Recompile with -Xlint:unchecked for details. 	
    	List names = new ArrayList();
    	names.add(new StringBuilder("Webby"));
    	try {
        	String name = (String) names.get(0); // class cast exception here    
        	
        	java.util.List numbers = new java.util.ArrayList();
        	numbers.add(5);
        	// int result = numbers.get(0); // DOES NOT COMPILE cannot convert Object to int
        	int result = (int) numbers.get(0); 
    	}
    	catch(ClassCastException c) {
    		System.out.println("ClassCastException");
    	}
    	
    	// Naming conventions for Generics:
    	// E: element, K: key, V: value, N: number, T: type, S,U,V: more types. 

    	/*
    	Here are the things that you can’t do with generics.
    	- Call the constructor. new T() is not allowed because at runtime it would be new Object().
    	- Create an array of that static type.
    	- Call instanceof. At runtime List<Integer> and List<String> look the same to Java, Objects.
    	- Use a primitive type as a generic type parameter.
    	- Create a static variable as a generic type parameter.    	
    	*/
    	
    }			
}
