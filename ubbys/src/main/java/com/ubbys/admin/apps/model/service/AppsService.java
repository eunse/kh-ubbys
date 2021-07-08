package com.ubbys.admin.apps.model.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.ubbys.admin.apps.model.dao.AppsDAO;
import com.ubbys.board.dao.BoardDAO;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.QnaPagination;
import com.ubbys.board.vo.Tag;

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

	/** apps 정렬 Service
	 * @param pagination
	 * @param sc
	 * @param sv
	 * @return appsList
	 * @throws Exception
	 */
	public List<Apps> getAppsSortList(Pagination pagination, String sc, String sv) throws Exception{
		Connection conn = getConnection();
		String condition = createCondition(sc, sv);
		List<Apps> appsList = dao.getAppsSortList(conn, pagination, condition);
		close(conn);
		return appsList;
	}
	
	/** apps 검색 조건에 따른 페이지네이션 생성 Service
	 * @param cp
	 * @param sc
	 * @param sv
	 * @return pagination
	 * @throws Exception
	 */
	public Pagination getPagination(int cp, String sc, String sv) throws Exception {
		Connection conn = getConnection();
		String condition = createCondition(sc, sv);
		int listCount = dao.getListCount(conn, condition);
		close(conn);
		return new Pagination(cp, listCount);
	}
	/** apps 카테고리/제목/작성자 검색 Service
	 * @param pagination
	 * @param sc
	 * @param sv
	 * @return appsList
	 * @throws Exception
	 */
	public List<Apps> getAppsSearchList(Pagination pagination, String sc, String sv) throws Exception{
		Connection conn = getConnection();
		String condition = createCondition(sc, sv);
		List<Apps> appsList = dao.getAppsSearchList(conn, pagination, condition);
		close(conn);
		return appsList;
	}
	
	// SQL 조건식 반환 메소드
	public String createCondition(String sc, String sv) {
		
		String condition = null;
		
		switch(sc) {
		case "sortNewest" : condition = " ORDER BY apps_post_id "+sv+" "; break;
		case "sortLike" : condition = " ORDER BY apps_like "+sv+" "; break;
		case "categoryId" : condition = " AND apps_category_id like '%"+sv+"%' "; break;
		case "postTitle" : condition = " AND apps_title like '%"+sv+"%' "; break;
		case "userName" : condition = " AND user_nickname like '%"+sv+"%' "; break;
		}
		return condition;
	}

	/** apps 상세 조회 Servivce
	 * @param postId
	 * @return apps
	 * @throws Exception
	 */
	public Apps selectApps(int postId) throws Exception {
		Connection conn = getConnection();
		Apps apps = new com.ubbys.board.dao.AppsDAO().selectApps(conn, postId);
		List<Tag> tagList = new com.ubbys.board.dao.AppsDAO().selectAppsTags(conn, postId);
		apps.setTagList(tagList);
		close(conn);
		return apps;
	}






}
