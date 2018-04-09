package com.ikeeps.pippy.authservice;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SimpleTest {
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Test
	public void passwordEncode() {
		System.out.println(encoder.encode("123456"));
	}
}
