package com.ubbys.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ubbys.admin.model.service.AdminService;
import com.ubbys.user.vo.User;


@WebServlet("/admin/adminRecover")
public class AdminRecoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/admin/adminRecover.jsp");
		view.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int userNo = ((User) session.getAttribute("loginUser")).getUserNo();
		
		try {
			int result = new AdminService().recoverAccount(userNo);
			
			String path = null;
			String icon = null;
			String title = null;
			String text = null;

			if (result > 0) {
				icon = "success";
				title = "회원 복구 성공";
				text = "회원 복구가 완료되었습니다.";
				System.out.println(title);

				path = request.getContextPath();
				session.invalidate();
			
			} else {
				
				icon = "error";
				title = "회원 복구 실패";
				text = "회원 복구 중 문제가 발생했습니다. \n문제가 지속될 경우 고객센터 문의 바랍니다.";
				System.out.println(title);
				path = "/ubbys/admin/adminUnuser/unRegList";
			}
			session = request.getSession();
			session.setAttribute("icon", icon);
			session.setAttribute("title", title);
			session.setAttribute("text", text);

			response.sendRedirect(path);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "회원 복구 과정에서 문제가 발생했습니다.");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/error.jsp");
			view.forward(request, response);
		}
	}

}
