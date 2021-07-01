package com.ubbys.admin.qna.dao;

import static com.ubbys.common.JDBCTemplate.*;

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
	// 자주 사용하는 JDBC 객체 참조 변수 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 외부 XML 파일에 작성된 SQL 구문을 읽어와 저장할 Properties객체 참조 변수 선언
	private Properties prop = null;
	
	public SelectQnaDAO() {
		String filePath = SelectQnaDAO.class.getResource("/com/ubbys/sql/selectAdminQna-query.xml").getPath();

		try {
			prop = new Properties();

			// filePath 변수에 저장된 경로로 부터 XML 파일을 읽어와 prop에 저장
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
	public Map<String, Object> getListCount(Connection conn, int cp, Qna qna) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = prop.getProperty("getListCount");
		
		// 조회 조건
		boolean isSearchCond = false;
		if (qna.getSearchQnaCond().equals("qnaCategoryName") && !"".equals(qna.getSearchQnaCondText())) {
			sql = sql.replaceAll("#\\{searchCond\\}", "AND QNA_CATEGORY_NAME LIKE ?");
			isSearchCond = true;
		} else if (qna.getSearchQnaCond().equals("qnaTitle") && !"".equals(qna.getSearchQnaCondText())) {
			sql = sql.replaceAll("#\\{searchCond\\}", "AND QNA_TITLE LIKE ?");
			isSearchCond = true;
		} else if (qna.getSearchQnaCond().equals("userNickname") && !"".equals(qna.getSearchQnaCondText())) {
			sql = sql.replaceAll("#\\{searchCond\\}", "AND USER_NICKNAME LIKE ?");
			isSearchCond = true;
		} else {
			sql = sql.replaceAll("#\\{searchCond\\}", "");
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			if (isSearchCond) {
				pstmt.setString(1, "%" + qna.getSearchQnaCondText() + "%");
			}
			
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				map.put( "listCount", rs.getInt(1));
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return map;
	}
	
	public List<Qna> selectQnaList(Connection conn, Pagination pagination, Qna qna) throws Exception {
		List<Qna> qnaList = new ArrayList<Qna>();
		
		String sql = prop.getProperty("selectQnaList");
		
		// 조회 조건
		boolean isSearchCond = false;
		if (qna.getSearchQnaCond().equals("qnaCategoryName") && !"".equals(qna.getSearchQnaCondText())) {
			sql = sql.replaceAll("#\\{searchCond\\}", "AND QNA_CATEGORY_NAME LIKE ?");
			isSearchCond = true;
		} else if (qna.getSearchQnaCond().equals("qnaTitle") && !"".equals(qna.getSearchQnaCondText())) {
			sql = sql.replaceAll("#\\{searchCond\\}", "AND QNA_TITLE LIKE ?");
			isSearchCond = true;
		} else if (qna.getSearchQnaCond().equals("userNickname") && !"".equals(qna.getSearchQnaCondText())) {
			sql = sql.replaceAll("#\\{searchCond\\}", "AND USER_NICKNAME LIKE ?");
			isSearchCond = true;
		} else {
			sql = sql.replaceAll("#\\{searchCond\\}", "");
		}
		
		// 정렬 조건
		if (qna.getSearchOrder().equals("qnaLike")) {
			sql = sql.replaceAll("#\\{orderBy\\}", "ORDER BY QNA_LIKE DESC");
		} else if (qna.getSearchOrder().equals("qnaDate")) {
			sql = sql.replaceAll("#\\{orderBy\\}", "ORDER BY QNA_DATE DESC");
		} else {
			sql = sql.replaceAll("#\\{orderBy\\}", "ORDER BY QNA_DATE DESC");
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// 조회할 범위를 지정할 변수 선언
			int startRow = (pagination.getCurrentPage() - 1) * pagination.getLimit() + 1;
			int endRow = startRow + pagination.getLimit() - 1;
					
			if (isSearchCond) {
				pstmt.setString(1, "%" + qna.getSearchQnaCondText() + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				// 조회된 한 행의 정보를 qna에 set
				Qna vo = new Qna();
				
				vo.setQnaPostId(rs.getInt("QNA_POST_ID"));
				vo.setQnaCategoryName(rs.getString("QNA_CATEGORY_NAME"));
				vo.setQnaTitle(rs.getString("QNA_TITLE"));
				vo.setQnaLike(rs.getInt("QNA_LIKE"));
				vo.setQnaReplyCount(rs.getInt("REPLY_COUNT"));
				vo.setUserNickname(rs.getString("USER_NICKNAME"));
				vo.setQnaDate(rs.getString("QNA_DATE"));
				
				// set 완료된 qna를 qnaList에 추가
				qnaList.add(vo);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return qnaList;
	}
}
