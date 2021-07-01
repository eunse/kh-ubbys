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
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Tag;

public class AppsDAO extends BoardDAO {
	private Statement stmtForTag = null;
	private PreparedStatement pstmtForTag = null;
	private ResultSet rsForTag = null;
	
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
		String sqlForTag = prop.getProperty("selectAppsTags");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();
			apps = new Apps();
			apps.setTagList(new ArrayList<Tag>());
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
				apps.setTagList(new ArrayList<Tag>());
			}
			pstmtForTag = conn.prepareStatement(sqlForTag);
			pstmtForTag.setInt(1, rs.getInt("apps_post_Id"));
			rsForTag = pstmtForTag.executeQuery();
			while(rsForTag.next()) {
				Tag tag = new Tag();
				tag.setTagId(rsForTag.getInt("apps_tag_id"));
				tag.setTagName(rsForTag.getString("apps_tag_name"));
				apps.getTagList().add(tag);
			}
		} finally {
			close(rsForTag);
			close(pstmtForTag);
			close(rs);
			close(pstmt);
		}
		return apps;
	}
}
