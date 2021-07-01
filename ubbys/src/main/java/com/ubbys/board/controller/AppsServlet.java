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
		String command = request.getRequestURI().substring((request.getContextPath() + "/apps/").length());
		try {
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			
			// 새로 작성 
			if(command.equals("new")) {
				HttpSession session = request.getSession();
				
				int maxSize = 2097152;
				String root = session.getServletContext().getRealPath("/");
				String filePath = "upload/";
				MultipartRequest mpRequest = new MultipartRequest(request, root+filePath, maxSize, "UTF-8", new UbbysRenamePolicy());
				
				int userNo = ((User)session.getAttribute("loginUser")).getUserNo();
				String postTitle = mpRequest.getParameter("postTitle");
				String postContent = mpRequest.getParameter("postContent");
				int categoryId = Integer.parseInt(mpRequest.getParameter("categoryId"));
				
				Apps apps = new Apps();
				apps.setPostTitle(postTitle);
				apps.setPostContent(postContent);
				apps.setCategoryId(categoryId);
				apps.setUserNo(userNo);
				
				Enumeration<String> images = mpRequest.getFileNames();			
				if(images.hasMoreElements()) {
					String name = images.nextElement();
					if(mpRequest.getFilesystemName(name) != null) { 
						apps.setAppsIconUrl(filePath);
					}
				}
				int result = service.insertApps(apps);
				if(result > 0) {
					path = request.getContextPath() + "/apps/view?no=" + result + "&cp=1";
				} else {
					modalText = "게시글 등록에 실패했습니다. 관리자에게 문의해주세요.";
					modalTitle = "게시글 등록 실패";
					
					path = request.getHeader("referer");
				}
				session.setAttribute("modalTitle", modalTitle);
				session.setAttribute("modalText", modalText);
				response.sendRedirect(path);
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
		
		
	}

}
