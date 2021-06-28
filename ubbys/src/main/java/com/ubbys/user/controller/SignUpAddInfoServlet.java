package com.ubbys.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.user.service.UserService;
import com.ubbys.user.vo.User;

@WebServlet("/signup/add")
public class SignUpAddInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignUpAddInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/signup2.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		String userImage = request.getParameter("userImageInput");
		String userWebsite = request.getParameter("inputWebsite");
		String userInterest = request.getParameter("inputInterest");
		String userIntroduce = request.getParameter("inputIntroduce");
		
		User user = new User(userNo, userImage, userWebsite, userInterest, userIntroduce);
		
		try {
			int result = UserService.signUpAddInfo(user);
			if(result > 0) {
				System.out.println("추가정보 입력 성공");
				response.sendRedirect(request.getContextPath());
			} else {
				System.out.println("추가정보 입력 실패");
				response.sendRedirect(request.getContextPath());
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
	}

}
