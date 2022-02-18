package com.tripleaims.exhibition.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

	private BCryptPasswordEncoder passwordEncoder;
	
	public SecurityUtil() {
		passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public String encodePassword(String password) {
		if(password == null || password.isEmpty())
			return "";
		else
			return passwordEncoder.encode(password);
	}
	
	public boolean isSamePassword(String rawPassword, String dbPassword) {
		return passwordEncoder.matches(rawPassword, dbPassword);
	}
	
}
