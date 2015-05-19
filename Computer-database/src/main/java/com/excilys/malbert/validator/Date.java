package com.excilys.malbert.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DateValidator.class)
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
	public enum Pattern {
		FR("dd-MM-yyyy"), EN("MM-dd-yyyy");
		private String pattern;

		private Pattern(String pattern) {
			this.pattern = pattern;
		}

		public String toString() {
			return pattern;
		}

		public static Pattern map(String str) {
			if (str.equalsIgnoreCase("fr")) {
				return FR;
			}
			if (str.equalsIgnoreCase("en")) {
				return EN;
			}
			return null;
		}
	}

	String message() default "Wrong date format";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
