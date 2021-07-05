package com.ubbys.user.service;

import java.sql.Connection;
import static com.ubbys.common.JDBCTemplate.*;
import com.ubbys.user.dao.UserDAO;
import com.ubbys.user.vo.User;

public class UserService {
	private static UserDAO dao = new UserDAO();
	
	/** 
	 * 회원가입 Service
	 * @author 백승훈
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
	 * 회원가입 후 추가정보 기입 Service
	 * @author 백승훈
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public static int signUpAddInfo(User user) throws Exception {
		Connection conn = getConnection();
		int result = dao.signUpAddInfo(conn, user);
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
	 * @author 백승훈
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
	
	/**
	 * 사용자 정보 갱신 Service
	 * @author 백승훈
	 * @param userNo
	 * @return loginUser
	 * @throws Exception
	 */
	public User refreshUserInfo(int userNo) throws Exception {
		Connection conn = getConnection();
		User loginUser = dao.refreshUserInfo(conn, userNo);
		close(conn);
		return loginUser;
	}
	
	/**
	 * 회원정보 수정 Service
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public int updateUser(User user) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateUser(conn, user);
		
		if(result > 0) {
			
			result = dao.updateUserInfo(conn, user);
			
			if(result > 0) {
				commit(conn);
			}else {
				rollback(conn);
			}
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/** 비밀번호 변경 Service
	 * @param currentPw
	 * @param newPw
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int changePw(String currentPw, String newPw, int userNo) throws Exception{
		Connection conn = getConnection();
		int result = dao.changePw(conn, currentPw, newPw, userNo);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/** 회원 탈퇴 Service
	 * @param currentPw
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int delectAccount(String currentPw, int userNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.delectAccount(conn, currentPw, userNo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/** 중복 검사 Service
	 * @param userEmail
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String userEmail) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.idDupCheck(conn, userEmail);
		
		close(conn);
		
		return result;
	}

	/** 마이페이지 유저 정보 Service
	 * @param userNo
	 * @return user
	 * @throws Exception
	 */
	public User userInfo(int userNo) throws Exception{
		Connection conn = getConnection();
		User user = dao.userInfo(conn, userNo);
		close(conn);		
		return user;
	}
}