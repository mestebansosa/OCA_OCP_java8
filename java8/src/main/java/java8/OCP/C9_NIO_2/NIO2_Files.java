package java8.OCP.C9_NIO_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;

public class NIO2_Files {

	public static void main(String[] args) throws URISyntaxException {
		// Interacting with Files
		// java.nio.file.Files
		// NIO.2 Files helper class is in no way related to the File class, 
		// as the Files class operates on Path instances, not File instances. 
		// Keep in mind that File belongs to the legacy java.io API, while Files belongs to the NIO.2 API.
		
		// Testing a Path with File.exists(Path) 
		System.out.println(Files.exists(Paths.get("/ostrich")));
		// Testing Uniqueness with Files.isSameFile(Path,Path). Not the content.
		// if two Path objects relate to the same file within the file system.
		try {
			System.out.println(Files.isSameFile(Paths.get("/user/home/cobra"), Paths.get("/user/home/snake")));
			System.out.println(Files.isSameFile(Paths.get("/user/tree/../monkey"), Paths.get("/user/monkey")));
			System.out.println(Files.isSameFile(Paths.get("/leaves/./giraffe.exe"), Paths.get("/leaves/giraffe.exe")));
			System.out.println(Files.isSameFile(Paths.get("/flamingo/tail.data"), Paths.get("/cardinal/tail.data")));
		} catch (IOException e) {
			System.out.println("Files.isSameFile(Path,Path) checks that both files exist within the file system");
			// Handle file I/O exception...
		}
		
		// Making Directories with createDirectory() and createDirectories()
		// To create directories in the legacy java.io API, we called mkdir() or mkdirs() on a File object. 
		// In the NIO.2 API, we can use the Files.createDirectory(Path) method to create a directory. 
		// createDirectories(), like mkdirs() is mkdir -p
		
		// Duplicating File Contents with Files.copy(Path,Path)
		// Directory copies are shallow rather than deep, meaning that files and
		// subdirectories within the directory are not copied.
		try {
			Files.copy(Paths.get("/panda"), Paths.get("/panda-save"));
			Files.copy(Paths.get("/panda/bamboo.txt"), Paths.get("/panda-save/bamboo.txt"));
		} catch (IOException e) {
			// Handle file I/O exception...
		}
		// Copying Files with java.io and NIO.2
		try (InputStream is = new FileInputStream("source-data.txt");
				OutputStream out = new FileOutputStream("output-data.txt")) {
			// Copy stream data to file
			Files.copy(is, Paths.get("c:\\mammals\\wolf.txt"), StandardCopyOption.ATOMIC_MOVE);
			// Copy file data to stream
			Files.copy(Paths.get("c:\\fish\\clown.xsl"), out);
		} catch (IOException e) {
			// Handle file I/O exception...
		}
		
		// Changing a File Location with move()
		// By default, follow links, exception if the file already exists, and not perform an atomic move.
		// While moving an empty directory across a drive is supported, 
		// moving a non-empty directory across a drive will throw DirectoryNotEmptyException
		try {
			Files.move(Paths.get("c:\\zoo"), Paths.get("c:\\zoo-new"));
			Files.move(Paths.get("c:\\user\\addresses.txt"), Paths.get("c:\\zoo-new\\addresses.txt"));
		} catch (IOException e) {
			// Handle file I/O exception...
		}
		
		// Removing a File with delete() and deleteIfExists()
		// The Files.delete(Path) method deletes a file or empty directory within the file system.
		// DirectoryNotEmptyException
		// The deleteIfExists(Path) will not throw an exception if the file or directory does not exist.
		
		// Reading and Writing File Data with newBufferedReader() and newBufferedWriter()
		// Files.newBufferedReader(Path,Charset) using a java.io.BufferedReader object
		Path path = Paths.get("/animals/gopher.txt");
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("US-ASCII"))) {
			// Read from the stream
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null)
				System.out.println(currentLine);
		} catch (IOException e) {
			// Handle file I/O exception...
		}	
		
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-16"))) {
			writer.write("Hello World");
		} catch (IOException e) {
			// Handle file I/O exception...
		}		
		
		// Reading Files with readAllLines()
		try {
			final List<String> lines = Files.readAllLines(path);
			for (String line : lines) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// Handle file I/O exception...
		}
		
		// Understanding File Attributes
		// In this context, file metadata is data about the file or directory record
		// within the file system and not the contents of the file
		
		// Reading Common Attributes with isDirectory(), isRegularFile(), and isSymbolicLink()
		// regular file: one that contains content.
		// if isSymbolicLink() is true, then isRegularFile() is true it the target is a regular file.
		// Note these methods do not throw exception is path does not exist.
		if(Files.exists(path) && Files.isDirectory(path)) {} // redundant
		if(Files.isDirectory(path)) {} // better
		
		// File Visibility with isHidden()
		// File Accessibility with isReadable() and isExecutable()
		// Note these methods do not throw exception is path does not exist, instead return false.
		
		// File Length with size(). only on files
		
		// Managing File Modifications with getLastModifiedTime() and setLastModifiedTime()
		// it is a lot faster to check a single file metadata attribute than to reload the entire file
		// The FileTime is a simple container class that stores the date/time
		// information about when a file was accessed, modified, or created
		try {
			path = Paths.get("/rabbit/food.jpg");
			System.out.println(Files.getLastModifiedTime(path).toMillis());
			Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
			System.out.println(Files.getLastModifiedTime(path).toMillis());
		} catch (IOException e) {
			// Handle file I/O exception...
		}	
		
		// Managing Ownership with getOwner() and setOwner()
		// UserPrincipal Files.getOwner(Path) and Files.setOwner(Path, UserPrincipal)
		// To set a file owner to an arbitrary user, the NIO.2 API provides a
		//   UserPrincipalLookupService helper class for finding a UserPrincipal record
		//   for a particular user within a file system.		
		try {
			// Read owner of file
			path = Paths.get("C:\\Users\\hp\\Documents\\mongoProc.log");
			System.out.println(Files.getOwner(path).getName());
			// Change owner of file
			UserPrincipal owner = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("jane");
			Files.setOwner(path, owner);
			// Output the updated owner information
			System.out.println(Files.getOwner(path).getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}	
		
		// Improving Access with Views
		// Far more efficient to retrieve all file metadata attributes in a single call.
		// A view is a group of related attributes for a particular file system type. 
		// A file may support multiple views, allows you to retrieve/update sets of information of file.
		
		// Files.readAttributes(Path,Class<A>), returns a read-only view of the file view. 
		BasicFileAttributes data;
		try {
			data = Files.readAttributes(path, BasicFileAttributes.class);
			System.out.println("Is path a directory? "+ data.isDirectory());
			System.out.println("Is path a regular file? "+ data.isRegularFile());
			System.out.println("Is path a symbolic link? "+ data.isSymbolicLink());
			System.out.println("Path not a file, directory, nor symbolic link? "+
			data.isOther());
			System.out.println("Size (in bytes): "+ data.size());
			System.out.println("Creation date/time: "+ data.creationTime());
			System.out.println("Last modified date/time: "+ data.lastModifiedTime());
			System.out.println("Last accessed date/time: "+ data.lastAccessTime());
			System.out.println("Unique file identifier (if available): "+data.fileKey());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// BasicFileAttributeView is used to modify a file’s set of date/time values.
		BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
		try {
			BasicFileAttributes data2 = view.readAttributes();
			FileTime lastModifiedTime = FileTime.fromMillis(data2.lastModifiedTime().toMillis() + 10_000);
			view.setTimes(lastModifiedTime, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
