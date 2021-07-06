package com.ubbys.admin.model.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.ubbys.admin.model.dao.AdminDAO;
import com.ubbys.board.vo.Pagination;
import com.ubbys.user.vo.User;

public class AdminService {
	
	private AdminDAO dao = new AdminDAO();

	/** 로그인 Service
	 * @param userEmail
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	public User login(String userEmail, String userPw) throws Exception{
		Connection conn = getConnection();
		
		User loginUser = dao.login(conn, userEmail, userPw);
		
		close(conn);
		
		return loginUser;
	}

	/** 전체 유저 수 조회 + 페이징 객체 생성 Service
	 * @param cp
	 * @return pagination
	 * @throws Exception
	 */
	public Pagination getPagination(int cp) throws Exception {
		
		Connection conn = getConnection();
		
		int userCount = dao.getUserCount(conn);
		
		close(conn);
		
		return new Pagination(cp, userCount);
	}


	/** 회원 목록 조회 Service
	 * @param pagination 
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectUserList(Pagination pagination) throws Exception{
		Connection conn = getConnection();
		
		List <User> userList = dao.selectUserList(conn);
		
		close(conn);
		
		return userList;
	}
	
	private String createCondition(String searchKey, String searchValue) {

		String condition = null;
		
//		condition 양 끝에는 띄어쓰기를 반드시 추가하여
//		SQL 구문이 연속되서 작성되는 것을 방지함
		switch (searchKey) {
		case "userEmail":
			condition = " AND USER_EMAIL LIKE '%" + searchValue + "%'  ";
			break;
			
		case "userNickname":
			condition = " AND USER_NICKNAME LIKE '%" + searchValue + "%'  ";
			break;
		}
		
		return condition;

	}

	public Pagination getPagination(int cp, int userNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 페이징처리 객체용 Service
	 * @param cp
	 * @param searchKey
	 * @param searchValue
	 * @return pagination
	 * @throws Exception 
	 */
	public Pagination getPagination(int cp, String searchKey, String searchValue) throws Exception {
		
		Connection conn = getConnection();
		
		String condition = createCondition(searchKey, searchValue);
		
		int listCount = dao.getUserCount(conn, cp, condition);
		
		close(conn);
		
		return  new Pagination(cp, listCount);
	}

	/** 회원관리 목록 조회 Service(검색용)
	 * @param pagination
	 * @param searchKey
	 * @param searchValue
	 * @return userList
	 * @throws Exception 
	 */
	public List<User> selectUserList(Pagination pagination, String searchKey, String searchValue) throws Exception {
		
		Connection conn = getConnection();
		
		String condition = createCondition(searchKey, searchValue);
		
		List<User> userList = dao.selectUserList(conn, pagination, condition);
		
		close(conn);
		
		
		return userList;
	}

}
