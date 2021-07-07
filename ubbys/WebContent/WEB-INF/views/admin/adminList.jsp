<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application" value="${pageContext.servletContext.contextPath}"/>
    <div class="container py-5">
      <div class="col-xs-12 col-sm-4">
        <div class="input-group mb-3">
          <select class="form-select" id="searchCond">
            <option selected>검색 조건</option>
            <option value="1">제목</option>
            <option value="2">작성자명</option>
          </select>
          <input type="text" class="form-control" placeholder="검색어 입력">
          <button class="btn btn-outline-secondary" type="button" id="searchQna"><i class="bi bi-search"></i> 검색</button>
        </div>
      </div>
      <table class="table table-hover">
        <thead>
          <tr>
            <th scope="col" style="width: 6%;"></th>
            <th scope="col" style="width: 6%;">글번호</th>
            <th scope="col" style="width: 10%;">카테고리</th>
            <th scope="col">제목</th>
            <th scope="col" style="width: 12%;">작성자</th>
            <th scope="col" style="width: 10%;">작성일</th>
            <th scope="col" style="width: 8%;">좋아요수</th>
            <th scope="col" style="width: 8%;">댓글수</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><input class="form-check-input" type="checkbox" value="" id="flexCheckDefault"></td>
            <td>1</td>
            <td>오류</td>
            <td>제목</td>
            <td>닉네임</td>
            <td>2021-12-12</td>
            <td>1</td>
            <td>1</td>
          </tr>
        </tbody>
      </table>
      
      <button type="button" class="btn btn-outline-danger">삭제</button>
      <button type="button" class="btn btn-outline-primary float-end">글작성</button>
      
      <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
          <li class="page-item disabled">
            <a class="page-link" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li class="page-item active"><a class="page-link" href="#">1</a></li>
          <li class="page-item"><a class="page-link" href="#">2</a></li>
          <li class="page-item"><a class="page-link" href="#">3</a></li>
          <li class="page-item">
            <a class="page-link" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>