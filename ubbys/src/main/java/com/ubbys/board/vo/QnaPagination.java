package com.ubbys.board.vo;

public class QnaPagination {
	
	private int currentPage;  // 현재 페이스
	private int listCount;	  // 게시글 전체 수
	
	private int limit = 10;	  // 한 페이지에 보여질 게시글 수
	private int pageSize = 5; // 보여질 페이지 번호의 수
	
	private int maxPage;	  // 마지막 페이지 번호
	private int startPage;	  // 보여지는 페이지 번호 중 시작 번호
	private int endPage;	  // 보여지는 페이지 번호 중 끝 번호
	
	private int prevPage;	  // 이전 페이지 번호 목록 중 끝 번호
	private int nextPage;	  // 다음 페이지 번호 목록 중 시작 번호
	
	public QnaPagination(int currentPage, int listCount) {
		this.currentPage = currentPage;
		this.listCount = listCount;
		mkPage();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		mkPage();
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
		mkPage();
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
		mkPage();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		mkPage();
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	@Override
	public String toString() {
		return "QnaPagination [currentPage=" + currentPage + ", listCount=" + listCount + ", limit=" + limit
				+ ", pageSize=" + pageSize + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage="
				+ endPage + ", prevPage=" + prevPage + ", nextPage=" + nextPage + "]";
	}
	
	private void mkPage() {
		
		maxPage = (int)Math.ceil((double)listCount/limit); 		// 총 페이지 수
		
		startPage = (currentPage - 1) / pageSize * pageSize +1; // 페이지 목록의 시작 번호
		endPage = startPage + pageSize - 1;						// 페이지 목록의 끝 번호
		
		if(endPage>maxPage) endPage = maxPage;
		
		if(currentPage<5) prevPage = 1;
		else			  prevPage = (currentPage - 1) / pageSize * pageSize; // 이전 페이지의 끝 번호
		nextPage = (currentPage + pageSize - 1) / pageSize * pageSize + 1;	  // 다음 페이지의 시작 번호
		
		if(nextPage>maxPage) nextPage = maxPage;
	}

}
