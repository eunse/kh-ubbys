package com.ubbys.board.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

@WebServlet({"/qnaList", "/qnaView"})
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
				
				QnaPagination pagination = null;
				List<Qna> qnaList = null;
				
				if(request.getParameter("sc")==null) {
					
					pagination = service.getPagination(cp);
					if(request.getSession().getAttribute("loginUser")!=null) {
						int loginUserId = ((User)request.getSession().getAttribute("loginUser")).getUserNo();
						qnaList = service.selectQnaList(pagination, loginUserId);
					}else {
						qnaList = service.selectQnaList(pagination);
					}
				}
				
				// 정렬
				else if(request.getParameter("sv").equals("DESC")) {
					
					String searchCondition = request.getParameter("sc");
					String searchValue = request.getParameter("sv");
					
					pagination = service.getPagination(cp);
					if(request.getSession().getAttribute("loginUser")!=null) {
						int loginUserId = ((User)request.getSession().getAttribute("loginUser")).getUserNo();
						qnaList = service.selectQnaSortList(pagination, searchCondition, searchValue, loginUserId);
					}else {
						qnaList = service.selectQnaSortList(pagination, searchCondition, searchValue);
					}
				}
				
				// 카테고리, 제목, 작성자 검색
				else {
					
					String searchCondition = request.getParameter("sc");
					String searchValue = request.getParameter("sv");
					
					pagination = service.getPagination(cp, searchCondition, searchValue);
					if(request.getSession().getAttribute("loginUser")!=null) {
						int loginUserId = ((User)request.getSession().getAttribute("loginUser")).getUserNo();
						qnaList = service.selectQnaList(pagination, searchCondition, searchValue, loginUserId);
					}else {
						qnaList = service.selectQnaList(pagination, searchCondition, searchValue);
					}
				}
				
				List<QnaCategory> qnaCategory = new QnaService().selectQnaCategory();
				
				request.setAttribute("pagination", pagination);
				request.setAttribute("qnaList", qnaList);
				request.setAttribute("qnaCategory", qnaCategory);
				
				request.getRequestDispatcher("/WEB-INF/views/board/qnaList.jsp").forward(request, response);
				
			}
			
			// qna 상세 조회 Controller
			else if(command.equals("View")) {
				
				int qnaPostId = Integer.parseInt(request.getParameter("no"));
				
				Qna qna = service.selectQna(qnaPostId);
				
				request.setAttribute("qna", qna);
				
				List<Reply> rList = new ReplyService().selectList(qnaPostId);
				request.setAttribute("rList", rList);
				
				view = request.getRequestDispatcher("/WEB-INF/views/board/qnaView.jsp");
				view.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "목록 조회 중 오류가 발생했습니다.");
			request.getRequestDispatcher("/WEB-INF/views/common/error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
