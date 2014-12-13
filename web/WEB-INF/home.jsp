<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
<%@ include file="/WEB-INF/includes/toolbar.jsp" %>
<div class="jumbotron">
    <div class="container">
        <h1>Welcome on SupMessaging</h1>
        <p>
            SupMessaging is the best way to share your thought with all your friends! <br>
            ${nbUsers} users are registered, ${nbMessages} messages have been written!
        </p>
        <p>
            <a class="btn btn-success" href="${pageContext.request.contextPath}/registration">Register now !</a>
        </p>
    </div>
</div>

<div class="container">
    <div id="ads" class="row">
        <div class="col-md-3">
            <div class="box">
                <a class="thumbnail">
                    <img src="${pageContext.request.contextPath}/static/img/lightning.png" />
                </a>
                <div style="height: 80px">
                    <h2>Lightning Fast!</h2>
                </div>
                <p>Your messages are delivered to your friends as fast as possible.</p>
            </div>
        </div>
        <div class="col-md-3">
            <div class="box">
                <a class="thumbnail">
                    <img src="${pageContext.request.contextPath}/static/img/book.png" />
                </a>
                <div style="height: 80px">
                    <h2>Friends are for life (Or not...)!</h2>
                </div>
                <p>Manage your friendlist as you want : Add or remove friends.</p>
            </div>
        </div>
        <div class="col-md-3">
            <div class="box">
                <a class="thumbnail">
                    <img src="${pageContext.request.contextPath}/static/img/lock.png" />
                </a>
                <div style="height: 80px">
                    <h2>Not NSA Compliant</h2>
                </div>
                <p>We do not sold informations to NSA and other governemental institutions. Send what you want, it doesn't matter!</p>
            </div>
        </div>
        <div class="col-md-3">
            <div class="box">
                <a class="thumbnail">
                    <img src="${pageContext.request.contextPath}/static/img/bubbles.png" />
                </a>
                <div style="height: 80px">
                    <h2>As simple as saying "Hello"</h2>
                </div>
                <p>You just have to register and add your friend then start talking! It can't be so simple!</p>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>