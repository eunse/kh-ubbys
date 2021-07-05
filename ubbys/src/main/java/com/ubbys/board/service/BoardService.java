package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.ubbys.board.dao.BoardDAO;
import com.ubbys.board.vo.Board;
import com.ubbys.board.vo.Category;
import com.ubbys.board.vo.Pagination;

public class BoardService {
	private BoardDAO dao = new BoardDAO();
	/**
	 * 페이징 처리 객체 생성용 Service
	 * @param cp
	 * @param boardType
	 * @return pagination
	 * @throws Exception
	 */
	public Pagination getPagination(String boardTableName, int cp) throws Exception {
		Connection conn = getConnection();
		int listCount = dao.getListCount(conn, boardTableName);
		close(conn);
		
		return new Pagination(cp, listCount);
	}
	
	/**
	 * 카테고리 목록 조회 Service
	 * @return category
	 * @throws Exception
	 */
	public List<Category> selectCategoryList(String boardTableName) throws Exception {
		Connection conn = getConnection();
		List<Category> category = dao.selectCategoryList(conn, boardTableName);
		close(conn);
		return category;
	}
	
	/**MyApps 목록 조회 Service
	 * @param userNo
	 * @return myAppsList
	 * @throws Exception
	 */
	public List<Board> selectMyAppsList(int userNo) throws Exception{
		Connection conn = getConnection();
		List<Board> myAppsList = dao.selectMyQnaList(conn, userNo);
		
		close(conn);
		
		return myAppsList;
	}
}
