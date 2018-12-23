// var IndexPlayer;
// var gameName;
// var boardSize;
// var numberOfmine;
// var refreshRate = 1500
// var arrPlayerBoard;
// var arrAttackBoard;
// var arrPssivlePlace;
// var begin;
// var turnoff = true;
// var turnOn = true;
// var disconnted;
// var won
// var loss
//
//
// $(function () {
//
//     $.ajaxSetup({cache: false});
//     IndexPlayer = getParam('gameIndex');
//     gameName = getParam('gamename');
//     UpdatePlayerName();
//     UpdateTurnNumber();
//     UpdateGameType();
//     UpdatePlayerScore();
//     StartGame();
//     UpdatePlayeStatus();
//     UpdateOpponentStatus();
//     begin = setInterval(StartGame, refreshRate);
// });
//
// function getParam(sParam) {
//     var sPageURL = window.location.search.substring(1);
//     var sURLVariables = sPageURL.split('&');
//     for (var i = 0; i < sURLVariables.length; i++) {
//         var sParameterName = sURLVariables[i].split('=');
//         if (sParameterName[0] == sParam) {
//             return sParameterName[1];
//         }
//     }
// }
//
// $(function () {
//     $.ajax({
//         url: "gameroom?method=getBoardSize&gameName=" + gameName,
//         success: function (size) {
//             boardSize = size;
//             CreateBoards();
//         }
//     })
// })
//
// $(function () {
//     $.ajax({
//         url: "gameroom?method=getNumberOfMine&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (size) {
//             numberOfmine = size;
//             CreateMine();
//         }
//     })
// })
//
// function CreateMine() {
//     for (var j = 0; j < numberOfmine; j++) {
//         var numberOfMine = j.toString();
//         $(' <img id="MineCell" src="../../images/mine.jpg" style="width: 10% ;height: 10%" draggable="true"
//         ondragstart="drag(event)">').appendTo($("#MineStack"));
//     }
// }
//
// function CreateBoards() {
//     CreatPalyerBoard();
//     CreatAttackBoard();
//
// }
//
// function createBattleShipBoard() {
//     for (var y = 0; y < boardSize; y++) {
//
//         $('<br>').appendTo($("#BattleShipBoardCell"));
//
//         for (var x = 0; x < boardSize; x++) {
//             var row = y.toString();
//             var col = x.toString();
//             var value = "value=" + row + "-" + col;
//
//             if (arrPlayerBoard[y][x] != 0) //if cell is not empty
//             {
//                 $('
//                     <button name="BattleCell" ondrop="drop(event,this)" ondragover="allowDrop(event)"' +
//                 'style=" text-align: center; vertical-align: top; font-size: 10px ;background-color: lightgreen; width: 35px; height: 35px; border-width: 3px; padding: 0px;"' +
//                 ' ' + value + ' disabled = "true"></button>').appendTo($("#BattleShipBoardCell"));
//             }
//             else {
//                 $('
//                     <button name="BattleCell" ondrop="drop(event,this)" ondragover="allowDrop(event)"' +
//                 'style=" text-align: center; vertical-align: top ;font-size: 10px ;background-color: lightgray; width: 35px; height: 35px; border-width: 3px; padding: 0px;"' +
//                 ' ' + value + ' disabled = "true"></button>').appendTo($("#BattleShipBoardCell"));
//
//             }
//         }
//     }
//
//     $('<br>').appendTo($("#BattleShipBoardCell"));
// }
//
// function createAttackBoard() {
//
//     for (var y = 0; y < boardSize; y++) {
//
//         $('<br>').appendTo($("#AttackBoardCell"));
//
//         for (var x = 0; x < boardSize; x++) {
//             var row = y.toString();
//             var col = x.toString();
//             var value = "value=" + row + "-" + col;
//
//             $('
//                 <button name="AttackCell" onclick="AttackPlayer(event)"' +
//             'style=" background-color: lightgray; width: 35px; height: 35px; border-width: 3px; padding: 0px;" ' +
//             ' ' + value + ' disabled = "true"></button>').appendTo($("#AttackBoardCell"));
//         }
//     }
//
//     $('<br>').appendTo($("#AttackBoardCell"));
// }
//
// function UpdatePlayerName() {
//     $.ajax({
//         url: "gameroom?method=getPlayerName&gameName=" + gameName,
//         success: function (name) {
//             document.getElementById("PlayerName").textContent = name;
//         }
//     })
// }
//
// function UpdateGameType() {
//     $.ajax({
//         url: "gameroom?method=getGameType&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (GameType) {
//             document.getElementById("GameType").textContent = "Game Type: " + GameType;
//         }
//     })
// }
//
// function UpdateTurnNumber() {
//     $.ajax({
//         url: "gameroom?method=getTurnNumber&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (TurnNumber) {
//             document.getElementById("turnNumber").textContent = "Turn Number: " + TurnNumber;
//         }
//     })
// }
//
// function UpdatePlayerScore() {
//     $.ajax({
//         url: "gameroom?method=getPlayerScore&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (PlayerScore) {
//             document.getElementById("PlayerScore").textContent = "Player Score: " + PlayerScore;
//         }
//     })
// }
//
// function UpdatePlayeStatus() {
//
//     UpdatePlayerBattleShipLeft();
//     UpdateHit();
//     UpdateMiss();
//     UpdateAverageMoveTime();
//
// }
//
// function UpdatePlayerBattleShipLeft() {
//
//     $.ajax({
//         url: "gameroom?method=getPlayerBattleShipLeft&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (BattleShipLeft) {
//             document.getElementById("PlayerBattleShipLeft").textContent = "BattleShip Left: " + BattleShipLeft;
//         }
//     })
// }
//
// function UpdateHit() {
//
//     $.ajax({
//         url: "gameroom?method=getHit&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (Hit) {
//             document.getElementById("Hit").textContent = "Hits: " + Hit;
//         }
//     })
// }
//
// function UpdateMiss() {
//
//     $.ajax({
//         url: "gameroom?method=getMiss&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (Miss) {
//             document.getElementById("Miss").textContent = "Miss: " + Miss;
//         }
//     })
// }
//
// function UpdateAverageMoveTime() {
//
//     $.ajax({
//         url: "gameroom?method=getAverageMoveTime&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (AverageMoveTime) {
//             document.getElementById("AverageMoveTime").textContent = "Average-Time: " + AverageMoveTime;
//         }
//     })
// }
//
// function UpdateOpponentStatus() {
//
//     UpdateOpponentBattleShipLeft();
//     UpdateOpponentScore();
//
// }
//
// function UpdateOpponentBattleShipLeft() {
//
//     $.ajax({
//         url: "gameroom?method=getOpponentBattleShipLeft&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (OpponentBattleShipLeft) {
//             document.getElementById("OpponentBattleShipLeft").textContent = "Opponent BattleShipLeft: " + OpponentBattleShipLeft;
//         }
//     })
// }
//
// function UpdateOpponentScore() {
//
//     $.ajax({
//         url: "gameroom?method=getOpponentScore&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (OpponentScore) {
//             document.getElementById("OpponentScore").textContent = "Opponent Score : " + OpponentScore;
//         }
//     })
// }
//
// function allowDrop(ev) {
//     ev.preventDefault();
// }
//
// function drag(ev) {
//     ev.dataTransfer.setData("text", ev.target.id);
// }
//
// function drop(ev) {
//     ev.preventDefault();
//     var data = ev.dataTransfer.getData("text");
//     ev.target.appendChild(document.getElementById(data));
//     ev.target.style.backgroundImage = "url('../../images/mine.jpg')"; //(document.getElementById(data)).src;
//     ev.target.style.backgroundRepeat = "no-repeat";
//     ev.target.style.backgroundSize = "cover";
//     var index = ev.target.value;
//     var sURLVariables = index.split('-');
//     var x = sURLVariables[0];
//     var y = sURLVariables[1];
//     $.ajax({
//         url: "gameroom?method=addMine&gameName=" + gameName + "&gameindex=" + IndexPlayer + "&x=" + x + "&y=" + y,
//     })
// }
//
// function isMyTurn() {
//     $.ajax({
//         url: "gameroom?method=isMyTurn&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (isMyTurn) {
//             if (isMyTurn.localeCompare("true") == 0) {
//                 document.getElementById("PlayerTurn").textContent = "Your Turn - Attack!";
//                 TurnOnAllButtns();
//
//                 UpdatePlayerScore();
//                 UpdatePlayeStatus();
//                 UpdateOpponentStatus();
//             }
//             else {
//                 document.getElementById("PlayerTurn").textContent = "Enemy Turn - Waiting...";
//                 TurnOffAllButtns();
//
//                 UpdatePlayerScore();
//                 UpdatePlayeStatus();
//                 UpdateOpponentStatus();
//             }
//         }
//     })
// }
//
//
// function AttackPlayer(ev) {
//     var index = ev.target.value;
//     var sURLVariables = index.split('-');
//     var x = sURLVariables[0];
//     var y = sURLVariables[1];
//     $.ajax({
//         url: "gameroom?method=AttackPlayer&gameName=" + gameName + "&gameindex=" + IndexPlayer + "&x=" + x + "&y=" + y,
//         success: function (isMyTurn) {
//             if (isMyTurn.localeCompare("true") == 0) {
//                 document.getElementById("AnimatePic").src = "../../images/hit.gif"
//                 document.getElementById("isHit").textContent = "HIT!!!";
//
//                 ReffesAttackBoard();
//             }
//             else {
//                 HisMine();
//             }
//         }
//     })
// }
//
// function HisMine() {
//
//     $.ajax({
//         url: "gameroom?method=getHitMine&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (Mine) {
//             if (Mine.localeCompare("true") == 0) {
//                 document.getElementById("AnimatePic").src = "../../images/mine.gif"
//                 document.getElementById("isHit").textContent = "You Hit Enemy Mine!!!";
//             }
//             else {
//                 document.getElementById("AnimatePic").src = ".../../images/miss.gif"
//                 document.getElementById("isHit").textContent = "MISS!!!";
//             }
//         }
//     })
// }
//
// function CreatPalyerBoard() {
//
//     $.ajax({
//         url: "gameroom?method=getPlayerBoard&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (bttleShipArray) {
//             arrPlayerBoard = JSON.parse(bttleShipArray);
//             createBattleShipBoard();
//         }
//     })
// }
//
// function CreatAttackBoard() {
//
//     $.ajax({
//         url: "gameroom?method=getAttackBoard&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (AttackBoardArray) {
//             arrAttackBoard = JSON.parse(AttackBoardArray);
//             createAttackBoard();
//         }
//     })
// }
//
// function TurnOffAllButtns() {
//     turnoff = true;
//     var allbuttons = document.getElementsByName("AttackCell");
//     document.getElementById("Quit").disabled = "true";
//     for (var i = 0; i < allbuttons.length; i++) {
//         allbuttons[i].disabled = "true";
// //  if(turnOn)
// //{
//         turnOn = false;
//         UpdateTurnNumber();
//         ReffesAttackBoard();
//         ReffesBattleShipBoard();
// //}
//     }
// }
//
// function TurnOnAllButtns() {
//     turnOn = true;
//     var allbuttons = document.getElementsByName("AttackCell");
//     document.getElementById("Quit").disabled = false;
//     for (var i = 0; i < allbuttons.length; i++) {
//         allbuttons[i].disabled = false;
//
//     }
//
//     UpdateTurnNumber();
//     ReffesAttackBoard();
//     ReffesBattleShipBoard();
//     turnoff = false;
//
// }
//
// function ReffesAttackBoard() {
//     $.ajax({
//         url: "gameroom?method=getAttackBoard&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (bttleShipArray) {
//             arrAttackBoard = JSON.parse(bttleShipArray);
//             RefreshWithattackBoard();
//         }
//     })
// }
//
// function ReffesBattleShipBoard() {
//     $.ajax({
//         url: "gameroom?method=getPlayerBoard&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (bttleShipArray) {
//             arrPlayerBoard = JSON.parse(bttleShipArray);
//             RefreshWithBattleShipBoard();
//         }
//     })
// }
//
// function RefreshWithattackBoard() {
//
//     var allbuttons = document.getElementsByName("AttackCell");
//     for (var y = 0; y < (arrAttackBoard.length); y++) {
//         for (var x = 0; x < (arrAttackBoard.length); x++) {
//
//             var buttonIndex = y * arrAttackBoard.length + x;
//
//             if(arrAttackBoard[y][x] != 0)
//             {
//                 allbuttons[buttonIndex].disabled = true;//button disable //todo:*****************
//
//                 if(arrAttackBoard[y][x] == -2)//destroyd
//                 {
//                     allbuttons[buttonIndex].style.background = "darkred";
//                 }
//                 else if(arrAttackBoard[y][x] == -1)//miss
//                 {
//                     allbuttons[buttonIndex].style.background = "dimgray";
//                 }
//             }
//         }
//     }
// }
//
// function RefreshWithBattleShipBoard() {
//
//     $.ajax({
//         url: "gameroom?method=getPosiblePlaceForMine&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (possiblePlaceArray) {
//             arrPssivlePlace = JSON.parse(possiblePlaceArray);
//             setBoard();
//         }
//     })
// }
//
// function setBoard() {
//     var allbuttons = document.getElementsByName("BattleCell");
//     for (var y = 0; y < (arrPlayerBoard.length); y++) {
//         for (var x = 0; x < (arrPlayerBoard.length); x++) {
//
//             var buttonIndex = y * arrPlayerBoard.length + x;
//
//             allbuttons[buttonIndex].innerText = arrPlayerBoard[y][x];//todo ------- delete!
//
//             if(arrPlayerBoard[y][x] != 0) //not empty //todo::::::::::::::::::
//             {
//                 if (arrPlayerBoard[y][x] == -2)//destryoyed
//                 {
// //  allbuttons[buttonIndex].innerText = arrPlayerBoard[y][x];//todo ------- delete!
//                     allbuttons[buttonIndex].style.background = "indianred";
//                 }
//                 else if(arrPlayerBoard[y][x] == -1)
//                 {
//                     allbuttons[buttonIndex].style.background = "drakgrey";
//                 }
//
//             }
//             if(arrPssivlePlace[y][x] == 'F')//Not Valid Mine Place
//             {
//                 allbuttons[buttonIndex].allowDrop = function (){return false;};
//             }
//
// // if(arrPlayerBoard[i][j] != 0 && arrPlayerBoard[i][j] != -3)
// // {
// //     allbuttons[i*arrPlayerBoard.length + j].innerText = arrPlayerBoard[i][j] ;
// //
// // }
// // if(arrPssivlePlace[i][j] == 'F')
// // {
// //     allbuttons[i*arrPlayerBoard.length + j].allowDrop = function (){return false;};
// // }
//
//         }
//     }
// }
//
// function QuitGame() {
//     $.ajax({
//         url: "gameroom?method=QuitGame&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (test) {
//             console.log(test);
//             window.location.replace("../MainPage/MainPage.jsp");
//         }
//     })
// }
//
// function PlayerDisconnect() {
//     $.ajax({
//         url: "gameroom?method=OtherPlayerInTheGame&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (test) {
//             if (test.localeCompare("true") != 0) {
//
//                 var answer = confirm("Other Player Disconnect From The Game...");
//                 if (answer) {
//                     QuitGame();
//                     stopAllInterval();
//                 }
//             }
//
//         }
//     })
// }
//
// function StartGame() {
//     $.ajax({
//         url: "gameroom?method=StartGame&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (test) {
//             if (test.localeCompare("true") != 0) {
//                 document.getElementById("PlayerTurn").textContent = "Waiting To Other Player";
//                 TurnOffAllButtns();
//             }
//             else {
//                 setInterval(isMyTurn, refreshRate);
//                 disconnted = setInterval(PlayerDisconnect, refreshRate);
//                 won = setInterval(CheckIfPlayerWon, refreshRate);
//                 loss = setInterval(CheckIfOtherPlayerWon, refreshRate);
//                 clearInterval(begin);
//             }
//
//         }
//     })
// }
//
// function stopAllInterval() {
//     clearInterval(disconnted);
//     clearInterval(won);
//     clearInterval(loss);
// }
//
// function CheckIfPlayerWon() {
//     $.ajax({
//         url: "gameroom?method=CheckIfSomeOneWon&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (test) {
//             if (test.localeCompare("true") == 0) {
//                 var answer = confirm("Well Done, You Are The Winner!!!");
//                 if (answer) {
//                     QuitGame();
//                     stopAllInterval();
//                 }
//
//             }
//         }
//     })
// }
//
// function CheckIfOtherPlayerWon() {
//     $.ajax({
//         url: "gameroom?method=CheckIfOtherPlayerWon&gameName=" + gameName + "&gameindex=" + IndexPlayer,
//         success: function (test) {
//             if (test.localeCompare("true") == 0) {
//                 var answer = confirm("You Lost The Game... Try Again!");
//                 if (answer) {
//                     QuitGame();
//                     stopAllInterval();
//                 }
//             }
//         }
//     })
// }
//
