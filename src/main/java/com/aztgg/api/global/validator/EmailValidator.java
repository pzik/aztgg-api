package com.aztgg.api.global.validator;

public class EmailValidator {
	private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

	private EmailValidator() {

	}

	public static boolean isValid(String email) {
		return email != null && email.matches(EMAIL_REGEX);
	}
}
