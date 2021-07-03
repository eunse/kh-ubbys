package com.ubbys.board.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.Properties;

import com.ubbys.board.vo.Category;

public class BoardDAO {
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	
	protected Properties prop = null;
	
	public BoardDAO() {
		String filePath = BoardDAO.class.getResource("/com/ubbys/sql/board-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * 전체 게시글 수 DAO
	 * @param conn
	 * @param boardTableName
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String boardTableName) throws Exception {
		int listCount = 0;
		// String sql = prop.getProperty("getListCount");
		String boardViewName = boardTableName + "_list";
		String boardStatusName = boardTableName + "_status";
		String sql = "SELECT COUNT(*) FROM " + boardViewName + " WHERE " + boardStatusName + "= 'Y'";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) listCount = rs.getInt(1);
		} finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}

	/**
	 * 카테고리 목록 조회 DAO
	 * @param conn
	 * @return category
	 * @throws Exception
	 */
	public List<Category> selectCategoryList(Connection conn, String boardTableName) throws Exception {
		List<Category> category = new ArrayList<Category>();
		String sql = prop.getProperty("selectCategoryList");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Category ca = new Category();
				ca.setCategoryId(rs.getInt(boardTableName + "_category_id"));
				ca.setCategoryName(rs.getString(boardTableName + "_category_name"));
				category.add(ca);
			}
		} finally {
			close(rs);
			close(stmt);
		}
		return category;
	}
	
	/**
	 * 다음 게시글 번호 조회 DAO
	 * @param conn
	 * @return postId
	 * @throws Exception
	 */
	public int nextPostId(Connection conn) throws Exception {
		int postId = 0;
		String sql = prop.getProperty("nextPostId");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				postId = rs.getInt(1);
			}
		} finally {
			close(rs);
			close(stmt);
		}
		return postId;
	}
}
