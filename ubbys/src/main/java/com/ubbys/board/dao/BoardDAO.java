package com.ubbys.board.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class BoardDAO {
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	
	protected Properties prop = null;
	
	public BoardDAO() {
		String filePath = BoardDAO.class.getResource("/com/ubbys/sql/board-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * 전체 게시글 수 DAO
	 * @param conn
	 * @param boardTableName
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String boardTableName) throws Exception {
		int listCount = 0;
		// String sql = prop.getProperty("getListCount");
		String boardViewName = boardTableName + "_list";
		String boardStatusName = boardTableName + "_status";
		String sql = "SELECT COUNT(*) FROM " + boardViewName + " WHERE " + boardStatusName + "= 'Y'";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) listCount = rs.getInt(1);
		} finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}
}
