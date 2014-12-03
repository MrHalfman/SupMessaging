<%-- 
    Document   : Registration
    Created on : 2 déc. 2014, 18:59:51
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
        <title>Registration</title>
    </head>
    <body>
        <%@ include file="includes/Toolbar.jsp" %>
        <div class="panel panel-primary form-style">
            <div class="panel-heading">
                <h3 class="panel-title">Connection</h3>
            </div>
            <div class="panel-body">
                <form method="post" action="connect">
                    <div class="form-group">
                        <label>Username :</label>
                        <div>
                            <input type="text" name="username" class="form-control" placeholder="KevinBG14">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>First name :</label>
                        <div>
                            <input type="text" name="username" class="form-control" placeholder="Johnny">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Last name :</label>
                        <div>
                            <input type="text" name="username" class="form-control" placeholder="BeGood">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Email :</label>
                        <div>
                            <input type="text" name="username" class="form-control" placeholder="example@example.com">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>Password :</label>
                        <div> 
                            <input type="password" name="password" class="form-control" id="pwd" placeholder="Enter password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Retype the password :</label>
                        <div> 
                            <input type="password" name="password" class="form-control" id="pwd" placeholder="Enter the password twice">
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
