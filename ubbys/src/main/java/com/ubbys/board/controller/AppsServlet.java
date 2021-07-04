package com.ubbys.board.controller;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.ubbys.board.service.AppsService;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Category;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Tag;
import com.ubbys.common.UbbysRenamePolicy;
import com.ubbys.user.vo.User;
/**
 * 
 * @author 백승훈
 *
 */
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
			// 작성
			else if(command.equals("write")) {
				List<Category> category = service.selectCategoryList(boardTableName);
				request.setAttribute("category", category);
				path = "/WEB-INF/views/board/apps_write.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			// 임시 태그 목록 조회 (ajax)
			else if(command.equals("tag")) {
				List<Tag> tagList = service.selectTagList();
				Gson gson = new Gson();
				gson.toJson(tagList, response.getWriter());
			}
			
			// 삭제
			else if(command.equals("delete")) {
				HttpSession session = request.getSession();
				int postId = Integer.parseInt(request.getParameter("no"));
				User loginUserNo = ((User)session.getAttribute("loginUser"));
				if(loginUserNo != null) {
					int userNo = ((User)session.getAttribute("loginUser")).getUserNo();
					int author = service.selectAuthor(postId);
					System.out.println("userNo:" + userNo);
					System.out.println("author:" + author);
					if(userNo != author) {
						path = request.getContextPath();
					} else {
						int result = service.deleteApps(postId, userNo);
						if(result > 0) { 
							path = request.getContextPath() + "/apps/list?cp=" + cp;
						} else {
							modalText = "게시글 삭제에 실패했습니다. 관리자에게 문의해주세요.";
							modalTitle = "게시글 삭제 실패";
							session.setAttribute("modalTitle", modalTitle);
							session.setAttribute("modalText", modalText);
							path = request.getHeader("referer");
						}
					}
				} else {
					path = request.getHeader("referer");
				}
				response.sendRedirect(path);
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
			if(command.equals("write")) {
				HttpSession session = request.getSession();
				int maxSize = 2097152;
				String root = session.getServletContext().getRealPath("/");
				String filePath = "/upload/";
				MultipartRequest mpRequest = new MultipartRequest(request, root+filePath, maxSize, "UTF-8", new UbbysRenamePolicy());
				int userNo = ((User)session.getAttribute("loginUser")).getUserNo();
				String postTitle = mpRequest.getParameter("inputTitle");
				String postContent = mpRequest.getParameter("inputContent");
				int categoryId = Integer.parseInt(mpRequest.getParameter("selectCategory"));
				String appsLink = mpRequest.getParameter("inputDownloadUrl");
				String tagString = mpRequest.getParameter("tagString");
				Apps apps = new Apps();
				apps.setPostTitle(postTitle);
				apps.setPostContent(postContent);
				apps.setCategoryId(categoryId);
				apps.setUserNo(userNo);
				apps.setAppsLink(appsLink);
				String[] tagArr = tagString.split(",");
				apps.setTagArr(tagArr);
				List<Tag> tagList = service.insertTagInAppsTags(tagArr);
				apps.setTagList(tagList);
				
				Enumeration<String> images = mpRequest.getFileNames();
				if(images.hasMoreElements()) {
					String name = images.nextElement();
					if(mpRequest.getFilesystemName(name) != null) { 
						apps.setAppsIconUrl(request.getContextPath() + filePath + mpRequest.getFilesystemName(name));
					}
				}
				int postId = service.insertApps(apps, tagList);
				if(postId > 0) {
					path = request.getContextPath() + "/apps/view?no=" + postId + "&cp=1";

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
