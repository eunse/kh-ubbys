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
import com.ubbys.board.vo.Board;
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
		
		try {
			
			// 내 qna 목록 관련
			SelectQnaService service = new SelectQnaService();
			int cp = request.getParameter("cp")==null? 1 : Integer.parseInt(request.getParameter("cp"));
			QnaPagination pagination = service.getPagination(cp);
			
			HttpSession session = request.getSession();
			int userNo = ((User) session.getAttribute("loginUser")).getUserNo();
			List<Qna> myQnaList = service.selectMyQnaList(userNo);
//			List<Qna> myQnaList = service.selectMyQnaList(pagination, userNo);
			
			request.setAttribute("pagination", pagination); // Service, DAO 부분 추가필요
			request.setAttribute("myQnaList", myQnaList);
			System.out.println("내질문"+myQnaList);

			// 내 apps 목록 관련
			AppsService appsService = new AppsService();
			List<Board> myAppsList = appsService.selectMyAppsList(userNo);
			request.setAttribute("myAppsList", myAppsList);
			System.out.println("내앱"+myAppsList);
			
			// 내 댓글 목록 관련
			ReplyService replyService = new ReplyService();
			List<Reply> myReplyList = replyService.selectMyReplyList(userNo);
			request.setAttribute("myReplyList", myReplyList);
			System.out.println("내댓글"+myReplyList);
			
			// 프로필 관련
			UserService userService = new UserService();
			userNo = ((User) session.getAttribute("loginUser")).getUserNo();
			User user = userService.userInfo(userNo);
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
