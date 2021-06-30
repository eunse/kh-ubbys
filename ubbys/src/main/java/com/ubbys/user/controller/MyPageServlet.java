package com.ubbys.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.board.service.SelectQnaService2;
import com.ubbys.board.vo.Qna;
import com.ubbys.user.vo.User;

@WebServlet("/user")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = null;
		
		try {
			SelectQnaService2 service2 = new SelectQnaService2();
			HttpSession session = request.getSession();
			int userNo = ((User) session.getAttribute("loginUser")).getUserNo();
			System.out.println("user넘" + userNo);
			List<Qna> myQnaList = service2.selectMyQnaList(userNo);

			request.setAttribute("myQnaList", myQnaList);
			System.out.println(myQnaList);
			view = request.getRequestDispatcher("/WEB-INF/views/user/mypage.jsp");
			view.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
