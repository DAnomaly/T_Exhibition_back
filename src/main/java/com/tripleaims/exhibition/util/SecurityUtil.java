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
		return passwordEncoder.encode(password);
	}
	
}
