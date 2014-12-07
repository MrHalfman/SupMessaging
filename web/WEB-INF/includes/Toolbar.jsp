<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="nav nav-tabs">

    <li role="presentation" class="active">
        <a href="${home}">
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
        </a>
    </li>
    ${contact}
    ${connect}
    ${registration}
    ${logOut}
</ul>
