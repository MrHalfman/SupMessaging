<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Log In" />
</jsp:include>
<%@ include file="/WEB-INF/includes/toolbar.jsp" %>
<div class="container"> <br>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Search a new friend !</h3>
        </div>
        <div class="panel-body">
            <form method="post" action="search">
                <div class="form-group">
                    <label>Username :</label>
                    <div>
                        <input type="text" name="friend" class="form-control" placeholder="KevinBG14" value="<c:out value="${friend}"/>">
                        <span class="error-field text-right">${error['friend']}</span>
                    </div>
                </div>
                <div class="form-group"> 
                    <div>
                        <button type="submit" value="connect" class="btn btn-default pull-right">Submit</button>
                    </div>
                </div>
            </form>
        </div>                 
    </div>
    
    <form action="addfriend" method='post'>
        <table class="table">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Firstname</th>
                    <th>Last name</th>
                    <th>Validate</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}" >
                    <tr>
                        <td>${user.pseudo}</td>
                        <td>${user.firstname}</td> 
                        <td>${user.name}</td> 
                        <td><a href="addfriend?userId=${user.id}">Add this friend</a></td>
                    </tr> 
                </c:forEach>
            </tbody>
        </table>
    </form>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>