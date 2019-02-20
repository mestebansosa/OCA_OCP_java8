package java8.OCA.C2_Java_Types;

public class Wrapper_classes_autoboxing {
    public static void main(String[] args) {
    	// wrapper classes are immutable.
    	// wrapper are: Boolean, Character, Byte, Short, Integer, Long, Float and Double 
    	// all can be serialized to a stream.
    	
    	// Autoboxing 
    	//   automatically converts a primitive to the corresponding wrapper classes
    	// Creating
    	// assignment
    	Boolean bool1 = true;
    	// Constructor. There is not no-argument constructor.
    	bool1 = new Boolean(true);
    	bool1 = new Boolean("true"); // all constructors accept String, except Character.
    	// Using static method
    	bool1 = Boolean.valueOf(true);
    	bool1 = Boolean.valueOf("TrUe");
    	Double.valueOf(5); 
    	
    	// Unboxing 
    	//    with <primitive>Value(). Automatically converts a wrapper class back to a primitive.
    	boolean bool2 = bool1.booleanValue();
    	// parsing a string value to a primitive, with parse<DataType>(String)
    	Boolean.parseBoolean("true");
    	Double.parseDouble("10.34");
    	
    	// comparing with == and equals()
    	Integer i1 = new Integer(10); // new object
    	Integer i2 = new Integer(10);
    	System.out.println(i1==i2); // false
    	System.out.println(i1.equals(i2)); // true
    	
    	// cached instances: true/false, Character from 0/127, Integer -127/128. Not for Double or Float
    	i1 = 10; // a cached copy for int 10.
    	i2 = 10; 
    	System.out.println(i1==i2); // true
    	System.out.println(i1.equals(i2)); // true

    	i1 = 1000; // there are no cached copy for int 1000.
    	i2 = 1000; 
    	System.out.println(i1==i2); // false
    	System.out.println(i1.equals(i2)); // true
    	
    	Integer o1 = 100;
    	Short o2 = 100;
    	// System.out.println(o1==o2); // does not compile
    	System.out.println(o1.equals(o2)); // false
    	
    	// Arithmetic operators like += cannot be used with objects. ??
    	Integer int1 = new Integer(100);
    	System.out.println(int1 += 1);
   	
    	// unboxing a wrapper reference variable, which refers to null, throws NullPointerException.
    	
    	// Use always StringBuilder and not StringBuffer as the last is synchronous
    }			
}
