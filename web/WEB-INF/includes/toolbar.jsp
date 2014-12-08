<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<nav class="navbar navbar-inverse navbar-fixed-top" style="border-radius: 0px;">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/SupMessaging/"><span class="gyphicon glyphicon-envelope"> SupMessaging</span></a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active">
                    <a href="/SupMessaging/">
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                    </a>
                </li>
                ${contact}${connect}${registration}${editProfile}${logOut}
            </ul>
        </div>
    </div>
</nav>