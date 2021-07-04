package com.ubbys.admin.qna.vo;

public class QnaCategory {

	private int qnaCategoryId;
	private String qnaCategoryName;
	
	public QnaCategory() {}

	public int getQnaCategoryId() {
		return qnaCategoryId;
	}

	public void setQnaCategoryId(int qnaCategoryId) {
		this.qnaCategoryId = qnaCategoryId;
	}

	public String getQnaCategoryName() {
		return qnaCategoryName;
	}

	public void setQnaCategoryName(String qnaCategoryName) {
		this.qnaCategoryName = qnaCategoryName;
	}

	@Override
	public String toString() {
		return "QnaCategory [qnaCategoryId=" + qnaCategoryId + ", qnaCategoryName=" + qnaCategoryName + "]";
	}
	
}
