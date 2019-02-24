package java8.OCP.C2_DesignPattern_Principles;

public class DesignPrinciples {
	String name;

	public void setName(String name) {
		// invariant. Good encapsulation
		this.name = (name == null || name.trim().length() == 0) ? null : name;
	}
	
	// Object compostion.
	public class Flippers {
		public void flap() {
			System.out.println("The flippers flap back and forth");
		}
	}

	public class WebbedFeet {
		public void kick() {
			System.out.println("The webbed feet kick to and fro");
		}
	}

	public class Penguin {
		private final Flippers flippers; // Object composition. Has-a sense
		private final WebbedFeet webbedFeet;

		public Penguin() {
			this.flippers = new Flippers();
			this.webbedFeet = new WebbedFeet();
		}

		public void flap() {
			this.flippers.flap();
		}

		public void kick() {
			this.webbedFeet.kick();
		}
	}			
	
	public static void main(String[] args) {
		// A design principle is an established idea or best practice that facilitates
		// the software design process.
		// More logical code, Code that is easier to understand, Classes that are easier
		// to reuse, Code that is easier to maintain
		
		// A data model is the representation of our objects and their properties within
		// our application and how they relate to items in the real world.
		
		// Encapsulation Data
		// Encapsulation is the idea of combining fields and methods in a class such
		// that the methods operate on the data, as opposed to the users of the class
		// accessing the fields directly.
		// Getters and Setters
		
		// With encapsulation, a class is able to maintain certain invariants about its internal data.
		// An invariant is a property or truth that is maintained even after the data is modified.
		// For instance: Each animal has an age field that is greater than or equal to zero
		// By using private members we can ensure that these invariants remain true.
		
		// A JavaBean is a design principle for encapsulating data in an object in Java.
		// Properties are private.
		// Getter for non‐boolean properties begins with get.
		// Getters for boolean properties may begin with is or get.
		// Setter methods begin with set.
		// The method name must have a prefix of set/get/is followed by the first letter
		//    of the property in uppercase and followed by the rest of the property name.	
		// public Boolean isDancing() { return dancing; } Is INCORRECT
		
		// Is‐a Relationship (the inheritance test)
		// Example: 
		//   Cat   is-a Feline is-a Animal implements Pet 
		//   Tiger is-a Feline is-a Animal.
		//   Dog               is-a Animal implements Pet
		
		// Has‐a Relationship (the object composition test)
		// private members are not inherited in Java.
		
		// Composing Objects
		// Object composition is the property of constructing a class using references
		// to other classes in order to reuse the functionality of the other classes.
		// In particular, the class contains the other classes in the has‐a sense and
		// may delegate methods to the other classes.
		// Object composition should be thought of as an alternate to inheritance and is
		// often used to simulate polymorphism behavior that cannot be achieved via single
		// inheritance.	
		
		// One of the advantages of object composition over inheritance is that it tends
		// to promote greater code reuse.
		
	}	
	
}
