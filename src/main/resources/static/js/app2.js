    /**
        * Created by Sebastian Boreback on 2017-01-17.
        */


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}
var ws;
function connect() {
    console.log("try to connect");
    ws = new WebSocket('ws://localhost:8080/alarms');
    ws.onopen = function (event) {
        console.log("open socket session");
        
    };
    ws.onerror = function (error) {
        console.log("error: " + JSON.stringify(error));
    };
    ws.onmessage = function (data) {
        console.log("got message from server");
        console.log("data:"+data);
        console.log("data2:"+data.data);
        showGreeting(data.data);
    };
    ws.onclose = function (event) {
        console.log("disconnected from ws");
        setConnected(false);
    };
    setConnected(true);
}

function disconnect() {
    if (ws!= null) {
        ws.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    console.log("send data");
    var str ="{ \"magnetSensor\": 1, \"pirSensor\": "+$("#name").val()+"}";
    ws.send(str);
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    console.log("started js");
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});