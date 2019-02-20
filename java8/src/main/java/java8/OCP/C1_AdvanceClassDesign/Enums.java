package java8.OCP.C1_AdvanceClassDesign;

public class Enums {
	
	public enum Season {
		WINTER, SPRING, SUMMER, FALL
	}
	
	// Constructors, Fields and Methods
	public enum Season2 {
		WINTER("Low"), SPRING("Medium"), SUMMER("High"), FALL("Medium"); // semicolon is compulsory
		private String expectedVisitors;

		private Season2(String expectedVisitors) {
			this.expectedVisitors = expectedVisitors;
		}

		public void printExpectedVisitors() {
			System.out.println(expectedVisitors);
		}
	}
	
	public enum Season3 {
		WINTER {
			public void printHours() {
				System.out.println("9am-3pm");
			}
		},
		SPRING {
			public void printHours() {
				System.out.println("9am-5pm");
			}
		},
		SUMMER {
			public void printHours() {
				System.out.println("9am-7pm");
			}
		},
		FALL {
			public void printHours() {
				System.out.println("9am-5pm");
			}
		};
		public abstract void printHours();
	}
	
	// override
	public enum Season4 {
		WINTER {
			public void printHours() {
				System.out.println("short hours");
			}
		},
		SUMMER {
			public void printHours() {
				System.out.println("long hours");
			}
		},
		SPRING, FALL;
		public void printHours() {
			System.out.println("default hours");
		}
	}	

	public static void main(String[] args) {

		// is like a fixed set of constants. enum is a class that represents an enumeration. 
		// It is much better than a bunch of constants because it provides type‐safe checking.
		// Use the uppercase letter convention that you used for constants.
		
		// Enum cannot be extended.

		Season s = Season.SUMMER;
		System.out.println(Season.SUMMER); // SUMMER
		System.out.println(s == Season.SUMMER); // true
		
		// values(), name(), ordinal()
		for (Season season : Season.values()) {
			System.out.println(season.name() + " " + season.ordinal());
		}
		
		// valueOf(). Create an enum from a String.
		Season s1 = Season.valueOf("SUMMER"); // SUMMER
		// Season s2 = Season.valueOf("summer"); // exception
		
		switch (s1) {
		// case 0: // does not compile
		case WINTER:
			System.out.println("Get out the sled!");
			break;
		case SUMMER:
			System.out.println("Time for the pool!");
			break;
		default:
			System.out.println("Is it summer yet?");
		}
		
		// Constructors, Fields and Methods	
		Season2.SUMMER.printExpectedVisitors();
		// Notice how we don’t appear to call the constructor. We just say that we want
		// the enum value. The first time that we ask for any of the enum values, Java
		// constructs all of the enum values. After that, Java just returns the
		// already‐constructed enum values.
		
		// EnumSet and EnumMap.
		// EnumMap is a specialized Map implementation meant to be used with enum constants as keys.
		// https://www.baeldung.com/a-guide-to-java-enums 
	}
	
}
