package java8;

public class Binding {
	/*
	 Important Points	 
		- private, final and static members (methods and variables) use static binding 
		     while for virtual methods (In Java methods are virtual by default) binding 
		     is done during run time based upon run time object.
		- Static binding uses Type information for binding while Dynamic binding uses 
		     Objects to resolve binding.
		- Overloaded methods are resolved (deciding which method to be called when there are
		     multiple methods with same name) using static binding while overridden methods 
		     using dynamic binding, i.e, at run time.
	 */
	
	
	// Static Binding: 
	// The binding which can be resolved at compile time by compiler is known as static or early binding. 
	// Binding of all the static, private and final methods is done at compile-time .
	// Static binding is better performance wise (no extra overhead is required). 
	
	// Dynamic Binding: 
	// In Dynamic binding compiler doesn’t decide the method to be called. 
	// Overriding is a perfect example of dynamic binding. 
	// In overriding both parent and child classes have same method .
	
	public static void main(String[] args) {
	}

}
