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
	private PreparedStatement pstmtSecond = null;
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
	 * @author 백승훈
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
	 * @author 백승훈
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
	 * @author 백승훈
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

			if (rs.next()) {
				loginUser = new User(rs.getInt("user_id"), rs.getString("user_email"), rs.getString("user_nickname"),
						rs.getDate("user_regdate"), rs.getString("user_is_admin"), rs.getString("user_pic"),
						rs.getString("user_link"), rs.getString("user_interest"), rs.getString("user_introduce"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return loginUser;
	}

	/**
	 * 회원정보 수정 DAO
	 * 
	 * @param conn
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public int updateUser(Connection conn, User user) throws Exception {
		int result = 0;

		try {
			String sql = prop.getProperty("editUser");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserNickname());
			pstmt.setInt(2, user.getUserNo());
			result += pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/** 회원 추가정보 수정 DAO
	 * @param conn
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public int updateUserInfo(Connection conn, User user) throws Exception{
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("editUserInfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserPic());
			pstmt.setString(2, user.getUserLink());
			pstmt.setString(3, user.getUserInterest());
			pstmt.setString(4, user.getUserIntroduce());
			pstmt.setInt(5, user.getUserNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/**
	 * 회원 추가정보 삽입 DAO(user_info 테이블에 값이 없을 경우)
	 * @param conn
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public int insertUserInfo(Connection conn, User user) throws Exception {
		int result = 0;
		String sql = prop.getProperty("insertUserInfo");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getUserNo());
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	

	/**
	 * 비밀번호 변경 DAO
	 * 
	 * @param conn
	 * @param currentPw
	 * @param newPw
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int changePw(Connection conn, String currentPw, String newPw, int userNo) throws Exception {
		int result = 0;

		try {
			String sql = prop.getProperty("changePw");
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newPw);
			pstmt.setInt(2, userNo);
			pstmt.setString(3, currentPw);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	
	/** 탈퇴시 비밀번호 확인 DAO
	 * @param conn
	 * @param currentPw
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int passwordCheck(Connection conn, String currentPw, int userNo) throws Exception {
		int result = 0;
		String sql = prop.getProperty("passwordCheck");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, currentPw);
			pstmt.setInt(2, userNo);
			rs = pstmt.executeQuery();
			if(rs.next()) result = rs.getInt(1);
			System.out.println(result);
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	/** 탈퇴할 회원 정보 DAO
	 * @param conn
	 * @param currentPw
	 * @param userNo
	 * @return unregUser
	 * @throws Exception
	 */
	public User getUnregUser(Connection conn, int userNo) throws Exception {
		User unregUser = new User();
		String sql = prop.getProperty("getUnregUser");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				unregUser.setUserNo(rs.getInt("USER_ID"));
				unregUser.setUserEmail(rs.getString("USER_EMAIL"));
				unregUser.setUserPw(rs.getString("USER_PW"));
				unregUser.setUserNickname(rs.getString("USER_NICKNAME"));
				unregUser.setUserRegdate(rs.getDate("USER_REGDATE"));
				unregUser.setUserIsAdmin(rs.getString("USER_IS_ADMIN"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return unregUser;
	}
	/** 탈퇴할 회원 정보 탈퇴회원 테이블에 삽입 DAO
	 * @param conn
	 * @param unregUser
	 * @return result
	 * @throws Exception
	 */
	public int insertUnregUser(Connection conn, User unregUser) throws Exception {
		int result = 0;
		String sql = prop.getProperty("insertUnregUser");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, unregUser.getUserNo());
			pstmt.setString(2, unregUser.getUserEmail());
			pstmt.setString(3, unregUser.getUserPw());
			pstmt.setString(4, unregUser.getUserNickname());
			pstmt.setDate(5, unregUser.getUserRegdate());
			pstmt.setString(6, unregUser.getUserIsAdmin());
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	/** 회원 테이블의 탈퇴회원 정보 변경 DAO
	 * @param conn
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int updateUnregUser(Connection conn, int userNo) throws Exception {
		int result = 0;
		String sql = prop.getProperty("updateUnregUser");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	/** 회원 추가정보 테이블 탈퇴회원 행 삭제 DAO
	 * @param conn
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteUnregUser(Connection conn, int userNo) throws Exception {
		int result = 0;
		String sql = prop.getProperty("deleteUnregUser");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	

	/**
	 * 중복검사 DAO
	 * 
	 * @param conn
	 * @param userEmail
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String userEmail) throws Exception {
		int result = 0;

		String sql = prop.getProperty("idDupCheck");

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userEmail);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}
		} finally {

			close(rs);
			close(pstmt);

		}

		return result;
	}

	/** 마이페이지 유저 정보 DAO
	 * @param conn
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public User userInfo(Connection conn, int userNo) throws Exception{
		User user = null;
		String sql = prop.getProperty("userInfo");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setUserPic(rs.getString("USER_PIC"));
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return user;
	}

	/**
	 * 사용자 정보 새로고침 DAO
	 * @author 백승훈
	 * @param conn
	 * @param userNo
	 * @return loginUser;
	 * @throws Exception
	 */
	public User refreshUserInfo(Connection conn, int userNo) throws Exception {
		User loginUser = null;
		String sql = prop.getProperty("selectUserInfo");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				loginUser = new User(rs.getInt("user_id"), rs.getString("user_email"), rs.getString("user_nickname"),
						rs.getDate("user_regdate"), rs.getString("user_is_admin"), rs.getString("user_pic"),
						rs.getString("user_link"), rs.getString("user_interest"), rs.getString("user_introduce"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return loginUser;
	}










}