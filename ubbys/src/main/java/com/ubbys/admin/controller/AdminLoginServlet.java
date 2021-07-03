package com.ubbys.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.admin.model.service.AdminService;
import com.ubbys.user.vo.User;


@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/admin/adminLogin.jsp");
		view.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userEmail = request.getParameter("inputEmail");
		String userPw = request.getParameter("inputPw");
		String saveId = request.getParameter("saveId");
		
		
		try {
			AdminService service = new AdminService();
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
				
							
			} else {
				session.setAttribute("modalTitle", "로그인 실패");
				session.setAttribute("modalText", "아이디 또는 비밀번호가 일치하지 않습니다.");
				System.out.println("로그인 실패");
				response.sendRedirect("adminLogin");
				System.out.println(loginUser);
			}	
			
			response.sendRedirect("adminMain");
			
		} catch(Exception err) {
			
		}
	}
	

}
