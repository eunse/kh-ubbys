package com.ubbys.user.controller;

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

import com.oreilly.servlet.MultipartRequest;
import com.ubbys.common.MyFileRenamePolicy;
import com.ubbys.user.service.UserService;
import com.ubbys.user.vo.User;

@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			HttpSession session = request.getSession();
			try {
				// 프로필 관련
				UserService userService = new UserService();
				int userNo = ((User) session.getAttribute("loginUser")).getUserNo();
				User userInfo = userService.userInfo(userNo);
				request.setAttribute("userInfo", userInfo);
				System.out.println("userInfo" + userInfo);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/user/setting.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			int userNo = loginUser.getUserNo();
			User user = new User();

			int maxSize = 1024 * 1024 * 20;
			String root = session.getServletContext().getRealPath("/");
			String filePath = "upload/profile/";

//		String userPicPath = filePath+userPic;
//		System.out.println(userPicPath);

			MultipartRequest mpRequest = new MultipartRequest(request, root + filePath, maxSize, "UTF-8",
					new MyFileRenamePolicy());

			String userPic = mpRequest.getParameter("userImageInput");
			String userNickName = mpRequest.getParameter("inputName");
			String userLink = mpRequest.getParameter("inputWebsite");
			String userInterest = mpRequest.getParameter("inputInterest");
			String userIntroduce = mpRequest.getParameter("inputIntroduce");

			user.setUserNo(userNo);
			user.setUserPic(userPic); //
			user.setUserNickname(userNickName);
			user.setUserLink(userLink);
			user.setUserInterest(userInterest);
			user.setUserIntroduce(userIntroduce);

//			request.setAttribute("user", user);
			
			List<User> picList = new ArrayList<User>();
			
			Enumeration<String> images = mpRequest.getFileNames();
			
			while (images.hasMoreElements()) {
				String name = images.nextElement();
				System.out.println("name : " + name); // 확인
				
				if (mpRequest.getFilesystemName(name) != null) {
					String picName = mpRequest.getFilesystemName(name);
					
					user.setUserPic(filePath + picName);
					user.setUserNo(userNo);
					
					picList.add(user);
				}
				System.out.println("회원수정 user" +user);
				for(User pic : picList) {
					System.out.println("pic:"+pic);
				}
			}
			
			
			
			int result = new UserService().updateUser(user);
//			int result = new UserService().updateUser(picList, user);
			System.out.println("회원수정결과행:"+result);
			String modalTitle = null;
			String modalText = null;
			String path = null;

			if (result > 0) {

//				System.out.println(alertTitle);
				loginUser.setUserNickname(userNickName);
				loginUser.setUserPic(userPic);
				loginUser.setUserLink(userLink);
				loginUser.setUserInterest(userInterest);
				loginUser.setUserIntroduce(userIntroduce);

				modalTitle = "회원정보 수정 성공";
				modalText = "회원정보 수정이 완료되었습니다.";
				path = request.getContextPath() + "/user";

			} else {
				modalTitle = "회원정보 수정 실패";
				modalText = "회원정보 수정 중 문제가 발생했습니다.";
//				System.out.println(alertTitle);
				path = request.getHeader("referer");
			}
			session = request.getSession();

			session.setAttribute("modalTitle", modalTitle);
			session.setAttribute("modalText", modalText);

			response.sendRedirect(path);
//			response.sendRedirect(request.getContextPath()+"/user"); // 절대 경로
//			response.sendRedirect("user"); // 상대 경로 <-안됨..

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("errorMsg", "회원정보 수정 과정에서 문제가 발생했습니다.");

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/error.jsp");
			view.forward(request, response);
		}
	}

}
