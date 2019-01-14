package java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTesting {
	Map<String, Integer> wordCounts = new HashMap<>();
	
	Predicate<Integer> atLeast5 = x -> x > 5;
	
	BinaryOperator<Long> addLongs = (x, y) -> x + y;	
	Long p = addLongs.apply(5L,4L);
	
	public static String join(String[] arrayOfString){
	    return Arrays.asList(arrayOfString)
	      .stream()
	      //.map(...)
	      .collect(Collectors.joining(","));
	}	
    public static void main(String[] args) {
        final Comparator<String> comp = (p1, p2) -> Integer.compare( p1.length(), p2.length());
    	String aux = "esto es una prueba que me hicieron estos, en no recuerdo el nombre, para ver si sabia programar";
    	System.out.println("Max: " + Stream.of(aux.split(" ")).max(comp).get());
    	System.out.println("Min: " + Stream.of(aux.split(" ")).min((p1, p2) -> Integer.compare(p1.length(), p2.length())).get());
    	System.out.println("Uppercase: " + Stream.of(aux.split(" ")).map(p1 -> p1.toUpperCase()).collect(Collectors.toList()));
    	List<String> list = Arrays.asList("A", "B", "HELLO");
    	list.stream().findFirst();
    }
	
	
	
	
}
