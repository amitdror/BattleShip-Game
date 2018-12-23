var EMPTY = 0;
var MISS = -1;
var DESTROYED = -2;
var MINE = -3;
var HIT = 1;

var m_RefreshRate = 1000;
var m_BeginInterval;
var m_WinnerInterval;
var m_LoserInterval;
var m_DisconnectedInterval;

var m_GameIndex;
var m_GameName;
var m_GameStart = false;

var m_BoardSize;
var m_NumberOfMines;
var m_ShipBoard;
var m_HitBoard;

//init page
$(function () {

    $.ajaxSetup({cache: false});
    m_GameIndex = getParam('gameIndex');
    m_GameName = getParam('gamename');
    GetBoardSize()

    UpdateGame();
    StartGame();
    m_BeginInterval = setInterval(StartGame, m_RefreshRate);
});

function getParam(i_Param) {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == i_Param) {
            return sParameterName[1];
        }
    }
}
function GetBoardSize() {
    $.ajax({
        url: "gameroom?method=getBoardSize&gameName=" + m_GameName,
        success: function (i_BoardSize) {
            m_BoardSize = i_BoardSize;
        }
    })
}

function StartGame() {
    $.ajax({
        url: "gameroom?method=StartGame&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_isGameStart) {
            if (i_isGameStart.localeCompare("true") != 0) {
                TurnOffAllButtons();
            }
            else { //opponent connected
                setInterval(isMyTurn, m_RefreshRate);
                m_DisconnectedInterval = setInterval(PlayerDisconnect, m_RefreshRate);
                m_WinnerInterval = setInterval(CheckIfPlayerWon, m_RefreshRate);
                m_LoserInterval = setInterval(CheckIfOpponentPlayerWon, m_RefreshRate);
                clearInterval(m_BeginInterval);
            }

        }
    })
}

function isMyTurn() {
    $.ajax({
        url: "gameroom?method=isMyTurn&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (isMyTurn) {

            if (isMyTurn.localeCompare("true") == 0) //player turn
            {
                document.getElementById("TurnInfo").textContent = "Your Turn - Attack!";
                TurnOnAllButtons();  //todo: update all???
            }
            else { //opponent turn
                document.getElementById("TurnInfo").textContent = "Opponent Turn...";
                TurnOffAllButtons(); //todo: update all???
            }
        }
    })
}
function CheckIfPlayerWon() {
    $.ajax({
        url: "gameroom?method=CheckIfSomeOneWon&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_Winner) {
            if (i_Winner.localeCompare("true") == 0) {
                var answer = confirm("Well Done, You Are The Winner!!!");
                if (answer) {
                    QuitGame();
                    stopAllInterval();
                }

            }
        }
    })
}
function CheckIfOpponentPlayerWon() {
    $.ajax({
        url: "gameroom?method=CheckIfOtherPlayerWon&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_Loser) {
            if (i_Loser.localeCompare("true") == 0) {
                var answer = confirm("You Lost The Game... Try Again!");
                if (answer) {
                    QuitGame();
                    stopAllInterval();
                }
            }
        }
    })
}
function PlayerDisconnect() {
    $.ajax({
        url: "gameroom?method=OtherPlayerInTheGame&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (test) {
            if (test.localeCompare("true") != 0) {

                var answer = confirm("Your Opponent Disconnected From The Game...");
                if (answer) {
                    QuitGame();
                    stopAllInterval();
                }
            }

        }
    })
}
function stopAllInterval() {
    clearInterval(m_DisconnectedInterval);
    clearInterval(m_WinnerInterval);
    clearInterval(m_LoserInterval);
}
function QuitGame() {
    $.ajax({
        url: "gameroom?method=QuitGame&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (test) {
            console.log(test);
            window.location.replace("../MainPage/MainPage.jsp");
        }
    })
}

