package com.ubbys.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.user.service.UserService;
import com.ubbys.user.vo.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("inputEmail");
		String userPw = request.getParameter("inputPw");
		String saveId = request.getParameter("saveId"); // on or null
		
		try {
			UserService service = new UserService();
			User loginUser = service.login(userEmail, userPw);
			HttpSession session = request.getSession();
			if(loginUser != null) {
				session.setAttribute("loginUser", loginUser);
				session.setMaxInactiveInterval(3600);
				
				Cookie cookie = new Cookie("saveId", userEmail);
				if(saveId != null) {
					cookie.setMaxAge(60*60*24*7); // 일주일
					cookie.setHttpOnly(true);
				} else {
					cookie.setMaxAge(0); // 생성 즉시 삭제
				}
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);
				System.out.println("로그인 성공");
				// 회원가입 페이지를 통해 도달했다면 추가 입력 화면으로 이동
				if(request.getAttribute("isFirstIn") == null) {
					response.sendRedirect(request.getContextPath());
				} else {
					response.sendRedirect(request.getContextPath() + "/signup/add");
				} // 무슨 이유에서인지 조건을 반대로 바꾸면 일반 로그인 후 redirect가 되지 않음.. 				
			} else {
				session.setAttribute("alertTitle", "오류");
				session.setAttribute("alertMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
				System.out.println("로그인 실패");
				response.sendRedirect("login");
			}			
		} catch(Exception err) {
			
		}
	}

}
