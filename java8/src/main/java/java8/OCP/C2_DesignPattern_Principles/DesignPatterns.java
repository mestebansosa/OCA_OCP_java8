package java8.OCP.C2_DesignPattern_Principles;

import java.util.ArrayList;
import java.util.List;

public class DesignPatterns {
	
	// Example of immutable class that accomplishes the 5 rules.
	public final class Animal {
		private final String species;
		private final int age;
		private final List<String> favoriteFoods;

		public Animal(String species, int age, List<String> favoriteFoods) {
			this.species = species;
			this.age = age;
			if (favoriteFoods == null) {
				throw new RuntimeException("favoriteFoods is required");
			}
			this.favoriteFoods = new ArrayList<String>(favoriteFoods); // IMPORTANT.
		}

		public String getSpecies() {
			return species;
		}

		public int getAge() {
			return age;
		}

		public int getFavoriteFoodsCount() {
			return favoriteFoods.size();
		}

		public String getFavoriteFood(int index) {
			return favoriteFoods.get(index);
		}
	}
	
	public class AnimalBuilder {
		private String species;
		private int age;
		private List<String> favoriteFoods;

		public AnimalBuilder setAge(int age) {
			this.age = age;
			return this;
		}

		public AnimalBuilder setSpecies(String species) {
			this.species = species;
			return this;
		}

		public AnimalBuilder setFavoriteFoods(List<String> favoriteFoods) {
			this.favoriteFoods = favoriteFoods;
			return this;
		}

		public Animal build() {
			// Alternatively, it may also set default values for anything the user failed to specify
			return new Animal(species, age, favoriteFoods);
		}
	}
	
	public static void main(String[] args) {
		// A design pattern is an established general solution to a commonly occurring SW development problem.
		// Common vocabulary.
		
		// Types, according to the Gang of four book (GoF Patterns).
		// Creational: Factory Method, Abstract Factory, Builder, Prototype, Singleton
		// Structural: Adapter, Bridge, Composite, Decorator, Facade, Proxy
		// Behavioral: Interpreter, Template Method, Chain of responsibility, Command,
		//    Iterator, Mediator, Memento, Flyweight, Observer, State, Strategy, Visitor.
		
		// Creational
		// Level of indirection to object creation by creating the object in some other class.
		
		// Singleton (only one instance)
		// only one instance of an object in memory within an application, sharable by all classes and threads.
		// By marking the constructors private, we have implicitly marked the class effectively final. 
		// Instantiation the singleton object:
		//   1.- directly in the definition of the instance reference
		/*
		      private HayStorage() {}  // with this the class is final.
		      private static final HayStorage instance = new HayStorage();
		      public static HayStorage getInstance() { return instance; }
		      and marked all the other methods with synchronized.
		*/
		//   2.- Instantiation using a static block
		/*
			public class StaffRegister {
				private static final StaffRegister instance;
				static {
					instance = new StaffRegister();
					// Perform additional steps
				}
				private StaffRegister() {}
		*/
		//   3.- Lazy Instantiation
		//       reduces memory usage and improves performance when an application starts up. 
		//       delay creation of the singleton until the first time the getInstance() method is called:
		/*
			public class VisitorTicketTracker {
				private static VisitorTicketTracker instance;  // NO FINAL AS IS LAZY INIT
				private VisitorTicketTracker() {}
				public static VisitorTicketTracker getInstance() {
					if(instance == null) {
						instance = new VisitorTicketTracker(); // NOT THREAD-SAFE!
					}
					return instance;
				}
		*/
		// To be thread-safe we can set the getInstance() method with synchronized.
		// But this is good only for the first initialization, not later.
		// The solution is to use double‐checked locking
		/*
		 	private static volatile VisitorTicketTracker instance;
			public static VisitorTicketTracker getInstance() {
				if(instance == null) {
					synchronized(VisitorTicketTracker.class) {
						if(instance == null) {
							instance = new VisitorTicketTracker();
						}
					}
				}
				return instance;
			}
		 */
		
		
		// Immutable objects pattern (read‐only objects)
		// go hand and hand with encapsulation, except that no setter methods exist that modify the object.
		// String class is immutable.
		/* Rules:
		   	1. Use a constructor to set all properties of the object.
			2. Mark all of the instance variables private and final.
			3. Don’t define any setter methods.
			4. Don’t allow referenced mutable objects to be modified or accessed directly.
			5. Prevent methods from being overridden. (mark the class as final)
		 */
		// The 4th rule: never share references to a mutable object contained within an immutable object.
		// Collections.unmodifiableList() method, which does exactly this.
		
		
		// Builder Pattern
		// - Motivation As our data objects grow in size, the constructor may grow to contain many attributes.
		// Every time we add a parameter, the constructor grows.
		// One solution is to use setter methods instead of the constructor to configure the object,
		// but this doesn’t work for immutable objects.
		// - Solution The builder pattern is a creational pattern in which parameters are passed to a
		// builder object, often through method chaining, and an object is generated with a final build
		// call. It is often used with immutable objects, since immutable objects do not have setters.
		
		// the builder class and target class are considered tightly coupled.
		// In practice, a builder class is often packaged alongside its target class, either as a static
		// inner class within the target class or within the same Java package.
		
		// Factory (Method) Pattern
		// - Motivation We’d like some way of encapsulating object creation.
		// - Solution factory class to produce instances of objects based on a set of input parameters. 
		//   It is similar to the builder pattern, although it is focused on supporting class polymorphism.
		// Factory patterns are often, although not always, implemented using static methods.
		/*
		 public class FoodFactory {
			public static Food getFood(String animalName) {
				switch(animalName) {
					case "zebra": return new Hay(100);
					case "rabbit": return new Pellets(5);
					case "goat": return new Pellets(30);
					case "polar bear": return new Fish(10);
				}
				// Good practice to throw an exception if no matching subclass could be found
				throw new UnsupportedOperationException("Unsupported animal: "+animalName);
			}
		 }
		 */
		
	}	
	
}
