package com.ubbys.admin.model.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ubbys.board.vo.Pagination;
import com.ubbys.user.vo.User;

public class AdminDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private Properties prop = null;

	public AdminDAO() {
		String filePath = AdminDAO.class.getResource("/com/ubbys/sql/admin-query.xml").getPath();

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * 관리자 로그인 DAO
	 * 
	 * @param conn
	 * @param userEmail
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	public User login(Connection conn, String userEmail, String userPw) throws Exception {
		User loginUser = null;

		String sql = prop.getProperty("adminLogin");
		String encryptPassword = "";

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


	/** 회원 목록 조회 DAO (페이지네이션 처리)
	 * @param conn
	 * @param pagination
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectUserList(Connection conn, Pagination pagination) throws Exception {
		
		List<User> userList = new ArrayList<User>();
		
		String sql = prop.getProperty("selectUserList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = (startRow + pagination.getLimit() - 1);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				User user = new User();
				
				user.setUserNo(rs.getInt("USER_ID"));
				user.setUserEmail(rs.getString("USER_EMAIL"));
				user.setUserPw(rs.getString("USER_PW"));
				user.setUserNickname(rs.getString("USER_NICKNAME"));
				user.setUserRegdate(rs.getDate("USER_REGDATE"));
				user.setUserIsAdmin(rs.getString("USER_IS_ADMIN"));
				
				List<String> filePath = new ArrayList<String>();
				List<String> fileName = new ArrayList<String>();
				
				filePath.add(rs.getString("FILE_PATH"));
				fileName.add(rs.getString("FILE_NM"));
				
				user.setFilePath(filePath);
				user.setFileName(fileName);
				
				userList.add(user);
			}
			
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		
		return userList;
	}

	

	/** 회원 목록 조회 DAO
	 * @param conn
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectUserList(Connection conn) throws Exception {
	
		List<User> userList = null;
		
		String sql = prop.getProperty("selectUserList");
		
		try { 
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			userList = new ArrayList<User>();
			
			while(rs.next()) {
				
				User u = new User( rs.getInt("USER_ID"),
									rs.getString("USER_EMAIL"),
									rs.getString("USER_NICKNAME"),
									rs.getDate("USER_REGDATE"),
									rs.getString("USER_IS_ADMIN"));
				
				userList.add(u);
			}
			
		} finally {
			
			close(rs);
			close(stmt);
			
		}
		
		return userList;
	}
	

	/** 회원 목록 검색용 DAO
	 * @param conn
	 * @param pagination
	 * @param condition
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectUserList(Connection conn, Pagination pagination, String condition)  throws Exception{
		
		List<User> userList = new ArrayList<User>();
		
		String sql = prop.getProperty("searchUserList1") + condition + prop.getProperty("searchUserList2");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;

			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				
				user.setUserNo(rs.getInt("USER_ID"));
				user.setUserEmail(rs.getString("USER_EMAIL"));
				user.setUserPw(rs.getString("USER_PW"));
				user.setUserNickname(rs.getString("USER_NICKNAME"));
				user.setUserRegdate(rs.getDate("USER_REGDATE"));
				user.setUserIsAdmin(rs.getString("USER_IS_ADMIN"));
				
				//List<String> filePath = new ArrayList<String>();
				//List<String> fileName = new ArrayList<String>();
				
//				2) 생성된 리스트에 DB 조회 결과를 추가 
				//filePath.add(rs.getString("FILE_PATH"));
				//fileName.add(rs.getString("FILE_NM"));
				
//				3) 리스트에 board에 set
				//user.setFilePath(filePath);
				//user.setFileName(fileName);
				
				userList.add(user);

			}
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		return userList;
	}

	/** 전체 회원 수 + 회원 닉네임 조회 DAO
	 * @param conn
	 * @param userNo
	 * @return result
	 * @throws Exception 
	 */
	public int getUserCount(Connection conn) throws Exception {
		
		int result = 0;

		String sql = prop.getProperty("getUserCount");
		
		try {
			
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				result = rs.getInt(1);

			}
			
		} finally {
			close(rs);
			close(stmt);
		}

		return result;
	}


	
	/** 전체 회원 수 + 회원 닉네임 조회 DAO
	 * @param conn
	 * @param cp
	 * @param userNo
	 * @param condition
	 * @return result
	 * @throws Exception 
	 */
	public int getUserCount(Connection conn, int cp, String condition) throws Exception {
		
		int result = 0;

		String sql = prop.getProperty("getUserCount") + condition;
		
		try {
			
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				result = rs.getInt(1);

			}
			
		} finally {
			close(rs);
			close(stmt);
		}

		return result;
	}

	/**
	 * 회원 정보 조회
	 * @param conn
	 * @param userEmail
	 * @return
	 */
	public User selectUser(Connection conn, String userEmail) throws Exception {
		User user = null;

		String sql = prop.getProperty("selectUser");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userEmail);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				
				user.setUserNo(rs.getInt("USER_ID"));
				user.setUserEmail(rs.getString("USER_EMAIL"));
				user.setUserPw(rs.getString("USER_PW"));
				user.setUserNickname(rs.getString("USER_NICKNAME"));
				user.setUserRegdate(rs.getDate("USER_REGDATE"));
				user.setUserIsAdmin(rs.getString("USER_IS_ADMIN"));
			}
			
		} finally {
			close(rs);
			close(stmt);
		}

		return user;
	}

}
