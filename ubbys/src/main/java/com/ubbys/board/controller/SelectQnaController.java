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

@WebServlet({"/qnaList", "/qnaView", "/qnaSearch"})
public class SelectQnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String command = request.getRequestURI().substring((request.getContextPath() + "/qna").length());
		
		RequestDispatcher view = null;
		
		try {
			SelectQnaService service = new SelectQnaService();
			
			int cp = request.getParameter("cp")==null? 1 : Integer.parseInt(request.getParameter("cp"));
			
			// qna 목록 조회 Controller
			if(command.equals("List")){
				
				QnaPagination pagination = service.getPagination(cp);
				
				List<Qna> qnaList = service.selectQnaList(pagination);
				
				List<QnaCategory> qnaCategory = new QnaService().selectQnaCategory();
				
				request.setAttribute("pagination", pagination);
				request.setAttribute("qnaList", qnaList);
				request.setAttribute("qnaCategory", qnaCategory);
				
				view = request.getRequestDispatcher("/WEB-INF/views/qnaList.jsp");
				view.forward(request, response);
			}
			
			// qna 상세 조회 Controller
			else if(command.equals("View")) {
				
				int qnaPostId = Integer.parseInt(request.getParameter("no"));
				
				Qna qna = service.selectQna(qnaPostId);
				
				request.setAttribute("qna", qna);
				
				List<Reply> rList = new ReplyService().selectList(qnaPostId);
				request.setAttribute("rList", rList);
				
				view = request.getRequestDispatcher("/WEB-INF/views/qnaView.jsp");
				view.forward(request, response);
			}
			
			// qna 검색 Controller
			else if(command.equals("Search")) {
				
				String searchCondition = request.getParameter("searchCondition");
				
				// qna 제목 조건 검색
				if(searchCondition.equals("T")) {
					String qnaTitle = request.getParameter("searchValue");
					
					QnaPagination pagination = service.getSearchTPage(cp, qnaTitle);
					List<Qna> qnaList = service.searchQnaTitle(pagination, qnaTitle);
					List<QnaCategory> qnaCategory = new QnaService().selectQnaCategory();
					
					request.setAttribute("pagination", pagination);
					request.setAttribute("qnaList", qnaList);
					request.setAttribute("qnaCategory", qnaCategory);
					
					request.getRequestDispatcher("/WEB-INF/views/qnaList.jsp").forward(request, response);
				}
				
				// qna 작성자 조건 검색
				else if(searchCondition.equals("N")) {
					String userNickname = request.getParameter("searchValue");
					
					QnaPagination pagination = service.getSearchNPage(cp, userNickname);
					List<Qna> qnaList = service.searchQnaAuthor(pagination, userNickname);
					List<QnaCategory> qnaCategory = new QnaService().selectQnaCategory();
					
					request.setAttribute("pagination", pagination);
					request.setAttribute("qnaList", qnaList);
					request.setAttribute("qnaCategory", qnaCategory);
					
					request.getRequestDispatcher("/WEB-INF/views/qnaList.jsp").forward(request, response);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
