<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  </main>

  <jsp:include page="modal.jsp" />
  <script src="${contextPath}/resources/js/common.js"></script>
  <c:if test="${!empty sessionScope.modalTitle}">
    <script>
    	modal.show();
    </script>
    <c:remove var="modalTitle" />
    <c:remove var="modalText" />
    <c:remove var="modalButtonLink" />
    <c:remove var="modalButtonText" />
  </c:if>

</body>
</html>