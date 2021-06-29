package com.ubbys.board.vo;

public class Pagination {
	private int currentPage;
	private int listCount;
	
	private int limit = 10;		// 한 페이지에 보여질 게시글 수
	private int pageSize = 10;	// pagination 개수
	
	private int maxPage;				// 마지막 페이지로 이동하는 버튼을 위한 번호
	private int startPage;				// 현재 Pagination의 가장 앞의 번호
	private int endPage;				// 현재 Pagination의 가장 뒤의 번호
	
	private int prevPage;				// 이전 페이지 번호 목록 중 끝 번호
	private int nextPage;				// 다음 페이지 번호 목록 중 시작 번호

			
	public Pagination(int currentPage, int listCount) {
		this.currentPage = currentPage;
		this.listCount = listCount;
		makePagination();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		makePagination();
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
		makePagination();
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
		makePagination();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		makePagination();
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

	// 페이징 처리에 필요한 값을 계산하는 메소드
	private void makePagination() {
		// 마지막 페이지 == 총 페이지 수 
		maxPage = (int)Math.ceil((double)listCount/limit);
		// 페이지 번호 목록 시작 번호
		startPage = (currentPage - 1) / pageSize * pageSize + 1;
		
		endPage = startPage + pageSize - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		if(currentPage < 10) {
			prevPage = 1;
		} else {
			prevPage = (currentPage - 1) / pageSize * pageSize;
		}
		
		nextPage = (currentPage + pageSize - 1) / pageSize * pageSize + 1;
		
		if(nextPage > maxPage) {
			nextPage = maxPage;
		}
	}
}
