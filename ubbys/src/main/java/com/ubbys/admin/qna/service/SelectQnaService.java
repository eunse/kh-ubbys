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
	
	/** 페이징 처리 객체 생성용 Service
	 * @param cp
	 * @param boardType
	 * @return pagination
	 * @throws Exception
	 */
	public Pagination getPagination(int cp, Qna qna) throws Exception {
		
		Connection conn = getConnection();

		// DB에서 전체 게시글 수 + 게시판 이름을 얻어옴
		Map<String, Object> map = dao.getListCount(conn, cp, qna);
		
		close(conn);
		
		// map의 value를 object로 해놨기 때문에 형변환을 해야함
		int listCount = (int)map.get("listCount");
		
		return new Pagination(cp, listCount);
	}
	
	public List<Qna> selectQnaList(Pagination pagination, Qna qna) throws Exception {
		Connection conn = getConnection();
		
		List<Qna> qnaList = dao.selectQnaList(conn, pagination, qna);
		
		close(conn);
		return qnaList;
	}
}
