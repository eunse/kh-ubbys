package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.ubbys.board.dao.BoardDAO;
import com.ubbys.board.vo.Board;
import com.ubbys.board.vo.Category;
import com.ubbys.board.vo.Like;
import com.ubbys.board.vo.Pagination;
import com.ubbys.user.vo.User;
/**
 * 
 * @author 백승훈
 *
 */
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

	/**
	 * 게시물 좋아요 회수 조회 Service
	 * @param boardTableName
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int selectLike(String boardTableName, int postId) throws Exception {
		Connection conn = getConnection();
		int result = dao.selectLike(conn, boardTableName, postId);
		close(conn);
		return result;
	}
	/**
	 * 게시물 좋아요 여부 조회 Service
	 * @param boardTableName
	 * @param postId
	 * @param loginUserNo
	 * @return like
	 * @throws Exception
	 */
	public Like selectLike(String boardTableName, int postId, int loginUserNo) throws Exception {
		Connection conn = getConnection();
		Like like = dao.selectLike(conn, boardTableName, postId, loginUserNo);
		close(conn);
		return like;
	}
	
	/**
	 * 게시글 좋아요 추가 Service
	 * @param boardTableName
	 * @param userNo
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int insertLike(String boardTableName, int userNo, int postId) throws Exception {
		Connection conn = getConnection();
		int result = dao.insertLike(conn, boardTableName, userNo, postId);
		if(result > 0) {
			String addSub = "+";
			result = dao.updateLike(conn, boardTableName, postId, addSub);
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		}
		close(conn);
		return result;
	}
	
	/**
	 * 게시글 좋아요 삭제 Service
	 * @param boardTableName
	 * @param userNo
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int deleteLike(String boardTableName, int userNo, int postId) throws Exception {
		Connection conn = getConnection();
		int result = dao.deleteLike(conn, boardTableName, userNo, postId);
		if(result > 0) {
			String addSub = "-";
			result = dao.updateLike(conn, boardTableName, postId, addSub);
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		} 
		close(conn);
		return result;
	}
}
