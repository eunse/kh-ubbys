package com.ubbys.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.board.service.QnaReplyService;
import com.ubbys.board.vo.QnaReplyPagination;
import com.ubbys.user.vo.User;
import com.ubbys.board.vo.QnaReply;

@WebServlet({"/qnaReplyList", "/qnaReplyInsert"})
public class QnaReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getRequestURI().substring((request.getContextPath() + "/qnaReply").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		try {
			QnaReplyService service = new QnaReplyService();
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			
			// qnaReply 댓글, 내 댓글 목록 관련
			if(command.equals("List")) {
				
				QnaReplyPagination pagination = service.getPagination(cp);
				
				HttpSession session = request.getSession();
				int userId = ((User) session.getAttribute("loginUser")).getUserNo();
				
				List<QnaReply> qnaReplyList = service.selectQnaReplyList(pagination, userId);
				
				request.setAttribute("pagination", pagination);
				request.setAttribute("qnaReplyList", qnaReplyList);
				request.setAttribute("userId", userId);
				System.out.println(userId);
				System.out.println(qnaReplyList);
				
				view = request.getRequestDispatcher("/WEB-INF/views/qnaReply.jsp");
				view.forward(request, response);
				
			}	
			// qnaReply 댓글 삽입 관련
			else if(command.equals("Insert")) {
				HttpSession session = request.getSession();
				int userNo = ((User) session.getAttribute("loginUser")).getUserNo();
				System.out.println("userNo : " + userNo); //확인
				
				String replyContent = request.getParameter("replyContent");
				
				QnaReply qnaReply = new QnaReply();
				qnaReply.setReplyContent(replyContent);
				
				int result = service.insertQnaReply(qnaReply);
				System.out.println("result" + result);
				
				if(result > 0 ) {
					System.out.println("댓글작성성공");
					
				}else {
					System.out.println("댓글작성실패");
					
				}
				
			}
			
			
			
			view = request.getRequestDispatcher("/WEB-INF/views/qnaList.jsp");
			view.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
