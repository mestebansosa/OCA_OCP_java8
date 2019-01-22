package java8.OCP.C9_NIO_2;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;

public class NIO2_Path {
	// TODO add this in Factory pattern.
	// General:
	// Helper or utility classes are similar to factory classes in that
	// they are often composed primarily of static methods that operate on a
	// particular class.
	// They differ in that helper classes are focused on manipulating or creating
	// new objects from existing instances, whereas factory classes are focused
	// primarily on object creation

	public static void main(String[] args) throws URISyntaxException {
		// Java 1.4 called NIO. It had no success. Intended as a replacement for java.io streams
		// Java 7 introduced the NIO.2 API. Is actually a replacement for the java.io.File class
		
		// Path (is an Interface)
		// Path is a replacement for java.io.File class, and contains many of the same properties.
		// A Path object is not a file but a representation of a location within the file system.
		// Path interface contains support for symbolic links.
		// Factory: Paths
		
		// Creating Paths
		// Paths.getPath(String)
		Path path1 = Paths.get("pandas/cuddly.png");
		Path path2 = Paths.get("c:\\zooinfo\\November\\employees.txt");
		Path path3 = Paths.get("/home/zoodirector");
		// Absolute vs. Relative paths Is File System Dependent. For the exam:
		//    If a path starts with a forward slash, it is an absolute path, such as /bird/parrot.
		//    If a path starts with a drive letter, it is an absolute path, such as C:\bird\emu.
		//    Otherwise, it is a relative path, such as ..\eagle.
		
		// Paths.get(String,String...). path.separator is automatically inserted between elements.
		path1 = Paths.get("pandas","cuddly.png");
		path2 = Paths.get("c:","zooinfo","November","employees.txt");
		path3 = Paths.get("/","home","zoodirector");
		
		// Paths path1 = Paths.get("/alligator/swim.txt"); // DOES NOT COMPILE
		// Path path2 = Path.get("/crocodile/food.csv"); // DOES NOT COMPILE
		
		// Paths.get(URI)
		// A uniform resource identifier (URI) is a string of characters that identify a resource.
		// URIs must reference absolute paths at runtime. Next here is an example.
		path1 = Paths.get(new URI("file://pandas/cuddly.png")); // THROWS EXCEPTION AT RUNTIME
		path2 = Paths.get(new URI("file:///c:/zoo-info/November/employees.txt"));
		path3 = Paths.get(new URI("file:///home/zoodirectory"));
		try {
			Path path4 = Paths.get(new URI("http://www.wiley.com"));
			Path path5 = Paths.get(new URI("ftp://username:password@ftp.the-ftp-server.com"));
		} catch (ProviderNotFoundException p) {
		} catch (FileSystemNotFoundException f) {
		}
		
		// FileSystems.getPath().
		path1 = FileSystems.getDefault().getPath("pandas/cuddly.png");
		path2 = FileSystems.getDefault().getPath("c:","zooinfo","November","employees.txt");
		path3 = FileSystems.getDefault().getPath("/home/zoodirector");
		// FileSystems.getFileSystem() to connect to a remote file system
		try {
			FileSystem fileSystem = FileSystems.getFileSystem(new URI("http://www.selikoff.net"));
			Path path = fileSystem.getPath("duck.txt");
		} catch (ProviderNotFoundException p) {
		} catch (FileSystemNotFoundException f) {
		}		
		
		// Working with Legacy File Instances
		// Java 7, the legacy java.io.File class was updated with a new method, toPath()
		File file = new File("pandas/cuddly.png");
		Path path0 = file.toPath();
		// For backward compatibility
		path0 = Paths.get("cuddly.png");
		file = path0.toFile();
		
		// Providing Optional Arguments
		// NOFOLLOW_LINKS, FOLLOW_LINKS, COPY_ATTRIBUTES, REPLACE_EXISTING, ATOMIC_MOVE

		// Using Path Objects
		// Viewing the Path with toString(), getNameCount(), and getName()
		Path path = Paths.get("/");
		System.out.println("The Name count is: " + path.getNameCount()); // prints 0
		path = Paths.get("/land/hippo/harry.happy"); // the same as "land/hippo/harry.happy"
		System.out.println("The Path Name is: " + path);
		System.out.println("The Name count is: " + path.getNameCount()); // prints 3
		for (int i = 0; i < path.getNameCount(); i++) {
			System.out.println(" Element " + i + " is: " + path.getName(i));
		}
		
		// Accessing Path Components with getFileName(), getParent(), and getRoot()
		System.out.println("Filename is: " + path.getFileName());
		System.out.println("Root is: " + path.getRoot());
		Path currentParent = path;
		// This while loop continues until getParent() returns null.
		while ((currentParent = currentParent.getParent()) != null) {
			System.out.println(" Current parent is: " + currentParent);
		}
		
		// Checking Path Type with isAbsolute() and toAbsolutePath()
		// Keep in mind that if the Path object already represents an absolute path.
		path = Paths.get("C:\\birds\\egret.txt");
		System.out.println("Path1 is Absolute? " + path.isAbsolute());
		System.out.println("Absolute Path1: " + path.toAbsolutePath());
		path = Paths.get("birds/condor.txt");
		System.out.println("Path2 is Absolute? " + path.isAbsolute());
		System.out.println("Absolute Path2 " + path.toAbsolutePath());
		
		// Creating a New Path with subpath()
		path = Paths.get("/mammal/carnivore/raccoon.image");
		System.out.println("Path is: " + path);
		System.out.println("Subpath from 0 to 3 is: "+path.subpath(0,3));
		System.out.println("Subpath from 1 to 3 is: "+path.subpath(1,3));
		System.out.println("Subpath from 1 to 2 is: "+path.subpath(1,2));
		// System.out.println("Subpath from 0 to 4 is: "+path.subpath(0,4)); // THROWS EXCEPTION AT RUNTIME
		// System.out.println("Subpath from 1 to 1 is: "+path.subpath(1,1)); // THROWS EXCEPTION AT RUNTIME	
		
		// Deriving a Path with relativize()
		path1 = Paths.get("fish.txt");
		path2 = Paths.get("birds.txt");
		System.out.println(path1.relativize(path2)); // ..\birds.txt
		System.out.println(path2.relativize(path1)); // ..\fish.txt
		path3 = Paths.get("E:\\habitat");
		Path path4 = Paths.get("E:\\sanctuary\\raven");
		System.out.println(path3.relativize(path4)); // ..\sanctuary\raven
		System.out.println(path4.relativize(path3)); // ..\..\habitat
		// The relativize() method requires that both paths be absolute or both relative, 
		//   and it will throw an IllegalArgumentException
		path1 = Paths.get("/primate/chimpanzee");
		path2 = Paths.get("bananas.txt");
		// path1.relativize(path3); // THROWS EXCEPTION AT RUNTIME
		// On Windows, it requires that if absolute paths are used, then both paths must have 
		//   the same root directory or drive letter
		path3 = Paths.get("c:\\primate\\chimpanzee");
		path4 = Paths.get("d:\\storage\\bananas.txt");
		// path3.relativize(path4); // THROWS EXCEPTION AT RUNTIME
		
		// Joining Path Objects with resolve()
		path2 = Paths.get("food");
		System.out.println(path1.resolve(path2)); // \primate\chimpanzee\food
		// relativize() and resolve() does not clean up path symbols, like parent directory ..
		path1 = Paths.get("/turkey/food");
		path2 = Paths.get("/tiger/cage");
		System.out.println(path1.resolve(path2)); // /tiger/cage
		
		// Cleaning Up a Path with normalize()
		path1 = Paths.get("/cats/dogs/../panther");
		System.out.println(path1.normalize()); // \cats\panther
		
		// Checking for File Existence with toRealPath()
		// Similar to toAbsolutePath(), it can convert relative to absolute path, 
		//   except that it also verifies that the file referenced by the path actually exists,
		// it implicitly calls to normalize()
		try {
			String firstPart = "C:\\\\Users\\\\hp\\\\Documents\\\\GitHub\\\\gs-rest-service\\\\complete";
			System.out.println(Paths.get(firstPart).toRealPath());
			System.out.println(Paths.get(firstPart + "\\..\\..\\ipv4converter\\README.md").toRealPath());
			System.out.println(Paths.get(".").toRealPath());
		} catch (IOException e) {
		}				
		
	}
}
