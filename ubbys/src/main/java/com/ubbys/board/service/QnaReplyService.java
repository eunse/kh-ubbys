package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.ubbys.board.dao.QnaReplyDAO;
import com.ubbys.board.vo.QnaReply;
import com.ubbys.board.vo.QnaReplyPagination;

public class QnaReplyService {

	private QnaReplyDAO dao = new QnaReplyDAO();

	/**
	 * 전체 댓글 수 조회 + 페이징 객체 생성용 Service
	 * 
	 * @param cp
	 * @return QnaReplyPagination
	 * @throws Exception
	 */
	public QnaReplyPagination getPagination(int cp) throws Exception {
		Connection conn = getConnection();

		int listCount = dao.getListCount(conn, cp);

		close(conn);

		return new QnaReplyPagination(cp, listCount);
	}

	/** QnaReply 목록 조회 Service
	 * @param pagination
	 * @param userNo 
	 * @return qnaReplyList
	 * @throws Exception
	 */
	public List<QnaReply> selectQnaReplyList(QnaReplyPagination pagination, int userNo) throws Exception {
		Connection conn = getConnection();

		List<QnaReply> qnaReplyList = dao.selectQnaReplyList(conn, pagination);

		close(conn);

		return qnaReplyList;
	}

	/** QnaReply 작성 Service
	 * @param qnaReply
	 * @return result
	 * @throws Exception
	 */
	public int insertQnaReply(QnaReply qnaReply) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.insertQnaReply(conn ,qnaReply);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	

}
