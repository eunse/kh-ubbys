package com.ubbys.admin.reply.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.admin.qna.service.SelectQnaService;
import com.ubbys.admin.qna.vo.Qna;
import com.ubbys.board.service.ReplyService;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Reply;
import com.ubbys.user.vo.User;


@WebServlet({"/admin/replyList", "/admin/replyView", "/admin/replyWrite", "/admin/replyInsert", "/admin/replyUpdate", "/admin/replyDelete"})
//@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/admin/reply").length());
		String path = null;	
		RequestDispatcher view = null;
		
		try {
			ReplyService service = new ReplyService();
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt( request.getParameter("cp") );
			if(command.equals("List")) { // 댓글 목록 조회
				
				Pagination pagination = service.getPagination(cp);
				List<Reply> rlist = service.selectList(pagination);
				
				view = request.getRequestDispatcher("/WEB-INF/views/admin/reply/replyList.jsp");
				request.setAttribute("replyList", rlist);
				request.setAttribute("pagination", pagination);
				view.forward(request, response);
//				System.out.println("목록 : "+rlist);//확인
			}
			
			else if(command.equals("Insert")) { // 댓글 삽입
				int userId = ((User)request.getSession().getAttribute("loginUser")).getUserNo();
				int qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
				String replyContent = request.getParameter("replyContent");
				System.out.println("댓글내용 : " + replyContent);//확인
				System.out.println("게시글번호 : " + qnaPostId);//확인
				System.out.println("회원번호 : " + userId);//확인
				Reply reply = new Reply();
				int result = 0;
				reply.setUserId(userId);
				reply.setQnaPostId(qnaPostId);
				reply.setReplyContent(replyContent);
				int replyId = 0;
				if(request.getParameter("replyId") != null){
					replyId = Integer.parseInt(request.getParameter("replyId"));
				}
				if(replyId > 0) {
					reply.setReplyId(replyId);
					result = service.updateReply(reply);
				}else{
					result = service.insertReply(reply);
				}
				
				HttpSession session = request.getSession();
				if(result>0) {
					session.setAttribute("modalTitle", "댓글 작성 성공");
					session.setAttribute("modalText", "댓글이 등록되었습니다.");
					session.setAttribute("modalButtonText", "목록으로");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/replyList");
					path = "replyList";
					
				} else {
					session.setAttribute("modalTitle", "댓글 작성 실패");
					session.setAttribute("modalText", "댓글 등록에 실패했습니다.");
					session.setAttribute("modalButtonText", "작성 취소");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/replyList");
					path = request.getHeader("referer");
				}
				response.sendRedirect(path);
			}else if(command.equals("Write")) {
				List<Qna> qnaList = new SelectQnaService().selectQnaList();
				request.setAttribute("qnaList", qnaList);
				
				int replyId = 0;
				if(request.getParameter("replyId") != null){
					replyId = Integer.parseInt(request.getParameter("replyId"));
				}
				
				if(replyId > 0) {
					Reply reply = service.selectReply(replyId);
					request.setAttribute("reply", reply);
				}
				
				view = request.getRequestDispatcher("/WEB-INF/views/admin/reply/replyWrite.jsp");
				
				view.forward(request, response);
			}
			else if(command.equals("Delete")){ // 댓글 삭제
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				System.out.println("댓글번호No:"+replyId);
				
				int result = service.deleteReply(replyId);

				HttpSession session = request.getSession();
				if(result>0) {
					session.setAttribute("modalTitle", "댓글 삭제 성공");
					session.setAttribute("modalText", "댓글이 삭제되었습니다.");
					session.setAttribute("modalButtonText", "목록으로");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/replyList");
					path = request.getContextPath()+"/admin/replyList";
					
				} else {
					session.setAttribute("modalTitle", "댓글 삭제 실패");
					session.setAttribute("modalText", "댓글 삭제에 실패했습니다.");
					session.setAttribute("modalButtonText", "삭제 취소");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/replyList");
					path = request.getHeader("referer");
				}
				response.sendRedirect(path);
			}else if(command.equals("View")){
				int replyId = Integer.parseInt(request.getParameter("replyId"));
				List<Qna> qnaList = new SelectQnaService().selectQnaList();
				Reply reply = service.selectReply(replyId);
				request.setAttribute("reply", reply);
				for(Qna qna : qnaList){
					if(qna.getQnaPostId() == reply.getQnaPostId()){
						request.setAttribute("qna", qna);	
					}
				}
				
				view = request.getRequestDispatcher("/WEB-INF/views/admin/reply/replyView.jsp");
				
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
