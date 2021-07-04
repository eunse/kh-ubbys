package com.ubbys.admin.qna.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.admin.qna.service.QnaService;
import com.ubbys.admin.qna.service.SelectQnaService;
import com.ubbys.admin.qna.vo.Qna;
import com.ubbys.admin.qna.vo.QnaCategory;
import com.ubbys.board.vo.Pagination;

@WebServlet({"/admin/qnaList", "/admin/qnaView"})
public class SelectQnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getRequestURI().substring((request.getContextPath() + "/admin/qna").length());
		
		String path = null; 
		RequestDispatcher view = null; 
		
		try {
			SelectQnaService service = new SelectQnaService();
			
		
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt( request.getParameter("cp") );
			

			
			if(command.equals("List")) { //qna 목록 조회
				

				
				Pagination pagination = service.getPagination(cp);
				List<Qna> qnaList = service.selectQnaList(pagination);
				
				List<QnaCategory> qnaCategory = new QnaService().selectQnaCategory();
				
				request.setAttribute("pagination", pagination);
				request.setAttribute("qnaList", qnaList);
				request.setAttribute("qnaCategory", qnaCategory);
				
				path = "/WEB-INF/views/admin/qna/qnaList.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}else if(command.equals("View"))  { // qna 상세 조회
				
				int qnaPostId = Integer.parseInt(request.getParameter("no"));			
				
				Qna qna = service.selectQna(qnaPostId);
				
				request.setAttribute("qna", qna);
				path = "/WEB-INF/views/admin/qna/qnaView.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
