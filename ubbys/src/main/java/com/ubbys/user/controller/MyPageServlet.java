package com.ubbys.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.board.service.AppsService;
import com.ubbys.board.service.ReplyService;
import com.ubbys.board.service.SelectQnaService;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Board;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaPagination;
import com.ubbys.board.vo.Reply;
import com.ubbys.user.service.UserService;
import com.ubbys.user.vo.User;

@WebServlet("/user")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = null;
		HttpSession session = request.getSession();
		User loginUser = ((User)session.getAttribute("loginUser"));
		try {
			// apps 최근 게시물 처리
			AppsService appService = new AppsService();
			try {
				Pagination pagination = appService.getPagination("apps", 1, loginUser.getUserNo());
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
				QnaPagination pagination = selectQnaService.getPagination(1, loginUser.getUserNo());
				pagination.setLimit(5);
				List<Qna> qnaList = selectQnaService.selectQnaList(pagination);
				
				request.setAttribute("pagination", pagination);
				request.setAttribute("qnaList", qnaList);			
			} catch(Exception err) {
				err.printStackTrace();
			}
			
			// 내 댓글 목록 관련
			ReplyService replyService = new ReplyService();
			try {
				List<Reply> myReplyList = replyService.selectMyReplyList(loginUser.getUserNo());
				request.setAttribute("myReplyList", myReplyList);
				System.out.println("내댓글"+myReplyList);
			} catch(Exception err) {
				err.printStackTrace();
			}

			
			// 프로필 관련
			UserService userService = new UserService();
			User user = userService.userInfo(loginUser.getUserNo());
			request.setAttribute("user", user);
			
			view = request.getRequestDispatcher("/WEB-INF/views/user/mypage.jsp");
			view.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "알 수 없는 오류가 발생하였습니다.");
            request.getRequestDispatcher("/WEB-INF/views/common/error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
