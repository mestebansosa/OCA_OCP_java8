package java8.OCP.C4_FunctionalProgramming;

import java.util.Optional;

public class OptionalType {
	public static Optional<Double> average(int... scores) {
		if (scores.length == 0)
			return Optional.empty();
		int sum = 0;
		for (int score : scores)
			sum += score;
		return Optional.of((double) sum / scores.length);
	}

	// Chainnig Optionals
	// A few of the intermediate operations for streams are available for Optional.
	private void threeDigitClassic(Optional<Integer> optional) {
		if (optional.isPresent()) { // outer if
			Integer num = optional.get();
			String string = "" + num;
			if (string.length() == 3) // inner if
				System.out.println(string);
		}
	}
	// With Functional programming
	private static void threeDigit(Optional<Integer> optional) {
		optional.map(n -> "" + n) 
				.filter(s -> s.length() == 3)
				.ifPresent(System.out::println);
	}
	
	public static void main(String[] args) {
		// An Optional is created using a factory
		// Returning an Optional is a clear statement in the API that there might not be
		// a value in there.
		
		Optional<Double> opt = average(90, 100);
		if (opt.isPresent())
			System.out.println(opt.get()); // 95.0
		opt.ifPresent(System.out::println); // Functional programming
		
		Integer value = 100;
		Optional<Integer> o = (value== null) ? Optional.empty(): Optional.of(value);
		Optional<Integer> o2 = Optional.ofNullable(value);
		
		Optional<Double> opt1 = average();
		// System.out.println(opt1.get()); // bad
		System.out.println(opt1.orElse(Double.NaN));
		System.out.println(opt1.orElseGet(() -> Math.random()));
		// System.out.println(opt1.orElseThrow(() -> new IllegalStateException()));
		
		Optional<Double> opt2 = average(90, 100, 200);
		System.out.println(opt2.orElse(Double.NaN));
		System.out.println(opt2.orElseGet(() -> Math.random()));
		System.out.println(opt2.orElseThrow(() -> new IllegalStateException()));
		
	}
}
