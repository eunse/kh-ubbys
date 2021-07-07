package com.ubbys.board.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ubbys.board.vo.Board;
import com.ubbys.board.vo.Like;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Tag;

public class AppsDAO extends BoardDAO {
	private Statement stmtForTag = null;
	private PreparedStatement pstmtForTag = null;
	private ResultSet rsForTag = null;
	private List<Tag> tagList = null;
	
	public AppsDAO() {
		super();
	}
	
	/**
	 * apps 게시판 목록 조회 DAO
	 * @param conn
	 * @param pagination
	 * @return appsList
	 * @throws Exception
	 */
	public List<Apps> selectAppsList(Connection conn, Pagination pagination) throws Exception {
		List<Apps> appsList = new ArrayList<Apps>();
		String sql = prop.getProperty("selectAppsList");
		String sqlForTag = prop.getProperty("selectAppsTags");
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = (startRow + pagination.getLimit() - 1);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Apps apps = new Apps();
				apps.setPostId(rs.getInt("apps_post_id"));
				apps.setCategoryName(rs.getString("apps_category_name"));
				apps.setPostTitle(rs.getString("apps_title"));
				apps.setPostDate(rs.getDate("apps_date"));
				apps.setAppsIconUrl(rs.getString("apps_icon"));
				apps.setPostLike(rs.getInt("apps_like"));
				apps.setUserName(rs.getString("user_nickname"));
				apps.setAppsSummary(rs.getString("apps_content_substr"));
				apps.setTagList(new ArrayList<Tag>());

				pstmtForTag = conn.prepareStatement(sqlForTag);
				pstmtForTag.setInt(1, rs.getInt("apps_post_Id"));
				rsForTag = pstmtForTag.executeQuery();
				while(rsForTag.next()) {
					Tag tag = new Tag();
					tag.setTagId(rsForTag.getInt("apps_tag_id"));
					tag.setTagName(rsForTag.getString("apps_tag_name"));
					apps.getTagList().add(tag);
				}			
				appsList.add(apps);
			}
		} finally {
			close(rsForTag);
			close(pstmtForTag);
			close(rs);
			close(pstmt);
		}
		return appsList;
	}

	/**
	 * apps 게시글 상세 조회 DAO
	 * @param conn
	 * @param postId
	 * @return apps
	 * @throws Exception
	 */
	public Apps selectApps(Connection conn, int postId) throws Exception {
		Apps apps = null;
		String sql = prop.getProperty("selectApps");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();
			apps = new Apps();
			if(rs.next()) {
				apps.setPostId(rs.getInt("apps_post_id"));
				apps.setCategoryName(rs.getString("apps_category_name"));
				apps.setPostTitle(rs.getString("apps_title"));
				apps.setPostContent(rs.getString("apps_content"));
				apps.setPostDate(rs.getDate("apps_date"));
				apps.setAppsIconUrl(rs.getString("apps_icon"));
				apps.setPostLike(rs.getInt("apps_like"));
				apps.setUserName(rs.getString("user_nickname"));
				apps.setAppsLink(rs.getString("apps_url"));
				apps.setUserNo(rs.getInt("user_id"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return apps;
	}
	/**
	 * apps 특정 게시글의 태그 조회 DAO
	 * @param conn
	 * @param postId
	 * @return
	 * @throws Exception
	 */
	public List<Tag> selectAppsTags(Connection conn, int postId) throws Exception {
		List<Tag> tagList = new ArrayList<Tag>();
		String sql = prop.getProperty("selectAppsTags");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Tag tag = new Tag();
				tag.setTagId(rs.getInt("apps_tag_id"));
				tag.setTagName(rs.getString("apps_tag_name"));
				tagList.add(tag);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return tagList;
	}
	
	/**
	 * apps 게시물 삽입 DAO
	 * @param conn
	 * @param apps
	 * @return result
	 * @throws Exception
	 */
	public int insertApps(Connection conn, Apps apps) throws Exception {
		int result = 0;
		String sql = prop.getProperty("insertApps");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, apps.getPostId());
			pstmt.setString(2, apps.getPostTitle());
			pstmt.setString(3, apps.getPostContent());
			pstmt.setString(4, apps.getAppsIconUrl());
			pstmt.setString(5, apps.getAppsLink());
			pstmt.setInt(6, apps.getCategoryId());
			pstmt.setInt(7, apps.getUserNo());
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/**
	 * apps 게시물 수정 DAO
	 * @param conn
	 * @param apps
	 * @return result
	 * @throws Exception
	 */
	public int updateApps(Connection conn, Apps apps) throws Exception {
		int result = 0;
		String sql = prop.getProperty("updateApps");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, apps.getPostTitle());
			pstmt.setString(2, apps.getPostContent());
			pstmt.setString(3, apps.getAppsIconUrl());
			pstmt.setString(4, apps.getAppsLink());
			pstmt.setInt(5, apps.getCategoryId());
			pstmt.setInt(6, apps.getPostId());
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	

	/**
	 * apps 태그 삽입 DAO
	 * @param conn
	 * @param tagName
	 * @return result
	 * @throws Exception
	 */
	public int insertTags(Connection conn, String tagName) throws Exception {
		int result = 0;
		String sql = prop.getProperty("insertTags");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tagName);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * TEST 용 태그 목록 조회
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Tag> selectTagList(Connection conn) throws Exception {
		List<Tag> tagList = new ArrayList<Tag>();		
		String sql = prop.getProperty("selectTagList");
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Tag tag = new Tag();
				
				tag.setTagId(rs.getInt(1));
				tag.setTagName(rs.getString(2));

				tagList.add(tag);
			}
		}finally {
			close(rs);
			close(pstmt);
		}
				
		return tagList;
	}
	/**
	 * 특정 이름의 태그 조회 DAO 
	 * @param conn
	 * @param tagName
	 * @return rowNo
	 * @throws Exception
	 */
	public int isExistTag(Connection conn, String tagName) throws Exception {
		String sql = prop.getProperty("isExistTag");
		HashMap<Integer, String> tagMap = new HashMap<Integer, String>();
		int rowNo = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tagName);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rowNo = rs.getInt(1);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return rowNo;
	}
	
	/**
	 * apps_tags 테이블에 태그 삽입 DAO
	 * @param conn
	 * @param postId
	 * @param tagList
	 * @return result
	 * @throws Exception
	 */
	public int insertAppsTags(Connection conn, int postId, int tagId) throws Exception {
		int result = 0;
		String sql = prop.getProperty("insertAppsTags");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			pstmt.setInt(2, tagId);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result; 
	}
	
	/**
	 * apps_tags 테이블의 태그 삭제 DAO
	 * @param conn
	 * @param postId
	 * @return
	 * @throws Exception
	 */
	public int deleteAppsTags(Connection conn, int postId) throws Exception {
		int result = 0;
		String sql = prop.getProperty("deleteAppsTags");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/**
	 * 게시글 삭제 DAO
	 * @param postId
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteApps(Connection conn, int postId, int userNo) throws Exception {
		int result = 0;
		String sql = prop.getProperty("deleteApps");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			pstmt.setInt(2, userNo);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * apps 게시물 작성자 조회 DAO
	 * @param conn
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int selectAuthor(Connection conn, int postId) throws Exception {
		int result = 0;
		String sql = prop.getProperty("selectAuthor");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

}
