package com.ubbys.admin.model.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ubbys.board.vo.Pagination;
import com.ubbys.user.vo.UnRegUser;
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

	

	/** 회원 목록 조회 DAO
	 * @param conn
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectUserList(Connection conn, Pagination pagination) throws Exception {
	
		List<User> userList = null;
		
		String sql = prop.getProperty("selectUserList");

		try { 
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
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

	
	/** 전체 회원 수 + 회원 닉네임 조회 DAO
	 * @param conn
	 * @param cp
	 * @param userNo
	 * @param condition
	 * @return result
	 * @throws Exception 
	 */
	public int getUserCount(Connection conn, int cp) throws Exception {
		
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

	
	/** 회원 정보 조회 DAO(정렬용)
	 * @param conn
	 * @param pagination
	 * @param condition
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectSortUserList(Connection conn, Pagination pagination, String condition) throws Exception {

		List<User> userList = new ArrayList<User>();
		String sql = prop.getProperty("sortUserList1")+condition+prop.getProperty("sortUserList2");
		try {
			pstmt = conn.prepareStatement(sql);

			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				User u = new User();

				u.setUserNo(rs.getInt("USER_ID"));
				u.setUserEmail(rs.getString("USER_EMAIL"));
				u.setUserPw(rs.getString("USER_PW"));
				u.setUserNickname(rs.getString("USER_NICKNAME"));
				u.setUserRegdate(rs.getDate("USER_REGDATE"));
				u.setUserIsAdmin(rs.getString("USER_IS_ADMIN"));

				userList.add(u);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return userList;
	}

	/** 탈퇴 회원 목록 DAO
	 * @param conn
	 * @return unRegUserList
	 * @throws Exception
	 */
	public List<UnRegUser> selectUnregUserList(Connection conn, Pagination pagination) throws Exception {
		
		List<UnRegUser> unRegUserList = null;
		
		String sql = prop.getProperty("selectUnRegUserList");
		
		try { 
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			unRegUserList = new ArrayList<UnRegUser>();
			
			while(rs.next()) {
				
				UnRegUser un = new UnRegUser( rs.getInt("USER_ID"),
									rs.getString("USER_EMAIL"),
									rs.getString("USER_NICKNAME"),
									rs.getDate("USER_REGDATE"),
									rs.getDate("USER_UNREGDATE"));
								
				
				unRegUserList.add(un);
			}
			
		} finally {
			
			close(rs);
			close(stmt);
			
		}
		return unRegUserList;
	}

	/** 탈퇴 회원 정보 조회 DAO(정렬용)
	 * @param conn
	 * @param pagination
	 * @param condition
	 * @return unRegUserList
	 * @throws Exception
	 */
	public List<UnRegUser> selectunRegUserList(Connection conn, Pagination pagination, String condition) throws Exception {
		
		List<UnRegUser> unRegUserList = new ArrayList<UnRegUser>();
		String sql = prop.getProperty("searchUnRegUserList1") + condition + prop.getProperty("searchUnRegUserList2");
		
		try {
			pstmt = conn.prepareStatement(sql);

			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				UnRegUser un = new UnRegUser();

				un.setUserNo(rs.getInt("USER_ID"));
				un.setUserEmail(rs.getString("USER_EMAIL"));
				un.setUserPw(rs.getString("USER_PW"));
				un.setUserNickname(rs.getString("USER_NICKNAME"));
				un.setUserRegdate(rs.getDate("USER_REGDATE"));
				un.setUserUnRegdate(rs.getDate("USER_UNREGDATE"));

				unRegUserList.add(un);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return unRegUserList;
	}

	/** 탈퇴 회원 목록 검색용 DAO
	 * @param conn
	 * @param pagination
	 * @param condition
	 * @return userList
	 * @throws Exception
	 */
	public List<UnRegUser> selectUnRegUserList(Connection conn, Pagination pagination, String condition)  throws Exception{
		
		List<UnRegUser> unRegUserList = new ArrayList<UnRegUser>();
		
		String sql = prop.getProperty("searchUnRegUserList1") + condition + prop.getProperty("searchUnRegUserList2");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;

			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				UnRegUser unRegUser = new UnRegUser();
				
				unRegUser.setUserNo(rs.getInt("USER_ID"));
				unRegUser.setUserEmail(rs.getString("USER_EMAIL"));
				unRegUser.setUserPw(rs.getString("USER_PW"));
				unRegUser.setUserNickname(rs.getString("USER_NICKNAME"));
				unRegUser.setUserRegdate(rs.getDate("USER_REGDATE"));
				unRegUser.setUserUnRegdate(rs.getDate("User_UNREGDATE"));
				
				unRegUserList.add(unRegUser);

			}
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		return unRegUserList;
	}

	/** 탈퇴 회원 정보 조회 DAO(정렬용)
	 * @param conn
	 * @param pagination
	 * @param condition
	 * @return unRegUserList
	 * @throws Exception
	 */
	public List<UnRegUser> selectSortUnRegUserList(Connection conn, Pagination pagination, String condition) throws Exception{
		
		List<UnRegUser> unRegUserList = new ArrayList<UnRegUser>();
		
		String sql = prop.getProperty("sortUnregUserList1")+condition+prop.getProperty("sortUnregUserList2");
		try {
			pstmt = conn.prepareStatement(sql);

			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				UnRegUser un = new UnRegUser();

				un.setUserNo(rs.getInt("USER_ID"));
				un.setUserEmail(rs.getString("USER_EMAIL"));
				un.setUserPw(rs.getString("USER_PW"));
				un.setUserNickname(rs.getString("USER_NICKNAME"));
				un.setUserRegdate(rs.getDate("USER_REGDATE"));
				un.setUserUnRegdate(rs.getDate("USER_UNREGDATE"));

				unRegUserList.add(un);
			}
		} finally {
			
			close(rs);
			close(pstmt);
		}
		return unRegUserList;
	}

	/** 관리자용 탈퇴 회원정보 DAO
	 * @param conn
	 * @param userNo
	 * @return unregUser
	 * @throws Exception
	 */
	public User getUnregUser(Connection conn, int userNo) throws Exception  {
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

	/** 관리자용 탈퇴시킬 회원 정보  탈퇴회원 테이블에 삽입 DAO
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

	/** 관리자용 회원 테이블의 탈퇴회원 정보 변경 DAO
	 * @param conn
	 * @param userNo
	 * @return
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

	/** 관리자용 회원 추가정보 테이블 탈퇴회원 행 삭제 DAO
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

	/** 탈퇴회원 복구 DAO
	 * @param conn
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int updateUser(Connection conn, User user) throws Exception {
		
		int result = 0;
		
		String sql = prop.getProperty("updateUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, user.getUserNo());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserEmail());
			pstmt.setString(4, user.getUserNickname());
			pstmt.setDate(5, user.getUserRegdate());
			pstmt.setString(6, user.getUserIsAdmin());
			pstmt.setInt(7, user.getUserNo());
			
			result = pstmt.executeUpdate();
			
				
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	/** 관리자용  탈퇴회원 복구용 정보 조회 DAO
	 * @param conn
	 * @param userNo
	 * @return unregUser
	 * @throws Exception
	 */
	public User getUnregUser2(Connection conn, int userNo) throws Exception{
		
		User unregUser = new User();
		String sql = prop.getProperty("getUnregUser2");
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
				
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return unregUser;
	}

	
	
	
	



}
