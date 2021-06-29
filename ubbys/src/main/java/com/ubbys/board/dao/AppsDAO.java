package com.ubbys.board.dao;

import static com.ubbys.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppsDAO extends BoardDAO {
	
	public AppsDAO() {
		String filePath = AppsDAO.class.getResource("/com/ubbys/sql/apps-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	

}
