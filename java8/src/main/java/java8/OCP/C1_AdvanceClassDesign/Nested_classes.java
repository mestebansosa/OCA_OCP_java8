package java8.OCP.C1_AdvanceClassDesign;

class AnonInner {
	interface SaleTodayOnly {
		int dollarsOff();
	}

	public int pay() {
		return admission(5, new SaleTodayOnly() {
			public int dollarsOff() {
				return 3;
			}
		});
	}

	public int payLambda() { // lambda is like an innerClass
		return admission(5, () -> 3);
	}

	public int admission(int basePrice, SaleTodayOnly sale) {
		return basePrice - sale.dollarsOff();
	}
}
	

public class Nested_classes {
	
	public static class NestedStatic {
		private int price = 6;
	}
	
	public class Inner {
		private String greeting = "Hi";

		protected class Inner2 {
			public int repeat = 3;

			public void go() {
				for (int i = 0; i < repeat; i++)
					System.out.println(greeting); // Can access Outer even private members.
			}
		}

		public void callInner() {
			Inner2 inner = new Inner2();
			inner.go();
		}
	}
		
	public static void main(String[] args) {
		// A nested class is a class that is defined within another class. 
		// A nested class that is not static is called an inner class. 
		// There are four of types of nested classes:
		
		// - Member inner class, defined at the same level as instance variables. It is not static.
		// Can be declared public, private, or protected or use default access
		// Can extend any class and implement interfaces
		// Can be abstract or final
		// Cannot declare static fields or methods
		// Can access members of the outer class including private members
		// Compiler creates Outer$Inner.class.
		Nested_classes c1 = new Nested_classes();
		Inner outer = c1.new Inner();
		outer.callInner();
		// other access way
		Nested_classes.Inner.Inner2 inner = outer.new Inner2();
		inner.go();
		
		// - A local inner class is defined within a method. Exists when method is invoked.
		// They do not have an access specifier.
		// They cannot be declared static and cannot declare static fields or methods.
		// They have access to all fields and methods of the enclosing class
		// They have access to local variables of a method if they are final or effectively final.
		// In java8 effectively final is a variable only initialize once, without final keyword.
		// Example in page 28
		
		// - An anonymous inner class is a special case of a local inner class that does not have a name.
		// Example above in class AnonInner
		
		// - A static nested class is a static class that is defined at the same level as static variables.
		// The nesting creates a namespace because the enclosing class name must be used to refer to it.
		// It can be made private or use one of the other access modifiers to encapsulate it.
		// The enclosing class can refer to the fields and methods of the static nested class.
		NestedStatic nestedStatic1 = new Nested_classes.NestedStatic();
		System.out.println(nestedStatic1.price);	
		// import static java8.OCP.C1_Nested_classes.NestedStatic;
		NestedStatic nestedStatic2 = new NestedStatic();
		System.out.println(nestedStatic2.price);	
	}
	
}
