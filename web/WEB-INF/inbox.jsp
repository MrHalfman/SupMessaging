<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Inbox" />
</jsp:include>
<%@ include file="/WEB-INF/includes/toolbar.jsp" %>
<div class="container" style="height: 100%;">
    <div id="inbox" class="row">
        <div id="messagesList" class="col-md-3">
            <h3>
                Liste des messages 
                <button id="newMessage" title="New Conversation" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span></button>
            </h3>
            <hr>
            <div class="messageItem">
<!--                <strong>Titre</strong> <br>-->
                <em>Blabl balblabl lbalblalb lablblabl</em>
                <div class="informations">
                    <div class="date">
                        Le 42 Août 2045
                    </div>
                    <div class="answers">
                        <span class="badge">42</span> Messages
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <hr>
        </div>
        <div id="conversation" class="col-md-9">
            <h3>Conversation avec GladOS</h3>
            <hr>
            <div id="bubbles">
                <div class="bubble you" value="<c:out value="${corpus}">
                    <p>
                        Hello world!
                    </p>
                    <div class="informations">
                        <div class="author">
                            <b>Par GladOS </b>
                        </div>
                        <div class="date">
                            Le 1 Janvier 2000
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="spacer"></div>
                <div class="bubble me">
                    <p>Plop</p>
                    <div class="informations">
                        <div class="author">
                            <b>Par vous</b>
                        </div>
                        <div class="date">Le 1 Janvier 2000</div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="spacer"></div>
                <div id="bubbleTemplate">
                    <div class="bubble me">
                        <p></p>
                        <div class="informations">
                            <div class="author">
                                <b>Par vous</b>
                            </div>
                            <div class="date"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="spacer"></div>
                </div>
                <div class="spacer"></div>
            </div>
            <div class="clear"></div>
            <div id="input">
                <form>
                    <textarea id="message" placeholder="Enter your message then press Enter..."></textarea>
                </form>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
