package com.ubbys.user.service;

import java.sql.Connection;
import static com.ubbys.common.JDBCTemplate.*;
import com.ubbys.user.dao.UserDAO;
import com.ubbys.user.vo.User;

public class UserService {
	private static UserDAO dao = new UserDAO();
	
	/** 
	 * 회원가입 Service
	 * @param user
	 * @return result
	 */
	public static int signUp(User user) throws Exception {
		Connection conn = getConnection();
		int result = dao.signUp(conn, user);
		if(result > 0) {
			commit(conn);
		} else { 
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/**
	 * 로그인 Service
	 * @param userEmail
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	public User login(String userEmail, String userPw) throws Exception {
		Connection conn = getConnection();
		User loginUser = dao.login(conn, userEmail, userPw);
		close(conn);
		return loginUser;
	}
}
