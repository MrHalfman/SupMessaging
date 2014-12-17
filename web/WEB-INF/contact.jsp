<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Contact" />
</jsp:include>
<%@ include file="/WEB-INF/includes/toolbar.jsp" %>
<div class="container"> <br>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Welcome on the Contact page, here you can send a message to the SupMessaging's admin. </h3>
        </div>
        <div class="panel-body">
            <form method="post" action="contact">
                <div class="form-group">
                    <label>Mail :</label>
                    <div>
                        <input type="text" name="email" class="form-control" placeholder="12345@supinfo.com" value="<c:out value="${email}"/>">                          
                        <span class="error-field text-right">${error['email']}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label>Message :</label>
                    <div>
                        <textarea type="text" name="message" rows=10 class="form-control" placeholder="Hi admin...">${message}</textarea>
                        <span class="error-field text-right">${error['message']}</span>
                    </div>
                </div>
                <button type="submit" class="btn btn-default navbar-btn pull-right" >Send Message</button>
            </form>
        </div>
    </div>
</div>
</body>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
