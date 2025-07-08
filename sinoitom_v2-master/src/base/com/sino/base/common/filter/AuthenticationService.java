package com.sino.base.common.filter;

import it.sauronsoftware.base64.Base64;

import java.io.IOException;
import java.util.StringTokenizer;


public class AuthenticationService {

	public boolean authenticate(String authCredentials) {

		if (null == authCredentials)
			return false;
		
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"+ " ", "");
				
		String usernameAndPassword = null;
		try {
			usernameAndPassword = Base64.decode(encodedUserPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		boolean authenticationStatus = "admin".equals(username)&& "admin".equals(password);
				
		return authenticationStatus;
	}
}
