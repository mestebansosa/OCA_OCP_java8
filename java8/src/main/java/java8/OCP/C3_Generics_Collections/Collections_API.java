package java8.OCP.C3_Generics_Collections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Collections_API {
	
    public static void main(String[] args) {
    	// Collections are: List, Set and Queue
    	//    List: ArrayList, LinkedList, Stack, Vector
    	//    Set: HashSet, TreeSet
    	//    Queue: ArrayDeque, LinkedList
    	
    	// Map: HashMap, TreeMap (Does not inherits from Collection)

    	// all data structures allow nulls except these:
    	//   TreeSet: no null elements, ArrayDeque: no null elements
    	
    	// Common methods: add(), remove(), isEmpty(), size(), clear(), contains()
    	
		// Big O notation lets you compare the "order of magnitude of performance" rather
		// than the exact performance. It also assumes the worst-case response time.
		// O(1) - constant time. It doesn’t matter how large the collection is, the
		//   answer will always take the same time to return.
		// O(log n) - logarithmic time: A logarithm is a mathematical function that grows
		//   much more slowly than the data size. The point is that logarithmic time is
		//   better than linear time.
		// O(n) - linear time: The performance will grow linearly with respect to the size
		//   of the collection.
		// O(n2) - n squared time: Code that has nested loops where each loop goes through
		//   the data takes n squared time.    	
    	
		// List: an 'insertion' ordered collection that allows duplicate entries.
		// LinkedList is special because it implements both List and Queue. The main
		//  benefits of a LinkedList are that you can access, add, and remove from the
		//  beginning and end of the list in constant time O(1). The tradeoff is that dealing
		//  with an arbitrary index takes linear time. This makes a LinkedList a good
		//  choice when you’ll be using it as Queue.
    	// There are also two old implementations:
    	//   Vector (ArrayList) and Stack (ArrayDeque). Both are thread-safe, so very slow
    	
		// List: an 'insertion' ordered collection that can contain duplicate entries.
    	List<String> list = new ArrayList<>();
    	list.add("SD"); // [SD]
    	list.add(0, "NY"); // [NY,SD]
    	list.set(1, "FL"); // [NY,FL]
    	list.remove("NY"); // [FL]
    	list.remove(0); // []

    	for (String string: list) {
    		System.out.println(string);
    	}
    	
    	Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			String string = iter.next();
			System.out.println(string);
		}
    	
		// Set. You use a set when you don’t want to allow duplicate entries.
		/*
		A HashSet stores its elements in a hash table. This means that it uses the hashCode()
		method of the objects to retrieve them more efficiently.
		The main benefit is that adding elements and checking if an element is in the set both
		have constant time O(1). The tradeoff is that you lose the order in which you inserted the
		elements. HashSet the most common set.
		A TreeSet stores its elements in a sorted tree structure. The main benefit is that the set
		is always in sorted order. The tradeoff is that adding and checking if an element is present
		are both O(log n).	
		*/	
		Set<Integer> setInt = new HashSet<>();
		boolean b1 = setInt.add(66);
		b1 = setInt.add(66); // false
		
		// NavigableSet methods: lower,higher,floor,ceiling
		NavigableSet<Integer> set = new TreeSet<>();
		for (int i = 1; i <= 20; i++) set.add(i);
		// Just remember that lower and higher elements do not include the target element.
		System.out.println(set.lower(10)); // 9
		System.out.println(set.floor(10)); // 10
		System.out.println(set.ceiling(20)); // 20
		System.out.println(set.higher(20)); // null
		
		// ArrayDeque
		// The main benefit of an ArrayDeque is that it is more efficient than a LinkedList 
		//     due to LinkedList implements both List and Queue.
		// offer: add to the back of the queue. This is a FiFo.
		// push: add to the front of the queue. This is a LiFo
		// poll: removes and returns next element
		// peek: returns next element
		// Queue. FiFo. offer/poll/peek. It is like a line of people
		// Stack. LiFo. push/poll/peek.  It is like a stack of plates.
		Queue<Integer> queue = new ArrayDeque<>();
		System.out.println(queue.offer(10)); // true
		System.out.println(queue.offer(4)); // true
		System.out.println(queue.peek()); // 10
		System.out.println(queue.poll()); // 10
		System.out.println(queue.poll()); // 4
		System.out.println(queue.peek()); // null
		
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		stack.push(10);
		stack.push(4);
		System.out.println(stack.peek()); // 4
		System.out.println(stack.poll()); // 4
		System.out.println(stack.poll()); // 10
		System.out.println(stack.peek()); // null
		
    }			
}
