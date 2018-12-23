package servlets;

import com.amitdr.BattleShip.logic.engine.GameEngine;
import com.google.gson.Gson;
import com.sun.javafx.collections.MappingChange;
import managers.GameManager;
import managers.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;


@WebServlet(name = "GamesListServlet", urlPatterns = {"/gameslist"})
public class GamesListServlet extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("application/json");
        String g = request.getParameter("method");
        Gson gja = new Gson();
        if(g != null && g.equals("getGamesList"))
        {
            try(PrintWriter out = response.getWriter())
            {
                Gson gson = new Gson();
                GameManager gameManager = ServletUtils.getGameManager(getServletContext());
                Map<String, String> usersList = gameManager.getGames();
                String json = gson.toJson(usersList);
                out.println(json);
                out.flush();
            }
        }
        else
        {
            String button = request.getParameter("button");
            switch(button)
            {
                case "logout":
                {
                    logout(request, response);
                    break;
                }
                case "joinGame":
                {
                    joinGame(request, response);
                    break;
                }
                case "PlayerUpload":
                {
                    PlayerUpload(request, response);
                    break;
                }
                case "GameSize":
                {
                    GameSize(request, response);
                    break;
                }
                case "GameType":
                {
                    GameType(request, response);
                    break;
                }

                case "PlayerInsideGame":
                {
                    PlayerInsideGame(request, response);
                    break;
                }
                case "DeleteGame":{
                    DeleteGame(request,response);
                    break;
                }
            }

        }
    }

    private void DeleteGame(HttpServletRequest request, HttpServletResponse response) {

        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        String result = "none";
        String userName = SessionUtils.getUsername(request);

        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gamename = request.getParameter("gamename");
                if(gamesManager.getOneGame(gamename).getUploaderName().compareTo(userName) == 0)
                {
                    gamesManager.removeGame(gamename);
                    result = "Game '" + gamename + "' Is Deleted.";
                }
                else
                {
                    result = "Game Not Deleted - Only The Uploader Can Delete His Game.";
                }
                Gson gson = new Gson();
                String json = gson.toJson(result);
                out.print(json);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from Delete game");
        }

    }

    private void PlayerInsideGame(HttpServletRequest request, HttpServletResponse response)
    {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try(PrintWriter out = response.getWriter())
        {
            if(gamesManager.getGames().size() > 0)
            {
                String gameName = request.getParameter("gamename");

                GameEngine gameEngine = gamesManager.getOneGame(gameName);
                int numberOfPlayerInGame = gameEngine.getNumberOfConncetedPlayer();
                String playerName = null;

                switch(numberOfPlayerInGame)
                {
                    case 2:
                        playerName = "Full";
                        break;
                    case 1:
                        playerName = "One Player";
                        break;
                    case 0:
                        playerName = "Empty";
                        break;
                }

                Gson gson = new Gson();
                String json = gson.toJson(playerName);
                out.print(json);
                out.flush();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception from player Upload");
        }
    }

    private void GameType(HttpServletRequest request, HttpServletResponse response)
    {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try(PrintWriter out = response.getWriter())
        {
            if(gamesManager.getGames().size() > 0)
            {
                String gameName = request.getParameter("gamename");
                  String GameType= (gamesManager.getOneGame(gameName)).getGameType();
                Gson gson = new Gson();
                  String json = gson.toJson(GameType);
                  out.print(json);
                out.flush();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception from get Game Type");
        }
    }

    private void GameSize(HttpServletRequest request, HttpServletResponse response)
    {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try(PrintWriter out = response.getWriter())
        {
            if(gamesManager.getGames().size() > 0)
            {
                String gameName = request.getParameter("gamename");
                 int boardSize = (gamesManager.getOneGame(gameName)).getBoardSize();
                  out.print(String.valueOf(boardSize));
                out.flush();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception from get Board Size");
        }
    }

    private void PlayerUpload(HttpServletRequest request, HttpServletResponse response)
    {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try(PrintWriter out = response.getWriter())
        {
            if(gamesManager.getGames().size() > 0)
            {
                String gameName = request.getParameter("gamename");
                  String playerName = (gamesManager.getOneGame(gameName)).getUploaderName();
                Gson gson = new Gson();
                 String json = gson.toJson(playerName);
                 out.print(json);
                out.flush();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception from player Upload");
        }
    }


    private void joinGame(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        String usernameFromSession = SessionUtils.getUsername(request);
        if(usernameFromSession != null)
        {
            String gamename = request.getParameter("gamename");
            request.getSession(true).setAttribute("gameName", gamename);
            GameManager gamesRoomManager = ServletUtils.getGameManager(getServletContext());
            if(gamesRoomManager.isGameStarted(gamename))
            {
                try(PrintWriter out = response.getWriter())
                {
                    out.print("Started");
                    out.flush();
                }
            }
            else
            {
                int playerNumber = gamesRoomManager.addPlayer(gamename, usernameFromSession);
                try(PrintWriter out = response.getWriter())
                {
                    out.print(playerNumber);
                    out.flush();
                }
            }
        }

    }


    private void logout(HttpServletRequest request, HttpServletResponse response)
    {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String userName = SessionUtils.getUsername(request);
        try(PrintWriter out = response.getWriter())
        {
            userManager.removeUser(userName);
            Gson gson = new Gson();
            String test = "test";
            String json = gson.toJson(test);
            out.print(json);
            out.flush();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception from log out");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }
    //
    // </editor-fold>
}

