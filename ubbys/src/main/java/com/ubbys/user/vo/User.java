package com.ubbys.user.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.sun.xml.internal.ws.api.message.Attachment;

public class User {
	private int userNo; // 회원 번호
	private String userEmail; // 아이디로 사용하는 회원 이메일
	private String userPw; // 회원 비밀번호
	private String userNickname; // 회원 닉네임
	private Date userRegdate; // 회원 가입일시
	private String userIsAdmin; // 회원 관리자 여부
	private String userPic; // (선택항목) 회원 프로필 사진
	private String userLink; // (선택항목) 회원 웹사이트
	private String userInterest; // (선택항목) 회원 관심분야
	private String userIntroduce; // (선택항목) 회원 자기소개
	
//	목록 조회 시 하나만 조회되지만,
//	상세 조회시 여러개가 조회 될 수 있기 때문에 List로 선언
	private List<String> filePath;
	private List<String> fileName;
	
	
//	삽입 시 필요한 필드 추가
	private int categoryCode;
	
	public User() {}
	
	// 회원가입 페이지에서 입력받는 항목을 위한 생성자
	public User(String userEmail, String userPw, String userNickname) {
		super();
		this.userEmail = userEmail;
		this.userPw = userPw;
		this.userNickname = userNickname;
	}
	
	// 추가 정보 입력용 생성자
	public User(int userNo, String userPic, String userLink, String userInterest, String userIntroduce) {
		super();
		this.userNo = userNo;
		this.userPic = userPic;
		this.userLink = userLink;
		this.userInterest = userInterest;
		this.userIntroduce = userIntroduce;
	}
	
	// 패스워드를 제외한 전체 생성자	
	public User(int userNo, String userEmail, String userNickname, Date userRegdate, String userIsAdmin,
			String userPic, String userLink, String userInterest, String userIntroduce) {
		super();
		this.userNo = userNo;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.userRegdate = userRegdate;
		this.userIsAdmin = userIsAdmin;
		this.userPic = userPic;
		this.userLink = userLink;
		this.userInterest = userInterest;
		this.userIntroduce = userIntroduce;
	}
	
	

	public User(int userNo, String userEmail, String userNickname, Date userRegdate,
			String userIsAdmin) {
		super();
		this.userNo = userNo;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.userRegdate = userRegdate;
		this.userIsAdmin = userIsAdmin;
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
		return "User [userNo=" + userNo + ", userEmail=" + userEmail + ", userPw=" + userPw + ", userNickname="
				+ userNickname + ", userRegdate=" + userRegdate + ", userIsAdmin=" + userIsAdmin + ", userPic="
				+ userPic + ", userLink=" + userLink + ", userInterest=" + userInterest + ", userIntroduce="
				+ userIntroduce + ", filePath=" + filePath + ", fileName=" + fileName + ", categoryCode=" + categoryCode
				+ "]";
	}

	

	

	
	
}
