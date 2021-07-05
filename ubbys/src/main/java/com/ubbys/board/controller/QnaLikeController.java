package com.ubbys.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ubbys.board.service.QnaService;
import com.ubbys.board.vo.Qna;
import com.ubbys.user.vo.User;

@WebServlet({ "/qnaLikeCheck", "/qnaLike", "/qnaLikeCount" })
public class QnaLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = request.getRequestURI().substring((request.getContextPath() + "/qna").length());

		int qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
		int userId = ((User) request.getSession().getAttribute("loginUser")).getUserNo();

		int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));

		QnaService service = new QnaService();
		// HttpSession session = request.getSession();

		try {
			if (command.equals("LikeCheck")) {

				List<User> uList = service.selectUserList(qnaPostId);

				Gson gson = new Gson();
				gson.toJson(uList, response.getWriter());
			}

			else if (command.equals("Like")) {

				int result = service.qnaLike(qnaPostId, userId);
				// 좋아요 처리시 result = 1, 좋아요 취소시 result = 0 반환

				response.getWriter().print(result);
			}

			else if (command.equals("LikeCount")) {

				int result = service.qnaLikeCount(qnaPostId);

				response.getWriter().print(result);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
