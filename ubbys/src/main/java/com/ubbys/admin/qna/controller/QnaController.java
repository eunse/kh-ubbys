package com.ubbys.admin.qna.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.admin.qna.service.QnaService;
import com.ubbys.admin.qna.service.SelectQnaService;
import com.ubbys.admin.qna.vo.Qna;
import com.ubbys.admin.qna.vo.QnaCategory;
import com.ubbys.user.vo.User;

@WebServlet({"/admin/qnaWrite","/admin/qnaInsert", "/admin/qnaUpdateForm", "/admin/qnaUpdate", "/admin/qnaDelete"})
public class QnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getRequestURI().substring((request.getContextPath() + "/admin/qna").length());
		
		String path = null;	
		RequestDispatcher view = null;
		
		try {
			QnaService service = new QnaService();
			
			int cp = request.getParameter("cp")==null? 1 : Integer.parseInt(request.getParameter("cp"));
			System.out.println(cp);
			// qna 글 작성 입력페이지 이동
			if(command.equals("Write")){
				
				List<QnaCategory> qnaCategory = service.selectQnaCategory();
				request.setAttribute("qnaCategory", qnaCategory);
				
				view = request.getRequestDispatcher("/WEB-INF/views/admin/qna/qnaWrite.jsp");
				view.forward(request, response);
			}
			
			// qna 글 삽입 Controller
			else if(command.equals("Insert")) {
				
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
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/qnaList");
					path = "qnaView?no="+result+"&cp=1";
					
				} else {
					session.setAttribute("modalTitle", "글 작성 실패");
					session.setAttribute("modalText", "QNA 글 등록에 실패했습니다.");
					session.setAttribute("modalButtonText", "작성 취소");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/qnaList");
					path = request.getHeader("referer");
				}
				response.sendRedirect(path);
			}
			

			else if(command.equals("UpdateForm")){ // qna 글 작성 입력페이지로 이동
				List<QnaCategory> qnaCategory = service.selectQnaCategory();
				
				int qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
				Qna qna = new SelectQnaService().selectQna(qnaPostId);
				qna.setQnaContent(qna.getQnaContent().replaceAll("<br>", "\r\n"));
				
				request.setAttribute("qnaCategory", qnaCategory);
				request.setAttribute("qna", qna);
				
				request.getRequestDispatcher("/WEB-INF/views/admin/qna/qnaUpdate.jsp").forward(request, response);
			}
			
			else if(command.equals("Update")) { // qna 글 수정
				
				int qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
				int qnaCategoryId = Integer.parseInt(request.getParameter("qnaCategoryId"));
				String qnaTitle = request.getParameter("inputTitle");
				String qnaContent = request.getParameter("inputContent");
				
				Qna qna = new Qna();
				qna.setQnaPostId(qnaPostId);
				qna.setQnaCategoryId(qnaCategoryId);
				qna.setQnaTitle(qnaTitle);
				qna.setQnaContent(qnaContent);
				
				int result = service.updateQna(qna);
				
				HttpSession session = request.getSession();
				cp = Integer.parseInt(request.getParameter("cp"));
				if(result>0) {
					session.setAttribute("modalTitle", "글 수정 성공");
					session.setAttribute("modalText", "QNA 글이 성공적으로 수정되었습니다.");
					session.setAttribute("modalButtonText", "목록으로");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/qnaList?cp="+cp);
					path = "qnaView?no="+qnaPostId+"&cp="+cp;
					
				} else {
					session.setAttribute("modalTitle", "글 수정 실패");
					session.setAttribute("modalText", "QNA 글 수정에 실패했습니다.");
					session.setAttribute("modalButtonText", "수정 취소");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/qna/qnaView?no="+qnaPostId);
					path = request.getHeader("referer");
				}
				response.sendRedirect(path);
			}
			
			else if(command.equals("Delete")) { // qna 글 삭제
				
				int qnaPostId = Integer.parseInt(request.getParameter("qnaPostId"));
				
				Qna qna = new Qna();
				qna.setQnaPostId(qnaPostId);
				
				int result = service.updateQnaStatus(qna);
				
				HttpSession session = request.getSession();
				cp = Integer.parseInt(request.getParameter("cp"));
				if(result>0) {
					session.setAttribute("modalTitle", "글 삭제 성공");
					session.setAttribute("modalText", "QNA 글이 성공적으로 삭제되었습니다.");
					session.setAttribute("modalButtonText", "목록으로");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/qnaList?cp="+cp);
					path = "qnaList?cp="+cp;
					
				} else {
					session.setAttribute("modalTitle", "글 삭제 실패");
					session.setAttribute("modalText", "QNA 글 삭제에 실패했습니다.");
					session.setAttribute("modalButtonText", "삭제 취소");
					session.setAttribute("modalButtonLink", request.getContextPath()+"/admin/qna/qnaView?no="+qnaPostId);
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
