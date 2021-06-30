package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.ubbys.board.dao.AppsDAO;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;

public class AppsService extends BoardService {
	private AppsDAO dao = new AppsDAO();
	
	/**
	 * 게시글 목록 조회 Service
	 * @param pagination
	 * @return boardList
	 * @throws Exception
	 */
	public List<Apps> selectAppsList(Pagination pagination) throws Exception {
		Connection conn = getConnection();
		List<Apps> appsList = dao.selectAppsList(conn, pagination);
		close(conn);
		return appsList;
	}
}
