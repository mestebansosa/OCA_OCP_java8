package java8.OCA.C6_Inheritance;

public class Interfaces_OCA_OCP_C2 {
	
	public interface Athelete {
		int speed();  // Abstract method: public abstract, by default.
		default void hola() {}; // Default method. new in java8. Define an implementation.

		static void adios() {
		}; // Static method. new in java8. Define utility methods. Must be calling using
			// the interface name. Cannot be overriding, it is static to the object.
		
		double distance = 70; // public static final
	}
	
	public interface Runner extends Athelete {
		int speed();  // public abstract
		double distance = 70; // public static final (it is an interface constant)
	}

	// implementing multiple interfaces with the same constant name.
	interface Jumpable {
		int MIN_DISTANCE = 10;
		String currentPosition();
		default void relax() {};
		static int maxDistance() { // static are never inherited
			return 100;
		}
	}
	interface Moveable {
		int MIN_DISTANCE = 100;
		void currentPosition();
		default void relax() {};
		static String maxDistance() {
			return "100";
		}
	}
	
	class Animal implements Jumpable, Moveable {
		Animal() {
			System.out.println(Jumpable.MIN_DISTANCE);
			// currentPosition(); // It is ambiguos. Don't compile
			Moveable.maxDistance();
			Jumpable.maxDistance();
		}
		// public String currentPosition() {}; // Won't compile
		// public void currentPosition() {}; // Won't compile
		public void relax() {}; // if overriden then it compiles. If not, don't compile due to ambiguity

	}
	
	public static void main(String[] args) {
    	// Interface is specifications or contract. A set of rules. 
		// Is an abstract data type.
    	// with java8 an interface can define:
		//    - constant public static final variables
    	//    - the default method implementation
    	//    - static methods
    	
    	// interface vs. abstract class.
		// both cannot be instantiated.
		// Interface can be instantiated in case we use anonymous inner class in the code.
    	// abstract class has constructor. It is a class. And it has a state.
    	// interface permits multiple inheritance, not a class.
    	// new interfaces are prepared to Stream collections.

		// An interface can extend another interface, but not a class.
		// A class can extend another class, but not an interface.
		
		// One team then develops code that uses the interfaces while the
		// other team develops code that implements the interface.
		// The developer using the interface can create a temporary mock/dummy object
		
		// access modifiers: public or no modifier (defautl access) for all (interface and members)
		// nonaccess modifiers: abstract or strictfp for interface
		
		// Reference variable and object types: objects of derived classes can be referred to using a 
		// reference variable of: its own type, its superclass or implemented interfaces.
    	
    }			
}
