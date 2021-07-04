package com.ubbys.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.board.service.QnaService;
import com.ubbys.board.service.ReplyService;
import com.ubbys.board.service.SelectQnaService;
import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaCategory;
import com.ubbys.board.vo.QnaPagination;
import com.ubbys.board.vo.Reply;
import com.ubbys.user.vo.User;

@WebServlet({"/qnaList", "/qnaView", "/qnaMyPage"})
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
				
				List<QnaCategory> qnaCategory = new QnaService().selectQnaCategory();
				
				request.setAttribute("pagination", pagination);
				request.setAttribute("qnaList", qnaList);
				request.setAttribute("qnaCategory", qnaCategory);
				
				view = request.getRequestDispatcher("/WEB-INF/views/qnaList.jsp");
				view.forward(request, response);
			}
			
			else if(command.equals("View")) { // qna 상세 조회
				
				int qnaPostId = Integer.parseInt(request.getParameter("no"));
				
				Qna qna = service.selectQna(qnaPostId);
				
				request.setAttribute("qna", qna);
				
				List<Reply> rList = new ReplyService().selectList(qnaPostId);
				request.setAttribute("rList", rList);
				
				view = request.getRequestDispatcher("/WEB-INF/views/qnaView.jsp");
				view.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
