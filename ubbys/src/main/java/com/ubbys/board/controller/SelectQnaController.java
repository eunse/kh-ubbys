package com.ubbys.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.board.service.SelectQnaService;
import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaPagination;

@WebServlet("/qnaList")
public class SelectQnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String command = request.getRequestURI().substring((request.getContextPath() + "/qna").length());
		
		RequestDispatcher view = null;
		
		try {
			SelectQnaService service = new SelectQnaService();
			
			int cp = request.getParameter("cp")==null? 1 : Integer.parseInt(request.getParameter("cp"));
			
			if(command.equals("List")){ // qna 목록 조회
				
				QnaPagination pagination = service.getPagination(cp);
				
				List<Qna> qnaList = service.selectQnaList(pagination);
				
				request.setAttribute("pagination", pagination);
				request.setAttribute("qnaList", qnaList);
				
				view = request.getRequestDispatcher("/WEB-INF/views/qnaList.jsp");
				view.forward(request, response);
			}
			
			else if(command.equals("View")) { // qna 상세 조회
				
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
