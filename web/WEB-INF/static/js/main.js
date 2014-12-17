window.onload = function () {
    $("#messageReceiver").html($(".selected").find(".nickname").html());
    
    $("#message").on("keydown", function (evt) {
        if (evt.keyCode === 13) {
           evt.preventDefault();
           submitMsg($(this).val());
           $(this).val("");
        }
    });
    
    $(".messageItem").on("click", function () {
        if ($(this).parent().attr("id") !== "newMessageSelect") {
            selectConversation($(this).data("uid"));
        }
    });
    
    $("#newMessage").on("click", function () {
        toggleNewMessageSelect();
    });
    
    $("#newMessageSelect").on("change", function () {
        var select = $(this).find("select"),
            option = select.find("option[value='" + select.val() + "']");
        createConversation(select.val(), option.html());
        toggleNewMessageSelect();
    });
};

function selectConversation(id) {
    window.location = window.location.protocol + "//" + window.location.host + window.location.pathname +  "?conversationId=" + id;
}

function toggleNewMessageSelect() {
    $(this).find("span").toggleClass("glyphicon-plus");
    $(this).find("span").toggleClass("glyphicon-minus");
    $("#newMessageSelect").slideToggle();
}

function createConversation(id, nickname) {
    if ($(".messageItem[data-uid='" + id + "']").length !== 0) {
        selectConversation(id);
    } else {
        var template = $("#messageItemTemplate").clone().removeAttr("id"),
            msgItem = template.find(".messageItem");
    
        $(".selected").removeClass("selected");
        $(".noMsg").removeClass("noMsg");
        msgItem.data("uid", id);
        msgItem.find(".nickname").html(nickname);
        msgItem.addClass("selected");
        template.insertBefore("#newMessageSelect");
        $("#messageReceiver").html($(".selected").find(".nickname").html());
        var tmp = $("#bubbleTemplate").clone();
        $("#bubbles").html("");
        $("#bubbles").append(tmp);
    }
    
}

function submitMsg(text) {
    if (!text) return;
    
    var bubbleTpl = $("#bubbleTemplate").clone().removeAttr("id"),
        bubble    = bubbleTpl.find(".bubble");

    bubble.find("p").html(text);
    bubble.find(".date").html(new Date());
    $("#bubbles").append(bubbleTpl);
    $.post(window.location, {
       receiver: $(".selected").data("uid"),
       message: text
    });
}