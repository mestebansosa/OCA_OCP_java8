package java8.OCP.C2_DesignPattern_Principles;

public class Polymorphism {
	class Primate {
		public boolean hasHair() {
			return true;
		}
	}

	interface HasTail {
		public boolean isTailStriped();
	}

	class Lemur extends Primate implements HasTail {
		public int age = 10;

		public boolean isTailStriped() {
			return false;
		}
	}
	
	public static void main(String[] args) {
		// Polymorphism is the ability of a single interface to support multiple underlying forms.
		// A Java object may be accessed using:
		//   a reference with the same type as the object, 
		//   a reference that is a superclass of the object, or 
		//   a reference that defines an interface that the object implements, either directly or through a superclass. 
		// Furthermore, a cast is not required if the object is being reassigned to a supertype or interface of the object.
			
		Lemur lemur = new Polymorphism().new Lemur();
		System.out.println(lemur.age);
		HasTail hasTail = lemur;
		System.out.println(hasTail.isTailStriped());
		// System.out.println(hasTail.age); // DOES NOT COMPILE
		Primate primate = lemur;
		System.out.println(primate.hasHair());
		// This code compiles and yields the following output: 10 false true
		
		// Distinguishing between an Object and a Reference
		// In Java, all objects are accessed by reference, so as a developer 
		// you never have direct access to the memory of the object itself.
		
		// We can summarize this principle with the following two rules:
		// 1. The type of the object determines which properties exist within the object in memory.
		// 2. The type of the reference to the object determines which methods and variables are
		//    accessible to the Java program.
		// The same object exists in memory regardless of which reference is pointing to it.
		// Depending on the type of the reference, we may have access only to certain methods.

		// Casting Object References
		/*
		Here are some basic rules to keep in mind when casting variables:
			1. Casting an object from a subclass to a superclass doesn’t require an explicit cast.
			2. Casting an object from a superclass to a subclass requires an explicit cast.
			3. The compiler will not allow casts to unrelated types.
			4. Even when the code compiles without issue, an exception may be thrown at runtime if
			the object being cast is not actually an instance of that class.
		 */
		// Use the instanceof prior to casting to avoid throwing ClassCastException at runtime.
	}	
	
}