function TurnOnAllButtons() {


    UpdateGame();
}
function TurnOffAllButtons() {

    m_GameStart = false;

    var hitButtons = document.getElementsByName("HitTile");
    var hitButtonsNumber = hitButtons.length;

    for (var i = 0; i < hitButtonsNumber; i++) {
        hitButtons[i].disabled = "true";
    }

    var mines = document.getElementsByName("MineTile");
    var minesNumber = mines.length;

    for (var j = 0; j < minesNumber; j++) {
        mines[j].disabled = "true";
    }

}



///////UpdateGame////////
function UpdateGame()
{
    UpdateGameStatus();
    debugger;
    UpdatePlayerStatus();
    debugger;
    UpdateOpponentStatus();
    debugger;
    UpdateBoards()
    debugger;
    UpdateMines();
    debugger;
}
/////////////////////////


/////UpdateBoards
function UpdateBoards() {
    CreateShipBoard();
    CreateHitsBoard();
}

function CreateShipBoard() {

    $.ajax({
        url: "gameroom?method=getPlayerBoard&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_ShipBoardArray) {
            m_ShipBoard = JSON.parse(i_ShipBoardArray);
            buildShipBoard();
        }
    })
}
function CreateHitsBoard() {

    $.ajax({
        url: "gameroom?method=getAttackBoard&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_HitBoardArray) {
            m_HitBoard = JSON.parse(i_HitBoardArray);
            buildHitBoard();
        }
    })
}
function buildShipBoard() {

    $("#ShipBoard").html('');

    for (var y = 0; y < m_BoardSize; y++) {
        $('<br>').appendTo($("#ShipBoard"));

        for (var x = 0; x < m_BoardSize; x++) {

            var xPos = x.toString();
            var yPos = y.toString();
            var position = "value=" + xPos + "-" + yPos;


            if (m_ShipBoard[y][x] == EMPTY) {

                $('<button name="ShipTile" ' + position + ' class="tile" style="background-color: lightgray" ondrop="drop(event)" ondragover="allowDrop(event)" disabled="true"></button>').appendTo($("#ShipBoard"));

            }
            else if(m_ShipBoard[y][x] == MISS){

                $('<button name="ShipTile" ' + position + ' class="tile" style="background-color: darkgray" ondragover="allowDrop(event)" disabled="true"</button>').appendTo($("#ShipBoard"));
            }
            else if (m_ShipBoard[y][x] == DESTROYED) {

                $('<button name="ShipTile" ' + position + ' class="tile" style="background-color: indianred" ondragover="allowDrop(event)" disabled="true"</button>').appendTo($("#ShipBoard"));
            }
            else if (m_ShipBoard[y][x] == MINE) {

                $('<button name="ShipTile" ' + position + ' class="tile" style="background-color: cornflowerblue" ondragover="allowDrop(event)" disabled="true"</button>').appendTo($("#ShipBoard"));
            }
            else//is Ship Part
            {

                $('<button name="ShipTile" ' + position + ' class="tile" style="background-color: lightgreen" ondrop="DropEvent(event,this)" ondragover="allowDrop(event)" disabled="true"</button>').appendTo($("#ShipBoard"));
            }
            //
            // if (arrPlayerBoard[y][x] != 0) //if cell is not empty
            // {
            //     $('<button name="BattleCell" ondrop="drop(event,this)" ondragover="allowDrop(event)"' +
            //         'style=" text-align: center; vertical-align: top; font-size: 10px ;background-color: lightgreen; width: 35px; height: 35px; border-width: 3px; padding: 0px;"' +
            //         ' ' + value + ' disabled = "true"></button>').appendTo($("#BattleShipBoardCell"));
            // }
            // else {
            //     $('<button name="BattleCell" ondrop="drop(event,this)" ondragover="allowDrop(event)"' +
            //         'style=" text-align: center; vertical-align: top ;font-size: 10px ;background-color: lightgray; width: 35px; height: 35px; border-width: 3px; padding: 0px;"' +
            //         ' ' + value + ' disabled = "true"></button>').appendTo($("#BattleShipBoardCell"));
            //
            // }
        }
    }
    $('<br>').appendTo($("#ShipBoard"));
}
function buildHitBoard() {

    $("#HitBoard").html('');
    debugger;
    for (var y = 0; y < m_BoardSize; y++) {
        $('<br>').appendTo($("#HitBoard"));

        for (var x = 0; x < m_BoardSize; x++) {

            var xPos = x.toString();
            var yPos = y.toString();
            var position = "value=" + xPos + "-" + yPos;

            if(m_HitBoard[y][x] == EMPTY)
            {
                $('<button name="HitTile" ' + position + ' class="tile" onclick="AttackPlayerEvent(event)"></button>').appendTo($("#HitBoard"));
            }
            else if(m_HitBoard[y][x] == MISS)
            {
                $('<button name="HitTile" ' + position + ' class="tile" style="background-color: darkgray" disabled="true"></button>').appendTo($("#HitBoard"));
            }
            else if(m_HitBoard[y][x] == DESTROYED || m_HitBoard[y][x] == MINE)
            {
                $('<button name="HitTile" ' + position + ' class="tile" style="background-color: indianred" disabled="true"></button>').appendTo($("#HitBoard"));
            }


            // $('<button name="HitTile" class="tile" onclick="AttackPlayer(event)" disabled = "true"></button>').appendTo($("#HitBoard"));
        }
    }

    $('<br>').appendTo($("#HitBoard"));
}
/////


