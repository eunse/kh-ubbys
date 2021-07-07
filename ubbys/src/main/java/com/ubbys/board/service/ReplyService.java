package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.ubbys.board.dao.ReplyDAO;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Reply;

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
	
	
	/** 관리자용 댓글 전체 목록 가져오기 Service
	 * @param pagination
	 * @return list
	 * @throws Exception
	 */
	public List<Reply> selectList(Pagination pagination) throws Exception {
		Connection conn = getConnection();

		List<Reply> list = dao.selectList(conn, pagination);

		close(conn);

		return list;
	}
	
	
	/** 관리자용 댓글 한개 가져오기 Service
	 * @param replyId
	 * @return reply
	 * @throws Exception
	 */
	public Reply selectReply(int replyId) throws Exception {
		Connection conn = getConnection();

		Reply reply = dao.selectReply(conn, replyId);

		close(conn);

		return reply;
	}
	
	
	/** 관리자용 댓글 전체 개수 가져오기 Service
	 * @param cp
	 * @return Pagination
	 * @throws Exception
	 */
	public Pagination getPagination(int cp) throws Exception {
		Connection conn = getConnection();

		
		Map<String, Object> map = dao.getListCount(conn, cp);
		
		close(conn);
		
		int listCount = (int)map.get("listCount");
		
		return new Pagination(cp, listCount);
	}
	
	
	
	
	
}
