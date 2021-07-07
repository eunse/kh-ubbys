package com.ubbys.admin.qna.service;

import static com.ubbys.common.JDBCTemplate.close;
import static com.ubbys.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.ubbys.admin.qna.dao.SelectQnaDAO;
import com.ubbys.admin.qna.vo.Qna;
import com.ubbys.board.vo.Pagination;

public class SelectQnaService {
	private SelectQnaDAO dao = new SelectQnaDAO();
	


	/** 페이징 객체 생성용 + 전체 게시글 조회 Service
	 * @param cp
	 * @return pagination
	 * @throws Exception
	 */
	public Pagination getPagination(int cp) throws Exception {
		
		Connection conn = getConnection();

		
		Map<String, Object> map = dao.getListCount(conn, cp);
		
		close(conn);
		
		int listCount = (int)map.get("listCount");
		
		return new Pagination(cp, listCount);
	}

	
	
	/** qna 목록 조회 service
	 * @param pagination
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(Pagination pagination) throws Exception {
		Connection conn = getConnection();
		
		List<Qna> qnaList = dao.selectQnaList(conn, pagination);
		
		close(conn);
		return qnaList;
	}


	
	/** 댓글 작성을 위한 qna 목록 조회 Service
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList() throws Exception {
		Connection conn = getConnection();
		
		List<Qna> qnaList = dao.selectQnaList(conn);
		
		close(conn);
		return qnaList;
	}
	
	
	/** qna 상세 조회 service
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
	
	/** qna 정렬 목록 조회 Service
	 * @param pagination
	 * @param searchCondition
	 * @param searchValue
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaSortList(Pagination pagination, String searchCondition, String searchValue) throws Exception {

		Connection conn = getConnection();
		
		String condition = createCondition(searchCondition, searchValue);
		
		List<Qna> qnaList = dao.sortQnaList(conn, pagination, condition);
		
		close(conn);
		
		return qnaList;
	}
	
	// --------------검색 시작----------------------------------

	/** 페이징 객체 생성용 Service (qna 게시판 검색용)
	 * @param cp
	 * @param searchQnaCond
	 * @param searchQnaCondText
	 * @return Pagination
	 * @throws Exception
	 */
	public Pagination getPagination(int cp, String searchCondition, String searchValue ) throws Exception {
		
		Connection conn = getConnection();
		
		String condition = createCondition(searchCondition, searchValue );

		
		Map<String, Object> map = dao.getListCount(conn, cp, condition);
		
		close(conn);
		
		int listCount = (int)map.get("listCount");
		
		return new Pagination(cp, listCount);
	}
	
	/** qna 검색 목록 조회 service
	 * @param pagination
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(Pagination pagination, String searchCondition, String searchValue) throws Exception {
		Connection conn = getConnection();
		
		String condition = createCondition(searchCondition, searchValue);
		
		List<Qna> qnaList = dao.selectQnaList(conn, pagination, condition);
		
		close(conn);
		return qnaList;
	}
	
	// 검색 조건에 따라 SQL에 사용할 조건식을 만들어 반환하는 메소드
	private String createCondition(String searchCondition, String searchValue) {
		
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
	
	
	// ----------------------검색  끝----------------------------------------
	
	
	

}
