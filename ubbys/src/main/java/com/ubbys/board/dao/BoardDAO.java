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

import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Board;
import com.ubbys.board.vo.Category;
import com.ubbys.board.vo.Like;
import com.ubbys.user.vo.User;
/**
 * 
 * @author 백승훈
 *
 */
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

	/**
	 * 게시물 좋아요 회수 조회 DAO
	 * @param conn
	 * @param boardTableName
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int selectLike(Connection conn, String boardTableName, int postId) throws Exception {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM " + boardTableName+ "_likes WHERE "+ boardTableName +"_post_id = " + postId;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	/**
	 * 게시글 좋아요 여부 확인 DAO
	 * @param conn
	 * @param boardTableName
	 * @param postId
	 * @param loginUserNo
	 * @return like
	 * @throws Exception
	 */
	public Like selectLike(Connection conn, String boardTableName, int postId, int loginUserNo) throws Exception {
		Like like = new Like();
		String sql = "SELECT * FROM " + boardTableName + "_likes WHERE " + boardTableName 
				+ "_post_id = " + postId + "AND user_id = " + loginUserNo;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				like.setLikeId(rs.getInt("likes_id"));
				like.setPostId(rs.getInt(boardTableName + "_post_id"));
				like.setUserId(rs.getInt("user_id"));
			}
		} finally {
			close(rs);
			close(stmt);
		}
		return like;
	}

	/**
	 * 게시글 좋아요 추가 DAO
	 * @param conn
	 * @param boardTableName
	 * @param userNo
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int insertLike(Connection conn, String boardTableName, int userNo, int postId) throws Exception {
		int result = 0;
		String boardLikeTable = boardTableName + "_likes";
		String boardLikeSeq = "seq_" + boardTableName + "_like_no";
		String sql = "INSERT INTO " + boardLikeTable + " VALUES(" + boardLikeSeq + ".NEXTVAL, ?, ?)";
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
	 * 게시물 좋아요 증감
	 * @param conn
	 * @param boardTableName
	 * @param userNo
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int updateLike(Connection conn, String boardTableName, int postId, String addSub) throws Exception {
		int result = 0;
		String sql = "UPDATE "+ boardTableName  +" SET " + boardTableName + "_like = " + boardTableName + "_like "+ addSub +" 1 WHERE " + boardTableName + "_post_id = " + postId;
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);			
		} finally {
			close(stmt);
		}	
		return result;
	}
	
	/**
	 * 게시글 좋아요 삭제 DAO
	 * @param conn
	 * @param boardTableName
	 * @param userNo
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int deleteLike(Connection conn, String boardTableName, int userNo, int postId) throws Exception {
		int result = 0;
		String sql = "DELETE FROM " + boardTableName + "_likes "
				+ "WHERE " + boardTableName + "_post_id = ? AND user_id = ?";
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
}
