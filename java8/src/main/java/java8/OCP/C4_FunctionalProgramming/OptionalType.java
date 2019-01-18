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

	// Chainning Optionals
	// A few of the intermediate operations for streams are available for Optional.
	private static void threeDigit(Optional<Integer> optional) {
		optional.map(n -> "" + n) 
				.filter(s -> s.length() == 3)
				.ifPresent(System.out::println);
	}
	
	public static void main(String[] args) {
		// An Optional is created using a factory
		// Returning an Optional is a clear statement in the API that there might not be
		// a value in there.
		
		System.out.println(average(90, 100)); // Optional[95.0]
		System.out.println(average()); // Optional.empty
		
		Optional<Double> opt = average(90, 100);
		System.out.println(opt.get()); // 95.0 bad
		if (opt.isPresent())
			System.out.println(opt.get()); // 95.0 better
		opt.ifPresent(System.out::println); // 95.0 Functional programming
		
		Integer value = 100;
		// next two lines are the same.
		Optional<Integer> o = (value== null) ? Optional.empty(): Optional.of(value);
		Optional<Integer> o2 = Optional.ofNullable(value);
		
		Optional<Double> opt1 = average();
		System.out.println(opt1.orElse(Double.NaN)); // when optional is empty returns NaN.
		System.out.println(opt1.orElseGet(() -> Math.random())); // when optional is empty returns Math.
		// System.out.println(opt1.orElseThrow(() -> new IllegalStateException())); // when optional is empty throw
		
		Optional<Double> opt2 = average(90, 100, 200);
		System.out.println(opt2.orElse(Double.NaN));
		System.out.println(opt2.orElseGet(() -> Math.random()));
		System.out.println(opt2.orElseThrow(() -> new IllegalStateException()));
		
		// Working with Advanced Stream Pipeline Concepts
		// Chainning Optionals
		threeDigit(Optional.of(567));
	}
}
