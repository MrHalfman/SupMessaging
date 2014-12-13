<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SupMessaging - ${param.title}</title>
        <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet"/>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/img/favicon.ico" />
        <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/static/css/sweet-alert.css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sweet-alert.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/main.js"></script>
        <script type="text/javascript">
            var csrf = "${csrf}";
        </script>
    </head>
    <body>
