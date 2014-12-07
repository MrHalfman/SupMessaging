<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Log In" />
</jsp:include>
        <%@ include file="/WEB-INF/includes/Toolbar.jsp" %>
        <div class="panel panel-primary form-style">
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
                    <div class="form-group"> 
                        <div>
                            <button type="submit" value="connect" class="btn btn-default pull-right">Submit</button>
                        </div>
                    </div>
                </form>
            </div>                 
        </div>
        
        <a href="registration">                
            <div class="form-alert">                           
                <div class="panel-body">
                    <form method="post" action="registration">
                        <div class="form-group">
                            <div class="form-group">                       
                                <div class="alert alert-danger" role="alert">
                                    <p>If you're not registered please do it</p>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>  
        </a>                 
<%@ include file="/WEB-INF/includes/footer.jsp" %>
