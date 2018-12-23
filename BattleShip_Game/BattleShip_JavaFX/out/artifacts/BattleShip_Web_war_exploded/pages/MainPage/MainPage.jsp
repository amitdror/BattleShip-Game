<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <%@page import="utils.*" %>
            <!-- Required meta tags -->
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">

            <title>Battle-Ship Game</title>

            <!-- Bootstrap CSS -->
            <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">

            <!-- jQuery first, then Bootstrap JS -->
            <script type="text/javascript" src="../../jquery/jquery-3.2.1.min.js"></script>
            <script src="../../jquery/context-path-helper.js"></script>
            <script type="text/javascript" src="../../bootstrap/js/bootstrap.min.js"></script>
            <script type="text/javascript" src="MainPage.js"></script>

            <style>
                body {
                    background: url('../../images/background.jpeg') no-repeat;
                    background-size: cover;
                }
            </style>

        </head>
<body>

    <%
        if(session.getAttribute("username") == null)
        {
            response.sendRedirect("../../index.html");
        }
    %>

    <div class="navbar navbar-inverse">

        <div class="container">

            <div class="navbar-header">

                <a href="" class="navbar-brand">Battle-Ship</a>

                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">

                    <span class="sr-only">Toggle Navigation</span>

                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>

                </button>

            </div>

            <div class="collapse navbar-collapse">

                <ul class="nav navbar-nav">

                    <li><a>Login</a></li>
                    <li class="active"><a>Main</a></li>
                    <li><a>Game</a></li>

                </ul>

            </div>

        </div>

    </div>

    <div class="container">
        <h1>Battle-Ship Lobby</h1>
    </div>


    <table class="table table-bordered">
        <!--row1-->
        <tr>
            <th class="col-md-4">Online Users:</th>
            <th class="col-md-4">Game Details</th>
            <th class="col-md-4">Games List</th>
        </tr>
        <!--row2-->
        <tr>
            <td>
                <ul id="userslist"></ul>
            </td>

            <td>
                <p>
                    <label id="GameName"></label>
                    <br>
                    <label id="PlayerUpload"></label>
                    <br>
                    <label id="GameSize"></label>
                    <br>
                    <label id="GameType"></label>
                    <br>
                    <label id="PlayerInsideGame"></label>
                </p>
            </td>

            <td>
                <select size="5" id="gameslist" name="decision"
                        style=" width: 80% ;color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left; background-color: transparent;"
                        onchange="changeName(this)">
                </select>

                <button value="Start Game" id="StartGame" name="StartGame" onclick="StartGame()">Start Game</button>
                <button value="Delete Game " id="DeleteGame" name="DeleteGame" onclick="DeleteGame()">Delete Game</button>
            </td>

        </tr>
        <!--row3-->
        <tr>
            <td>
                <form method="POST" action="uploadfile" enctype="multipart/form-data">

                    <div class="form-group">

                        <label for="userGameName">Game Name:</label>
                        <input type="text" name="userGameName" id="userGameName"/><br>

                        <label for="xmlFile">
                            <input type="file" name="xmlFile" id="xmlFile"/>
                            <input type="submit" value="Upload File"/>
                        </label>
                    </div>
                </form>

                <% Object errorMessage = request.getAttribute("errorMassage");%>
                <% if (errorMessage != null) {%>
                <span class="bg-danger" style="color:red;"><%=errorMessage%></span>
                <% } %>
                <button id="Logout" onclick="logout()">LogOut</button>
            </td>
        </tr>

    </table>

</body>
</html>