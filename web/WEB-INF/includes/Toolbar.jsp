<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>    
        <ul class="nav nav-tabs">
            
            <li role="presentation" class="active">
                <a href="${home}">
                    <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                </a>
            </li>
            ${contact}
            ${connect}
            ${registration}
            ${editProfile}
            ${logOut}
       </ul>
   </body>
</html>
