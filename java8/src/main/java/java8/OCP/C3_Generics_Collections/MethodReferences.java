package java8.OCP.C3_Generics_Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodReferences {
    public static void main(String[] args) {
    	// Method references are a way to make the code shorter by reducing some of the code that
    	// can be inferred and simply mentioning the name of the method.
    	/* 
    	Remember that :: is like lambdas, and it is typically used for deferred execution.
    	There are four formats for method references:
    	- Static methods
    	- Instance methods on a particular instance
    	- Instance methods on an instance to be determined at runtime
    	- Constructor reference
    	 */
    	
    	// static methods
    	Consumer<List<Integer>> methodRef1 = Collections::sort;
    	Consumer<List<Integer>> lambda1 = l -> Collections.sort(l);
		// How does Java know that we want to call the version that omits the comparator? 
    	// Consumer takes only one parameter.
    	
    	// Instance methods on a particular instance
		String str = "abc";
		Predicate<String> methodRef2 = str::startsWith;
		Predicate<String> lambda2 = s -> str.startsWith(s);
		
		// Instance methods on an instance to be determined at runtime
		Predicate<String> methodRef3 = String::isEmpty;
		Predicate<String> lambda3 = s -> s.isEmpty();
		
		// Constructor reference
		Supplier<ArrayList> methodRef4 = ArrayList::new;
		Supplier<ArrayList> lambda4 = () -> new ArrayList();

    }			
}
