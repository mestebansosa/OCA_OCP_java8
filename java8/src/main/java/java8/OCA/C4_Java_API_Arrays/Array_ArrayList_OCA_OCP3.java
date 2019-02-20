package java8.OCA.C4_Java_API_Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class Array_ArrayList_OCA_OCP3 {
	public static void main(String[] args) {
		// Arrays stores its elements (objects or primitives) continuously. Thats is
		// better for the performance.
		// ArrayList contain objects but not primitives. List is like resizable array.

		// Declaration with any type (primitive, interface, abstract or concrete class)
		// but not with null type
		int intArray[];
		String[][] stringArray; // is preferred [] next to the type.
		char[] charArray[];

		// Allocation with new. When defined it cannot be changed. The allocation insert
		// the default values of the type.
		intArray = new int[30];
		intArray = new int[3*10];
		stringArray = new String[2][10];
		charArray = new char[2][]; // OK to define only the first dimension.
		// char[][] charArray2 = new char[][2]; // Not OK
		// intArray = new int[]; // Not OK
		// intArray[2] = new int; // Not OK
		// intArray = new int[3.0]; // Not OK

		// Initialization
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = i;
		}
		// int a = intArray[399]; // ArrayIndexOutOfBoundsException runtime
		// int a = intArray[-10]; // ArrayIndexOutOfBoundsException runtime
		// int a = intArray[-3]; // the same

		// Declaration, Allocation and Initialization, all together
		// you can't specify the size of the array.
		String[] strArray = new String[] { "Hola", "adios" };
		String[] strArray2 = { "Hola", "adios" };
		String[] strArray3[] = { { "Hola", "adios" }, null, { "pepe", "juan", "maria" } }; // Asymmetrical
		// String strArray2[2] = {"Hola","adios"}; // Won't compile
		int ia[];
		// ia = {0,1,2}; Won't compile
		ia = new int[] { 0, 1, 2 };
		int ia2[] = { 0, 1, 2 };

		// Asymmetrical: an array can define a different number of columns for each of its rows.

		// Array of objects, or interfaces of abstract classes.
		Object[] objArray = new Object[] { new String(), null, new Integer(5) };

		// Take care of the length. It is an attribute in Array and a method() in String.
		String a = new String();
		System.out.println(a.length());
		System.out.println(strArray.length);

		// java.utils.Arrays. 
		// This class contains various methods for manipulating arrays (such as sorting and searching). 
		// This class also contains a static factory that allows arrays to be viewed as lists.
		int[] numbers = { 6, 9, 1, 8 };
		Arrays.sort(numbers); // [1,6,8,9]
		Arrays.asList(numbers);
		Arrays.asList(9, 7, 5, 3);
		System.out.println(Arrays.binarySearch(numbers, 6)); // 1
		System.out.println(Arrays.binarySearch(numbers, 3)); // -2. Number 3 would be inserted at index 1. -1-1= -2


		// ArrayList 
		// internally uses an array to store its data
		// Is a resizable array
		// No needed to specify initial size.
		// Allows null and duplicates. Preserves the order of insertion
		// Supports generics.

		// java.util.Collections. 
		// This class consists exclusively of static methods that operate on or return collections. 
		// It contains polymorphic algorithms that operate on collections, "wrappers", 
		// which return a new collection backed by a specified collection, and a few other odds and ends.
		// Search and Sorting
		List<Integer> list = Arrays.asList(9, 7, 5, 3);
		Collections.sort(list); // [3, 5, 7, 9]
		System.out.println(Collections.binarySearch(list, 3)); // 0
		System.out.println(Collections.binarySearch(list, 2)); // -1. Number 2 would be at index 0. 0-1 = -1

		ArrayList<String> arrayList = new ArrayList<>();  // diamond operator from Java 7
		List<String> arrayList2 = new ArrayList<>(); // Better assigning to the interface
		arrayList2.add("pepe");
		arrayList2.add(new String("pepe"));
		for (String string : arrayList2) {
			System.out.println(string);
		}
		ListIterator<String> iter = arrayList2.listIterator(); // Iterator lets you remove items as you iterate.
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		ArrayList<String> arrayList3 = new ArrayList<String>() {
			{
				add("one");
				add("two");
				add("three");
				add("four");
			}
		};
		String[] arrayList33 = {"one","two","three","four"};

		// Changing reflected in both
		String[] array = { "gerbil", "mouse" }; // [gerbil, mouse]
		List<String> listCopy = Arrays.asList(array); // returns fixed size list
		listCopy.set(1, "test"); // [gerbil, test]
		array[0] = "new"; // [new, test]
		String[] array2 = (String[]) listCopy.toArray(); // [new, test]
		// listCopy.remove(1); // throws UnsupportedOperationException
		// listCopy.add("another"); // throws UnsupportedOperationException
		
		ArrayList<String> arrayListAddAll = new ArrayList<String>();
		List<String> arrayListAsList = new ArrayList<String>();
		ArrayList<String> arrayListAssignation = new ArrayList<String>();
		ArrayList<String> arrayListClone = new ArrayList<String>();
		
		arrayListAddAll.addAll(arrayList3); // append
		arrayListAsList = Arrays.asList(arrayList33); // copies references. So both are modified.
		arrayListAssignation = arrayList3; // copies.
		arrayListClone = (ArrayList<String>) arrayList3.clone(); // clone is a copy
		arrayList33[1] = "XXXXXXXX";
		System.out.println("arrayList3:" + arrayList3);
		System.out.println("arrayListAddAll:" + arrayListAddAll);
		System.out.println("arrayListAsList:" + arrayListAsList);
		System.out.println("arrayListAssignation:" + arrayListAssignation);
		System.out.println("arrayListClone:" + arrayListClone);

		// Arrays.asList creates an unmodifiable list, so cannot add or remove elements
		List<String> places = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");
		// places.add("Madrid"); // UnsupportedOperationException
		// places.remove("La Plata"); // UnsupportedOperationException
		places.set(1, "La Plata"); // allowed
		List<String> places2 = Arrays.asList("Madrid", "Lerida", "Gerona");
		System.out.println("places2:" + places2);
		
		arrayList3.add("Madrid");
		arrayList3.remove("Gerona");
		System.out.println(arrayList3);
		arrayList3.clear();
		System.out.println(arrayList3);
		
		// Wrapper Classes and Autoboxing
		// Autoboxing automatically converts a primitive to the corresponding wrapper classes when needed. 
		// Unsurprisingly, unboxing automatically converts a wrapper class back to a primitive.
		// primitives are: boolean byte, short, int, long, float, double and char
		// wrappers   are: Boolean Byte, Short, Integer, Long, Float, Double and Character

	}
}
