package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.Map;

import com.ubbys.board.vo.Pagination;

public class BoardService {
	/**
	 * 페이징 처리 객체 생성용 Service
	 * @param cp
	 * @param boardType
	 * @return pagination
	 * @throws Exception
	 */
	public Pagination getPagination(int cp) throws Exception {
		Connection conn = getConnection();
		int listCount = dao.getListCount(conn, cp);
		close(conn);
		
		return new Pagination(cp, listCount);
	}
}
