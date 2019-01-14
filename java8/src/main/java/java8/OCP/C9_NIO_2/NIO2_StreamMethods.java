package java8.OCP.C9_NIO_2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NIO2_StreamMethods {

	public static void main(String[] args) throws URISyntaxException {
		// Walking or traversing a directory is the process by which you start with a
		// parent directory and iterate over all of its descendants.
		
		// Search Strategy
		/*
		There are two common strategies associated with walking a directory tree: 
		- depth-first search traverses the structure from the root to an arbitrary leaf and then 
		navigates back up toward the root, traversing fully down any paths it skipped along the way. 
		The search depth is the distance from the root to current node. 
		For performance reasons, some processes have a maximum search depth.
		It requires less memory.
		- breadth-first search starts at the root and processes all elements of each
		particular depth, or distance from the root, before proceeding to the next depth level. The 
		results are ordered by depth, with all nodes at depth 1 read before all nodes at depth 2, and so on.
		It works better when the node for which you are searching is likely near the root
		*/
		
		// Streams API uses depth-first searching with a default maximum depth of Integer.MAX_VALUE.
		
		// Walking a Directory
		// The Files.walk(path) method returns a Stream<Path> object that traverses the
		//   directory in a depth-first, lazy manner. By lazy, we mean the set of elements
		//   is built and read while the directory is being traversed.	
		// Keep in mind that when you create a Stream<Path> object using Files.walk(), 
		//   the contents of the directory have not yet been traversed
		Path path = Paths.get("C:\\Users\\hp\\workspace_packlink");
		try {
			Files.walk(path).filter(p -> p.toString().endsWith(".java")).forEach(System.out::println);
			System.out.println("------");
			Files.walk(path,9).filter(p -> p.toString().endsWith(".java")).forEach(System.out::println);
		} catch (IOException e) {
		}
		
		// Disregarding newDirectoryStream(). Similar behavior as Files.walk()
		// except the DirectoryStream<Path> object that it returns does not inherit from
		// the java.util.stream.Stream class. In other words, despite its name, it is
		// not actually a stream as described in Chapter 4,
		
		// Avoiding Circular Paths.
		// the walk() method will not traverse symbolic links by default.
		// NIO.2 offers the FOLLOW_LINKS option as a vararg to the walk() method.
		
		// Searching a Directory.
		// NIO.2 API provides a more direct method. The Files.find(Path,int,BiPredicate)
		// method behaves in a similar manner as the Files.walk() method, except that it
		// requires the depth value to be explicitly set along with a BiPredicate to
		// filter the data. Like walk(), find() also supports the FOLLOW_LINK vararg option
		path = Paths.get("C:\\Users\\hp\\workspace_packlink");
		long dateFilter = 1420070400000l;
		try {
			// the two object types are Path and BasicFileAttributes
			Stream<Path> stream = Files.find(path, 10,
					(p, a) -> p.toString().endsWith(".java") && a.lastModifiedTime().toMillis() > dateFilter);
			stream.forEach(System.out::println);
		} catch (Exception e) {
			// Handle file I/O exception...
		}		
		
		// Listing Directory Contents
		// Although you could use the Files. walk() method with a maximum depth limit of
		// 1 to perform this same task, the NIO.2 API includes a new stream method,
		// Files.list(Path), that does this for you.
		// Files.list() is equals to java.io.File.listFiles(), except that it relies on streams
		try {
			path = Paths.get("C:\\Users\\hp\\workspace_packlink");
			Files.list(path).filter(p -> !Files.isDirectory(p)).map(p -> p.toAbsolutePath())
					.forEach(System.out::println);
		} catch (IOException e) {
			// Handle file I/O exception...
		}
		
		// Printing File Contents
		// NIO.2 API includes a Files.lines(Path) method that returns a Stream<String>
		// object that preserves of the Files.readAllLines() and commented that using it
		// to OutOfMemoryError problem.
		path = Paths.get("/fish/sharks.log");
		try {
			Files.lines(path).forEach(System.out::println);
			
			System.out.println(Files.lines(path)
					.filter(s -> s.startsWith("WARN "))
					.map(s -> s.substring(5)).collect(Collectors.toList()));
		} catch (IOException e) {
			// Handle file I/O exception...
		}
		
		// Files.readAllLines() vs. Files.lines()
		try {
			Files.readAllLines(Paths.get("birds.txt")).forEach(System.out::println);
			Files.lines(Paths.get("birds.txt")).forEach(System.out::println);
			
			// Files.readAllLines(path).filter(s -> s.length()>2).forEach(System.out::println); WON'T COMPILE
			Files.lines(path).filter(s -> s.length()>2).forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// The first code snippet reads the entire file into memory and then performs a print
		// operation on the resulting object. The second code snippet reads the lines lazily and
		// prints them as they are being read. The advantage of the second code snippet is that it
		// does not require the entire file to be stored in memory as it is being read.
		
	}
}
