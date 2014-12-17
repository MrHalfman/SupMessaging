window.onload = function () {
    $("#message").on("keydown", function (evt) {
        if (evt.keyCode === 13) {
           evt.preventDefault();
           submitMsg($(this).val());
           $(this).val("");
        }
    });
    
    $(".messageItem").on("click", function () {
        window.location = window.location.protocol + "//" + window.location.host + window.location.pathname +  "?conversationId=" + $(this).data("uid");
    });
};

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