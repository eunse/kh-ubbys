package com.ubbys.user.vo;

import java.sql.Date;
import java.util.List;

public class UnRegUser {
	
	private int userNo;
	private String userEmail;
	private String userPw;
	private String userNickname; 
	private Date userRegdate; 
	private String userIsAdmin; 
	private Date userUnRegdate;
	private String userPic; 
	private String userLink; 
	private String userInterest; 
	private String userIntroduce; 

	private List<String> filePath;
	private List<String> fileName;
	
	private int categoryCode;
	
	public UnRegUser() {}

	public UnRegUser(int userNo, String userEmail, String userPw, String userNickname, Date userRegdate,
			String userIsAdmin, Date userUnRegdate, String userPic, String userLink, String userInterest,
			String userIntroduce, List<String> filePath, List<String> fileName, int categoryCode) {
		super();
		this.userNo = userNo;
		this.userEmail = userEmail;
		this.userPw = userPw;
		this.userNickname = userNickname;
		this.userRegdate = userRegdate;
		this.userIsAdmin = userIsAdmin;
		this.userUnRegdate = userUnRegdate;
		this.userPic = userPic;
		this.userLink = userLink;
		this.userInterest = userInterest;
		this.userIntroduce = userIntroduce;
		this.filePath = filePath;
		this.fileName = fileName;
		this.categoryCode = categoryCode;
	}

	

	public UnRegUser(int userNo, String userEmail, String userNickname, Date userRegdate, Date userUnRegdate) {
		super();
		this.userNo = userNo;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.userRegdate = userRegdate;
		this.userUnRegdate = userUnRegdate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public Date getUserRegdate() {
		return userRegdate;
	}

	public void setUserRegdate(Date userRegdate) {
		this.userRegdate = userRegdate;
	}

	public String getUserIsAdmin() {
		return userIsAdmin;
	}

	public void setUserIsAdmin(String userIsAdmin) {
		this.userIsAdmin = userIsAdmin;
	}

	public Date getUserUnRegdate() {
		return userUnRegdate;
	}

	public void setUserUnRegdate(Date userUnRegdate) {
		this.userUnRegdate = userUnRegdate;
	}

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getUserLink() {
		return userLink;
	}

	public void setUserLink(String userLink) {
		this.userLink = userLink;
	}

	public String getUserInterest() {
		return userInterest;
	}

	public void setUserInterest(String userInterest) {
		this.userInterest = userInterest;
	}

	public String getUserIntroduce() {
		return userIntroduce;
	}

	public void setUserIntroduce(String userIntroduce) {
		this.userIntroduce = userIntroduce;
	}

	public List<String> getFilePath() {
		return filePath;
	}

	public void setFilePath(List<String> filePath) {
		this.filePath = filePath;
	}

	public List<String> getFileName() {
		return fileName;
	}

	public void setFileName(List<String> fileName) {
		this.fileName = fileName;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Override
	public String toString() {
		return "UnRegUser [userNo=" + userNo + ", userEmail=" + userEmail + ", userPw=" + userPw + ", userNickname="
				+ userNickname + ", userRegdate=" + userRegdate + ", userIsAdmin=" + userIsAdmin + ", userUnRegdate="
				+ userUnRegdate + ", userPic=" + userPic + ", userLink=" + userLink + ", userInterest=" + userInterest
				+ ", userIntroduce=" + userIntroduce + ", filePath=" + filePath + ", fileName=" + fileName
				+ ", categoryCode=" + categoryCode + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
