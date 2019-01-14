package java8.OCP.C7_Concurrency;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ConcurrentProcesses {
	private void removeAnimals() {
		System.out.println("Removing animals");
	}

	private void cleanPen() {
		System.out.println("Cleaning the pen");
	}

	private void addAnimals() {
		System.out.println("Adding animals");
	}

	public void performTask(CyclicBarrier c1, CyclicBarrier c2) {
		try {
			removeAnimals();
			c1.await();
			cleanPen();
			c2.await();
			addAnimals();
		} catch (InterruptedException | BrokenBarrierException e) {
			// Handle checked exceptions here
		}
	}
	
	public static class WeighAnimalAction extends RecursiveAction {
		private int start;
		private int end;
		private Double[] weights;

		public WeighAnimalAction(Double[] weights, int start, int end) {
			this.start = start;
			this.end = end;
			this.weights = weights;
		}

		protected void compute() {
			if (end - start <= 3)
				for (int i = start; i < end; i++) {
					weights[i] = (double) new Random().nextInt(100);
					System.out.println("Animal Weighed: " + i);
				}
			else {
				int middle = start + ((end - start) / 2);
				System.out.println("[start=" + start + ",middle=" + middle + ",end=" + end + "]");
				invokeAll(new WeighAnimalAction(weights, start, middle), new WeighAnimalAction(weights, middle, end));
			}
		}
	}
	
	public static void main(String[] args) {
		// CyclicBarrier (threads coordination)
		// The CyclicBarrier takes in its constructors a limit value, indicating the
		// number of threads to wait for. As each thread finishes, it calls the await()
		// method on the cyclic barrier. Once the specified number of threads have each
		// called await(), the barrier is released and all threads can continue.
		
		// If you use a thread pool, make sure that you set the number of available threads
		// to be at least as large as your CyclicBarrier limit value. 
		// If not it will hang indefinitely, a deadlock.
		// After a CyclicBarrier is broken, all threads are released and the number of
		// threads waiting on the CyclicBarrier goes back to zero. The CyclicBarrier may be 
		// used again for a new set of waiting threads. For example, 
		// CyclicBarrier limit is 5 and we have 15 threads that call await(), 
		// then the CyclicBarrier will be activated a total of three times.
		
		// all threads stop and wait at logical barriers.
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(4);
			ConcurrentProcesses manager = new ConcurrentProcesses();
			CyclicBarrier c1 = new CyclicBarrier(4);
			CyclicBarrier c2 = new CyclicBarrier(4, () -> System.out.println("*** Pen Cleaned!"));
			for (int i = 0; i < 4; i++)
				service.submit(() -> manager.performTask(c1, c2));
		} finally {
			if (service != null)
				service.shutdown();
		}
		
		// Fork/Join Framework
		// When a task gets too complicated, we can split the task into multiple other tasks.
		// The fork/join framework relies on the concept of recursion to solve complex tasks.
		// Applying the fork/join framework requires us to perform three steps:
		// 1.- Create a ForkJoinTask (the most complex. Requires defining the recursive process.
		//     RecursiveAction abstract class. void compute()
		//     RecursiveTask<T> abstract class. T compute()
				
		// 2.- Create the ForkJoinPool.
		// 3.- Start the ForkJoinTask.
		Double[] weights = new Double[10];
		ForkJoinTask<?> task = new WeighAnimalAction(weights,0,weights.length);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(task);
		System.out.println();
		System.out.print("Weights: ");
		Arrays.asList(weights).stream().forEach(
		d -> System.out.print(d.intValue()+" "));
		
		FALTA RecursiveTasks
	}
}
