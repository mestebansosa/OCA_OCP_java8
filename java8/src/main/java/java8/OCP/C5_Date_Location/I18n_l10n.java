package java8.OCP.C5_Date_Location;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

public class I18n_l10n {
	public static void printProperties(Locale locale) {
		// Java class resource bundles are given preference. Then the properties file
		// If not found goes to the default bundle. That is Zoo.java or Zoo.bundle
		ResourceBundle rb = ResourceBundle.getBundle("Zoo", locale);
		System.out.println(rb.getString("hello"));
		System.out.println(rb.getString("open"));
		
		Set<String> keys = rb.keySet();
		keys.stream().map(k -> k + " " + rb.getString(k)).forEach(System.out::println);

		// Converting from ResourceBundle to Properties is easy
		Properties props = new Properties();
		rb.keySet().stream().forEach(k -> props.put(k, rb.getString(k)));
	}

    public static void main(String[] args) {
    	// Locale. Format: language in lowercase and county in uppercase. 
    	// language only: fr. language and country: es_ES
    	Locale locale = Locale.getDefault();
    	System.out.println(locale);
    	
    	// You can create a locale with a new, or with Locale.Builder
    	System.out.println(new Locale("hi", "IN"));
		Locale l1 = new Locale.Builder().setLanguage("en").setRegion("US").build();
    	
		Locale.setDefault(l1); // set it only in this program.
		
    	// Locale constants
    	System.out.println(Locale.GERMAN); // de
    	System.out.println(Locale.GERMANY); // de_DE

		Locale us = new Locale("en", "US");
		Locale spain = new Locale("es", "ES");
		printProperties(us);
		System.out.println();
		printProperties(spain);
		
		// NumberFormat
		NumberFormat usNf = NumberFormat.getInstance(Locale.US);
		System.out.println(usNf.format(3200000/12));
		double price = 48;
		usNf = NumberFormat.getCurrencyInstance(Locale.UK);
		System.out.println(usNf.format(price));
		
		NumberFormat fr = NumberFormat.getInstance(Locale.FRANCE);
		String s = "40.45";
		try {
			System.out.println(fr.parse(s)); // 40
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String amt = "$92,807.99";
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		double value = 0;
		try {
			value = (Double) cf.parse(amt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(value); // 92807.99
	}   	
}
