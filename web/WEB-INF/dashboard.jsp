<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Dashboard" />
</jsp:include>
<%@ include file="/WEB-INF/includes/toolbar.jsp" %>
<div class="container">
    <h1>Welcome to your dashboard, ${username} !</h1> <br> <br>
    <strong>Statistics : </strong> ${nbUsers} users are registered, ${nbMessages} messages have been written! <br> <br>
    <div class="row">
        <div class="col-md-6">
            <h1>Your 10 last messages</h1>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Author</th>
                        <th>Message</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="3" align="center">No messages. <a href="${pageContext.request.contextPath}/inbox">Write one!</a></td>
                    </tr>
                </thead>
            </table>
        </div>
        <div class="col-md-6">
            <h1>Your 10 best friends!</h1>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Messages Sent</th>
                        <th>Messages received</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="4" align="center">No friends :(. <a href="${pageContext.request.contextPath}/search">Add one!</a></td>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>