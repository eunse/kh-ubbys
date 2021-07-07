package com.ubbys.admin.apps.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.admin.apps.model.service.AppsService;
import com.ubbys.board.service.BoardService;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Category;
import com.ubbys.board.vo.Pagination;

@WebServlet({"/admin/appsList", "/admin/appsDeleteAlert", "/admin/appsDelete"})
public class AppsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getRequestURI().substring((request.getContextPath() + "/admin/apps").length());
		String path = null; 
		String boardTableName = "apps";
		HttpSession session = request.getSession();
		
		try {
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			AppsService service = new AppsService();
			
			// apps 목록
			if(command.equals("List")) {
				
				Pagination pagination = null;
				List<Apps> appsList = null;
				
				// 일반 목록
				if(request.getParameter("sc")==null) {
					pagination = service.getPagination(cp);
					appsList = service.getAppsList(pagination);
				}
				
				// 정렬
				else if(request.getParameter("sv").equals("DESC")) {
					String sc = request.getParameter("sc");
					String sv = request.getParameter("sv");
					pagination = service.getPagination(cp);
					appsList = service.getAppsSortList(pagination, sc, sv);
				}
				
				// 카테고리, 제목, 작성자
				else {
					String sc = request.getParameter("sc");
					String sv = request.getParameter("sv");
					pagination = service.getPagination(cp, sc, sv);
					appsList = service.getAppsSearchList(pagination, sc, sv);
				}
				
				List<Category> category = new BoardService().selectCategoryList(boardTableName);
				request.setAttribute("pagination", pagination);
				request.setAttribute("appsList", appsList);
				request.setAttribute("category", category);
				request.getRequestDispatcher("/WEB-INF/views/admin/apps/apps_list.jsp").forward(request, response);
			}
			
			// apps 삭제
			else if(command.equals("DeleteAlert")) {
				int postId = Integer.parseInt(request.getParameter("no"));
				session.setAttribute("modalTitle", "글 삭제");
				session.setAttribute("modalText", postId+"번 글이 삭제됩니다.");
				session.setAttribute("modalButtonText", "삭제하기");
				session.setAttribute("modalButtonLink", "appsDelete?no="+postId+"&cp="+cp);
				response.sendRedirect(request.getHeader("referer"));
			}
			else if(command.equals("Delete")) {
				int postId = Integer.parseInt(request.getParameter("no"));
				int result = service.deleteApps(postId);
				if(result>0) {
					session.setAttribute("modalTitle", "게시글 삭제 성공");
					session.setAttribute("modalText", "게시글 상태가 '삭제'로 변경되었습니다.");
					request.getRequestDispatcher("appsList?cp="+cp).forward(request, response);
				} else {
					session.setAttribute("modalTitle", "게시글 삭제 실패");
					session.setAttribute("modalText", "게시글 삭제에 실패했습니다.");
					request.getRequestDispatcher(request.getHeader("referer")).forward(request, response);
				}
			}
			

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
