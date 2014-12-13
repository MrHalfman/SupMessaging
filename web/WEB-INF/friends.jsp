<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Dashboard" />
</jsp:include>
<%@ include file="/WEB-INF/includes/toolbar.jsp" %>
<div class="container">
    <h1>My hudge friend list!</h1>
</div>
<span class="space-between"></span>
<div class="container">
    <table class="table">
        <thead>
            <tr>
                <th>Username</th>
                <th>Firstname</th>
                <th>Last name</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}" >
                <tr>
                    <td>${user.pseudo}</td>
                    <td>${user.firstname}</td> 
                    <td>${user.name}</td> 
                    <td>${user.mail}</td>
                </tr> 
            </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
