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
import com.ubbys.user.vo.UnRegUser;


@WebServlet("/admin/adminUnuser/*")

public class AdminUnregUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/admin/adminUnuser/").length());

		int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt(request.getParameter("cp"));

		try {
			AdminService service = new AdminService(); //

			if (command.equals("unRegList")) {

				Pagination pagination = null;
				List<UnRegUser> unRegUserList = null;

				if (request.getParameter("sv") == null) {
					pagination = service.getPagination(cp);

					unRegUserList = service.selectUnregUserList(pagination);
					
				} else if (request.getParameter("sk").substring(0, 4).equals("sort")) {
					String searchKey = request.getParameter("sk");
					String searchValue = request.getParameter("sv");

					pagination = service.getPagination(cp, searchKey, searchValue);
					unRegUserList = service.getUnRegUserSoltList(pagination, searchKey, searchValue);

				} else {
					String searchKey = request.getParameter("sk");
					String searchValue = request.getParameter("sv");

					pagination = service.getPagination(cp, searchKey, searchValue);
					unRegUserList = service.selectunRegUserList(pagination, searchKey, searchValue);

				}

				request.setAttribute("pagination", pagination);
				request.setAttribute("unRegUserList", unRegUserList);
				request.getRequestDispatcher("/WEB-INF/views/admin/adminUnregUserList.jsp").forward(request, response);

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