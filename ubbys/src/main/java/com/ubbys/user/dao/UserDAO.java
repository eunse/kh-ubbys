package com.ubbys.user.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.ubbys.user.vo.User;

public class UserDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	
	public UserDAO() {
		String filePath = UserDAO.class.getResource("/com/ubbys/sql/user-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	/** 
	 * 회원가입 DAO
	 * @param conn
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, User user) throws Exception {
		int result = 0;
		String sql = prop.getProperty("signUp");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserEmail());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserNickname());
			result = pstmt.executeUpdate();	
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 회원가입 후 추가정보 기입 DAO
	 * @param conn
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public int signUpAddInfo(Connection conn, User user) throws Exception {
		int result = 0;
		String sql = prop.getProperty("signUpAddInfo");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getUserNo());
			pstmt.setString(2, user.getUserPic());
			pstmt.setString(3, user.getUserLink());
			pstmt.setString(4, user.getUserInterest());
			pstmt.setString(5, user.getUserIntroduce());
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/**
	 * 로그인 DAO
	 * @param conn
	 * @param userEmail
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	public User login(Connection conn, String userEmail, String userPw) throws Exception {
		User loginUser = null;
		String sql = prop.getProperty("login");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userEmail);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginUser = new User(
					rs.getInt("user_id"), 
					rs.getString("user_email"), 
					rs.getString("user_nickname"), 
					rs.getDate("user_regdate"),
					rs.getString("user_is_admin"),
					rs.getString("user_pic"),
					rs.getString("user_link"),
					rs.getString("user_interest"),
					rs.getString("user_introduce")
				);
			}
		} finally {
			close(rs);
			close(pstmt);
		}		
		return loginUser;
	}
}
