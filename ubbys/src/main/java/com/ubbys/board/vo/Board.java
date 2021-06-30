package com.ubbys.board.vo;

import java.sql.Date;

public class Board {
	protected int postId;
	protected String postTitle;
	protected String postContent;
	protected Date postDate;
	protected int userNo;
	protected String userName;
	protected int postLike;
	protected String categoryName;

	public Board() {}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPostLike() {
		return postLike;
	}

	public void setPostLike(int postLike) {
		this.postLike = postLike;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "Board [postId=" + postId + ", postTitle=" + postTitle + ", postContent=" + postContent + ", postDate="
				+ postDate + ", userNo=" + userNo + ", userName=" + userName + ", postLike=" + postLike
				+ ", categoryName=" + categoryName + "]";
	}
	
	
	
}
