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
	 * @param loginUserId 
	 * @return qna
	 * @throws Exception
	 */
	public Qna selectQna(int qnaPostId) throws Exception {
		
		Connection conn = getConnection();
		
		Qna qna = dao.selectQna(conn, qnaPostId);

		close(conn);
		
		return qna;
	}
	
	/** MyQna 목록 조회 Service
	 * @param pagination
	 * @return myQnaList
	 * @throws Exception
	 */
	public List<Qna> selectMyQnaList(int userNo) throws Exception{
		Connection conn = getConnection();
		List<Qna> myQnaList = dao.selectMyQnaList(conn, userNo);
		
		close(conn);
		
		return myQnaList;
	}

	
	/** qna 제목이 일치하는 글 수 카운트 + 페이지네이션 생성 Service
	 * @param cp
	 * @param qnaTitle
	 * @return pagination
	 * @throws Exception
	 */
	public QnaPagination getSearchTPage(int cp, String qnaTitle) throws Exception {
		
		Connection conn = getConnection();
		
		int listCount = dao.getTListCount(conn, cp, qnaTitle);
		
		close(conn);
		
		return new QnaPagination(cp, listCount);
	}
	/** qna 제목 조건 검색 Service
	 * @param pagination
	 * @param qnaTitle
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> searchQnaTitle(QnaPagination pagination, String qnaTitle) throws Exception {
		
		Connection conn = getConnection();
		
		List<Qna> qnaList = dao.searchQnaTitle(conn, pagination, qnaTitle);
		
		close(conn);
		
		return qnaList;
	}

	/** qna 작성자가 일치하는 글 수 카운트 + 페이지네이션 생성 Service
	 * @param cp
	 * @param qnaTitle
	 * @return pagination
	 * @throws Exception
	 */
	public QnaPagination getSearchNPage(int cp, String userNickname) throws Exception {
		
		Connection conn = getConnection();
		
		int listCount = dao.getTListCount(conn, cp, userNickname);
		
		close(conn);
		
		return new QnaPagination(cp, listCount);
	}
	/** qna 작성자 조건 검색 Service
	 * @param pagination
	 * @param userNickname
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> searchQnaAuthor(QnaPagination pagination, String userNickname) throws Exception {
		
		Connection conn = getConnection();
		
		List<Qna> qnaList = dao.searchQnaAuthor(conn, pagination, userNickname);
		
		close(conn);
		
		return qnaList;
	}

}
