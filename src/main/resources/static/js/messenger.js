



window.addEventListener("load", function(){

    let btn1 = document.getElementById("inboxBtn");
    let btn2 = document.getElementById("composeBtn");
    let btn3 = document.getElementById("sentMessagesBtn");


    btn1.addEventListener("click", function() {
             document.getElementById("compose").style.visibility = "hidden";
             document.getElementById("sentMessages").style.visibility = "hidden";
             document.getElementById("inbox").style.visibility = "visible";
        });

    btn2.addEventListener("click", function() {
             document.getElementById("inbox").style.visibility = "hidden";
             document.getElementById("sentMessages").style.visibility = "hidden";
             document.getElementById("compose").style.visibility = "visible";
        });

    btn3.addEventListener("click", function() {
                document.getElementById("inbox").style.visibility = "hidden";
                document.getElementById("compose").style.visibility = "hidden";
                document.getElementById("sentMessages").style.visibility = "visible";
           });

});