/////UpdateGameStatus
function UpdateGameStatus() {
    UpdateTurnNumber();
}

function UpdateTurnNumber() {
    $.ajax({
        url: "gameroom?method=getTurnNumber&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_TurnNumber) {
            document.getElementById("TurnNumber").textContent = "Turn Number: " + i_TurnNumber;
        }
    })
}
/////


/////UpdatePlayerStatus
function UpdatePlayerStatus() {

    UpdatePlayerName();
    UpdatePlayerScore();
    UpdateHit();
    UpdateMiss();
    UpdateAverageMoveTime();
    UpdatePlayerBattleShipLeft();

}

function UpdatePlayerName() {
    $.ajax({
        url: "gameroom?method=getPlayerName&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_PlayerName) {
            document.getElementById("PlayerName").textContent = "Name: " + i_PlayerName;
        }
    })
}
function UpdatePlayerScore() {
    $.ajax({
        url: "gameroom?method=getPlayerScore&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_PlayerScore) {
            document.getElementById("PlayerScore").textContent = "Score: " + i_PlayerScore;
        }
    })
}
function UpdateHit() {

    $.ajax({
        url: "gameroom?method=getHit&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_Hit) {
            document.getElementById("PlayerHit").textContent = "Hits: " + i_Hit;
        }
    })
}
function UpdateMiss() {

    $.ajax({
        url: "gameroom?method=getMiss&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_Miss) {
            document.getElementById("PlayerMiss").textContent = "Miss: " + i_Miss;
        }
    })
}
function UpdateAverageMoveTime() {

    $.ajax({
        url: "gameroom?method=getAverageMoveTime&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_AverageMoveTime) {
            document.getElementById("PlayerAvgTime").textContent = i_AverageMoveTime;
        }
    })
}
function UpdatePlayerBattleShipLeft() {

    $.ajax({
        url: "gameroom?method=getPlayerBattleShipLeft&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_ShipsDetails) {
            document.getElementById("PlayerShips").textContent = "Ships: " + i_ShipsDetails;
        }
    })
}
/////


/////UpdateOpponentStatus
function UpdateOpponentStatus() {

    UpdateOpponentName();

    UpdateOpponentScore();

    UpdateOpponentHit();

    UpdateOpponentMiss();

    UpdateOpponentAverageMoveTime();

    UpdateOpponentBattleShipLeft();


}

