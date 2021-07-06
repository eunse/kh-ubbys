package com.ubbys.user.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.ubbys.common.UbbysRenamePolicy;
import com.ubbys.user.service.UserService;
import com.ubbys.user.vo.User;
/**
 * 
 * @author 백승훈
 *
 */
@WebServlet("/signup/add")
public class SignUpAddInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignUpAddInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/signup2.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		int maxSize = 2097152;
		String root = session.getServletContext().getRealPath("/");
		String filePath = "/upload/profile/";
		MultipartRequest mpRequest = new MultipartRequest(request, root+filePath, maxSize, "UTF-8", new UbbysRenamePolicy());
		
		String userLink = mpRequest.getParameter("inputWebsite");
		String userInterest = mpRequest.getParameter("inputInterest");
		String userIntroduce = mpRequest.getParameter("inputIntroduce");
		User user = new User();
		
		user.setUserNo(userNo);
		user.setUserLink(userLink);
		user.setUserInterest(userInterest);
		user.setUserIntroduce(userIntroduce);		
		Enumeration<String> images = mpRequest.getFileNames();
		if(images.hasMoreElements()) {
			String name = images.nextElement();
			if(mpRequest.getFilesystemName(name) != null) { 
				user.setUserPic(request.getContextPath() + filePath + mpRequest.getFilesystemName(name));
			}
		}
		
		try {
			UserService service = new UserService();
			int result = service.signUpAddInfo(user);
			if(result > 0) {
				System.out.println("추가정보 입력 성공");
				User refreshloginUser = service.refreshUserInfo(userNo);
				
				session = request.getSession();
				session.setAttribute("loginUser", refreshloginUser);
				response.sendRedirect(request.getContextPath());
			} else {
				System.out.println("추가정보 입력 실패");
				response.sendRedirect(request.getContextPath());
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
	}

}
