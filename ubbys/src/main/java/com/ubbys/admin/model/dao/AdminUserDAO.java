package com.ubbys.admin.model.dao;

import static com.ubbys.common.JDBCTemplate.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.ubbys.board.dao.SelectQnaDAO;

public class AdminUserDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	
	public AdminUserDAO() {
		String filePath = SelectQnaDAO.class.getResource("/com/ubbys/sql/adminUser-query.xml").getPath(); // 경로 혹은 xml파일 이름 바꾸기
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/** 유저 수 조회 DAO
	 * @param conn
	 * @param cp
	 * @return listCount
	 * @throws Exception
	 */
	public int getUserCount(Connection conn) throws Exception {
		
		int userCount = 0;
		String sql = prop.getProperty("getUserCount");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) userCount = rs.getInt(1);
			
		} finally {
			close(rs);
			close(stmt);
		}
		return userCount;
	}

}
