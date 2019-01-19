package java8.OCP.C7_Concurrency;

class PrintData implements Runnable {
	public void run() {
		for (int i = 0; i < 3; i++)
			System.out.println("Printing record: " + i);
	}
}

public class Threads extends Thread {
	/*
	 A THREAD is the smallest unit of execution that can be scheduled by the operating system. 
	 A PROCESS is a group of associated threads that execute in the same, shared environment. 
	 By shared environment, we mean that the threads in the same process share the same memory space and 
	 can communicate directly with one another. 
	 static variables can be useful for performing complex, multi-threaded tasks! 
	 TASKS and their relationships to threads: a task is a single unit of work performed by a thread. 
	 a task will commonly be implemented as a lambda expression. 
	 A thread can complete multiple independent tasks but only one task at a time.
	 */
	/*
	Java is multi-threaded:
	  - System-Defined Threads: GC thread. If these system threads find a problem they throw a Java Error.
	  - User-Defined Threads: one created by the developer that executes tasks.
	  - Daemon Threads. If the JVM detects only daemon threads then the JVM will shut down. 
	      Daemon threads can be system or user threads. 
	*/
	
	// Thread concurrency, thread-scheduler
	// SSOO have a thread-scheduler with a round-robin schedule to give the CPU and number of cycles
	//    to be executed by the thread. 
	// A context switch is the process of storing a thread’s current state and later restoring the 
	//    state of the thread to continue execution.
	// Thread priority. From 1 to 10.
	
	// Runnable interface (from Java 1)
	/*
	 @FunctionalInterface public interface Runnable { void run(); }
	 Lambda expressions:
	 	() -> System.out.println("Hello World")
		() -> {int i=10; i++;}
		() -> {return;}
		() -> {}
	Invalid ones are: (they each return a value)
		() -> ""
		() -> 5
		() -> {return new Object();}
	 
	 */

	public void run() {
		System.out.println("Printing zoo inventory");
	}
	
	public static void main(String[] args) {
		// create a thread and execute the task.
		// Two ways: 

		// extends Thread. // less common
		(new Thread(new Threads())).start();  
				// Causes this thread to begin execution; the Java Virtual Machine calls the run method of this thread
		// implements Runnable
		(new Thread(new PrintData())).start();
		// with lambdas
		new Thread(() -> {
			for (int i = 0; i < 3; i++)
				System.out.println("Printing record with lambda: " + i);
		}).start();
		// with lambdas 2nd form
		Runnable runnableLambda = () -> {
			for (int i = 0; i < 3; i++)
				System.out.println("Printing record with lambda2: " + i);
		};
		new Thread(runnableLambda).start();
		/*
		 diff between extending the Thread class and implementing Runnable:
         - If you need to define your own Thread rules upon which multiple tasks will rely, 
           such as a priority Thread, extending Thread may be preferable.
         - Since Java doesn't support multiple inheritance, better implement Runnable
         - Implementing Runnable is often a better object-oriented design practice since it separates
           the task being performed from the Thread object performing it.
         - Implementing Runnable allows the class to be used by numerous Concurrency API classes.
		 */
		
		// While the order of thread execution once the threads have been started is
		// indeterminate, the order within a single thread is still linear
		
		// Polling with Sleep
		try { Thread.sleep(1000); } catch (InterruptedException e) {} // 1 SECOND
		
		// ExecutorService does all of these or you.
	}
	
}
