package com.ubbys.board.vo;

import java.sql.Date;

public class Qna {
	
	private String qnaCategoryName; // 카테고리 이름
	private String qnaDate;			// 작성일
	private String qnaTitle;		// 제목
	private String userNickname;	// 작성자
	private int qnaLike;			// 좋아요슈
	private int qnaReplyCount;		// 댓글수
	
	public Qna() {}

	public String getQnaCategoryName() {
		return qnaCategoryName;
	}

	public void setQnaCategoryName(String qnaCategoryName) {
		this.qnaCategoryName = qnaCategoryName;
	}

	public String getQnaDate() {
		return qnaDate;
	}

	public void setQnaDate(String qnaDate) {
		this.qnaDate = qnaDate;
	}

	public String getQnaTitle() {
		return qnaTitle;
	}

	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public int getQnaLike() {
		return qnaLike;
	}

	public void setQnaLike(int qnaLike) {
		this.qnaLike = qnaLike;
	}

	public int getQnaReplyCount() {
		return qnaReplyCount;
	}

	public void setQnaReplyCount(int qnaReplyCount) {
		this.qnaReplyCount = qnaReplyCount;
	}

	@Override
	public String toString() {
		return "Qna [qnaCategoryName=" + qnaCategoryName + ", qnaDate=" + qnaDate + ", qnaTitle=" + qnaTitle
				+ ", userNickname=" + userNickname + ", qnaLike=" + qnaLike + ", qnaReplyCount=" + qnaReplyCount + "]";
	}

}
