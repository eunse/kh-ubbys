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

@WebServlet("/user/changePw")
public class ChangePwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/user/change_pw.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String currentPw = request.getParameter("inputPresentPw");
		String newPw = request.getParameter("inputChangePw");
		
		HttpSession session = request.getSession();
		int userNo = ((User) session.getAttribute("loginUser")).getUserNo();

		try {
			int result = new UserService().changePw(currentPw, newPw, userNo);

			String alertTitle = null;
			String alertMsg = null;

			if (result > 0) {
				alertTitle = "비밀번호 변경 성공";
				alertMsg = "비밀번호 변경이 완료되었습니다.";
//				System.out.println("비번변경 성공");
			} else {
				alertTitle = "비밀번호 변경 실패";
				alertMsg = "비밀번호 변경 중 문제가 발생했습니다. \n문제가 지속될 경우 고객센터 문의 바랍니다.";
//				System.out.println("비번변경 실패");
				
			}
			session = request.getSession();
			session.setAttribute("alertTitle", alertTitle);
			session.setAttribute("alertMsg", alertMsg);

			response.sendRedirect("update");

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("errorMsg", "회원정보 수정 과정에서 문제가 발생했습니다.");

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/error.jsp");
			view.forward(request, response);
		}
	}

}
