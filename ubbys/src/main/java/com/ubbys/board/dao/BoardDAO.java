package com.ubbys.board.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class BoardDAO {
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	
	protected Properties prop = null;
	
	public BoardDAO() {}
}
