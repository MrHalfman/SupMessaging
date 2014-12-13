window.onload = function () {
    $("#message").on("keydown", function (evt) {
        if (evt.keyCode === 13) {
           evt.preventDefault();
           submitMsg($(this).val());
           $(this).val("");
        }
    });
};

function submitMsg(text) {
    if (!text) return;
    
    var bubbleTpl = $("#bubbleTemplate").clone().removeAttr("id"),
        bubble    = bubbleTpl.find(".bubble");

    bubble.find("p").html(text);
    bubble.find(".date").html(new Date());
    $("#bubbles").append(bubbleTpl);
}