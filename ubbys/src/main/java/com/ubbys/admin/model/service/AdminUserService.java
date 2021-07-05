package com.ubbys.admin.model.service;

import static com.ubbys.common.JDBCTemplate.*;
import java.sql.Connection;

import com.ubbys.admin.model.dao.AdminUserDAO;
import com.ubbys.board.vo.Pagination;

public class AdminUserService {
	
	AdminUserDAO dao = new AdminUserDAO(); // import할 DAO 클래스명 바꾸기
	
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

}
