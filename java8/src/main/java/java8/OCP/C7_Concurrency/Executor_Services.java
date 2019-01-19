package java8.OCP.C7_Concurrency;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class Executor_Services {
	// Executor methods are:
	
	/*
	   newSingleThreadExecutor(), returns ExecutorService
	   newSingleThreadScheduledExecutor(), returns ScheduledExecutorService
	   newCachedThreadPool(), returns ExecutorService
	   newFixedThreadPool(int nThreads), returns ExecutorService
	   newScheduledThreadPool(int nThreads), returns ScheduledExecutorService
		
		While a single-thread executor will wait for an available thread to become available 
		before running the next task, a pooled-thread executor can execute the next task concurrently. 
		If the pool runs out of available threads, the task will be queued by the thread executor 
		and wait to be completed.
		
		The newFixedThreadPool() takes a number of threads and allocates them all upon creation. 
		As long as our number of tasks is less than our number of threads, all tasks will be executed 
		concurrently. If at any point the number of tasks exceeds the number of threads in the pool, 
		they will wait.

		The newCachedThreadPool() will create a thread pool of unbounded size, allocating a new thread
		any time one is required or all existing threads are busy. This is commonly used for pools that 
		require executing many short-lived asynchronous tasks. 
		   
		For long-lived processes, usage of this executor is strongly discouraged.
		   
		newFixedThreadPool(1) is equals to newSingleThreadExecutor()
		
		Choosing poolSize: Runtime.getRuntime().availableProcessors()
	 */
	
	// ExecutorService is an interface. 
	// The Executors factory class that can be used to create instance of the ExecutorService object.
    // You can create a static instance of a thread executor and have all processes share it.
	static ExecutorService service = Executors.newSingleThreadExecutor();
	static ScheduledExecutorService scheduledService = null;
	static ExecutorService cachedThreadPool = null;
	static ExecutorService fixedThreadPool = null;
	static ScheduledExecutorService scheduledThreadPool = null;

	public static void useCallable(Callable<Integer> expression) {}
	public static void useSupplier(Supplier<Integer> expression) {}
	public static void use(Supplier<Integer> expression) {}
	public static void use(Callable<Integer> expression) {}
	
	public static void main(String[] args) {
		// the newSingleThreadExecutor() method, which is the simplest ExecutorService
		//   that we could create. The order is guaranteed as it is only one thread.
		// the singleThreadExecutor will queue the tasks and wait until the previous task completes 
		//   before executing the next task
		try {
			service = Executors.newSingleThreadExecutor();
			System.out.println("begin");
			service.submit(() -> System.out.println("newSingleThreadExecutor"));
			service.execute(() -> {
				for (int i = 0; i < 3; i++)
					System.out.println("Printing record: " + i);
			});
			service.execute(() -> System.out.println("newSingleThreadExecutor"));
			System.out.println("end");
		} finally {
			if (service != null)
				service.shutdown();
		}
		
		// shutdown the thread executor
		/* 
		 A thread executor creates a non-daemon thread on the first task that is executed, 
		 so failing to call shutdown() will result in your application never terminating.
		 
         The shutdown process for a thread executor involves first rejecting any new tasks submitted to
         the thread executor while continuing to execute any previously submitted tasks. During this time,
         calling isShutdown() will return true, while isTerminated() will return false. If a new task is
         submitted to the thread executor while it is shutting down, a RejectedExecutionException will
         be thrown. Once all active tasks have been completed, isShutdown() and isTerminated() will
         both return true.
         
         shutdownNow() returns a List<Runnable> of tasks that were submitted to the thread executor 
         but that were never started.
         
         ExecutorService interface does not implement AutoCloseable, so cannot use a try-with-resources.
         Use finally instead.
         
         You can create a static instance of a thread executor and have all processes share it.
		 */
		
		// Submitting Tasks. Recommended submit over execute, even if you don't store the Future reference.
		/*
		  void execute(Runnable command). 
		     Executes a Runnable task at some point in the future. Fire and forget.
		  Future<?> submit(Runnable task)
		     Executes a Runnable task at some point in the future and returns a Future object 
		     representing the task.
		  <T> Future<T> submit(Callable<T> task)
		     Executes a Callable task at some point in the future and returns a Future object 
		     representing the pending results of the task.
		  <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException
			 Executes the given tasks, synchronously returning the results of all tasks as a Collection of 
			 Future objects, in the same order they were in the original collection
		  <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException
			 Executes the given tasks, synchronously returning the result of one of finished tasks, 
			 cancelling any unfinished tasks
		 */
		/* 
		  invokeAll and invokeAny are synchronous, so will wait until tasks (all or any) are executed.
		  There are overloaded method with TimeUnit parameter to do not wait too much.
		 */
		
		// Waiting for Results with Future<V> object over a Runnable		
		try {
			service = Executors.newSingleThreadExecutor();
			System.out.println("begin");
			
			Future<?> future = service.submit(() -> System.out.println("newSingleThreadExecutor. waiting results"));
			// As Future<V> is a generic class, the type V is determined by the return type of the Runnable. 
			// Since the return type of Runnable.run() is void, the get() method always returns null.
			System.out.println(future.get()); // retrieves the result of a task, waiting endlessly if it is not yet available.
			
			// notice the submit parameter is Callable
			future = service.submit(() -> {System.out.println("newSingleThreadExecutor. waiting results"); return 10;});
			System.out.println(future.get(1, TimeUnit.SECONDS)); // retrieves the result of a task, waiting the specified time.
			
			System.out.println("cancel: " + future.cancel(true));
			System.out.println("done: " + future.isDone());
			System.out.println("cancelled: " + future.isCancelled());
			service.execute(() -> {
				for (int i = 0; i < 3; i++)
					System.out.println("Printing record: " + i);
			});
			service.execute(() -> System.out.println("newSingleThreadExecutor. waiting results"));
			System.out.println("end");
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		} finally {
			if (service != null) service.shutdown();
		}
		
		// Callable functional interface
		// @FunctionalInterface public interface Callable<V> { V call() throws Exception; }
	    // The Callable interface was introduced as an alternative to the Runnable interface.
		// It returns something. It is preferred over Runnable in Concurrency API
		// Diff with Supplier in the throws Exception
		useCallable(() -> {throw new IOException();}); // COMPILES
		// useSupplier(() -> {throw new IOException();}); // DOES NOT COMPILE
		// use(() -> {throw new IOException();}); // DOES NOT COMPILE
		// The ambiguity can be resolved with an explicit cast. 
		use((Callable<Integer>)() -> {throw new IOException("");}); // COMPILES
		
		try {
			service = Executors.newSingleThreadExecutor();
			Future<Integer> result = service.submit(() -> 30+11);
			System.out.println(result.get());
			service.submit(() -> {Thread.sleep(1000); return null;});
			// service.submit(() -> {Thread.sleep(1000);});  // DOES NOT COMPILE, 
			// Runnable does not support checked exceptions.
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			if(service != null) service.shutdown();
		}
		
		// Waiting for All Tasks to Finish with awaitTermination
		if (service != null) {
			try {
				service.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Check whether all tasks are finished
			if (service.isTerminated())
				System.out.println("All tasks finished");
			else
				System.out.println("At least one task is still running");

		}
		
		// Scheduling Tasks.
		/* 
		 The ScheduledExecutorService, which is a subinterface of ExecutorService.
		 Methods:
		 schedule(Callable<V> callable, long delay, TimeUnit unit)
			Creates and executes a Callable task after the given delay
		 schedule(Runnable command, long delay, TimeUnit unit)
			Creates and executes a Runnable task after the given delay
		 scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
			Creates and executes a Runnable task after the given initial delay, 
			creating a new task every period value that passes.
			closest built-in java equivalent to Unix cron
		 scheduleAtFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
			Creates and executes a Runnable task after the given initial delay and 
			subsequently with the given delay between the termination of one execution 
			and the commencement of the next
		 */
		try {
			scheduledService = Executors.newSingleThreadScheduledExecutor();
			Runnable runnableTask = () -> System.out.println("Hello Zoo");
			Callable<String> callableTask = () -> "Monkey";
			Future<?> result1 = scheduledService.schedule(runnableTask, 5, TimeUnit.SECONDS);
			Future<?> result2 = scheduledService.schedule(callableTask, 10, TimeUnit.SECONDS);
			System.out.println(result2.get());
			Future<?> result3 = scheduledService.scheduleAtFixedRate(runnableTask, 10, 1, TimeUnit.SECONDS);
			Future<?> result4 = scheduledService.scheduleWithFixedDelay(runnableTask, 10, 1, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException e) {
		} finally {
			// if(scheduledService != null) scheduledService.shutdown();
		}
		
		// Concurrency with pools
		// A thread pool is a group of pre-instantiated reusable threads that are
		// available to perform a set of arbitrary tasks
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("availableProcessors: " + availableProcessors);
		
		try {
			cachedThreadPool = Executors.newCachedThreadPool();
			cachedThreadPool.execute(() -> System.out.println("cachedThreadPool"));
			cachedThreadPool.execute(() -> {
				for (int i = 0; i < 3; i++)
					System.out.println("ExecutorService: " + i);
			});
		} finally {
			if (cachedThreadPool != null)
				cachedThreadPool.shutdown();
		}
	
		try {
			fixedThreadPool = Executors.newFixedThreadPool(availableProcessors);
			fixedThreadPool.execute(() -> System.out.println("fixedThreadPool"));
			fixedThreadPool.execute(() -> {
				for (int i = 0; i < 3; i++)
					System.out.println("ExecutorService: " + i);
			});
		} finally {
			if (fixedThreadPool != null)
				fixedThreadPool.shutdown();
		}

	}
}
