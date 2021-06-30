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

import com.ubbys.board.service.QnaService;
import com.ubbys.board.service.SelectQnaService;
import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaCategory;
import com.ubbys.user.vo.User;

@WebServlet({"/qnaWrite", "/qnaInsert"})
public class QnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getRequestURI().substring((request.getContextPath() + "/qna").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		try {
			QnaService service = new QnaService();
			
			int cp = request.getParameter("cp")==null? 1 : Integer.parseInt(request.getParameter("cp"));
			
			if(command.equals("Write")){ // qna 글 작성 입력페이지로 이동
				
				List<QnaCategory> qnaCategory = service.selectQnaCategory();
				request.setAttribute("qnaCategory", qnaCategory);
				
				view = request.getRequestDispatcher("/WEB-INF/views/qnaWrite.jsp");
				view.forward(request, response);
			}
			
			else if(command.equals("Insert")) { // qna 글 삽입
				
				int qnaCategoryId = Integer.parseInt(request.getParameter("qnaCategoryId"));
				String qnaTitle = request.getParameter("inputTitle");
				String qnaContent = request.getParameter("inputContent");
				int userId = ((User)request.getSession().getAttribute("loginUser")).getUserNo();
				
				Qna qna = new Qna();
				qna.setQnaCategoryId(qnaCategoryId);
				qna.setQnaTitle(qnaTitle);
				qna.setQnaContent(qnaContent);
				qna.setUserId(userId);
				
				int result = service.insertQna(qna);
				
				HttpSession session = request.getSession();
				if(result>0) {
					session.setAttribute("modalTitle", "글 작성 성공");
					session.setAttribute("modalText", "QNA 게시판에 글이 등록되었습니다.");
					session.setAttribute("modalButtonText", "목록으로");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/qnaList");
					path = "qnaView?no="+result+"&cp=1";
					
				} else {
					session.setAttribute("modalTitle", "글 작성 실패");
					session.setAttribute("modalText", "QNA 글 등록에 실패했습니다.");
					session.setAttribute("modalButtonText", "작성 취소");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/qnaList");
					path = request.getHeader("referer");
				}
				response.sendRedirect(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
