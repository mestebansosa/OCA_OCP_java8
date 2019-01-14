package java8.OCP.C3_Generics_Collections;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bounds {

	public <T> T method1(List<? extends T> list) {
		return list.get(0);
	}
	
	void method4(List<? super String> list) { // normal use.
	}
	
	/*
	<T> <? extends T> method2(List<? extends T> list) { // DOES NOT COMPILE
		return list.get(0);
	}
	*/
	
	// method2() does not compile because the return type isn’t actually a type.
	// public <T> <? extends T> method2(List<? extends T> list) { // DOES NOT COMPILE
	// 	return list.get(0);
	//	}
		
	//tricky: take care, there is no ?
	// <B extends A> B method3(List<B> list) {
	//	return new B(); // DOES NOT COMPILE
	//	}
	
	// <X> void method5(List<X super B> list) {} // DOES NOT COMPILE
		
		
    public static void main(String[] args) {
    	// A bounded parameter type is a generic type that specifies a bound for the generic.
    	// A wildcard generic type is an unknown generic type represented with a question mark
    	// (?). You can use generic wildcards in three ways:
    	
    	// Unbounded wildcards. You want to specify that any type is OK with you.
    	// List<String> cannot be assigned to List<Object>
    	// List<Object> l = new ArrayList<String>(); DOES NOT COMPILE
    	List<?> unbounded = new ArrayList<String>(); 
    	// unbounded.add("Pepe"); Is inmutable.
    	
    	// Upper-Bounded Wildcards. 
    	// ArrayList<Number> list = new ArrayList<Integer>(); // DOES NOT COMPILE
    	// It says, any class that extends Number
    	List<? extends Number> upperBounded = new ArrayList<Integer>();
    	// upperBounded.add(5); Is inmutable.
    	// List<? extends Integer> upperBounded = new ArrayList<Number>(); // DOES NOT COMPILE
    	
		// Lower-Bounded Wildcards. The list will be a list of Integer objects or a list
		// of some objects that are superclass of Integer
    	List<? super Integer> lowerBounded = new ArrayList<Number>();
    	lowerBounded.add(5); // Is mutable.
    	
    	List<? super IOException> exceptions = new ArrayList<Exception>();
    	// exceptions.add(new Exception()); // DOES NOT COMPILE
    	exceptions.add(new IOException());
    	exceptions.add(new FileNotFoundException());   
    	
    	// List<?> list6 = new ArrayList<? extends String>(); // DOES NOT COMPILE, undefined type for <?>
    	
    }			
}
