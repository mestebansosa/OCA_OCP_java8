package java8.OCP.C3_Generics_Collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class RemovingConditionally {
    public static void main(String[] args) {
    	// Java8. removeIf on Lists
    	List<String> places = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");
    	List<String> list = new ArrayList<>();
    	list.add("hola");
    	list.add("Adios");
    	list.removeIf(s -> s.startsWith("A"));
    	System.out.println(list);
    	// Arrays.asList returns a Fixed List. Add() and remove() method are not allowed, but set() yes
    	places.removeIf(s -> s.startsWith("A")); 
    	// places.removeIf(s -> s.startsWith("B")); // throws UnsupportedOperationnException
    	System.out.println(places);
    	
    	// Java8. replaceAll on Lists
    	places.replaceAll(s -> s.toUpperCase());
    	System.out.println(places);
    	
    	UnaryOperator<String> unaryOperator = s -> s.toLowerCase();
    	places.replaceAll(unaryOperator);
    	System.out.println(places);
    	
    	List<Integer> listInt = Arrays.asList(1, 2, 3);
    	listInt.replaceAll(x -> x*2);
    	System.out.println(listInt); // [2, 4, 6]
    	
    	// forEach. Looping through a collection.
		List<String> cats = Arrays.asList("Annie", "Ripley");
		for (String cat : cats)
			System.out.println(cat);
		
		cats.forEach(s -> System.out.println(s));
		cats.forEach(System.out::println);
    }			
}
