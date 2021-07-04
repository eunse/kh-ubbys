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

import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaCategory;
import com.ubbys.board.vo.QnaPagination;
import com.ubbys.user.vo.User;

public class QnaDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	
	public QnaDAO() {
		
		String filePath = QnaDAO.class.getResource("/com/ubbys/sql/qna-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 카테고리 목록 조회 DAO
	 * @param conn
	 * @return qnaCategory
	 * @throws Exception
	 */
	public List<QnaCategory> selectQnaCategory(Connection conn) throws Exception {
		
		List<QnaCategory> qnaCategory = new ArrayList<QnaCategory>();
		
		String sql = prop.getProperty("selectQnaCategory");
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				QnaCategory qc = new QnaCategory();
				qc.setQnaCategoryId(rs.getInt("QNA_CATEGORY_ID"));
				qc.setQnaCategoryName(rs.getString("QNA_CATEGORY_NAME"));
				qnaCategory.add(qc);
			}
		} finally {
			close(rs);
			close(stmt);
		}
		return qnaCategory;
	}
	

	/** qna 다음 게시글 번호 얻어오는 DAO
	 * @param conn
	 * @return result
	 * @throws Exception
	 */
	public int nextQnaPostId(Connection conn) throws Exception {
		
		int result = 0;
		
		String sql = prop.getProperty("nextQnaPostId");
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) result = rs.getInt(1);
			
		} finally {
			close(rs);
			close(stmt);
		}
		return result;
	}

	/** qna 게시글 삽입 DAO
	 * @param conn
	 * @param qna
	 * @return result
	 * @throws Exception
	 */
	public int insertQna(Connection conn, Qna qna) throws Exception {
		
		int result = 0;
		
		String sql = prop.getProperty("insertQna");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna.getQnaPostId());
			pstmt.setString(2, qna.getQnaTitle());
			pstmt.setString(3, qna.getQnaContent());
			pstmt.setInt(4, qna.getQnaCategoryId());
			pstmt.setInt(5, qna.getUserId());
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** qna 게시글 수정 DAO
	 * @param conn
	 * @param qna
	 * @return result
	 * @throws Exception
	 */
	public int updateQna(Connection conn, Qna qna) throws Exception {
		
		int result = 0;
		
		String sql = prop.getProperty("updateQna");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getQnaTitle());
			pstmt.setString(2, qna.getQnaContent());
			pstmt.setInt(3, qna.getQnaCategoryId());
			pstmt.setInt(4, qna.getQnaPostId());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	

	/** qna 게시글 삭제 접근권한 확인 Service
	 * @param conn
	 * @param qnaPostId
	 * @return useId
	 * @throws Exception
	 */
	public int postingUserCheck(Connection conn, int qnaPostId) throws Exception {

		int userId = 0;
		String sql = prop.getProperty("postingUserCheck");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			rs = pstmt.executeQuery();

			if(rs.next()) userId = rs.getInt("USER_ID");

		} finally {
			close(rs);
			close(pstmt);
		}
		return userId;
	}
	/** qna 게시글 삭제 DAO
	 * @param conn
	 * @param qnaPostId
	 * @return result
	 * @throws Exception
	 */
	public int deleteQna(Connection conn, int qnaPostId) throws Exception {

		int result = 0;
		String sql = prop.getProperty("deleteQna");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}
	
	
	/** qna 게시글에 좋아요를 누른 userList DAO
	 * @param conn
	 * @param qnaPostId
	 * @return uList
	 * @throws Exception
	 */
	public List<User> selectUserList(Connection conn, int qnaPostId) throws Exception {
		
		List<User> uList = new ArrayList<User>();
		String sql = prop.getProperty("selectUserList");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				User u = new User();
				u.setUserNo(rs.getInt("USER_ID"));
				uList.add(u);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return uList;
	}

	
	/** qna 좋아요 확인용 DAO
	 * @param qnaPostId
	 * @param userId
	 * @return result
	 * @throws Exception
	 */
	public int qnaLikeCheck(Connection conn, int qnaPostId, int userId) throws Exception {
		
		int result = 0;
		String sql = prop.getProperty("qnaLikeCheck");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			pstmt.setInt(2, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) result = 1;
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	/** qna 게시글 좋아요 삽입 DAO
	 * @param qnaPostId
	 * @param userId
	 * @return result
	 * @throws Exception
	 */
	public int qnaLike(Connection conn, int qnaPostId, int userId) throws Exception {
		
		int result = 0;
		String sql = prop.getProperty("qnaLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			pstmt.setInt(2, userId);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	/** qna 게시글 좋아요 수 증가 DAO
	 * @param conn
	 * @param qnaPostId
	 * @return result
	 * @throws Exception
	 */
	public int qnaLikeIncrease(Connection conn, int qnaPostId) throws Exception {
		
		int result = 0;
		String sql = prop.getProperty("qnaLikeIncrease");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	/** qna 게시글 좋아요 취소(삭제) DAO
	 * @param conn
	 * @param qnaPostId
	 * @param userId
	 * @return result
	 * @throws Exception
	 */
	public int qnaLikeCancel(Connection conn, int qnaPostId, int userId) throws Exception {
		
		int result = 0;
		String sql = prop.getProperty("qnaLikeCancel");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			pstmt.setInt(2, userId);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	/** qna 게시글 종아요 수 감소 DAO
	 * @param conn
	 * @param qnaPostId
	 * @return result
	 * @throws Exception
	 */
	public int qnaLikeDecrease(Connection conn, int qnaPostId) throws Exception {
		
		int result = 0;
		String sql = prop.getProperty("qnaLikeDecrease");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 좋아요 수 반환 DAO
	 * @param conn
	 * @param qnaPostId
	 * @return result
	 * @throws Exception
	 */
	public int qnaLikeCount(Connection conn, int qnaPostId) throws Exception {
		
		int result = 0;
		String sql = prop.getProperty("qnaLikeCount");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			rs = pstmt.executeQuery();
			if(rs.next()) result = rs.getInt(1);
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

}
