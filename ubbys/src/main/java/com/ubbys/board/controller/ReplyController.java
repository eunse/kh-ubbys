package com.ubbys.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ubbys.board.service.ReplyService;
import com.ubbys.board.vo.Reply;
import com.ubbys.user.vo.User;


@WebServlet({"/replyList", "/replyInsertReply", "/replyUpdateReply", "/replyDeleteReply"})
//@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/reply").length());
	
		
		try {
			ReplyService service = new ReplyService();
			
			if(command.equals("List")) { // 댓글 목록 조회
				
				int qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
//				System.out.println("목록클릭 게시판번호:"+qnaPostId); //확인
				
				List<Reply> rlist = service.selectList(qnaPostId);
				Gson gson = new Gson();
				gson.toJson(rlist, response.getWriter());
//				System.out.println("목록 : "+rlist);//확인
			}
			
			else if(command.equals("InsertReply")) { // 댓글 삽입
				int userId = Integer.parseInt(request.getParameter("userId"));
				int qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
				String replyContent = request.getParameter("replyContent");
				System.out.println("댓글내용 : " + replyContent);//확인
				System.out.println("게시글번호 : " + qnaPostId);//확인
				System.out.println("회원번호 : " + userId);//확인
				Reply reply = new Reply();
				
				reply.setUserId(userId);
				reply.setQnaPostId(qnaPostId);
				reply.setReplyContent(replyContent);
				
				int result = service.insertReply(reply);
				
				response.getWriter().print(result);
				System.out.println("삽입result : " +result);
			}
			
			else if(command.equals("UpdateReply")) { // 댓글 수정
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				String replyContent = request.getParameter("replyContent");
				
				Reply reply = new Reply();
				
				reply.setReplyId(replyId);
				reply.setReplyContent(replyContent);
				
				int result = service.updateReply(reply);
				
				response.getWriter().print(result);
				System.out.println("수정result : " +result);
			}
			else if(command.equals("DeleteReply")); // 댓글 삭제
//				int replyId = Integer.parseInt(request.getParameter("replyId"));
//				System.out.println("댓글번호No:"+replyId);
//				
//				int result = service.deleteReply(replyId);
//				response.getWriter().print(result);
//				System.out.println("삭제result : " +result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
