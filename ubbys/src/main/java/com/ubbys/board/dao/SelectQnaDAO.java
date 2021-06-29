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
import com.ubbys.board.vo.QnaPagination;

public class SelectQnaDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	
	public SelectQnaDAO() {
		
		String filePath = SelectQnaDAO.class.getResource("/com/ubbys/sql/selectQna-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 전체 게시글 수 조회 DAO
	 * @param conn
	 * @param cp
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, int cp) throws Exception {
		
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

	/** Qna 목록 조회 DAO
	 * @param conn
	 * @param pagination
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(Connection conn, QnaPagination pagination) throws Exception {
		
		List<Qna> qnaList = new ArrayList<Qna>();
		
		String sql = prop.getProperty("selectQnaList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int start = (pagination.getCurrentPage()-1)*pagination.getLimit()+1;
			int end = start+pagination.getLimit()-1;
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Qna qna = new Qna();
				
				qna.setQnaPostId(rs.getInt("QNA_POST_ID"));
				qna.setQnaCategoryName(rs.getString("QNA_CATEGORY_NAME"));
				qna.setQnaDate(rs.getString("QNA_DATE"));
				qna.setQnaTitle(rs.getString("QNA_TITLE"));
				qna.setUserNickname(rs.getString("USER_NICKNAME"));
				qna.setQnaLike(rs.getInt("QNA_LIKE"));
				qna.setQnaReplyCount(rs.getInt("REPLY_COUNT"));
				
				qnaList.add(qna);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return qnaList;
	}

	/** Qna 상세 조회 DAO
	 * @param conn
	 * @param qnaPostId
	 * @return qna
	 * @throws Exception
	 */
	public Qna selectQna(Connection conn, int qnaPostId) throws Exception {
		
		Qna qna = null;
		
		String sql = prop.getProperty("selectQna");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, qnaPostId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				qna = new Qna();
				qna.setQnaPostId(rs.getInt("QNA_POST_ID"));
				qna.setQnaCategoryName(rs.getString("QNA_CATEGORY_NAME"));
				qna.setQnaDate(rs.getString("QNA_DATE"));
				qna.setQnaTitle(rs.getString("QNA_TITLE"));
				qna.setQnaContent(rs.getString("QNA_CONTENT"));
				qna.setUserNickname(rs.getString("USER_NICKNAME"));
				qna.setQnaLike(rs.getInt("QNA_LIKE"));
				qna.setUserId(rs.getInt("USER_ID"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return qna;
	}

}
