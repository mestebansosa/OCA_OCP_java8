package java8.OCP.C7_Concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Concurrency {

	// Creating threads
	// with Runnable, better OO practice and used in Concurrency API
	public static class PrintData implements Runnable {
		public void run() {
			for (int i = 0; i < 3; i++)
				System.out.println("with Runnable: " + i);
		}
	}
	
	// with Thread. Less used. Only specific cases, like change thread priority.
	// With this approach you cannot extend another class.
	public static class ReadInventoryThread extends Thread {
		public void run() {
			System.out.println("with Thread");
		}
	}
	
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	// order not guaranteed. Start is executed by the scheduler.
		(new Thread(new PrintData())).start();
		(new ReadInventoryThread()).start();
		// order guaranteed, but not concurrency. The scheduler is not involved.
		(new Thread(new PrintData())).run();
		(new ReadInventoryThread()).run();
		// with lambda
		new Thread(() -> {
			for(int i=0; i<5; i++) System.out.println("i=" + i);
			}).start();		
		
		
		// ExecutorService
		// The simplest is newSingleThreadExecutor(). It uses only one thread.
		// No waits for results
		ExecutorService service = null;
		try {
			service = Executors.newSingleThreadExecutor();
			service.execute(() -> System.out.println("ExecutorService"));
			service.execute(() -> {
				for (int i = 0; i < 3; i++)
					System.out.println("ExecutorService: " + i);
			});
		} finally {
			if (service != null)
				service.shutdown();
		}
		
		// waiting for results. Runnable
		try {
			service = Executors.newSingleThreadExecutor();
			Future<?> future = service.submit(() -> System.out.println("waiting for results. Runnable"));
			//future.get();
		} finally {
			if (service != null)
				service.shutdown();
		}
		
		// waiting for results. Callable
		try {
			service = Executors.newSingleThreadExecutor();
			System.out.println("waiting for results. Callable");
			Future<?> future = service.submit(() -> 30+11);
			System.out.println(future.get());
		} finally {
			if (service != null)
				service.shutdown();
		}

		// waiting for all tasks to finish.
		try {
			service = Executors.newSingleThreadExecutor();
			System.out.println("waiting for all tasks to finish");
			for(int i=0; i < 10; i++) {
				Future<?> future = service.submit(() -> {Thread.sleep(1000); return null;});
				System.out.println("launched " + i);				
			}
			System.out.println("end");
		} finally {
			if (service != null)
				service.shutdown();
		}
		if(service != null) {
			service.awaitTermination(5, TimeUnit.SECONDS);
			// Check whether all tasks are finished
			if(service.isTerminated())
			System.out.println("All tasks finished");
			else
			System.out.println("At least one task is still running");
			}


		// Scheduling Tasks
		ScheduledExecutorService scheduler = null;
		try {
			scheduler = Executors.newSingleThreadScheduledExecutor();
			Runnable task1 = () -> System.out.println("Scheduling Tasks");
			Callable<String> task2 = () -> "Monkey";
			ScheduledFuture<?> result1 = scheduler.schedule(task1, 100, TimeUnit.MILLISECONDS);
			ScheduledFuture<?> result2 = scheduler.schedule(task2, 1, TimeUnit.SECONDS);
			// scheduler.scheduleAtFixedRate(task1, 3, 1, TimeUnit.MILLISECONDS);
			//System.out.println(result1.get());
			System.out.println(result2.get());			
		} finally {
			if (scheduler != null)
				scheduler.shutdown();
		}
		
		// Concurrency with pools
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService cachedThreadPool = null;
		ExecutorService fixedThreadPool = null;
		ScheduledExecutorService schedulerPool = null;
		
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
