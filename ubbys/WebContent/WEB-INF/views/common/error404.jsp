<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application" value="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ERROR</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
  <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
  <div class="container">
    <div class="err-div">
      <div class="mb-3">
        <img src="${ contextPath }/resources/img/logo.png" height="32" class="mx-auto d-block">
      </div>
      <div class="mb-4 err-msg">
        <h1 align="center">404</h1>
        <h3 align="center">페이지를 찾을 수 없습니다.</h3>
      </div>
      <div align="center">
        <button type="button" class="btn btn-outline-secondary btn-sm" onclick="history.back();">이전 목록으로</button>
        <button type="button" class="btn btn-outline-secondary btn-sm" onclick="location.href='${ contextPath }';">메인 페이지로</button>
      </div>
    </div>
  </div>
</body>
</html>