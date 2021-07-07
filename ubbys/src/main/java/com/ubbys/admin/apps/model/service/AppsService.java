package com.ubbys.admin.apps.model.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.ubbys.admin.apps.model.dao.AppsDAO;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;

public class AppsService {
	AppsDAO dao = new AppsDAO();
	
	/** 페이지네이션 생성 Service
	 * @param cp
	 * @return pagination
	 * @throws Exception
	 */
	public Pagination getPagination(int cp) throws Exception {
		
		Connection conn = getConnection();
		int listCount = dao.getListCount(conn);
		close(conn);
		return new Pagination(cp, listCount);
	}

	/** apps 목록 출력 Service
	 * @param pagination
	 * @return appsList
	 * @throws Exception
	 */
	public List<Apps> getAppsList(Pagination pagination) throws Exception {
		
		Connection conn = getConnection();
		List<Apps> appsList = dao.getAppsList(pagination, conn);
		close(conn);
		return appsList;
	}

	/** apps 삭제 Service
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int deleteApps(int postId) throws Exception {
		
		Connection conn = getConnection();
		int result = dao.deleteApps(conn, postId);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

}
