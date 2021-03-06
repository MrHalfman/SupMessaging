<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Log In" />
</jsp:include>
<%@ include file="/WEB-INF/includes/toolbar.jsp" %>
<div class="container"> <br>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Connection</h3>
        </div>
        <div class="panel-body">
            <form method="post" action="connect">
                <div class="form-group">
                    <label>Username :</label>
                    <div>
                        <input type="text" name="username" class="form-control" placeholder="KevinBG14" value="<c:out value="${username}"/>">
                        <span class="error-field text-right">${error['username']}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label>Password :</label>
                    <div> 
                        <input type="password" name="password" class="form-control" id="pwd" placeholder="Enter password">
                        <span class="error-field text-right">${error['password']}</span>
                    </div>
                </div>
                <span class="error-field text-right">${error['connection']}</span>
                <div class="form-group"> 
                    <div>
                        <button type="submit" value="connect" class="btn btn-default pull-right">Submit</button>
                    </div>
                </div>
            </form>
        </div>                 
    </div>


    <div class="form-alert">                           
        <div class="panel-body">
            <a href="registration"> 
                <form method="post" action="registration">
                    <div class="form-group">                  
                        <div class="alert alert-danger" role="alert">
                            <p>If you're not registered, please do it.</p>
                        </div>
                    </div>
                </form>
            </a>
        </div>
    </div>  
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
