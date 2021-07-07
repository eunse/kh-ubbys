package com.ubbys.admin.apps.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.admin.apps.model.service.AppsService;
import com.ubbys.board.service.BoardService;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Category;
import com.ubbys.board.vo.Pagination;

@WebServlet({"/admin/appsList", "/admin/appsWrite"})
public class AppsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getRequestURI().substring((request.getContextPath() + "/admin/apps").length());
		String path = null; 
		String boardTableName = "apps";
		
		try {
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			AppsService service = new AppsService();
			
			// apps 목록
			if(command.equals("List")) {
				Pagination pagination = service.getPagination(cp);
				List<Apps> appsList = service.getAppsList(pagination);
				List<Category> category = new BoardService().selectCategoryList(boardTableName);
				request.setAttribute("pagination", pagination);
				request.setAttribute("appsList", appsList);
				request.setAttribute("category", category);
				request.getRequestDispatcher("/WEB-INF/views/admin/apps/apps_list.jsp").forward(request, response);
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
