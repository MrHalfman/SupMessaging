<%-- 
    Document   : dashboard
    Created on : 1 déc. 2014, 19:50:44
    Author     : Martin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/mycss/style.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/js/bootstrap.min.js" rel="stylesheet"/>
        <title>My dashboard</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/includes/Toolbar.jsp" %>
        <h1>Welcome to your dashboard ${username}!</h1>
    </body>
</html>
