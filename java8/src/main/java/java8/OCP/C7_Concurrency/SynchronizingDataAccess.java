package java8.OCP.C7_Concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizingDataAccess {
	// Since threads run in a shared environment and memory space, 
	// how do we prevent two threads from interfering with each other?
	// two tasks executing at the same time is referred to as a race condition.

	// Atomic is the property of an operation to be carried out as a single unit 
	//   of execution without any interference by another thread.
	/* Atomic classes are:
	   Atomic[Boolean | Integer | IntegerArray | Long | LongArray | Reference | ReferenceArray]
	   CommonMethod: get|set|getAndSet|[in|de]crementAndGet|getAnd[in|de]crement
	 */
	/* 
	   Monitor. Synchronized Blocks
	   In Java any object can be used as a monitor, along with the synchronized keyword
			private void incrementAndReport() {
				synchronized(this) { System.out.print((++sheepCount)+" "); }
			}
       This example is referred to as a synchronized block.
       Other way:
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

		private void incrementAndReport1() {
			System.out.print((++sheepCount1) + " ");
		}

		// synchronized block with int counter
		private void incrementAndReport2() {
			synchronized(this) {
				System.out.print((++sheepCount2) + " ");
			}
		}

		// synchronized method with int counter
		private synchronized void incrementAndReport3() {
			System.out.print((++sheepCount3) + " ");
		}

		private void atomicIncrementAndReport1() {
			System.out.print((atomicSheepCount1.incrementAndGet()) + " ");
		}

		// synchronized block with atomicInteger counter. The atomicInteger would not be needed
		private void atomicIncrementAndReport2() {
			synchronized(this) {
				System.out.print((atomicSheepCount2.incrementAndGet()) + " ");
			}
		}
		
		// synchronized method with atomicInteger counter. The atomicInteger would not be needed
		private synchronized void atomicIncrementAndReport3() {
			System.out.print((atomicSheepCount3.incrementAndGet()) + " ");
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(10);
			SheepManager manager = new SheepManager();
			
			System.out.println("incrementAndReport1");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.incrementAndReport1());			
			Thread.sleep(1000);
			
			System.out.println("incrementAndReport2");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.incrementAndReport2());			
			Thread.sleep(1000);
			
			System.out.println("incrementAndReport3");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.incrementAndReport3());			
			Thread.sleep(1000);
			
			System.out.println("atomicIncrementAndReport1");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.atomicIncrementAndReport1());			
			Thread.sleep(1000);
			
			System.out.println("atomicIncrementAndReport2");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.atomicIncrementAndReport2());			
			Thread.sleep(1000);
			
			System.out.println("atomicIncrementAndReport3");
			for (int i = 0; i < 10; i++)
				service.submit(() -> manager.atomicIncrementAndReport3());
			
		} finally {
			if (service != null)
				service.shutdown();
		}
	}
}
