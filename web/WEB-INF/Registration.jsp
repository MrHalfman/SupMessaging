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
        <%@ include file="/WEB-INF/includes/Toolbar.jsp" %>
        <div class="panel panel-primary form-style">
            <div class="panel-heading">
                <h3 class="panel-title">Registration</h3>
            </div>
            <div class="panel-body">
                <form method="post" action="registration">
                    <div class="form-group">
                        <label>Username :</label>
                        <div>
                            <input type="text" name="username" class="form-control" placeholder="KevinBG14" value="<c:out value="${username}"/>">
                            <span class="error-field text-right">${error['username']}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>First name :</label>
                        <div>
                            <input type="text" name="firstName" class="form-control" placeholder="Johnny" value="<c:out value="${firstName}"/>">
                            <span class="error-field text-right">${error['firstName']}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Last name :</label>
                        <div>
                            <input type="text" name="lastName" class="form-control" placeholder="BeGood" value="<c:out value="${lastName}"/>">
                            <span class="error-field text-right">${error['lastName']}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Email :</label>
                        <div>
                            <input type="text" name="email" class="form-control" placeholder="example@example.com" value="<c:out value="${email}"/>">
                            <span class="error-field text-right">${error['email']}</span>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>Password (twice):</label>
                        <div> 
                            <input type="password" name="passwordOne" class="form-control" id="pwd" placeholder="Enter password">
                            <div class="space-between"></div>
                            <input type="password" name="passwordTwo" class="form-control" id="pwd" placeholder="Enter the password twice">
                            <span class="error-field text-right">${error['password']}</span>
                        </div>
                    </div>
                    <div class="form-group"> 
                        <div>
                            <button type="submit" value="Sign up" class="btn btn-default pull-right">Submit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
