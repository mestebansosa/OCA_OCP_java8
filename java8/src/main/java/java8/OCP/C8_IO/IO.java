package java8.OCP.C8_IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class IO {
	public static void copyStream(File source, File destination) throws IOException {
		// If the destination fi le already exists, it will be overridden.
		// bad performance.
		// successive calls to the read() method until a value of -1 is returned.
		try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(destination)) {
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
		}
	}
	
	public static void copyBufferedStream(File source, File destination) throws IOException {
		// wrapping the FileInputStream and FileOutputStream
		// number of bytes read is important: 
		//    0 means EOF.
		//    The length value tells us how many of the bytes in the array were actually read from the file.
		try (InputStream in = new BufferedInputStream(new FileInputStream(source));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(destination))) {
			byte[] buffer = new byte[1024]; // power of 2 buffer size
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		}
	}
	
	public static List<String> readFile(File source) throws IOException {
		List<String> data = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
			String s;
			while ((s = reader.readLine()) != null) {  // EOF is null
				data.add(s);
			}
		}
		return data;
	}

	public static void writeFile(List<String> data, File destination) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {
			for (String s : data) {
				writer.write(s);
				writer.newLine();
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File("C:\\data");
		System.out.println(file.exists());
		if (file.exists()) {
			System.out.println("Absolute Path: " + file.getAbsolutePath());
			System.out.println("Is Directory: " + file.isDirectory());
			System.out.println("Parent Path: " + file.getParent());
			if (file.isFile()) {
				System.out.println("File size: " + file.length());
				System.out.println("File LastModified: " + file.lastModified());
			} else {
				for (File subfile : file.listFiles()) {
					System.out.println("\t" + subfile.getName());
				}
			}
		}
		File parent = new File("/home/smith");
		File child = new File(parent,"data/zoo.txt");
		
		// IO Streams. Do not confuse with new Stream API.
		// Stream nomenclature:
		// 1. The stream classes are used for inputting and outputting all types of binary/byte data.
		//       all of the stream classes have the word InputStream or OutputStream in their name
		//       This is low-level stream: connects directly with the source of the data.
		//       Exception: PrintStream (OutputStream) class has no corresponding InputStream class.
		// 2. The reader/writer classes are used for inputting and outputting only char/String data.
		//       all Reader/Writer classes have either Reader or Writer in their name.
		//       This is high-level stream: is built on top of another stream using wrapping.
		//       high-level stream adds new methods, such as readLine(), as well as performance enhancements.
		//       Exception: PrintWriter has no accompanying PrintReader class.
		// Use Buffered Streams When Working with Files. The performance gain cannot be overstated.
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("zoo-data.txt"))) {
			System.out.println(bufferedReader.readLine());
		}
		// FileReader is the low-level stream reader, whereas BufferedReader is the high-level stream.
		try (ObjectInputStream objectStream = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("zoo-data.txt")))) {
			System.out.println(objectStream.readObject());
		}
		// FileInputStream is the low-level, which is wrapped by a high-level BufferedInputStream 
		// to improve performance. Finally, is wrapped by a high-level ObjectInputStream, 
		// which allows us to filter the data as Java objects.
		
		// Stream base classes (InputStream, OutputStream, Reader, and Writer abstract classes)
		// new BufferedInputStream(new FileReader("zoo-data.txt")); // DOES NOT COMPILE
		// new BufferedWriter(new FileOutputStream("zoo-data.txt")); // DOES NOT COMPILE
		// new ObjectInputStream(new FileOutputStream("zoo-data.txt")); // DOES NOT COMPILE
		// new BufferedInputStream(new InputStream()); // DOES NOT COMPILE, InputStream is abstract
		
		// The java.io stream classes
		// Input/OutputStream (abstract)
		//    FileInput/OutputStream low (data as bytes)
		//    ObjectInput/OutputStream high (de/serialize primitive Java data types)
		//    PrintStream high (Writes formatted representations of Java objects to a binary stream)
		// Reader/Writer (abstract)
		//    FileReader/Writer low (data as characters)
		//    BufferReader/Writer high (data as buffer, better performance)
		//    InputStreamrReader/Writer high (data as characters form an existing Input/OutputStream)
		//    PrintWriter high (Writes formatted representations of Java objects to a text-based output stream)
		
		// Common Stream Operations
		//   Closing the Stream: Since streams are considered resources, it is imperative that they be closed.
		//      finally block or using the try-with-resource syntax.
		//   Flushing the Stream: If the data is cached in memory and the application terminates unexpectedly, 
		//      the data would be lost, because it was never written to the file system.
		//      flush(). Only use in extreme cases. Close call flush() automatically.
		//   Marking the Stream: The InputStream and Reader classes include mark(int) and reset() methods 
		//      to move the stream back to an earlier position. You should call to markSuppoerted().
		//      Not call the mark() operation with too large a value as this could take up a lot of memory.
		//   Skipping over Data: InputStream and Reader classes include a skip(long) method, 
		//      which skips over a certain number of bytes.
		
		// Working with Streams
		// The FileInputStream and FileOutputStream Classes.
		File source = new File("Zoo.class"); // It is a binary file
		File destination = new File("ZooCopy.class");
		// copyStream(source,destination);
		
		// The BufferedInputStream and BufferedOutputStream Classes
		
		// The FileReader and FileWriter classes
		
		// The BufferedReader and BufferedWriter Classes
		
		// The ObjectInputStream and ObjectOutputStream Classes
		// For Serializing and Deserializing Objects.
		// ObjectInputStream.readObject() (a cast will be needed as the return type is an Object)
		//     catch EOFException to find the EOF.
		// ObjectOutputStream.writeObject(Object)
		// The object must be Serializable.
		// The Serializable interface is a tagging or marker interface, 
		//    which means that it does not have any methods associated with it.
		// NotSerializableException
		// You can use the transient keyword on the reference to the object, which will instruct 
		// the process serializing the object to skip it (the data stored in the object will be
		// lost during the serialization process)
		// Besides, static class members will also be ignored during the serialization process.
		// serialVersionUID: (version control) update this static class variable if you modify the class.
		//   do not rely on the generated serialVersionUID provided by the Java compiler
		
		// The PrintStream (outputStream for bytes) and PrintWriter Classes (for characters)
		// System.out and System.err are actually PrintStream objects.
		// print(), println(), format(), and printf() methods. Unlike the underlying write() method, 
		// which throws a checked IOException, print-based methods do not throw any checked exceptions.
		
		// Using PrintWriter (although it can be done with PrintStream)
		// print()
		PrintWriter out = new PrintWriter("zoo.log");
		out.print(5); // PrintWriter method
		out.write(String.valueOf(5)); // Writer method
		out.print(2.0); // PrintWriter method
		out.write(String.valueOf(2.0)); // Writer method
		// println. Idem to print() but it uses System.getProperty("line.separator");
		// format() and printf(): public PrintWriter format(String format, Object args...)
		File zoo = new File("zoo.log");
		try (PrintWriter zooWriter = new PrintWriter(new BufferedWriter(new FileWriter(zoo)))) {
			zooWriter.print("Today's weather is: ");
			zooWriter.println("Sunny");
			zooWriter.print("Today's temperature at the zoo is: ");
			zooWriter.print(1 / 3.0);
			zooWriter.println('C');
			zooWriter.format("It has rained 10.12 inches this year");
			zooWriter.println();
			zooWriter.printf("It may rain 21.2 more inches this year");
		}		
		
		// Interacting with Users
		// The Old Way: System.in returns an InputStream 
		// It can be chained to a BufferedReader to allow input that terminates with the Enter key.
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String userInput = reader.readLine();
		System.out.println("You entered the following: " + userInput);	
		// The New Way: In Java 6, the java.io.Console class. Console class is a singleton
		Console console = System.console();
		if (console != null) {
			String userInput2 = console.readLine();
			console.writer().println("You entered the following: " + userInput2);
		}		
		// reader() and writer()
		Reader consoleReader = console.reader();
		Writer consoleWriter = console.writer();
		// format() and printf()
		console.writer().format(new Locale("fr", "CA"),"Hello World");
		console.writer().println("Welcome to Our Zoo!");
		console.format("Our zoo has 391 animals and employs 25 people.");
		console.writer().println();
		console.printf("The zoo spans 128.91 acres.");
		// flush() It is recommended call it prior to calling any readLine() or readPassword().
		// readLine() retrieves a single line of text, and user presses the Enter key to terminate.
		console.writer().print("How excited are you about your trip today? ");
		console.flush();
		String excitementAnswer = console.readLine();
		String name = console.readLine("Please enter your name: ");
		Integer age = null;
		console.writer().print("What is your age? ");
		console.flush();
		BufferedReader reader2 = new BufferedReader(console.reader());
		String value = reader2.readLine();
		age = Integer.valueOf(value);
		console.writer().println();
		console.format("Your name is "+name);
		console.writer().println();
		console.format("Your age is "+age);
		console.printf("Your excitement level is: "+excitementAnswer);
		// readPassword() similar to the readLine() method, except that echoing is disabled.
		// the readPassword() method returns an array of characters instead of a String
		// Why Does readPassword() Return a Character Array? String are added to a shared memory pool.
		char[] password = console.readPassword("Enter your password: ");
		console.format("Enter your password again: ");
		console.flush();
		char[] verify = console.readPassword();
		boolean match = Arrays.equals(password, verify);
		// Immediately clear passwords from memory
		for (int i = 0; i < password.length; i++) {
			password[i] = 'x';
		}
		// other way to fill the array with x's
		Arrays.fill(verify,'x');
		console.format("Your password was " + (match ? "correct" : "incorrect"));		
		
		
		
	}
}
