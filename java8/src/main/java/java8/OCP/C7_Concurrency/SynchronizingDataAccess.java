package java8.OCP.C7_Concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SynchronizingDataAccess {
	// Since threads run in a shared environment and memory space, 
	// how do we prevent two threads from interfering with each other?
	// Race condition: two tasks, which should be completed sequentially, are completed at the same time.
	//   example: create the account at the same time.
	//     scenarios: one user creates the other not. Both do not create. Both create the account.
	// race conditions lead to invalid data if they are not properly handled.

	// Atomic is the property of an operation to be carried out as a single unit 
	//   of execution without any interference by another thread.
	/* Atomic classes are:
	   Atomic[Boolean | Integer | IntegerArray | Long | LongArray | Reference | ReferenceArray]
	   CommonMethod: get|set|getAndSet|[in|de]crementAndGet|getAnd[in|de]crement
	 */
	/* 
	   Monitor or locks. Synchronized Blocks
	   In Java any object can be used as a monitor, along with the synchronized keyword
			private void incrementAndReport() {
				synchronized(this) { System.out.print((++sheepCount)+" "); }
			}
       This example is referred to as a synchronized block.
       
       Other way:
          Although we didn’t need to make the lock variable final , doing so ensures that it is not
          reassigned after threads start using it.
          
			private final Object lock = new Object();
			private void incrementAndReport() {
				synchronized(lock) {System.out.print((++sheepCount)+" ");}
			}
			
		This is equivalent to:
			private synchronized void incrementAndReport() {
				System.out.print((++sheepCount)+" ");
			}
		
	 */
	// Synchronization is about protecting data integrity at the cost of performance.
	// The better is using Concurrent collections
		
	public static class SheepManager {
		private int sheepCount1 = 0;
		private int sheepCount2 = 0;
		private int sheepCount3 = 0;
		private AtomicInteger atomicSheepCount1 = new AtomicInteger();
		private AtomicInteger atomicSheepCount2 = new AtomicInteger();
		private AtomicInteger atomicSheepCount3 = new AtomicInteger();

		private void incrementAndReport() {
			// disorder and some number can be repeated in the output
			System.out.print((++sheepCount1) + " ");
		}

		// synchronized block with int counter
		private void incAndReportSyncBlock() {
			synchronized(this) {
				System.out.print((++sheepCount2) + " ");
			}
		}

		// synchronized method with int counter
		private synchronized void incAndReportSyncMethod() {
			System.out.print((++sheepCount3) + " ");
		}

		private void atomicIncrementAndReport() {
			// disorder and no repeated number in the output
			System.out.print((atomicSheepCount1.incrementAndGet()) + " ");
		}

		// synchronized block with atomicInteger counter. The atomicInteger would not be needed
		private void atomicIncrementAndReportSyncBlock() {
			synchronized(this) {
				System.out.print((atomicSheepCount2.incrementAndGet()) + " ");
			}
		}
		
		// synchronized method with atomicInteger counter. The atomicInteger would not be needed
		private synchronized void atomicIncrementAndReportSyncMethod() {
			System.out.print((atomicSheepCount3.incrementAndGet()) + " ");
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Consumer<String> println = x -> System.out.println(x);

		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(100);
			SheepManager manager = new SheepManager();
			
			println.accept("incrementAndReport:");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.incrementAndReport());			
			Thread.sleep(1000);
			println.accept("");
			
			println.accept("incAndReportSyncBlock:");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.incAndReportSyncBlock());			
			Thread.sleep(1000);
			println.accept("");
			
			println.accept("incAndReportSyncMethod:");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.incAndReportSyncMethod());			
			Thread.sleep(1000);
			println.accept("");
			
			println.accept("atomicIncrementAndReport:");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.atomicIncrementAndReport());			
			Thread.sleep(1000);
			println.accept("");
			
			println.accept("atomicIncrementAndReportSyncBlock:");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.atomicIncrementAndReportSyncBlock());			
			Thread.sleep(1000);
			println.accept("");
			
			println.accept("atomicIncrementAndReportSyncMethod:");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.atomicIncrementAndReportSyncMethod());
			println.accept("");
			
		} finally {
			if (service != null)
				service.shutdown();
		}
	}
}
