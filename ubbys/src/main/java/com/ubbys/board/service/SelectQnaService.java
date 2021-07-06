package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

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
	/** Qna 목록 조회 Service (로그인유저)
	 * @param pagination
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(QnaPagination pagination, int loginUserId) throws Exception {
		
		Connection conn = getConnection();
		
		List<Qna> qnaList = dao.selectQnaList(conn, pagination);
		
		qnaList = dao.getLikeFlag(conn, qnaList, loginUserId);
		
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
	
	
	/** qna 정렬 목록 조회 Service
	 * @param pagination
	 * @param searchCondition
	 * @param searchValue
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaSortList(QnaPagination pagination, String searchCondition, String searchValue) throws Exception {

		Connection conn = getConnection();
		
		String condition = createCondition(searchCondition, searchValue);
		
		List<Qna> qnaList = dao.sortQnaList(conn, pagination, condition);
		
		close(conn);
		
		return qnaList;
	}
	/** qna 정렬 목록 조회 Service (로그인유저)
	 * @param pagination
	 * @param searchCondition
	 * @param searchValue
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaSortList(QnaPagination pagination, String searchCondition, String searchValue, int loginUserId) throws Exception {

		Connection conn = getConnection();
		
		String condition = createCondition(searchCondition, searchValue);
		
		List<Qna> qnaList = dao.sortQnaList(conn, pagination, condition);
		
		qnaList = dao.getLikeFlag(conn, qnaList, loginUserId);
		
		close(conn);
		
		return qnaList;
	}
	
	
	/** qna 검색 조건이 일치하는 글 수 카운트 + 페이지네이션 생성 Service
	 * @param cp
	 * @param searchCondition
	 * @param searchValue
	 * @return QnaPagination
	 * @throws Exception
	 */
	public QnaPagination getPagination(int cp, String searchCondition, String searchValue) throws Exception {
		
		Connection conn = getConnection();
		
		String condition = createCondition(searchCondition, searchValue);
		
		int listCount = dao.getListCount(conn, cp, condition);
		
		close(conn);
		
		return new QnaPagination(cp, listCount);
	}
	/** qna 검색 목록 조회 Service
	 * @param pagination
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(QnaPagination pagination, String searchCondition, String searchValue) throws Exception {
		
		Connection conn = getConnection();
		
		String condition = createCondition(searchCondition, searchValue);
		
		List<Qna> qnaList = dao.selectQnaList(conn, pagination, condition);
		
		close(conn);
		
		return qnaList;
	}
	/** qna 검색 목록 조회 Service (로그인유저)
	 * @param pagination
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(QnaPagination pagination, String searchCondition, String searchValue, int loginUserId) throws Exception {
		
		Connection conn = getConnection();
		
		String condition = createCondition(searchCondition, searchValue);
		
		List<Qna> qnaList = dao.selectQnaList(conn, pagination, condition);
		
		qnaList = dao.getLikeFlag(conn, qnaList, loginUserId);
		
		close(conn);
		
		return qnaList;
	}
	
	// SQL 조건식 반환 메소드
	public String createCondition(String searchCondition, String searchValue) {
		
		String condition = null;
		
		switch(searchCondition) {
		
		case "qnaTitle" : condition = " AND qna_title like '%"+searchValue+"%' "; break;
		
		case "userNickname" : condition = " AND user_nickname like '%"+searchValue+"%' "; break;
		
		case "qnaCategoryId" : condition = " AND qna_category_id like '%"+searchValue+"%' "; break;
		
		case "sortNewest" : condition = " ORDER BY qna_post_id "+searchValue+" "; break;
		
		case "sortLike" : condition = " ORDER BY qna_like "+searchValue+" "; break;
		
		}
		return condition;
	}

}
