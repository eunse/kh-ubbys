<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 검사</title>
</head>
<style>
	body>*{
		margin-left: 75px;
	}
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

<body>
	<h4 class="mt-3">아이디 중복 검사</h4>
	<br>
	
	<!-- form 태그 : 값을 전송하는 기능 -->
	<form action="${contextPath }/user/idDupCheck" id="idCheckForm" method="post">
		<input type="text" id="inputEmail" name="inputEmail" >
		<input type="submit" value="중복확인">
		<!-- inputEmail라는 값이 key값을 제출된다 -->
	</form>
	<br>
	
	<!-- 사용 가능 여부 메세지 출력 -->
	<span>
	
	<%-- 중복 검사를 수행한 경우 --%>
	<c:if test="${!empty result }">
		<c:choose>
			<c:when test="${result == 0 }">사용 가능한 아이디입니다.</c:when>
			<c:otherwise>이미 사용중인 아이디입니다.</c:otherwise>
		</c:choose>
	</c:if>
	</span>
	<br>
	<br>
	
	<div>
		<input type="button" id="cancel" value="취소" onclick="window.close();">
		<input type="button" id="confirmId" value="확인" onclick="confirmId();">
	</div>
	
	<script>
	
		let inputEmail; // 입력된 아이디를 저장하기 위한 변수
		const result = "${result}"; // "0", "1" -> 문자열로 들어옴
		// "0" : 중복 X
		// "1" : 중복 O
		// "" (빈칸) : 팝업창이 최초로 열려서 검사가 진행되지 않았다.
		
		
	// 현재 문서가 로드 완료 된 후 동작 
		$(function(){
			
			if(result === "") { // 최초 팝업창 오픈 시 
				// result값과 문자열 0의 값이 같을 때
				
				inputEmail = opener.document.signUpForm.inputEmail.value;
				// opener : 팝업을 연 창(부모창)
				// opener.document : 부모창 내부 HTML 문서 
				// opener.document.signUpForm : 부모창 내부 HTML 문서 중 name속성값이 signUpForm인 요소 
				// ...signUpForm.id : name 속성값이 signUpForm인 요소 내부에 name 속성 값이 id인 요소 선택
				// value : input 태그에 작성된 값을 얻어옴
				
			} else {
				
				inputEmail = "${inputEmail}"; // 중복 검사 진행한 아이디
				
			}
			
			
			
			$("#inputEmail").val(inputEmail);
			
		});
		
		// 확인 버튼 클릭 시 동작
		function confirmInputEmail() {
			
			// 1. 중복 검사를 진행한 아이디를 부모창으로 전달
			// 2. 아이디 중복검사 진행여부를 확인하기 위한 hidden 타입 태그 값 변경
			// 3. 팝업창 닫기
			
			if(result === "0") { // 중복검사를 진행했는데 사용 가능한 아이디인 경우 
					
				opener.document.signUpForm.idDup.value = true;
			// 열었던 부모창에 signUpForm이라는 이름을 가진 요소에서  idDup name을 가진 요소의 값을 불러와 true로 출력한다 
			
			} else {
				
				opener.document.signUpForm.idDup.value = false;
				
			}
	
			
			opener.document.signUpForm.inputEmail.value = $("#inputEmail").val();
			// 팝업창에 쓴 id 입력값을 부모창(원래 페이지)으로 전달
			
			
			
			if(opener != null) { // 부모창이 닫혀있지 않다면
				
				self.close();
				// self : 현재창
				
			}
			
		}
		
		// 아이디 정규식 검사
		$("#idCheckForm").submit(function(){
			
			// 정규표현식 객체 생성
		        const regExp = /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/;

		    // 입력된 아이디(양쪽 공백 제거)를 얻어와 inputId 변수에 저장
		    const inputEmail = $("#inputEmail").val().trim();
		    
		    if(!regExp.test(inputId)) { // 입력된 id가 유효하지 않을 때
		    	alert("유효하지 않은 아이디 형식입니다.");
		    	return false; // 유효하지 않다 == submit 반환이 되지 않는다
		    }
			
		});
	</script>
	
	
	<c:remove var="inputEmail"/>
	<c:remove var="result"/>
</body>
</html>