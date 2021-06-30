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

@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/user/setting.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		String userNickName = request.getParameter("inputName");
		String userPic = request.getParameter("userImageInput"); 
		String userLink = request.getParameter("inputWebsite"); 
		String userInterest = request.getParameter("inputInterest"); 
		String userIntroduce = request.getParameter("inputIntroduce"); 
		
		User user = new User();
		user.setUserNo(userNo);
		user.setUserNickname(userNickName);
		user.setUserPic(userPic);
		user.setUserLink(userLink);
		user.setUserInterest(userInterest);
		user.setUserIntroduce(userIntroduce);
		
		
		try {
			
			int result = new UserService().updateUser(user); 
			
			String alertTitle = null;
			String alertMsg = null;
			
			
			if(result > 0 ) {
				alertTitle = "회원정보 수정 성공";
				alertMsg = "회원정보 수정이 완료되었습니다.";
//				System.out.println(alertTitle);
				loginUser.setUserNickname(userNickName);
				loginUser.setUserPic(userPic);
				loginUser.setUserLink(userLink);
				loginUser.setUserInterest(userInterest);
				loginUser.setUserIntroduce(userIntroduce);
				
			}else {
				alertTitle = "회원정보 수정 실패";
				alertMsg = "회원정보 수정 중 문제가 발생했습니다. \n문제가 지속될 경우 고객센터 문의 바랍니다.";
//				System.out.println(alertTitle);
			}
			session = request.getSession();
			session.setAttribute("alertTitle", alertTitle);
			session.setAttribute("alertMsg", alertMsg);
			
			response.sendRedirect(request.getContextPath()+"/user"); // 절대 경로
//			response.sendRedirect("user"); // 상대 경로 <-안됨..
			
		}catch (Exception e) {
			e.printStackTrace();
			
			request.setAttribute("errorMsg", "회원정보 수정 과정에서 문제가 발생했습니다.");
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/error.jsp");
			view.forward(request, response);
		}
	}

}
