<%-- 
    Document   : Connection
    Created on : 1 déc. 2014, 22:11:29
    Author     : Martin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/mycss/style.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/js/bootstrap.min.js" rel="stylesheet"/>
        <title>Connection Page</title>
    </head>
    <body>
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
    </body>
</html>