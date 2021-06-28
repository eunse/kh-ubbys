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

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignUpServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/signup.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("inputEmail");
		String userPw = request.getParameter("inputPw");
		String userNickname = request.getParameter("inputName");
		
		User user = new User(userEmail, userPw, userNickname);
		
		try {
			int result = UserService.signUp(user);
			// 모달을 위한 변수 생성 예정
			if(result > 0) { // 회원가입 성공
				// 추후 성공시 모달 처리할 영역
				System.out.println("회원가입 성공");
				
				// 회원가입 직후 로그인 처리
				RequestDispatcher view = request.getRequestDispatcher("/login");
				request.setAttribute("isFirstIn", "true");
				System.out.println(request.getAttribute("isFirstIn"));
				view.forward(request, response);
			} else {
				// 추후 실패시 모달 처리할 영역
				System.out.println("회원가입 실패");
				response.sendRedirect(request.getContextPath());
			}			
		} catch (Exception err) {
			err.printStackTrace();
			// request.setAttribute("errorMsg", "회원 가입 과정에서 문제가 발생했습니다.");
			// RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/error.jsp");
			// view.forward(request, response);
		}
	}

}
