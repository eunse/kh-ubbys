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

import com.ubbys.board.vo.Reply;
import com.ubbys.user.vo.User;

public class ReplyDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private Properties prop = null;

	public ReplyDAO() {
		String filePath = ReplyDAO.class.getResource("/com/ubbys/sql/reply-query.xml").getPath();

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 댓글 목록 조회 DAO
	 * @param conn
	 * @param qnaPostId
	 * @return list
	 * @throws Exception
	 */
	public List<Reply> selectList(Connection conn, int qnaPostId) throws Exception {

		List<Reply> list = new ArrayList<Reply>();
		String sql = prop.getProperty("selectList");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Reply reply = new Reply();
				
				reply.setReplyId(rs.getInt("REPLY_ID"));
				reply.setReplyContent(rs.getString("REPLY_CONTENT"));
				reply.setReplyLike(rs.getInt("REPLY_LIKE"));
				reply.setReplyDate(rs.getString("REPLY_DATE"));
				reply.setQnaPostId(rs.getInt("QNA_POST_ID"));
				reply.setUserId(rs.getInt("USER_ID"));
				reply.setUserNickname(rs.getString("USER_NICKNAME"));
				reply.setUserPic(rs.getNString("USER_PIC"));
				
				list.add(reply);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}

		return list;
	}

	/**
	 * 댓글 삽입 DAO
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertReply(Connection conn, Reply reply) throws Exception {
		int result = 0;
		String sql = prop.getProperty("insertReply");
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, reply.getReplyContent());
			pstmt.setInt(2, reply.getQnaPostId());
			pstmt.setInt(3, reply.getUserId());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 댓글 수정 DAO
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateReply(Connection conn, Reply reply) throws Exception {
		int result = 0;
		String sql = prop.getProperty("updateReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, reply.getReplyContent());
			pstmt.setInt(2, reply.getReplyId());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 댓글 삭제
	 * @param conn
	 * @param replyId
	 * @return result
	 * @throws Exception
	 */
	public int deleteReply(Connection conn, int replyId) throws Exception {
		int result = 0;

		String sql = prop.getProperty("deleteReply");
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, replyId);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 내 댓글 목록 조회 DAO
	 * @param conn
	 * @param userNo
	 * @return myReplyList
	 * @throws Exception
	 */
	public List<Reply> selectMyReplyList(Connection conn, int userNo) throws Exception{
		
		List<Reply> myReplyList = new ArrayList<Reply>();
		String sql = prop.getProperty("myReplyList");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Reply reply = new Reply();
				
				reply.setReplyId(rs.getInt("REPLY_ID"));
				reply.setReplyContent(rs.getString("REPLY_CONTENT"));
				reply.setReplyDate(rs.getString("REPLY_DATE"));
				reply.setUserNickname(rs.getString("USER_NICKNAME"));
				reply.setQnaPostId(rs.getInt("QNA_POST_ID"));
				reply.setReplyLike(rs.getInt("REPLY_LIKE"));
				reply.setUserId(rs.getInt("USER_ID"));
				myReplyList.add(reply);
			}

		} finally {
			close(rs);
			close(pstmt);
		}
		return myReplyList;
	}

	/** 댓글 좋아요 유저목록 DAO
	 * @param conn
	 * @param replyId
	 * @return rList
	 * @throws Exception
	 */
	public List<User> selectUserList(Connection conn, int replyId) throws Exception{
		List<User> rList = new ArrayList<User>();
		String sql = prop.getProperty("selectUserList");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setUserNo(rs.getInt("USER_ID"));
				rList.add(user);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return rList;
	}

	/** Reply 좋아요 확인 DAO
	 * @param conn
	 * @param replyId
	 * @param userId
	 * @return result
	 * @throws Exception
	 */
	public int replyLikeCheck(Connection conn, int replyId, int userId) throws Exception{
		int result = 0;
		String sql = prop.getProperty("replyLikeCheck");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			pstmt.setInt(2, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) result = 1;
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	/** Reply 게시글 좋아요 삽입 DAO
	 * @param conn
	 * @param replyId
	 * @param userId
	 * @return result
	 * @throws Exception
	 */
	public int replyLike(Connection conn, int replyId, int userId) throws Exception{
		int result = 0;
		String sql = prop.getProperty("replyLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, replyId);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** Reply 게시글 좋아요 수 증가 DAO
	 * @param conn
	 * @param replyId
	 * @return result
	 * @throws Exception
	 */
	public int replyLikeIncrease(Connection conn, int replyId) throws Exception{
		int result = 0;
		String sql = prop.getProperty("replyLikeIncrease");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** Reply 게시글 좋아요 취소 DAO
	 * @param conn
	 * @param replyId
	 * @param userId
	 * @return result
	 * @throws Exception
	 */
	public int replyLikeCancel(Connection conn, int replyId, int userId) throws Exception{
		int result = 0;
		String sql = prop.getProperty("replyLikeCancel");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			pstmt.setInt(2, userId);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** Reply 게시글 종아요 수 감소 DAO
	 * @param conn
	 * @param replyId
	 * @return result
	 * @throws Exception
	 */
	public int replyLikeDecrease(Connection conn, int replyId) throws Exception{
		int result = 0;
		String sql = prop.getProperty("replyLikeDecrease");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 좋아요 수 반환 DAO
	 * @param conn
	 * @param replyId
	 * @return result
	 * @throws Exception
	 */
	public int replyLikeCount(Connection conn, int replyId) throws Exception{
		int result = 0;
		String sql = prop.getProperty("replyLikeCount");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			rs = pstmt.executeQuery();
			if(rs.next()) result = rs.getInt(1);
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

}
