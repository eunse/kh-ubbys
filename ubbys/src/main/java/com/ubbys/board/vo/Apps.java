package com.ubbys.board.vo;

import java.util.List;

public class Apps extends Board {
	private String appsIconUrl;
	private String appsLink;
	private List<Tag> tagList;
	private String appsSummary;
	
	public Apps() {
		super();
	}

	public String getAppsIconUrl() {
		return appsIconUrl;
	}

	public void setAppsIconUrl(String appsIconUrl) {
		this.appsIconUrl = appsIconUrl;
	}

	public String getAppsLink() {
		return appsLink;
	}

	public void setAppsLink(String appsLink) {
		this.appsLink = appsLink;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public String getAppsSummary() {
		return appsSummary;
	}

	public void setAppsSummary(String appsSummary) {
		this.appsSummary = appsSummary;
	}

	@Override
	public String toString() {
		return "Apps [appsIconUrl=" + appsIconUrl + ", appsLink=" + appsLink + ", tagList=" + tagList + ", appsSummary="
				+ appsSummary + ", postId=" + postId + ", postTitle=" + postTitle + ", postContent=" + postContent
				+ ", postDate=" + postDate + ", userNo=" + userNo + ", userName=" + userName + ", postLike=" + postLike
				+ ", categoryName=" + categoryName + "]";
	}


}
