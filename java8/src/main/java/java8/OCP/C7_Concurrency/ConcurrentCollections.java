package java8.OCP.C7_Concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrentCollections {

	// using the concurrent collections is extremely convenient in practice. 
	// It also prevents us from introducing mistakes in own custom implementation. 
	// The concurrent collections often include performance enhancements.
	// ensuring that concurrent threads do not throw ConcurrentModificationException
		
	public static void main(String[] args) {
		// Understanding Memory consistency error.
		// A memory consistency error occurs when two threads have inconsistent views of
		// what should be the same data. Conceptually,
		// When two threads try to modify the same non-concurrent collection, the JVM may
		// throw a ConcurrentModificationException at runtime.
		Map<String, Object> foodData0 = new HashMap<>();
		foodData0.put("penguin", 1);
		foodData0.put("flamingo", 2);
		// for (String key : foodData0.keySet())
		//	foodData0.remove(key); // thows exception
		
		// Here The iterator created by keySet() is updated as soon as an object is removed from the Map.
		Map<String, Object> foodData = new ConcurrentHashMap<>();
		ConcurrentMap<String, Object> foodData2 = new ConcurrentHashMap<>();
		// it is considered a good practice to instantiate a concurrent collection but
		// pass it around using a non-concurrent interface whenever possible.
		foodData.put("penguin", 1);
		foodData.put("flamingo", 2);
		for (String key : foodData.keySet())
			foodData.remove(key); 
		
		// Concurrent collection classes.
		
		// ConcurrentHashMap
		// ConcurrentLinkedQueue
		// ConcurrentLinkedDeque
		Queue<Integer> queue = new ConcurrentLinkedQueue<>();
		queue.offer(31);
		System.out.println(queue.peek());
		System.out.println(queue.poll());
		
		Deque<Integer> deque = new ConcurrentLinkedDeque<>();
		deque.offer(10);
		deque.push(4);
		System.out.println(deque.peek());
		System.out.println(deque.pop());
		
		// Understanding Blocking Queues
		// LinkedBlockingQueue. inherits all methods from Queue, and add waiting methods.
		// BlockingQueue methods
		//   offer(E e, long timeout, TimeUnit unit)
		//   poll(long timeout, TimeUnit unit)
		// Implements both Queue and BlockingQueue
		try {
			BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
			blockingQueue.offer(39);
			blockingQueue.offer(3, 4, TimeUnit.SECONDS);
			System.out.println(blockingQueue.poll());
			System.out.println(blockingQueue.poll(10, TimeUnit.MILLISECONDS));
		} catch (InterruptedException e) {
			// Handle interruption
		}		
		
		// LinkedBlockingDeque (Deque means: double ended queue)
		// BlockingDeque methods
		//   offerFirst(E e, long timeout, TimeUnit unit)  add item to the head of the queue.
		//   offerLast(E e, long timeout, TimeUnit unit)   add item to the tail of the queue.
		//   pollFirst(long timeout, TimeUnit unit)  retrieves and removes item from the head.
		//   pollLast(long timeout, TimeUnit unit)   retrieves and removes item from the tail.
		try {
			BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();
			blockingDeque.offer(91);
			blockingDeque.offerFirst(5, 2, TimeUnit.MINUTES);
			blockingDeque.offerLast(47, 100, TimeUnit.MICROSECONDS);
			blockingDeque.offer(3, 4, TimeUnit.SECONDS);
			System.out.println(blockingDeque.poll());
			System.out.println(blockingDeque.poll(950, TimeUnit.MILLISECONDS));
			System.out.println(blockingDeque.pollFirst(200, TimeUnit.NANOSECONDS));
			System.out.println(blockingDeque.pollLast(1, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			// Handle interruption
		}
		
		// SkipList collections: "sorted concurrent collections with the natural order"
		// ConcurrentSkipListSet (counterpart of TreeSet)
		// ConcurrentSkipListMap (counterpart of TreeMap)
		
		// CopyOnWrite collections
		// CopyOnWriteArrayList
		// CopyOnWriteArraySet
		// These classes copy all of their elements to a new underlying structure
		// any time an element is added, modified, or removed from the collection
		List<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(4, 3, 52));
		for (Integer item : list) {
			System.out.print(item + " ");
			list.add(9);
			// we avoid entering an infinite loop in which elements are constantly added		
		}
		System.out.println();
		System.out.println("Size: " + list.size());
		for (Integer item : list) {
			System.out.print(item + " ");
		}

		System.out.println();
		// Obtaining Synchronized Collections
		// methods for obtaining synchronized versions of existing non-concurrent collection objects.
		// synchronized(Collection|List|Map|Set|NavigableMap|NavigableSet|SortedMap|SortedSet)
		List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>(Arrays.asList(4, 3, 52)));
		synchronized (synchronizedList) {
			for (int data : synchronizedList)
				System.out.print(data + " ");
		}
		// Therefore, it is imperative that you use a synchronization block if you need
		// to iterate over any of the returned collections.
		
		// Unlike the concurrent collections, the synchronized collections also throw an exception
		// if they are modified within an iterator by a single thread
				
	}
	
}