function UpdateOpponentName(){
    $.ajax({
        url: "gameroom?method=getOpponentName&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_OpponentName) {
            document.getElementById("OpponentName").textContent = "Name: " + i_OpponentName;
        }
    })
}
function UpdateOpponentScore() {

    $.ajax({
        url: "gameroom?method=getOpponentScore&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_OpponentScore) {
            if(i_OpponentScore != null) {
                document.getElementById("OpponentScore").textContent = "Score: " + i_OpponentScore;
            }
        }
    })
}
function UpdateOpponentHit(){

    $.ajax({
        url: "gameroom?method=getOpponentHit&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_OpponentHit) {
            document.getElementById("OpponentHits").textContent = "Hits: " + i_OpponentHit;
        }
    })
}
function UpdateOpponentMiss(){
    $.ajax({
        url: "gameroom?method=getOpponentMiss&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_OpponentMiss) {
            document.getElementById("OpponentMiss").textContent = "Miss: " + i_OpponentMiss;
        }
    })
}
function UpdateOpponentAverageMoveTime(){
    $.ajax({
        url: "gameroom?method=getOpponentAverageMoveTime&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_AverageMoveTime) {
            document.getElementById("OpponentAvgTime").textContent = i_AverageMoveTime;
        }
    })
}
function UpdateOpponentBattleShipLeft() {

    $.ajax({
        url: "gameroom?method=getOpponentBattleShipLeft&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_OpponentShips) {
            document.getElementById("OpponentShips").textContent = "Ships: " + i_OpponentShips;
        }
    })
}
/////


/////UpdateMines
function UpdateMines() {
    $.ajax({
        url: "gameroom?method=getNumberOfMine&gameName=" + m_GameName + "&gameindex=" + m_GameIndex,
        success: function (i_MinesNumber) {
            m_NumberOfMines = i_MinesNumber;
            CreateMine();
        }
    })
}

function CreateMine() {

    $("#Mines").html('');

    for (var i = 0; i < m_NumberOfMines; i++)
    {
        //$('<img id="MineTile" src="../../images/mine.jpg" style="width:7%; height: 7%" draggable="true" ondragstart="drag(event)">').appendTo($("#Mines"));
        $('<button id=MineTile name="MineTile" class="tile" style="background-color: cornflowerblue" draggable="true" ondragstart="drag(event)"></button>').appendTo($("#Mines"));
    }
}
/////


/////Events
function allowDrop(ev) {
    ev.preventDefault();

}

function drag(ev) {
    //ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    //ev.preventDefault();
    // var data = ev.dataTransfer.getData("text");
    // ev.target.appendChild(document.getElementById(data));
    // ev.target.style.backgroundColor = "blue";
    var index = ev.target.value;
    var sURLVariables = index.split('-');
    var x = sURLVariables[0];
    var y = sURLVariables[1];

    $.ajax({
        url: "gameroom?method=addMine&gameName=" + m_GameName + "&gameindex=" + m_GameIndex + "&x=" + x + "&y=" + y,
        success: function(i_MineAdded)
        {
            if(i_MineAdded){
               // ev.target.style.backgroundColor = "blue";
            }
        }
    })
}

function AttackPlayerEvent(event) {

    var position = event.target.value;
    var sURLVariables = position.split('-');
    var x = sURLVariables[0];
    var y = sURLVariables[1];
    $.ajax({
        url: "gameroom?method=AttackPlayer&gameName=" + m_GameName + "&gameindex=" + m_GameIndex + "&x=" + x + "&y=" + y,
        success: function (i_AttackResult) {
            if (i_AttackResult == HIT)
            {
                document.getElementById("AttackResult").textContent = "HIT!!!";
            }
            else if(i_AttackResult == MINE){
                document.getElementById("AttackResult").textContent = "You Hit A MINE!";
                debugger;
            }
            else if(i_AttackResult == MISS){
                document.getElementById("AttackResult").textContent = "MISS!!!";
            }

            UpdateGame();
        }
    })
}
