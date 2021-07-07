package com.ubbys.admin.model.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.ubbys.admin.model.dao.AdminDAO;
import com.ubbys.board.vo.Pagination;
import com.ubbys.user.vo.UnRegUser;
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
		

		switch (searchKey) {
		case "userEmail":
			condition = " AND USER_EMAIL LIKE '%" + searchValue + "%'  ";
			break;
			
		case "userNickname":
			condition = " AND USER_NICKNAME LIKE '%" + searchValue + "%'  ";
			break;
			
		case "sortYoung":
			condition = " ORDER BY user_id " + searchValue ; break;

		case "sortOld":
			condition = " ORDER BY user_id " + searchValue ; break;	
		}
		
		return condition;

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

	public User selectUser(String userEmail) throws Exception {
		Connection conn = getConnection();

		User user = dao.selectUser(conn, userEmail);

		close(conn);

		return user;
	}

	
	/** 회원관리 목록 조회 Service (정렬용)
	 * @param pagination
	 * @param searchKey
	 * @param searchValue
	 * @return userList
	 * @throws Exception
	 */
	public List<User> getUserSoltList(Pagination pagination, String searchKey, String searchValue) throws Exception {

		Connection conn = getConnection();

		String condition = createCondition(searchKey, searchValue);

		List<User> userList = dao.selectSortUserList(conn, pagination, condition);

		close(conn);

		return userList;
	}

	/** 탈퇴 회원 목록 조회 Service
	 * @param pagination
	 * @return unRegUserList
	 * @throws Exception
	 */
	public List<UnRegUser> selectUnregUserList(Pagination pagination) throws Exception {
		
		Connection conn = getConnection();
		
		List <UnRegUser> unRegUserList = dao.selectUnregUserList(conn);
		
		close(conn);
		
		return unRegUserList;
	}

	/** 탈퇴 회원관리 목록 조회 Service(검색용)
	 * @param pagination
	 * @param searchKey
	 * @param searchValue
	 * @return unRegUserList
	 * @throws Exception
	 */
	public List<UnRegUser> selectunRegUserList(Pagination pagination, String searchKey, String searchValue) throws Exception {
		
		Connection conn = getConnection();
		
		String condition = createCondition(searchKey, searchValue);
		
		List<UnRegUser> unRegUserList = dao.selectunRegUserList(conn, pagination, condition);
		
		close(conn);
		
		
		return unRegUserList;
	}

	/** 탈퇴 회원관리 목록 조회 Service (정렬용)
	 * @param pagination
	 * @param searchKey
	 * @param searchValue
	 * @return userList
	 * @throws Exception
	 */
	public List<UnRegUser> getUnRegUserSoltList(Pagination pagination, String searchKey, String searchValue) throws Exception {

		Connection conn = getConnection();

		String condition = createCondition(searchKey, searchValue);

		List<UnRegUser> unRegUserList = dao.selectSortUnRegUserList(conn, pagination, condition);

		close(conn);

		return unRegUserList;
	}

}
