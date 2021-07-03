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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "loginFilter", urlPatterns = {"/user/myPage", "/user/update", "/user/changePwd", "/user/delete", "/qnaView", "/apps/write"})
public class LoginFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		if(session.getAttribute("loginUser") == null) {
			session.setAttribute("modalTitle", "회원 전용");
			session.setAttribute("modalText", "로그인 후에 이용해주세요.");
			session.setAttribute("modalButtonText", "로그인");
			session.setAttribute("modalButtonLink", req.getContextPath()+"/login");
			((HttpServletResponse)response).sendRedirect(req.getHeader("referer"));
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
