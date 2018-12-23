<%--
  Created by IntelliJ IDEA.
  User: amitd
  Date: 31/10/2017
  Time: 3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Battle-Ship Game</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">

    <!-- jQuery first, then Bootstrap JS -->
    <script type="text/javascript" src="jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>

    <style>
        body {
            background: url('images/background.jpeg') no-repeat;
            background-size: cover;
        }
    </style>

</head>

<body>

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

                    <li class="active"><a>Login</a></li>
                    <li><a>Main</a></li>
                    <li><a>Game</a></li>

                </ul>

            </div>

        </div>

    </div>

    <div class="container">

        <h1>Welcome To The Battle-Ship Game!</h1>

        <form method="GET" action="login">

            <div class="form-group">

                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username">

            </div>

            <div class="checkbox">

                <label>
                    <input type="checkbox">Keep me logged in
                </label>

            </div>

            <div class="form-group">

                <input type="submit" value="Log In" class="btn btn-default">

            </div>

        </form>

        <p style="text-align:center">
            <% Object errorMessage = request.getAttribute("username_error");%>
            <% if (errorMessage != null) {%>
            <span class="bg-danger" style="color:red "><%=errorMessage%></span>
            <% } %>
        </p>

    </div>

</body>
</html>
