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
                    <strong class="nickname">${conversation.getPseudo()}</strong>
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
            <h3 class='<c:if test="${messagesList.isEmpty()}">noMsg</c:if>'>Conversation avec <span id="messageReceiver"></span></h3>
            <hr>
            <div id="bubbles">
                <c:if test="${!messagesList.isEmpty()}">
                <c:forEach items="${messagesList}" var="message">
                    <div class="bubble <c:choose><c:when test="${message.getIdUserAuthor() == userid}">me</c:when><c:otherwise>you</c:otherwise></c:choose>">
                                <p>
                            ${message.getCorpus()}
                        </p>
                        <div class="informations">
                            <div class="author">
                                <b>Par <c:choose><c:when test="${message.getIdUserAuthor() == userid}">vous</c:when><c:otherwise>${message.getAuthorName()}</c:otherwise></c:choose></b>
                                    </div>
                                    <div class="date">
                                ${message.getDateMessage()}
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="spacer"></div>
                </c:forEach>
                </c:if>
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
