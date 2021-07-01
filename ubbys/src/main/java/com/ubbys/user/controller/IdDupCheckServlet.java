package com.ubbys.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.user.service.UserService;

@WebServlet("/signup/idDupCheck")
public class IdDupCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/signup/idDupCheck");
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/user/idDupCheck.jsp");
//		중복검사 팝업창

		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userEmail = request.getParameter("inputEmail");
		
//		ajax를 이용해 비동기로 중복 검사
		try {
	         
	         // DB에서 아이디 중복 검사 수행 후 결과를 반환 받아 저장
	         int result = new UserService().idDupCheck(userEmail);
	         
	         // 응답을 받을 클라이언트와의 연결 스트림
	         PrintWriter out = response.getWriter();
	         out.print(result);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
	
}

}
