<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Dashboard" />
</jsp:include>
<%@ include file="/WEB-INF/includes/toolbar.jsp" %>
<div class="container">
    <h1>Welcome to your dashboard ${username}!</h1>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>