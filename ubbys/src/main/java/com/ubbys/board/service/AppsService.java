package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import com.ubbys.board.dao.AppsDAO;
import com.ubbys.board.vo.Apps;
import com.ubbys.board.vo.Board;
import com.ubbys.board.vo.Pagination;
import com.ubbys.board.vo.Tag;

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
		for(Apps apps : appsList) {
			String postContent = apps.getAppsSummary();
			postContent = postContent.replaceAll("<br>", " ");
			apps.setAppsSummary(postContent);
		}
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
		List<Tag> tagList = dao.selectAppsTags(conn, postId);
		apps.setTagList(tagList);
		close(conn);
		return apps;
	}

	/**
	 * apps 게시물 삽입 Service
	 * @param apps
	 * @return
	 * @throws Exception
	 */
	public int insertApps(Apps apps, List<Tag> tagList, boolean flag) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		int postId = 0;
		if(apps.getPostId() == 0) {
			postId = dao.nextPostId(conn);
		} else {
			postId = apps.getPostId();
		}
		if(postId > 0) {
			apps.setPostId(postId);
			String postContent = apps.getPostContent();
			postContent = replaceParameter(postContent);
			postContent = postContent.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
			apps.setPostContent(postContent);
			apps.setPostTitle(replaceParameter(apps.getPostTitle()));
			if(flag) {
				result = dao.updateApps(conn, apps);
			} else {
				result = dao.insertApps(conn, apps);
			}			
			if(result > 0) {
				commit(conn);
				for(Tag tag : tagList) {
					result = dao.insertAppsTags(conn, postId, tag.getTagId());
					if(result == 0) {
						rollback(conn);
						break;
					}
				}
				if(result > 0) {
					commit(conn);
				} else {
					rollback(conn);
				}
			} else {
				rollback(conn);
			}
		}
		close(conn);
		return postId;
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

	public List<Tag> selectTagList() throws Exception {
		Connection conn = getConnection();
		List<Tag> tagList = dao.selectTagList(conn);
		return tagList;
	}

	
	/**
	 * apps 작성시 입력된 태그 삽입 Service
	 * @param conn
	 * @param tagArr
	 * @return
	 * @throws Exception
	 */
	public List<Tag> insertTagInAppsTags(String[] tagArr) throws Exception {
		Connection conn = getConnection();
		List<Tag> tagList = new ArrayList<Tag>();
		ResultSet rs = null;
		for(String tagItem : tagArr) {
			Tag tag = new Tag();
			int rowNo = 0;
			int result = 0;
			rowNo = dao.isExistTag(conn, tagItem); // DB에 등록되어 있는 태그인지 확인
			if(rowNo > 0) { // 이미 존재하는 태그라면
				tag.setTagId(rowNo);
				tag.setTagName(tagItem);
			} else { // 등록된 적 없는 태그라면
				result = dao.insertTags(conn, tagItem);
				if(result > 0) {
					commit(conn);
					rowNo = dao.isExistTag(conn, tagItem);
					if(rowNo > 0) {
						tag.setTagId(rowNo);
						tag.setTagName(tagItem);
					}
				} else {
					rollback(conn);
					break;
				}
			}
			tagList.add(tag);
		}
		close(conn);
		return tagList;
	}
	
	/**
	 * apps 삭제 Service
	 * @param postId
	 * @param userNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteApps(int postId, int userNo) throws Exception {
		Connection conn = getConnection();
		int result = dao.deleteAppsTags(conn, postId);
		System.out.println("dao실행결과:" + result);
		if(result > 0) {
			result = dao.deleteApps(conn, postId, userNo);
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/**
	 * apps 게시물 작성자 Serivce
	 * @param postId
	 * @return result
	 * @throws Exception
	 */
	public int selectAuthor(int postId) throws Exception {
		Connection conn = getConnection();
		int result = dao.selectAuthor(conn, postId);
		close(conn);
		return result;
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
