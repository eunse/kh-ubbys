package com.ubbys.admin.qna.dao;

import static com.ubbys.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ubbys.admin.qna.vo.Qna;
import com.ubbys.board.vo.Pagination;

public class SelectQnaDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	
	private Properties prop = null;
	
	public SelectQnaDAO() {
		String filePath = SelectQnaDAO.class.getResource("/com/ubbys/sql/selectAdminQna-query.xml").getPath();

		try {
			prop = new Properties();

			
			prop.loadFromXML(new FileInputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 전체 게시글 수 조회 dao
	 * @param conn
	 * @param cp
	 * @return map
	 * @throws Exception
	 */
	public Map<String, Object> getListCount(Connection conn, int cp) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = prop.getProperty("getListCount");
		
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				map.put( "listCount", rs.getInt(1) );
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return map;
	}
	
	
	/** Qna 목록 조회 DAO
	 * @param conn
	 * @param pagination
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(Connection conn, Pagination pagination) throws Exception {
		
		List<Qna> qnaList = new ArrayList<Qna>();
		
		String sql = prop.getProperty("selectQnaList");


		try {
			pstmt = conn.prepareStatement(sql);
			
			// 조회할 범위를 지정할 변수 선언
			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			

			
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				
				Qna vo = new Qna();
				
				vo.setQnaPostId(rs.getInt("QNA_POST_ID"));
				vo.setQnaCategoryName(rs.getString("QNA_CATEGORY_NAME"));
				vo.setQnaTitle(rs.getString("QNA_TITLE"));
				vo.setQnaLike(rs.getInt("QNA_LIKE"));
				vo.setQnaReplyCount(rs.getInt("REPLY_COUNT"));
				vo.setUserNickname(rs.getString("USER_NICKNAME"));
				vo.setQnaDate(rs.getString("QNA_DATE"));
				
				
				qnaList.add(vo);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return qnaList;
	}

	
	
	/** Qna 상세 조회 dao
	 * @param conn
	 * @param qnaPostId
	 * @return
	 * @throws Exception
	 */
	public Qna selectQna(Connection conn, int qnaPostId) throws Exception {
		
		Qna qna = null;
		
		String sql = prop.getProperty("selectQna");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaPostId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				qna = new Qna();
				qna.setQnaPostId(rs.getInt("QNA_POST_ID"));
				qna.setQnaCategoryName(rs.getString("QNA_CATEGORY_NAME"));
				qna.setQnaDate(rs.getString("QNA_DATE"));
				qna.setQnaTitle(rs.getString("QNA_TITLE"));
				qna.setQnaContent(rs.getString("QNA_CONTENT"));
				qna.setUserNickname(rs.getString("USER_NICKNAME"));
				qna.setQnaLike(rs.getInt("QNA_LIKE"));
				qna.setUserId(rs.getInt("USER_ID"));
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return qna;
	}

	//--------------검색 시작-------------------------------------
	
	/** qna 정렬 목록 조회 DAO
	 * @param conn
	 * @param pagination
	 * @param condition
	 * @return qnaList
	 * @throws Exception
	 */
	public List<Qna> sortQnaList(Connection conn, Pagination pagination, String condition) throws Exception {
		
		List<Qna> qnaList = new ArrayList<Qna>();
		
		String sql = prop.getProperty("sortQnaList1") + condition + prop.getProperty("sortQnaList2");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int start = (pagination.getCurrentPage()-1)*pagination.getLimit()+1;
			int end = start+pagination.getLimit()-1;
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Qna qna = new Qna();
				
				qna.setQnaPostId(rs.getInt("QNA_POST_ID"));
				qna.setQnaCategoryName(rs.getString("QNA_CATEGORY_NAME"));
				qna.setQnaDate(rs.getString("QNA_DATE"));
				qna.setQnaTitle(rs.getString("QNA_TITLE"));
				qna.setUserNickname(rs.getString("USER_NICKNAME"));
				qna.setQnaLike(rs.getInt("QNA_LIKE"));
				qna.setQnaReplyCount(rs.getInt("REPLY_COUNT"));
				
				qnaList.add(qna);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return qnaList;
	}

	/**  Qna 검색 조건에 맞는 전체 게시글 수  dao (검색용)
	 * @param conn
	 * @param cp
	 * @param condition
	 * @return map
	 * @throws Exception
	 */
	public Map<String, Object> getListCount(Connection conn, int cp, String condition ) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = prop.getProperty("getSearchCount") + condition;
		System.out.println(sql);
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				map.put( "listCount", rs.getInt(1) );
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return map;
	}


	public List<Qna> selectQnaList(Connection conn, Pagination pagination, String condition) throws Exception {
		
		List<Qna> qnaList = new ArrayList<Qna>();
		
		String sql = prop.getProperty("searchQnaList1") + condition
					+ prop.getProperty("searchQnaList2");
		System.out.println(sql);

		try {
			pstmt = conn.prepareStatement(sql);
			
			// 조회할 범위를 지정할 변수 선언
			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			

			
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				
				Qna vo = new Qna();
				
				vo.setQnaPostId(rs.getInt("QNA_POST_ID"));
				vo.setQnaCategoryName(rs.getString("QNA_CATEGORY_NAME"));
				vo.setQnaTitle(rs.getString("QNA_TITLE"));
				vo.setQnaLike(rs.getInt("QNA_LIKE"));
				vo.setQnaReplyCount(rs.getInt("REPLY_COUNT"));
				vo.setUserNickname(rs.getString("USER_NICKNAME"));
				vo.setQnaDate(rs.getString("QNA_DATE"));
				
				
				qnaList.add(vo);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return qnaList;
	}
	
	
}
