package com.ubbys.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.board.service.AppsService;
import com.ubbys.board.service.QnaService;
import com.ubbys.board.service.SelectQnaService;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaPagination;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// apps 최근 게시물 처리
		AppsService appService = new AppsService();
		try {
			Pagination pagination = appService.getPagination("apps", 1);
			pagination.setLimit(4);
			List<Apps> appsList = appService.selectAppsList(pagination);
			
			request.setAttribute("pagination", pagination);
			request.setAttribute("appsList", appsList);
		} catch(Exception err) {
			err.printStackTrace();
		}
		
		// qna 최근 게시물 처리 
		SelectQnaService selectQnaService = new SelectQnaService();
		try {
			QnaPagination pagination = selectQnaService.getPagination(1);
			pagination.setLimit(5);
			List<Qna> qnaList = selectQnaService.selectQnaList(pagination);
			
			request.setAttribute("pagination", pagination);
			request.setAttribute("qnaList", qnaList);			
		} catch(Exception err) {
			err.printStackTrace();
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/main.jsp");
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}