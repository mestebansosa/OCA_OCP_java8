package java8.OCP.C5_Date_Location;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

// internationalization (i18n) and localization (l10n)
public class I18n_l10n {
	
	public static void printProperties(Locale locale) {
		// A resource bundle contains the local specific objects to be used by a program. 
		// It is like a map with keys and values. 
		// The resource bundle can be in a property file or in a Java class.
		// A property file is a file in a specific format with key/value pairs.
		
		// Java class resource bundles are given preference. Then the properties file
		// If not found goes to the default bundle. That is Zoo.java or Zoo.bundle
		ResourceBundle rb = ResourceBundle.getBundle("Zoo", locale);
		System.out.println(rb.getString("hello"));
		System.out.println(rb.getString("open"));
		System.out.println(rb.getString("animal1"));
		System.out.println(rb.getString("animal2"));
		System.out.println(rb.getString("animal3"));
		
		Set<String> keys = rb.keySet();
		keys.stream().map(k -> k + " " + rb.getString(k)).forEach(System.out::println);

		// Converting from ResourceBundle to Properties is easy
		Properties props = new Properties();
		rb.keySet().stream().forEach(k -> props.put(k, rb.getString(k)));
		System.out.println(props.getProperty("hello"));
	}

    public static void main(String[] args) {
    	// Internationalization is the process of designing your program so it can be adapted. 
    	// This involves placing strings in a property file and using classes like DateFormat.
    	// Localization means actually supporting multiple locales. 
    	// You can think of a locale as being like a language and country pairing. 
    	// Localization includes translating strings to different languages.
    	// It includes outputting dates and numbers in the correct format for that locale.

    	// Locale. Format: language in lowercase and county in uppercase. 
    	// language only: fr. language and country: es_ES
    	// these Locales are invalid:
    	/*
    		US 		// can have a language without a country, but not the reverse
    		enUS 	// missing underscore
    		US_en 	// the country and language are reversed
    		EN 		// language must be lowercase
    	*/
    	Locale locale = Locale.getDefault();
    	System.out.println(locale);
    	
    	// You can create a locale with a new, or with Locale.Builder
    	System.out.println(new Locale("hi", "IN"));
		Locale l1 = new Locale.Builder().setLanguage("en").setRegion("US").build();
    	
		Locale.setDefault(l1); // set it only in this program.
		
    	// Locale constants
    	System.out.println(Locale.GERMAN); // de
    	System.out.println(Locale.GERMANY); // de_DE

    	// ResourceBundle property file
		Locale us = new Locale("en", "US");
		Locale spain = new Locale("es", "ES");
		printProperties(us);
		System.out.println();
		printProperties(spain);
		
    	// ResourceBundle java class
		ResourceBundle rb = ResourceBundle.getBundle("java8.OCP.C5_Date_Location.Zoo", Locale.UK);
		System.out.println(rb.getObject("hello"));

		// Handling Variables Inside Resource Bundles
		// For example, suppose that we had this property defined: helloByName=Hello, {0}
		String format = rb.getString("helloByName");
		String formatted = MessageFormat.format(format, "Tammy");
		System.out.println(formatted);
		
		
		// Format and Parse Numbers and Currency
		// NumberFormat. Factory with these creational methods:
		NumberFormat usNf = NumberFormat.getInstance(Locale.US);
		System.out.println(usNf.format(3200000/12));
		NumberFormat usNf2 = NumberFormat.getNumberInstance(Locale.US);
		System.out.println(usNf2.format(3200000/12));
		
		double price = 48;
		System.out.println(NumberFormat.getCurrencyInstance(Locale.UK).format(price));
		System.out.println(NumberFormat.getPercentInstance(Locale.UK).format(price));
		
		// parsing
		NumberFormat fr = NumberFormat.getInstance(Locale.FRANCE);
		String s = "40.45";
		try {
			System.out.println(fr.parse(s)); // 40
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String amt = "$92,807.99";
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		double value = 0;
		try {
			value = (Double) cf.parse(amt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(value); // 92807.99
		
		// The format classes are not thread-safe. Do not store them in instance variables or static variables.
		
		// What Does Java Do with Extra Characters When Parsing?
		// The parse method parses only the beginning of a string. After it reaches a character that
		// cannot be parsed, the parsing stops and the value is returned.
		NumberFormat nf = NumberFormat.getInstance();
		String one = "456abc";
		String two = "-2.5165x10";
		String three = "x85.3";
		try {
			System.out.println(nf.parse(one)); // 456
			System.out.println(nf.parse(two)); // -2.5165
			System.out.println(nf.parse(three));// throws ParseException
		} catch (ParseException e) {
		} 
	}   	
}
