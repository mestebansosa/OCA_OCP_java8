package java8.OCP.C6_Assertions;

public class Assertions {

    public static void main(String[] args) {
    	// An assertion is a Boolean expression you expect something to be true. 
    	// An assertion allows for detecting defects in the code. 
    	// Turn on assertions for testing and debugging and leaving off in production.
    	// It is like: if (!boolean_expression) throw new AssertionError();
   
    	// java -ea:com.wiley.demos... -da:com.wiley.demos.TestColors my.programs.Main
    	
    	int numGuests = -5;
    	assert numGuests > 0;
    	
    	// Use in:
    	// internal invariants. class invariants, control flow invariant, pre/postconditions
    	
    	// Assertions should not alter outcomes.
    	int x = 10;
    	assert ++x > 10; // Not a good design!
    	
    	// Do not use assertions to check for valid arguments passed in to a method. 
    	// Use an IllegalArgumentException instead.
    	int n = -1;
    	assert n < 0: "OhNo";
    	// assert n < 0, "OhNo"; // WRONG
    	// assert n < 0 ("OhNo");  // WRONG
    	assert(n < 0): "OhNo";
    	// assert(n < 0, "OhNo");  // WRONG
    }   	
}
