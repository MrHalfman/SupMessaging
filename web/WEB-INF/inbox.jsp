<jsp:include page="/WEB-INF/includes/headers.jsp">
    <jsp:param name="title" value="Inbox" />
    <jsp:param name="csrf" value="${csrf}" />
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
            <div id="newMessageSelect">
                <div class="messageItem">
                    <select class="form-control" id="newMsgDest">
                        <option>Destinataire</option>
                    <c:forEach items="${friends}" var="friend">
                        <option value="${friend.getId()}">${friend.getPseudo()}</option>
                    </c:forEach>
                    </select>
                </div>
                <hr>
            </div>
            <c:forEach items="${conversationsList}" var="conversation">
                <div class="messageItem <c:if test="${conversation.getId() == actualConversation}">selected</c:if>" data-uid="${conversation.getId()}">
                    <strong class="nickname">${conversation.getPseudo()}</strong><!-- <br>
                    <em>Lorem Ipsum dolor sit amet</em>
                    <div class="informations">
                        <div class="date">
                            Le xx xxxxxxx xxxx
                        </div>
                        <div class="answers">
                            <span class="badge">x</span> Messages
                        </div>
                        <div class="clear"></div>
                    </div>-->
                </div>
                <hr>
            </c:forEach>
                <div id="messageItemTemplate">
                    <div class="messageItem">
                        <strong class="nickname"></strong>
                    </div>
                    <hr>
                </div>
        </div>
        <div id="conversation" class="col-md-9">
            <c:choose >
                <c:when test="${messagesList.isEmpty()}">
                    <h1>Merci de sélectionner une conversation.</h1>
                </c:when>
                <c:otherwise>
                    
            <h3>Conversation avec <span id="messageReceiver"></span></h3>
            <hr>
            <div id="bubbles">
                <img class="spinner" width="100%" height="32" src="/SupMessaging/static/img/loading-cylon-red.svg" />
                
                <c:forEach items="${messagesList}" var="message">
                    <div class="bubble <c:choose><c:when test="${message.getIdUserAuthor() == userid}">me</c:when><c:otherwise>you</c:otherwise></c:choose>">
                        <p>
                            ${message.getCorpus()}
                        </p>
                        <div class="informations">
                            <div class="author">
                                <b>Par <c:choose><c:when test="${message.getIdUserAuthor() == userid}">vous</c:when><c:otherwise>${message.getAuthorName(message.getIdUserAuthor(), message.getMail())}</c:otherwise></c:choose></b>
                            </div>
                            <div class="date">
                                ${message.getDateMessage()}
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="spacer"></div>
                </c:forEach>
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
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
