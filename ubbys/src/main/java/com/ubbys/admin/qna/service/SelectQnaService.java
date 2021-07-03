package com.ubbys.admin.qna.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static com.ubbys.common.JDBCTemplate.*;
import com.ubbys.admin.qna.dao.SelectQnaDAO;
import com.ubbys.admin.qna.vo.Qna;
import com.ubbys.board.vo.Pagination;

public class SelectQnaService {
	private SelectQnaDAO dao = new SelectQnaDAO();
	


	/** 페이징 객체 생성용 Service
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
}
