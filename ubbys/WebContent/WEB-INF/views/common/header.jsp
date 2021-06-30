<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application" value="${pageContext.servletContext.contextPath}"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Layout</title>
  
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
  <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</head>
<body>
  <main>
    <header class="p-3 border-bottom">
      <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
          <a href="${contextPath}" class="d-flex align-items-center mb-2 mb-lg-0 navbar-brand"><img src="${contextPath}/resources/img/logo.png" height="24"/></a>

          <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${contextPath}/apps/list" class="nav-link px-2 link-dark">apps</a></li>
            <li><a href="${contextPath}/qnaList?" class="nav-link px-2 link-dark">qna</a></li>
          </ul>
          <c:choose>
            <c:when test="${empty sessionScope.loginUser }">
              <ul class="nav">
                <li><a href="${contextPath}/login" class="nav-link px-2 link-dark">login</a></li>
                <li><a href="${contextPath}/signup" class="nav-link px-2 link-dark">signUp</a></li>
              </ul>
            </c:when>
            <c:otherwise>
              <div class="dropdown text-end">
                <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser1"
                  data-bs-toggle="dropdown" aria-expanded="false">
                  <img src="${sessionScope.loginUser.userPic}" width="32" height="32" class="rounded-circle me-2">
                  ${sessionScope.loginUser.userNickname}
                </a>
                <ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1" style="">
                  <li><a class="dropdown-item" href="${contextPath}/user">myPage</a></li>
                  <li>
                    <hr class="dropdown-divider">
                  </li>
                  <li><a class="dropdown-item" href="${contextPath}/logout">logOut</a></li>
                </ul>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </header>