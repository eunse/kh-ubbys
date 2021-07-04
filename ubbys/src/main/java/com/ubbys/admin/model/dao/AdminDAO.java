package com.ubbys.admin.model.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import com.ubbys.user.vo.User;

public class AdminDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	
	public AdminDAO() {
		String filePath = AdminDAO.class.getResource("/com/ubbys/sql/admin-query.xml").getPath();

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	
	/** 관리자 로그인 DAO
	 * @param conn
	 * @param userEmail
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	public User login(Connection conn, String userEmail, String userPw) throws Exception{
		User loginUser = null;
		
		String sql = prop.getProperty("adminLogin");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userEmail);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				loginUser = new User(rs.getInt("user_id"), rs.getString("user_email"), rs.getString("user_nickname"),
						rs.getDate("user_regdate"), rs.getString("user_is_admin"), rs.getString("user_pic"),
						rs.getString("user_link"), rs.getString("user_interest"), rs.getString("user_introduce"));
			}
			
		} finally {
			close(rs);
			close(pstmt);			
		}
				
		return loginUser;
	}
	
	

}
