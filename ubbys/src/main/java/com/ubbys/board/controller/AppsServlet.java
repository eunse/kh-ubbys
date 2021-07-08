package com.ubbys.board.controller;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
import com.ubbys.board.vo.Like;
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
			// 목록 출력에 필요한 조건(파라미터) 취득 
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			String categoryId = request.getParameter("category") == null ? "" : request.getParameter("category");
			String searchKey = request.getParameter("q") == null ? "" : request.getParameter("q");
			String searchType = request.getParameter("searchType") == null ? "" : request.getParameter("searchType");
			
			// 목록
			if (command.equals("list")) {
//				Pagination pagination = service.getPagination(boardTableName, cp);
//				List<Apps> appsList = service.selectAppsList(pagination);

				Pagination pagination = service.getPagination(boardTableName, cp, categoryId, searchKey, searchType);
				List<Apps> appsList = service.selectAppsList(pagination, categoryId, searchKey, searchType);
				List<Category> category = service.selectCategoryList(boardTableName);
				
				request.setAttribute("category", category);
				request.setAttribute("pagination", pagination);
				request.setAttribute("appsList", appsList);
				view = request.getRequestDispatcher("/WEB-INF/views/board/apps_list.jsp");
				view.forward(request, response);
			} 
			// 상세
			else if(command.equals("view")) {
				HttpSession session = request.getSession();
				int postId = Integer.parseInt(request.getParameter("no"));
				Apps apps = service.selectApps(postId);
				if(session.getAttribute("loginUser") != null) {
					int loginUserNo = ((User) session.getAttribute("loginUser")).getUserNo();
					Like like = service.selectLike(boardTableName, postId, loginUserNo);
					request.setAttribute("like", like);
				}				
				request.setAttribute("apps", apps);
				view = request.getRequestDispatcher("/WEB-INF/views/board/apps_view.jsp");
				view.forward(request, response);
			}
			// 작성&수정
			else if(command.equals("write")) {
				List<Category> category = service.selectCategoryList(boardTableName);
				request.setAttribute("category", category);
				path = "/WEB-INF/views/board/apps_write.jsp";
				view = request.getRequestDispatcher(path);
				
				if(request.getParameter("no") != null) {
					int postId = Integer.parseInt(request.getParameter("no"));
					Apps apps = new AppsService().selectApps(postId);
					HttpSession session = request.getSession();
					int loginUserNo = ((User)session.getAttribute("loginUser")).getUserNo();
					int author = apps.getUserNo();
					
					if(loginUserNo != author) {
						modalText = "잘못된 접근입니다.";
						modalTitle = "잘못된 접근";
						session.setAttribute("modalTitle", modalTitle);
						session.setAttribute("modalText", modalText);
						response.sendRedirect(request.getContextPath());
					} else {
						apps.setPostContent(apps.getPostContent().replaceAll("<br>", "\r\n"));
						request.setAttribute("apps", apps);
						view.forward(request, response);
					}
				} else {
					view.forward(request, response);
				}

			}
			// 삭제
			else if(command.equals("delete")) {
				HttpSession session = request.getSession();
				int postId = Integer.parseInt(request.getParameter("no"));
				User loginUserNo = ((User)session.getAttribute("loginUser"));
				if(loginUserNo != null) {
					int userNo = ((User)session.getAttribute("loginUser")).getUserNo();
					int author = service.selectAuthor(postId);
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
			// 좋아요 수 조회(for AJAX)
			else if(command.equals("like")) {
				int postId = Integer.parseInt(request.getParameter("no"));
				int result = service.selectLike(boardTableName, postId);
				request.setAttribute("likeCount", result);
				response.getWriter().print(result);
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getRequestURI().substring((request.getContextPath() + "/apps/").length());
		try {
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));
			
			// 작성, 수정 
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
				int postId = 0;
				boolean flag = false;
				if(request.getParameter("no") != null) {
					apps.setPostId(Integer.parseInt(request.getParameter("no")));
					flag = true; // 수정하는 경우
				} 
				postId = service.insertApps(apps, tagList, flag);
				
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
			// 좋아요 추가/삭제/증감(for AJAX)
			else if(command.equals("like")) {
				HttpSession session = request.getSession();
				int doLikeResult = 0;
				int currentLikeCount = 0;
				HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
				if(session.getAttribute("loginUser") != null) {
					int userNo = ((User)session.getAttribute("loginUser")).getUserNo();
					int postId = Integer.parseInt(request.getParameter("no"));
					int likePostId = 0;
					int upDownFlag = 0;
					// request 속성에 like가 전달되어 있는 경우에만 likePostId 취득
					if(request.getParameter("likePostId") != null) {
						likePostId = Integer.parseInt(request.getParameter("likePostId"));
					}
					
					if(likePostId == postId) { // 이미 좋아요 한 경우
						upDownFlag = -1;
						doLikeResult = service.deleteLike(boardTableName, userNo, postId);
					} else if(likePostId != postId) { // 좋아요 하지 않은 경우
						upDownFlag = 1;
						doLikeResult = service.insertLike(boardTableName, userNo, postId);
					}
					currentLikeCount = service.selectLike(boardTableName, postId);

					resultMap.put("doLikeResult", doLikeResult);
					resultMap.put("currentLikeCount", currentLikeCount);
					resultMap.put("upDownFlag", upDownFlag);
//					Like like = service.selectLike(boardTableName, postId, userNo);
//					request.setAttribute("like", like);
					Gson gson = new Gson();
					gson.toJson(resultMap, response.getWriter());
				} else {
					// 회원이 아닌 경우의 응답
					response.getWriter().print(0);
				}

//				response.getWriter().print(resultArr);
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
		
		
	}

}
