package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Locale.Category;

import com.ubbys.board.dao.AppsDAO;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Pagination;

public class AppsService extends BoardService {
	private AppsDAO dao = new AppsDAO();
	
	/**
	 * apps 게시판 목록 조회 Service
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

	/**
	 * apps 게시글 상세 조회 Service
	 * @param postId
	 * @return
	 * @throws Exception
	 */
	public Apps selectApps(int postId) throws Exception {
		Connection conn = getConnection();
		Apps apps = dao.selectApps(conn, postId);
		close(conn);
		return apps;
	}

	/**
	 * apps 게시물 + 태그 삽입 Service
	 * @param apps
	 * @return
	 * @throws Exception
	 */
	public int insertApps(Apps apps) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		int postId = dao.nextPostId(conn);
		if(postId > 0) {
			apps.setPostId(postId);
			String postContent = apps.getPostContent();
			postContent = replaceParameter(postContent);
			postContent = postContent.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
			apps.setPostContent(postContent);
			apps.setPostTitle(replaceParameter(apps.getPostTitle()));
			result = dao.insertTags(conn, apps, postId);
			
			result = dao.insertApps(conn, apps);
			if(result > 0) {
				
			}
		}
		
		return result;
	}
	// XSS 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if(param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}		
		return result;
	}

}
