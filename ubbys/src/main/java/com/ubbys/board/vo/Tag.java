package com.ubbys.board.vo;

public class Tag {
	private int tagId;
	private String tagName;
	private int postId;
	
	public Tag() {}
	
	public Tag(int tagId, String tagName, int postId) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
		this.postId = postId;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
	
}
