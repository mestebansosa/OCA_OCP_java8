package java8.OCP.C5_Date_Location;

import java.util.ListResourceBundle;

// The ListResourceBundle abstract class leaves one method for subclasses to implement.
// There are two main advantages of using a Java class instead of a property file for a resource bundle:
// - You can use a value type that is not a String.
// - You can create the values of the properties at runtime.	

// Determining Which Resource Bundle to Use
// - When there is a tie, Java class resource bundles are given preference.
// - Always look for the property file after the matching Java class.
// - Drop one thing at a time if there are no matches. First drop the country and then the language.
// - Look at the default locale and the default resource bundle last.
/*
How many files do you think Java would need to look for to find the resource bundle with the code?
	Locale.setDefault(new Locale("hi"));
	ResourceBundle rb = ResourceBundle.getBundle("Zoo", new Locale("en"));
	The answer is six. They are listed here:
		1. Zoo_hi.java
		2. Zoo_hi.properties
		3. Zoo_en.java
		4. Zoo_en.properties
		5. Zoo.java
		6. Zoo.properties
*/

public class Zoo_en extends ListResourceBundle {
	protected Object[][] getContents() {
		return new Object[][] { { "hello", "Hello" }, { "open", "The zoo is open" }, { "helloByName", "Hello, {0}" } };
	}
}

