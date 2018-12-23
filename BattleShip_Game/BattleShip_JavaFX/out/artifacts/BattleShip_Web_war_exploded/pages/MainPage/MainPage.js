
var gameNameSaver;

console.log('works');
var chatVersion = 0;
var refreshRate = 2000; //mili seconds
var USER_LIST_URL = buildUrlWithContextPath("userslist");
//var CHAT_LIST_URL = buildUrlWithContextPath("chat");

//users = a list of usernames, essentially an array of javascript strings:
// ["moshe","nachum","nachche"...]
function refreshUsersList(users) {
    //clear all current users
    $("#userslist").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(users || [], function (index, username) {
        console.log("Adding user #" + index + ": " + username);
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $('<li>' + username + '</li>').appendTo($("#userslist"));
    });
}
function refreshGamesList(Games) {
    //clear all current users
    $("#gameslist").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(Games || [], function(key, xmlFile) {
        console.log("Adding game #" + key + ": " + xmlFile);

        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $('<option>' + key + '</option>').appendTo($("#gameslist"));
    });

}

//entries = the added chat strings represented as a single string
function appendToChatArea(entries) {
//    $("#chatarea").children(".success").removeClass("success");

    // add the relevant entries
    $.each(entries || [], appendChatEntry);

    // handle the scroller to auto scroll to the end of the chat area
    var scroller = $("#chatarea");
    var height = scroller[0].scrollHeight - $(scroller).height();
    $(scroller).stop().animate({ scrollTop: height }, "slow");
}

function appendChatEntry(index, entry){
    var entryElement = createChatEntry(entry);
    $("#chatarea").append(entryElement).append("<br>");
}

function createChatEntry (entry){
    entry.chatString = entry.chatString.replace (":)", "<span class='smiley'></span>");
    return $("<span class=\"success\">").append(entry.username + "> " + entry.chatString);
}

function ajaxUsersList() {
    $.ajax({
        url: "userslist?method=getUsersList" , //USER_LIST_URL,
        success: function(users) {
            console.log("before callback");
            refreshUsersList(users);
        },
        error: function(error){
            console.log(error);
        }
    });
}

function ajaxGamesList() {

    $.ajax({
        url: "gameslist?method=getGamesList", //USER_LIST_URL,
        success: function (Games) {
            console.log("before callback game list");
            refreshGamesList(Games);
        },
        error: function(error) {
            console.log(error);
        }
    });
}

//call the server and get the chat version
//we also send it the current chat version so in case there was a change
//in the chat content, we will get the new string as well
function ajaxChatContent() {
    $.ajax({
        url: CHAT_LIST_URL,
        data: "chatversion=" + chatVersion,
        dataType: 'json',
        success: function(data) {
            console.log("Server chat version: " + data.version + ", Current chat version: " + chatVersion);
            if (data.version !== chatVersion) {
                chatVersion = data.version;
                appendToChatArea(data.entries);
            }
            triggerAjaxChatContent();
        },
        error: function(error) {
            triggerAjaxChatContent();
        }
    });
}


function triggerAjaxChatContent() {
    setTimeout(ajaxChatContent, refreshRate);
}


//activate the timer calls after the page is loaded
$(function() {
    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);

    //The games list is refreshed automatically every second
    setInterval(ajaxGamesList, refreshRate);

    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    //triggerAjaxChatContent();
});


function StartGame()
{
    $.ajax({
        url: "gameslist",
        data:{
            gamename:gameNameSaver,
            button:"joinGame",
        },
        success: function (isFull) {
            if (isFull == "Full") {
                alert("Full game!");
            } else if (isFull == "Started") {
                alert("Game has started!");
            }
            else {
                window.location.replace("../GamePage/GamePage.html?gameIndex=" + isFull + "&gamename=" + gameNameSaver);
            }
        }
    });
}
function changeName(sel) {
    gameNameSaver = (sel.options[sel.selectedIndex].text);
    document.getElementById("GameName").textContent = "Game Name: " + gameNameSaver;
    getPlayerNameUplaod();
    getGameSize();
    getGameType();
    getPlayerInsideGame();

}

function getPlayerNameUplaod(){
    $.ajax({
        url: "gameslist",
        data:{
            gamename:gameNameSaver,
            button:"PlayerUpload",
        },
        success: function (PlayerUpload) {
            document.getElementById("PlayerUpload").textContent = "Game Creator: " + PlayerUpload;
        },
        error: function(error) {
            console.log(error);
        }
    });
}

function getGameSize(){
    $.ajax({
        url: "gameslist",
        data:{
            gamename:gameNameSaver,
            button:"GameSize",
        },
        success: function (size) {
            document.getElementById("GameSize").textContent = "Board Size: " + size + "x" +size;
        }
    });
}
function getGameType(){
    $.ajax({
        url: "gameslist",
        data:{
            gamename:gameNameSaver,
            button:"GameType",
        },
        success: function (GameType) {
            document.getElementById("GameType").textContent = "Type: " + GameType;
        },
        error: function(error) {
            console.log(error);
        }
    });
}

function getPlayerInsideGame(){
    $.ajax({
        url: "gameslist",
        data:{
            gamename:gameNameSaver,
            button:"PlayerInsideGame",
        },
        success: function (PlayerInsideGame) {
            document.getElementById("PlayerInsideGame").textContent = "Players Number: " + PlayerInsideGame;
            if(PlayerInsideGame ==="Full")
            {
                document.getElementById("StartGame").disabled = "true";
            }
            else
            {
                document.getElementById("StartGame").disabled = false;
            }
        }
    });
}
function logout() {
    $.ajax({
        url: "gameslist",
        data:{
            button:"logout",
        },
        success: function (test) {
            console.log(test);
            window.location.replace("../../index.jsp");
        }
    });
}

function DeleteGame() {
    $.ajax({
        url: "gameslist",
        data:{
            gamename:gameNameSaver,
            button:"DeleteGame"
        },
        success: function (test) {
            confirm(test);
        }
    });
}

