package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.ubbys.board.dao.ReplyDAO;
import com.ubbys.board.vo.Reply;
import com.ubbys.user.vo.User;

public class ReplyService {

	private ReplyDAO dao = new ReplyDAO();

	/**
	 * 댓글 목록 조회 Service
	 * 
	 * @param qnaPostId
	 * @return list
	 * @throws Exception
	 */
	public List<Reply> selectList(int qnaPostId) throws Exception {

		Connection conn = getConnection();

		List<Reply> list = dao.selectList(conn, qnaPostId);

		close(conn);

		return list;
	}

	/**
	 * 댓글 삽입 Service
	 * 
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertReply(Reply reply) throws Exception {
		Connection conn = getConnection();
		reply.setReplyContent(replaceParameter(reply.getReplyContent()));
		reply.setReplyContent(reply.getReplyContent().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));

		int result = dao.insertReply(conn, reply);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

	/**
	 * 댓글 수정 Service
	 * 
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateReply(Reply reply) throws Exception {
		Connection conn = getConnection();
		reply.setReplyContent(replaceParameter(reply.getReplyContent()));
		reply.setReplyContent(reply.getReplyContent().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));

		int result = dao.updateReply(conn, reply);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

	/** 댓글 삭제 Service
	 * @param replyId
	 * @return result
	 * @throws Exception
	 */
	public int deleteReply(int replyId) throws Exception {
		Connection conn = getConnection();
		int result = dao.deleteReply(conn, replyId);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

	// 크로스 사이트 스크립트 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if (param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}

		return result;
	}

	/**내 댓글 목록 조회 Service
	 * @param userNo
	 * @return myReplyList
	 * @throws Exception
	 */
	public List<Reply> selectMyReplyList(int userNo) throws Exception{
		
		Connection conn = getConnection();
		List<Reply> myReplyList = dao.selectMyReplyList(conn, userNo);
		

		close(conn);
		
		return myReplyList;
	}

	/** 댓글 좋아요 유저목록 Service
	 * @param replyId
	 * @return rList
	 * @throws Exception
	 */
	public List<User> selectUserList(int replyId) throws Exception{
		Connection conn = getConnection();
		
		List<User> rList = dao.selectUserList(conn, replyId);
		
		close(conn);
		
		return rList;
	}

	/** Reply 좋아요 동작 Service
	 * @param replyId
	 * @param userId
	 * @return result
	 * @throws Exception
	 */
	public int replyLike(int replyId, int userId) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.replyLikeCheck(conn, replyId, userId);
		
		if(result==0) { 
			
			result = dao.replyLike(conn, replyId, userId);
			if(result>0) {
				result = dao.replyLikeIncrease(conn, replyId);
				if(result>0) commit(conn);
				else		 rollback(conn);
				result = 1;
			} else {
				rollback(conn);
			}
			
		} else { 
			
			result = dao.replyLikeCancel(conn, replyId, userId);
			if(result>0) {
				result = dao.replyLikeDecrease(conn, replyId);
				if(result>0) commit(conn);
				else		 rollback(conn);
				result = 0;
			} else {
				rollback(conn);
			}
		}
		return result;
	}

	/** 좋아요 수 반환 Service
	 * @param replyId
	 * @return result
	 * @throws Exception
	 */
	public int replyLikeCount(int replyId) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.replyLikeCount(conn, replyId);
		
		close(conn);
		
		return result;
	}
}
