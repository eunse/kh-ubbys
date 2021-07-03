package com.ubbys.board.dao;

import static com.ubbys.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ubbys.board.vo.QnaReply;
import com.ubbys.board.vo.QnaReplyPagination;

public class QnaReplyDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	
	public QnaReplyDAO() {
		String filePath = SelectQnaDAO.class.getResource("/com/ubbys/sql/qnaReply-query.xml").getPath();

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 전체 댓글 수 조회 DAO
	 * @param conn
	 * @param cp
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, int cp) throws Exception {
		int listCount = 0;

		String sql = prop.getProperty("getReplyListCount");

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
				listCount = rs.getInt(1);

		} finally {
			close(rs);
			close(stmt);
		}
		return listCount;
	}

	/** QnaReply 목록 조회 DAO
	 * @param conn
	 * @param pagination
	 * @return qnaReplyList
	 * @throws Exception
	 */
	public List<QnaReply> selectQnaReplyList(Connection conn, QnaReplyPagination pagination) throws Exception{
		
		List<QnaReply> qnaReplyList = new ArrayList<QnaReply>();
		
		String sql = prop.getProperty("selectQnaReplyList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int start = (pagination.getCurrentPage()-1)*pagination.getLimit()+1;
			int end = start+pagination.getLimit()-1;
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				QnaReply qnaReply = new QnaReply();
				
				qnaReply.setReplyId(rs.getInt("REPLY_ID"));
				qnaReply.setReplyContent(rs.getString("REPLY_CONTENT"));
				qnaReply.setReplyLike(rs.getInt("REPLY_LIKE"));
				qnaReply.setReplyDate(rs.getString("REPLY_DATE"));
				qnaReply.setQnaPostId(rs.getInt("QNA_POST_ID"));
				qnaReply.setUserNickname(rs.getString("USER_NICKNAME"));
				qnaReply.setUserId(rs.getInt("USER_ID"));
				
				qnaReplyList.add(qnaReply);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return qnaReplyList;
	}

	/** QnaReply 작성 DAO
	 * @param conn 
	 * @param qnaReply
	 * @return result
	 * @throws Exception
	 */
	public int insertQnaReply(Connection conn, QnaReply qnaReply) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertQnaReply");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, qnaReply.getReplyContent());
			pstmt.setInt(2, qnaReply.getUserId());
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}


}
