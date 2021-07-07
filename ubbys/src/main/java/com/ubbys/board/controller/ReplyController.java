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


@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/reply/").length());
		int qnaPostId = 0;
		int userId = 0;
		
		try {
			ReplyService service = new ReplyService();
			
			if(command.equals("list")) { // 댓글 목록 조회
				
				qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
				System.out.println("목록클릭 게시판번호:"+qnaPostId); //확인
				
				List<Reply> rlist = service.selectList(qnaPostId);
				Gson gson = new Gson();
				gson.toJson(rlist, response.getWriter());
//				System.out.println("목록 : "+rlist);//확인
			}
			
			else if(command.equals("insertReply")) { // 댓글 삽입
				userId = Integer.parseInt(request.getParameter("userId"));
				qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
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
			
			else if(command.equals("updateReply")) { // 댓글 수정
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				System.out.println("댓글 번호 : " + replyId);
				String replyContent = request.getParameter("replyContent");
				System.out.println("수정된 댓글 내용: " + replyContent);
				Reply reply = new Reply();
				
				reply.setReplyId(replyId);
				reply.setReplyContent(replyContent);
				
				int result = service.updateReply(reply);
				
				response.getWriter().print(result);
				System.out.println("수정result : " +result);
			}
			
			else if(command.equals("deleteReply")) { // 댓글 삭제
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				
				int result = service.deleteReply(replyId);
				
				response.getWriter().print(result);
			}
			// 좋아요
			
			else if(command.equals("likeCheck")) {
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				List<User> rList = service.selectUserList(replyId);
				Gson gson = new Gson();
				gson.toJson(rList, response.getWriter());
			
			}
			else if(command.equals("like")) {
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				int result = service.replyLike(replyId, userId);

				response.getWriter().print(result);
			}
			else if(command.equals("likeCount")) {
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				int result = service.replyLikeCount(replyId);

				response.getWriter().print(result);
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "알 수 없는 문제가 발생하였습니다.");
            request.getRequestDispatcher("/WEB-INF/views/common/error.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
