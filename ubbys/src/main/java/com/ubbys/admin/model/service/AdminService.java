package com.ubbys.admin.model.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;

import com.ubbys.admin.model.dao.AdminDAO;
import com.ubbys.user.vo.User;

public class AdminService {
	
	private AdminDAO dao = new AdminDAO();

	/** 로그인 Service
	 * @param userEmail
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	public User login(String userEmail, String userPw) throws Exception{
		Connection conn = getConnection();
		
		User loginUser = dao.login(conn, userEmail, userPw);
		
		close(conn);
		
		return loginUser;
	}

}
