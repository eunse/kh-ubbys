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


}
