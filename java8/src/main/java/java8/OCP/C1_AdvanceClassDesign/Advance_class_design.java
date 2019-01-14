package java8.OCP.C1_AdvanceClassDesign;

interface Animal {
	public void feed();
	String name = "???";
	default public void printName() {
		System.out.println(name);
	}
}	

class Bobcat {
	public void findDen() {};
}

public class Advance_class_design {
	public void feedAnimal(Animal animal) {
		animal.feed();
		animal.printName();
	}		

	public static void main(String[] args) {
		// OCA concepts
		// Access modifiers: public, protected, and private and default.

		// Oveloading and Overriding. 
		// Method signature is method name and parameter list.
		
		/*
		For overriding, the overridden method has a few rules:
			- The access modifier must be the same or more accessible.
			- The return type must be the same or a more restrictive type, also known as covariant
			return types.
			- If any checked exceptions are thrown, only the same exceptions or subclasses of those
			exceptions are allowed to be thrown.
		The methods must not be static. (If they are, the method is hidden and not overridden.)
		*/
		
		class BobcatKitten extends Bobcat {
			@Override
			public void findDen() {
			} // override

			public void findDen(boolean b) {
			} // overload

			public int findden() throws Exception {
				return 0;
			} // new method.
		}

		// Abstract class
		// may contain any number of methods including zero. 
		// The methods can be abstract or concrete. 
		// Abstract methods may not appear in a class that is not abstract.
		// The first concrete subclass of an abstract class is required to implement all
		// abstract methods

		// static and final. 
		// Final prevents a variable from changing or a method from being overridden.
		// Static makes a variable shared at the class level and uses the class name to
		// refer to a method.

		// instanceOf
		// In "a instanceof B", the expression returns true if the reference to which "a" points
		// is an instance of class B, a subclass of B (directly or indirectly), or a class that 
		// implements the B interface (directly or indirectly).
		class HeavyAnimal {
		}
		class Hippo extends HeavyAnimal {
		}
		class Elephant extends HeavyAnimal {
		}
		HeavyAnimal hippo = new Hippo();
		boolean b1 = hippo instanceof Hippo; // true
		boolean b2 = hippo instanceof HeavyAnimal; // true
		boolean b3 = hippo instanceof Elephant; // false
		boolean b4 = hippo instanceof Object; // true
		
		Hippo nullHippo = null; // null is not an object
		boolean b5 = nullHippo instanceof Object; // false
		
		Hippo anotherHippo = new Hippo();
		// boolean b6 = anotherHippo instanceof Elephant; // DOES NOT COMPILE.
		
		// The compilation check only applies when instanceof is called on a class. When
		// checking whether an object is an instanceof an interface, Java waits until
		// runtime to do the check.
		
		// Virtual Method Invocation. 
		// It does not work for instance variables.
		// Is this polymorphism? Yes. Polymorphism includes overloading and overriding
		class Cow implements Animal {
			String name = "Cow";
			public void feed() { addHay(); }
			private void addHay() { }
		}
		class Bird implements Animal {
			@Override
			public String toString() {
				return "Bird [name=" + name + "]";
			}
			String name = "Bird";
			public void feed() { addSeed(); }
			private void addSeed() { }
			@Override
			public void printName() {};
		}
		// See feedAnimal method above.
		Animal cow = new Cow();
		new Advance_class_design().feedAnimal(cow); // will print ???
		
		// Annotating Overridden methods
		// An annotation is extra information about the program, and it
		//   is a type of metadata. It can be used by the compiler or even at runtime.
		// The @Override annotation is used to express that you, the programmer, intend for this
		//   method to override one in a superclass or implement one from an interface.
		// @Override is allowed only when referencing a method
		// 3 cases: Implementing a method from an interface
		//       Overriding a superclass method of a class shown in the example
		//       Overriding a method declared in Object, such as hashCode, equals, or toString
		
		// Object: toString, equals and hashCode
		// toString() with Apache Commons: print outs all the attributes of the class
			/*
			public String toString() {
				return ToStringBuilder.reflectionToString(this);
				}
			 */
		// equals(). There are several rules to override equals. If x.equals(null) return false
		/*
			@Override 
			public boolean equals(Object obj) {
				if ( !(obj instanceof Lion)) return false;
				Lion otherLion = (Lion) obj;
				return this.idNumber == otherLion.idNumber;
			}
		 */
		// hashCode(). Whenever you override equals(), do the same with hashCode(). 
		// 		public int hashCode() { return idNumber; }
		// So, variables used in equals() should be used in hashCode()
		// If equals() returns true when called with two objects, calling hashCode() on
		//   each of those objects must return the same result.
		// If equals() returns false when called with two objects, calling hashCode() on
		//   each of those objects does not have to return a different result.
		
	}

}
