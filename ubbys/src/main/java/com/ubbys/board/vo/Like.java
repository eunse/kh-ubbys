package com.ubbys.board.vo;

public class Like {
	private int likeId;
	private int userId;
	private int postId;
	
	public Like() {}
		
	public Like(int likeId, int userId, int postId) {
		super();
		this.likeId = likeId;
		this.userId = userId;
		this.postId = postId;
	}

	public int getLikeId() {
		return likeId;
	}
	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "Like [likeId=" + likeId + ", userId=" + userId + ", postId=" + postId + "]";
	}	
}
