<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application" value="${pageContext.servletContext.contextPath}"/>
<jsp:include page="header.jsp" />
    <div class="container py-5">
      <h2>회원관리</h2>
      <form>
        <div class="input-group mb-3 w-50">
          <select class="form-select" id="searchUserCond" name="searchUserCond">
            <option selected>검색 조건</option>
            <option value="userEmail">이메일</option>
            <option value="userNickname">닉네임</option>
          </select>
          <input type="text" class="form-control" placeholder="닉네임 혹은 이메일로 검색하세요">
          <button class="btn btn-outline-secondary" type="button" id="searchUser" name="searchUser">검색</button>
        </div>
        <table class="table table-striped table-hover w-100">
          <thead>
            <tr>
              <th scope="col"><input type="checkbox"></th>
              <th scope="col">회원번호</th>
              <th scope="col">이메일</th>
              <th scope="col">닉네임</th>
              <th scope="col">가입일시</th>
              <th scope="col">관리</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><input type="checkbox"></td>
              <td>3</td>
              <td><a href="#">email@example.com</a></td>
              <td>김개똥</td>
              <td>0000년 00월 00일 00:00</td>
              <td>
                <a href="#" class="btn btn-primary btn-sm">수정</a>
                <a href="#" class="btn btn-danger btn-sm">삭제</a>
              </td>
            </tr>
            <tr>
              <td><input type="checkbox"></td>
              <td>2</td>
              <td><a href="#">email@example.com</a></td>
              <td>김개똥</td>
              <td>0000년 00월 00일 00:00</td>
              <td>
                <a href="#" class="btn btn-primary btn-sm">수정</a>
                <a href="#" class="btn btn-danger btn-sm">삭제</a>
              </td>
            </tr>
            <tr>
              <td><input type="checkbox"></td>
              <td>1</td>
              <td><a href="#">email@example.com</a></td>
              <td>김개똥</td>
              <td>0000년 00월 00일 00:00</td>
              <td>
                <a href="#" class="btn btn-primary btn-sm">수정</a>
                <a href="#" class="btn btn-danger btn-sm">삭제</a>
              </td>
            </tr>
          </tbody>
        </table>
      </form>
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
<jsp:include page="footer.jsp" />