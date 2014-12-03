<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/mycss/style.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/js/bootstrap.min.js" rel="stylesheet"/>
        <title>Contact</title>
    </head>
    <body>   
        <%@ include file="/WEB-INF/includes/Toolbar.jsp" %>
        <div class="panel panel-primary form-style">
            <div class="panel-heading">
                <h5> Welcome on the Contact page, here you can send a message to the SupMessaging's admin. </h5>
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
    </body>
</html>
