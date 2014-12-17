<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Profile" />
</jsp:include>
        <%@ include file="/WEB-INF/includes/toolbar.jsp" %>
        <div class="container">
            <form method="post" action="profile">
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
                        <span class="error-field text-right">${errorPassword['oldPassword']}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label>New password (twice):</label>
                    <div> 
                        <input type="password" name="newPasswordOne" class="form-control" id="pwd" placeholder="Enter password">
                        <div class="space-between"></div>
                        <input type="password" name="newPasswordTwo" class="form-control" id="pwd" placeholder="Retype the password">
                        <span class="error-field text-right">${errorPassword['newPassword']}</span>
                    </div>
                </div>
                <div class="form-group"> 
                    <div>
                        <button type="submit" value="Sign up" class="btn btn-primary pull-right">Submit</button>
                    </div>
                </div>
            </form>
        </div>
<c:if test="${popup}">
    <script type="text/javascript">
        window.onload = function () {swal("Succes!", "Your informations were changed with a great succes!", "success");};
    </script>
</c:if>
<%@ include file="/WEB-INF/includes/footer.jsp" %>