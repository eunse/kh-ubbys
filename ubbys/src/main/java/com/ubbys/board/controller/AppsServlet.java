package com.ubbys.board.controller;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.board.service.AppsService;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;

@WebServlet("/apps/*")
public class AppsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AppsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getRequestURI().substring((request.getContextPath() + "/apps/").length());
		RequestDispatcher view = null;
		
		String boardTableName = "apps";
		
		try {
			AppsService service = new AppsService();
			// Current Page
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			if (command.equals("list")) {
				Pagination pagination = service.getPagination(boardTableName, cp);
				List<Apps> appsList = service.selectAppsList(pagination);
				
				// pagination, boardList를 request에 속성으로 추가한 뒤 forward 
				request.setAttribute("pagination", pagination);
				request.setAttribute("appsList", appsList);
				System.out.println(appsList);
				view = request.getRequestDispatcher("/WEB-INF/views/board/apps_list.jsp");
				view.forward(request, response);
			} else if(command.equals("view")) {
//				int boardNo = Integer.parseInt(request.getParameter("no"));
//				Apps apps = service.selectAppsList(boardNo);
//				request.setAttribute("board", board);
//				path = "/WEB-INF/views/board/boardView.jsp";
//				view = request.getRequestDispatcher(path);
//				view.forward(request, response);
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
