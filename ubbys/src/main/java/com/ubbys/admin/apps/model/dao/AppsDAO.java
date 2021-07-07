package com.ubbys.admin.apps.model.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;

public class AppsDAO {
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	
	protected Properties prop = null;
	
	public AppsDAO() {
		String filePath = AppsDAO.class.getResource("/com/ubbys/sql/appsAdmin-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	/** apps 전체 게시글 수 조회 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		int listCount = 0;
		String sql = prop.getProperty("getListCount");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) listCount = rs.getInt(1);
		} finally {
			close(rs);
			close(stmt);
		}
		return listCount;
	}

	/** apps 목록 출력 DAO
	 * @param pagination
	 * @param conn
	 * @return appsList
	 * @throws Exception
	 */
	public List<Apps> getAppsList(Pagination pagination, Connection conn) throws Exception {
		List<Apps> appsList = new ArrayList<Apps>();
		String sql = prop.getProperty("getAppsList");
		try {
			pstmt = conn.prepareStatement(sql);
			int start = (pagination.getCurrentPage()-1)*pagination.getLimit()+1;
			int end = start+pagination.getLimit()-1;
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Apps a = new Apps();
				a.setPostId(rs.getInt("APPS_POST_ID"));
				a.setCategoryName(rs.getString("APPS_CATEGORY_NAME"));
				a.setPostTitle(rs.getString("APPS_TITLE"));
				a.setPostLike(rs.getInt("APPS_LIKE"));
				a.setUserName(rs.getString("USER_NICKNAME"));
				a.setPostDate(rs.getDate("APPS_DATE"));
				appsList.add(a);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return appsList;
	}

	/** apps 삭제 DAO
	 * @param conn
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int deleteApps(Connection conn, int postId) throws Exception {
		int result = 0;
		String sql = prop.getProperty("deleteApps");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** apps 정렬 DAO
	 * @param conn
	 * @param pagination
	 * @param condition
	 * @return appsList
	 * @throws Exception
	 */
	public List<Apps> getAppsSortList(Connection conn, Pagination pagination, String condition) throws Exception{
		List<Apps> appsList = new ArrayList<Apps>();
		String sql = prop.getProperty("sortApps1")+condition+prop.getProperty("sortApps2");
		try {
			pstmt = conn.prepareStatement(sql);
			int start = (pagination.getCurrentPage()-1)*pagination.getLimit()+1;
			int end = start+pagination.getLimit()-1;
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Apps a = new Apps();
				a.setPostId(rs.getInt("APPS_POST_ID"));
				a.setCategoryName(rs.getString("APPS_CATEGORY_NAME"));
				a.setPostTitle(rs.getString("APPS_TITLE"));
				a.setPostLike(rs.getInt("APPS_LIKE"));
				a.setUserName(rs.getString("USER_NICKNAME"));
				a.setPostDate(rs.getDate("APPS_DATE"));
				appsList.add(a);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return appsList;
	}
	
	/** apps 검색된 게시글 수 조회
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String condition) throws Exception {
		int listCount = 0;
		String sql = prop.getProperty("getListCount")+condition;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) listCount = rs.getInt(1);
		} finally {
			close(rs);
			close(stmt);
		}
		return listCount;
	}
	/** apps 검색 DAO
	 * @param conn
	 * @param pagination
	 * @param sc
	 * @param sv
	 * @return appsList
	 * @throws Exception
	 */
	public List<Apps> getAppsSearchList(Connection conn, Pagination pagination, String condition) throws Exception{
		List<Apps> appsList = new ArrayList<Apps>();
		String sql = prop.getProperty("searchApps1")+condition+prop.getProperty("searchApps2");
		try {
			pstmt = conn.prepareStatement(sql);
			int start = (pagination.getCurrentPage()-1)*pagination.getLimit()+1;
			int end = start+pagination.getLimit()-1;
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Apps a = new Apps();
				a.setPostId(rs.getInt("APPS_POST_ID"));
				a.setCategoryName(rs.getString("APPS_CATEGORY_NAME"));
				a.setPostTitle(rs.getString("APPS_TITLE"));
				a.setPostLike(rs.getInt("APPS_LIKE"));
				a.setUserName(rs.getString("USER_NICKNAME"));
				a.setPostDate(rs.getDate("APPS_DATE"));
				appsList.add(a);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return appsList;
	}



}
