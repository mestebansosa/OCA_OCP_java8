package java8.OCA.C4_Java_API_Arrays;

public class String_OCA_OCP_C5 {
    public static void main(String[] args) {
    	// String. Create
    	// default value for string is null.
    	String str1 = new String("Paul"); // new object, never in string pool (or String constant pool)
    	String str2 = new String("Paul");
    	// The operator == compares the addresses.
    	System.out.println(str1==str2); // false
    	String str3 = "Harry"; // new string and set in the string pool
    	String str4 = "Harry"; // found at string pool, so reused.
    	System.out.println(str3==str4); // true
    	
    	System.out.println("Otra forma de crear un string"); // add to string pool
    	
    	char[] name = new char[] {'P','a','u','l'};
    	String boy = new String(name); // another way to create a string
    	// and with StringBuilder and StringBuffer.

    	// String is inmutable. JVM reuse String objects, reduce memory overhead and best performance.
    	// String uses a char array to store its value: 
    	//    private final char value[]; The arrays are fixed in size. They can't grow after initialization
    	// String methods do not modify the value.
    	
    	// Methods
    	System.out.println(str1.charAt(0));
    	System.out.println(str1.indexOf('a'));
    	System.out.println(str1.indexOf('a',2));
    	System.out.println(str1.substring(1)); // creates a new String
    	System.out.println(str1.substring(1,2)); // is the same as substring(1). Does not include the last.
    	System.out.println(str1.trim()); // Does not remove the spaces (\n,\t,\ ) within the string.
    	System.out.println(str1.replace('a', 'b')); // creates a new String
    	System.out.println(str1.length()); 
    	System.out.println(str1.startsWith("P")); 
    	System.out.println(str1.endsWith("ul")); 
    	System.out.println(str1.substring(1,2).trim().startsWith("o")); // chaining
    	
    	// String objects and operators.
    	// the + operator create a new object.
    	String initializedToNull = null;
    	initializedToNull += "Pepe";
    	System.out.println(initializedToNull); // nullPepe
    	
    	// equals()
    	/* Contract:
    	 * 1.- if reference variables (this==anObject) are the same return true.
    	 * 2.- compares the type (anObject instanceOf String). Else false.
    	 * 3.- check length (count). 
    	 * 4.- compares char by char.
    	 */
    	// never use == to compare two strings.
    	
    	// StringBuilder. Mutable String
    	//     char value[];
    	// creation
    	StringBuilder sb1 = new StringBuilder();
    	// methods
    	System.out.println(sb1.append("hola")); // at the end
    	System.out.println(sb1.append("hola",1,3)); // holaol
    	System.out.println(sb1.insert(2,"hola")); // at a position. hoholalaol
    	System.out.println(sb1.delete(2, 6)); // holaol
    	System.out.println(sb1.deleteCharAt(sb1.length()-1)); // holao
    	// there is no trim() 
    	System.out.println(sb1.reverse()); // oaloh
    	System.out.println(sb1.replace(2, 4, "ABBA")); // oaABBAh
    	System.out.println(sb1.substring(2, 6)); // ABBA
    	System.out.println(sb1.subSequence(2, 6)); // ABBA
    	
    	// StringBuffer. Same as StringBuilder but all the methods are synchronized.    	
    	
    }			
}
