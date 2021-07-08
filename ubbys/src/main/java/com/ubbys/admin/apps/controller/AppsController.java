package com.ubbys.admin.apps.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.ubbys.admin.apps.model.service.AppsService;
import com.ubbys.board.service.BoardService;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Category;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Tag;
import com.ubbys.common.UbbysRenamePolicy;
import com.ubbys.user.vo.User;

@WebServlet({"/admin/appsList", "/admin/appsDeleteAlert", "/admin/appsDelete", "/admin/appsView", "/admin/appsWrite"})
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
			
			// apps 상세
			else if(command.equals("View")) {
				int postId = Integer.parseInt(request.getParameter("no"));
				Apps apps = service.selectApps(postId);
				request.setAttribute("apps", apps);
				request.getRequestDispatcher("/WEB-INF/views/admin/apps/apps_view.jsp").forward(request, response);
			}
			
			// apps 작성/수정
			else if(command.equals("Write")) {
				List<Category> category = new BoardService().selectCategoryList(boardTableName);
				request.setAttribute("category", category);
				path = "/WEB-INF/views/admin/apps/apps_write.jsp";
				
				if(request.getParameter("no") != null) {
					int postId = Integer.parseInt(request.getParameter("no"));
					Apps apps = new AppsService().selectApps(postId);
					
					// 수정에 이용할 부분
					int no = Integer.parseInt(request.getParameter("no"));
					int author = Integer.parseInt(request.getParameter("author"));
					
					apps.setPostContent(apps.getPostContent().replaceAll("<br>", "\r\n"));
					request.setAttribute("no", no);
					request.setAttribute("author", author);
					request.setAttribute("apps", apps);
					request.getRequestDispatcher(path).forward(request, response);
				} else {
					request.getRequestDispatcher(path).forward(request, response);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getRequestURI().substring((request.getContextPath() + "/admin/apps").length());
		String path = null; 
		String boardTableName = "apps";
		HttpSession session = request.getSession();
		String modalTitle = null;
		String modalText = null;
		
		try {
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			AppsService service = new AppsService();
			
			// apps 작성
			if(command.equals("Write")) {
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
				List<Tag> tagList = new com.ubbys.board.service.AppsService().insertTagInAppsTags(tagArr);
				apps.setTagList(tagList);
				
				Enumeration<String> images = mpRequest.getFileNames();
				if(images.hasMoreElements()) {
					String name = images.nextElement();
					if(mpRequest.getFilesystemName(name) != null) { 
						apps.setAppsIconUrl(request.getContextPath() + filePath + mpRequest.getFilesystemName(name));
					}
				}
				int postId = 0;
				boolean flag = false;
				if(!mpRequest.getParameter("no").equals("")) {
					apps.setPostId(Integer.parseInt(mpRequest.getParameter("no")));
					apps.setUserNo(Integer.parseInt(mpRequest.getParameter("author")));
					flag = true; // 수정하는 경우
				} 
				postId = new com.ubbys.board.service.AppsService().insertApps(apps, tagList, flag);
				
				if(postId > 0) {
					path = request.getContextPath() + "/admin/appsView?no=" + postId + "&cp=1";

				} else {
					modalText = "게시글 등록에 실패했습니다.";
					modalTitle = "게시글 등록 실패";
					
					path = request.getHeader("referer");
				}
				session.setAttribute("modalTitle", modalTitle);
				session.setAttribute("modalText", modalText);
				response.sendRedirect(path);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
