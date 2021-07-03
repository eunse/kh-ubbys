package com.ubbys.board.vo;

public class Apps extends Board {
	private String appsIconUrl;
	private String appsLink;
	private String tagString;
	private String appsSummary;
	private List<Tag> tagList;
	private String[] tagArr;
	
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

	public String getTagString() {
		return tagString;
	}

	public void setTagString(String tagString) {
		this.tagString = tagString;
	}

	public String getAppsSummary() {
		return appsSummary;
	}

	public void setAppsSummary(String appsSummary) {
		this.appsSummary = appsSummary;
	}
	
	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}


	public String[] getTagArr() {
		return tagArr;
	}

	public void setTagArr(String[] tagArr) {
		this.tagArr = tagArr;
	}

	@Override
	public String toString() {
		return "Apps [appsIconUrl=" + appsIconUrl + ", appsLink=" + appsLink + ", tagString=" + tagString
				+ ", appsSummary=" + appsSummary + "]";
	}

	


}
