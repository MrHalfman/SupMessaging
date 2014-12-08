<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<nav class="navbar navbar-inverse navbar-fixed-top" style="border-radius: 0px;">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/SupMessaging/"><span class="gyphicon glyphicon-envelope"> SupMessaging</span></a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <c:forEach items="${links}" var="link">
                    <li class="${link.value.get(0)}">
                        <a href="${link.value.get(1)}">${link.value.get(2)}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</nav>