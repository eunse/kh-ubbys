package com.ubbys.admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.admin.model.service.AdminUserService;
import com.ubbys.board.vo.Pagination;
import com.ubbys.user.vo.User;

@WebServlet("/admin/adminUserList") // 경로 바꾸기
public class AdminUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			AdminUserService service = new AdminUserService(); // import할 service 클래스명 바꾸기
			
			int cp = request.getParameter("cp")==null? 1 : Integer.parseInt(request.getParameter("cp"));
			
			Pagination pagination = service.getPagination(cp);
			//User userList = service.getUserList(pagination);
			
			
			
			
			
			request.getSession().setAttribute("pagination", pagination);
			request.getRequestDispatcher("/WEB-INF/views/admin/adminUserList.jsp").forward(request, response); // 경로 또는 jsp 파일명 바꾸기
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
