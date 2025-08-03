package com.aztgg.api.global.validator;


public class NicknameValidator {
	public static boolean isValid(String nickname) {
		return nickname != null && nickname.length() >= 2 && nickname.length() <= 20;
	}
}
