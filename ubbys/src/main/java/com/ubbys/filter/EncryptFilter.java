package com.ubbys.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.ubbys.wrapper.EncryptWrapper;

@WebFilter(filterName = "encryptFilter", urlPatterns = {"/login", "/signup", "/user/changePw", "/user/deleteAccount", "/admin/adminLogin"})
public class EncryptFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest)request;
		if(hreq.getMethod().equals("POST")) {
			EncryptWrapper encWrapper = new EncryptWrapper(hreq);
			chain.doFilter(encWrapper, response);
		} else {
			chain.doFilter(request, response);
		}		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
