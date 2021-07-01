package com.ubbys.board.controller;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.ubbys.board.service.AppsService;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;
import com.ubbys.common.UbbysRenamePolicy;
import com.ubbys.user.vo.User;

@WebServlet("/apps/*")
public class AppsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher view = null;
	private String boardTableName = "apps";
	private AppsService service = new AppsService();
	private String modalTitle = null;
	private String modalText = null;
	private String path = null;
       
    public AppsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getRequestURI().substring((request.getContextPath() + "/apps/").length());
		
		try {
			// Current Page
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			
			// 목록
			if (command.equals("list")) {
				Pagination pagination = service.getPagination(boardTableName, cp);
				List<Apps> appsList = service.selectAppsList(pagination);
				
				// pagination, appsList를 request에 속성으로 추가한 뒤 forward 
				request.setAttribute("pagination", pagination);
				request.setAttribute("appsList", appsList);
				view = request.getRequestDispatcher("/WEB-INF/views/board/apps_list.jsp");
				view.forward(request, response);
			} 
			// 상세
			else if(command.equals("view")) {
				int postId = Integer.parseInt(request.getParameter("no"));
				Apps apps = service.selectApps(postId);
				request.setAttribute("apps", apps);
				view = request.getRequestDispatcher("/WEB-INF/views/board/apps_view.jsp");
				view.forward(request, response);
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
