package java8.OCA.C7_Exceptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Exceptions_OCA_OCP_C6 {
	// throw means an exception is actually being thrown 
	// throws indicate that the method merely has the potential to throw that exception.	
	class ThowExceptions {
    	// A method can throw a runtime or error exception irrespective of whether is included in throws.
		void method1() throws Error {};
		void method2() throws ArrayIndexOutOfBoundsException {};
		void method3() throws FileNotFoundException {};
		// not allow you to declare a catch block for a checked exception type that
		// cannot potentially be thrown by the try clause body. This is again to avoid
		// unreachable code.
		// void method4() {try {} catch(FileNotFoundException e) {}}; // Won't compile

	}
	
	// try-with-resources. Java 7
	// Only a try-with-resources statement is permitted to omit both the catch and
	// finally blocks. A traditional try statement must have either or both.
	
	// In order for a class to be created in the try clause, Java requires implement AutoCloseable. 
	// allow to skip finally. Example. BufferedReadeer implements AutoCloseable
	static String readFirstLineFromFile(String path) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.readLine();
		}
	}
	
	public static String tryFinallyWithTwoReturns() {
		// it will be executed the finally return.
		// a try-finally (no catch) is legal, but won't suffice if code in the try throws a checkedException.
		System.out.println("tryFinallyWithTwoReturns");
		try {
			System.out.println("Executing try block");
			return "Return from try block";
		} finally {
			System.out.println("Executing finally block");
			return "Return from finally block";
		}
	}
	
	public static int tryFinallyExample2() {
		try { throw new NullPointerException(); }
		finally { System.out.println("tryFinallyExample2 Executing finally block");}
	}
	
	public static int tryFinallyExample3() {
		try { return 0; }
		catch(Exception io) { throw new NullPointerException();}
		finally { System.out.println("Executing finally block");}
		// catch(Exception io) { throw new NullPointerException();} Finally can't be place before catch
	}
	
    public static void main(String[] args) {
    	// unchecked exception
    	//    Object.Throwable.Exception.RuntimeException
    	//    java.lang.RuntimeException
    	//    may be caught but it is not required that it be caught. 
    	//    Examples: 
    	//       NullPointerException, 
    	//       ArrayIndexOutOfBoundsException (try to access an invalid array position)
    	//       IndexOutOfBoundsException (try to access an invalid ArrayList position)
    	//       ClassCastException (you can use instanceOf before the casting) 
    	//       IllegalArgumentException (used by developers)
    	//       ArithmeticException (division by 0 (or 0/0). Not by 0.0, in this return +-Infinity or NaN)
    	// System.out.println(0/0); // ArithmeticException
    	System.out.println(77.0/0); // Infinity
    	System.out.println(77.0/0.0); // Infinity
    	System.out.println(-77.0/0); // -Infinity
    	System.out.println(0.0/0); // NaN
    	//       NumberFormatException
    	System.out.println(Integer.parseInt("123")); // 123
    	System.out.println(Integer.parseInt("-123")); // -123
    	System.out.println(Integer.parseInt("+123")); // 123
    	// System.out.println(Integer.parseInt("123_45")); // NumberFormatException
    	// System.out.println(Integer.parseInt("123ABC")); // NumberFormatException
    	System.out.println(Integer.parseInt("123ABC", 16)); // 1194684
    	
		// checked exception
    	//    Object.Throwable.Exception (checked during compilation) 
    	//    java.lang.Exception
    	//    is any class that extends Exception but not RuntimeException.
		//    Checked exceptions must follow the handle or declare rule where they are 
    	//    either caught or thrown to the caller. 
    	//    It is part of the API and is well documented.
    	//    Examples: FileNotFoundException, IOException, ParseException, NotSerializableException, SQLException

    	
    	// error exception. Can be referred as unchecked exception
    	//    Object.Throwable.Error
    	//    java.lang.Error
    	//    Error is fatal and should not be caught by the program, but can be caught
    	//    Examples: NoClassDefException, StackOverflowError, OutOfMemoryError (ended in *Error)
    	//       ExceptionInInitializeError. In static block, static variable or method
    	
    	// Handle-or-declare rule. 
    	//   Only applies to checked exceptions. Not needed for RuntimeException. Not for Errors.
    	//   Handle, enclose with try-catch block,
    	//   Declare in the declaration with throws clause.
    	//   Both at the same time.
    	
    	// try-catch-finally
    	// finally always is executed. It is called the cleanup code.
    	// only two scenarios not execute finally: System.exit and Fatal Errors, a crash.
    	// multiple catch blocks but only one finally block
    	// order in catch blocks. Specific exception to mayor in hierarchy.
    	System.out.println(tryFinallyWithTwoReturns()); // prints: Executing try block, Executing finally block and return from finally block
    	// tryFinallyExample2(); // prints: Executing finally block and the Exception stack     	

    	// create checked exceptions.
		class CannotSwimException extends Exception {
			// the 3 more common constructors. Default constructor is set automatically if not defined any.
			public CannotSwimException() {
				super();
			}

			public CannotSwimException(Exception e) {
				super(e);
			}

			public CannotSwimException(String message) {
				super(message);
			}
		} 
		
		// Multi-catch. A catch with several exceptions to avoid duplicate code.
		try {
			Path path = Paths.get("dolphinsBorn.txt");
			String text = new String(Files.readAllBytes(path));
			LocalDate date = LocalDate.parse(text);
			System.out.println(date);
		} catch (DateTimeParseException | IOException e) {
			System.out.println("Exception in : " + e.getLocalizedMessage());
			// throw new RuntimeException(e);
		}
		// syntax
		// catch(Exception1 e | Exception2 e | Exception3 e) // DOES NOT COMPILE
		// catch(Exception1 e1 | Exception2 e2 | Exception3 e3) // DOES NOT COMPILE
		// catch(Exception1 | Exception2 | Exception3 e) // OK
		// catch (FileNotFoundException | IOException e) { } // DOES NOT COMPILE		
		
		// try-with-resources. Java 7
		// It can have catch and finally. 
		System.out.println("Scanner: type any string and enter");
		try (Scanner s = new Scanner(System.in)) {
			s.nextLine();
			} catch(Exception e) {
			// s.nextInt(); // DOES NOT COMPILE. The scanner s is out of scope.
			} finally{
			// s.nextInt(); // DOES NOT COMPILE
		}
		// AutoCloseable interface: method: public void close() throws Exception
		// Before there is the Closeable interface.
		//   You can’t just put any random class in a try-with-resources statement. 
		//   Java commits to closing automatically any resources opened in the try clause.
		// The main() method has to declare the exception
		// Java strongly recommends that close() not actually throw Exception. It is better to
		// throw a more specific exception.
		class JammedTurkeyCage implements AutoCloseable {
			public void close() throws IllegalStateException {
				throw new IllegalStateException("Cage door does not close");
			}
		}
		try (JammedTurkeyCage t = new JammedTurkeyCage()) {
			System.out.println("put turkeys in");
		} catch (IllegalStateException e) {
			System.out.println("caught: " + e.getMessage());
		}
		// the close method is automatically called by try-with-resources
		
		// When multiple exceptions are thrown, all but the first are called "suppressed exceptions". 
		// The idea is that Java treats the first exception as the primary one.
		
		// try-with-resources statement:
		// Resources are closed after the try clause ends and before any catch/finally clauses.
		// Resources are closed in the reverse order from which they were created.
		
		
		// OCP checked exceptions
		//   ParseException, IOException, FileNotFoundException, NotSerializableException,
		//   SQLException
		// OCP runtime exceptions
		//   ArrayStoreException, DateTimeException, MissingResourceException,
		//   IllegalStateException, UnsupportedOperationException
    	
    }			
}
