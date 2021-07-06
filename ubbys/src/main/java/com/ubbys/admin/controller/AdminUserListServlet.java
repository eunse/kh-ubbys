package com.ubbys.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.admin.model.service.AdminService;
import com.ubbys.board.vo.Pagination;
import com.ubbys.user.vo.User;

@WebServlet("/adminUser/*") // 경로 바꾸기
public class AdminUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command =  uri.substring((contextPath + "/adminUser/").length()); 
		
		int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));

		try {
			AdminService service = new AdminService(); // import할 service 클래스명 바꾸기

			if (command.equals("list")) {

				Pagination pagination = null;
				List<User> userList = null;
				
				

				if (request.getParameter("sv") == null) {
					pagination = service.getPagination(cp);

					userList = service.selectUserList(pagination);
					
					System.out.println(userList);
				} else {
					String searchKey = request.getParameter("sk");
					String searchValue = request.getParameter("sv");

					pagination = service.getPagination(cp, searchKey, searchValue);
					userList = service.selectUserList(pagination, searchKey, searchValue);

				}

				request.setAttribute("pagination", pagination);
				request.setAttribute("userList", userList);
				request.getRequestDispatcher("/WEB-INF/views/admin/adminUserList.jsp").forward(request, response); 

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
