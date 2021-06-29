package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;
import java.sql.Connection;
import java.util.List;

import com.ubbys.board.dao.SelectQnaDAO;
import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaPagination;

public class SelectQnaService {
	
	private SelectQnaDAO dao = new SelectQnaDAO();

	/** 전체 게시글 수 조회 + 페이징 객체 생성용 Service
	 * @param cp
	 * @return QnaPagination
	 * @throws Exception
	 */
	public QnaPagination getPagination(int cp) throws Exception {
		
		Connection conn = getConnection();
		
		int listCount = dao.getListCount(conn, cp);
		
		close(conn);
		
		return new QnaPagination(cp, listCount);
	}

	/** Qna 목록 조회 Service
	 * @param pagination
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(QnaPagination pagination) throws Exception {
		
		Connection conn = getConnection();
		
		List<Qna> qnaList = dao.selectQnaList(conn, pagination);
		
		close(conn);
		
		return qnaList;
	}

	/** Qna 상세 조회 Service
	 * @param qnaPostId
	 * @return qna
	 * @throws Exception
	 */
	public Qna selectQna(int qnaPostId) throws Exception {
		
		Connection conn = getConnection();
		
		Qna qna = dao.selectQna(conn, qnaPostId);
		
		close(conn);
		
		return qna;
	}

}
