package com.ubbys.board.controller;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/apps/*")
public class AppsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AppsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/apps/").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		try {
			SelectBoardService service = new SelectBoardService();
			// Current Page
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			if (command.equals("list")) {
				int boardType = Integer.parseInt(request.getParameter("type"));
				Pagination pagination = service.getPagination(cp, boardType);
				
				List<Board> boardList = service.selectBoardList(pagination);
				
				// pagination, boardList를 request에 속성으로 추가한 뒤 forward 
				request.setAttribute("pagination", pagination);
				request.setAttribute("boardList", boardList);
				path = "/WEB-INF/views/board/boardList.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			} else if(command.equals("view")) {
				int boardNo = Integer.parseInt(request.getParameter("no"));
				Board board = service.selectBoardList(boardNo);
				request.setAttribute("board", board);
				path = "/WEB-INF/views/board/boardView.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
