<%-- 
    Document   : Profile
    Created on : 7 déc. 2014, 12:11:53
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
        <title>My Profile</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/includes/Toolbar.jsp" %>
        <div class="form-style">
            <form method="post" action="registration">
                <h2>Edit your informations</h2>
                <p>
                    If you want to correct somes informations, please insert your new informations. <br />
                    Note that your username isn't alterable.
                </p>
                <div class="space-between"></div>
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
                
                <div class="space-between"></div>
                <hr>    
                <h2>Change your password</h2>
                <p> Thank you enter your old password before informing twice your new password </p>
                <div class="space-between"></div>
                
                <div class="form-group">
                    <label>Old password :</label>
                    <div>
                        <input type="password" name="oldPassword" class="form-control" id="pwd" placeholder="Enter your old password">
                        <span class="error-field text-right">${error['oldPassword']}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label>New password (twice):</label>
                    <div> 
                        <input type="password" name="passwordOne" class="form-control" id="pwd" placeholder="Enter password">
                        <div class="space-between"></div>
                        <input type="password" name="passwordTwo" class="form-control" id="pwd" placeholder="Enter the password twice">
                        <span class="error-field text-right">${error['newPassword']}</span>
                    </div>
                </div>
                <div class="form-group"> 
                    <div>
                        <button type="submit" value="Sign up" class="btn btn-default pull-right">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
