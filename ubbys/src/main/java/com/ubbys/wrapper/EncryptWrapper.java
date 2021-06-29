package com.ubbys.wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncryptWrapper extends HttpServletRequestWrapper{
	public EncryptWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String key) {
		String value = null;
		switch(key) {
		case "inputPw": 
		case "inputChangePw":	
		case "inputPresentPw":	
			value = getSha512(super.getParameter(key));
			break;
		default:
			value = super.getParameter(key);
		}
		return value;
	}

	private String getSha512(String pwd) {
		String encPw = null;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] bytes = pwd.getBytes(Charset.forName("UTF-8"));
			
			md.update(bytes);
			encPw = Base64.getEncoder().encodeToString(md.digest());
		} catch (NoSuchAlgorithmException err) {
			err.printStackTrace();
		}
		return encPw;
	}

}