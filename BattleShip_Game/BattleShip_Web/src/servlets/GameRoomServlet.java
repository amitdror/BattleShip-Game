package servlets;


import com.amitdr.BattleShip.logic.engine.GameEngine;
import com.amitdr.BattleShip.logic.engine.types.Type;
import com.amitdr.BattleShip.logic.exceptions.GameEngineException;
import com.google.gson.Gson;
import managers.GameManager;
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

@WebServlet(name = "GameRoomServlet", urlPatterns = {"/gameroom"})
public class GameRoomServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws GameEngineException
    {
        response.setContentType("text/html;charset=UTF-8");
        String method = request.getParameter("method");
        if (method != null) {
            switch (method) {
                case "getBoardSize": {
                    getBoardSize(request, response);
                    break;
                }
                case "getNumberOfMine": {
                    getNumberOfMine(request, response);
                    break;
                }
                case "getPlayerName":{
                    getUserName(request,response);
                    break;
                }

                case "getOpponentName":{
                    getOpponentName(request,response);
                    break;
                }

                case "getTurnNumber":{
                    getTurnNumber(request,response);
                    break;
                }

                case "getPlayerScore":{
                    getPlayerScore(request,response);
                    break;
                }

                case "getPlayerBattleShipLeft":{
                    getPlayerBattleShipLeft(request,response);
                    break;
                }

                case "getHit":{
                    getHit(request,response);
                    break;
                }
                case "getOpponentHit":{
                    getOpponentHit(request,response);
                    break;
                }

                case "getMiss":{
                    getMiss(request,response);
                    break;
                }

                case "getOpponentMiss":{
                    getOpponentMiss(request,response);
                    break;
                }

                case "getAverageMoveTime":{
                    getAverageMoveTime(request,response);
                    break;
                }

                case "getOpponentAverageMoveTime":{
                    getOpponentAverageMoveTime(request,response);
                    break;
                }

                case "getOpponentBattleShipLeft":{
                    getOpponentBattleShipLeft(request,response);
                    break;
                }

                case "getOpponentScore":{
                    getOpponentScore(request,response);
                    break;
                }
                case "addMine":{
                    addMine(request,response);
                    break;
                }
                case "isMyTurn":{
                    isMyTurn(request,response);
                    break;
                }
                case "AttackPlayer":{
                    AttackPlayer(request,response);
                    break;
                }
                case "getPlayerBoard":{
                    getPlayerBoard(request,response);
                    break;
                }
                case "getAttackBoard":{
                    getAttackBoard(request,response);
                    break;
                }
                case "getPosiblePlaceForMine":{
                    getPosiblePlaceForMine(request,response);
                    break;
                }
                case "QuitGame":{
                    QuitGame(request,response);
                    break;
                }
                case "OtherPlayerInTheGame":{
                    OtherPlayerInTheGame(request,response);
                    break;
                }
                case "StartGame":{
                    OtherPlayerInTheGame(request,response);
                    break;
                }
                case "CheckIfSomeOneWon":{
                    CheckIfSomeOneWon(request,response);
                    break;
                }
                case "CheckIfOtherPlayerWon":{
                    CheckIfOtherPlayerWon(request,response);
                    break;
                }
                case "getGameType":{
                    getGameType(request,response);
                    break;
                }
                case "getHitMine":{
                   // getHitMine(request,response);
                    break;
                }
            }
        }
    }

    private void getGameType(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String gameType= (gamesManager.getOneGame(gameName).getGameType());
                out.print(gameType);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Game Type in game Room");
        }
    }

    private void getHitMine(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
//                boolean hitMine= (gamesManager.getOneGame(gameName)).getHadHitMine();
//                out.print(hitMine);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from check Hit Mine");
        }
    }

    private void CheckIfOtherPlayerWon(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = ((Integer.parseInt(indexPlayerStr) + 1) % 2) + 1;
                boolean playerInsideBoard = gamesManager.getOneGame(gameName).getWinner() == indexPlayer;
                out.print(String.valueOf(playerInsideBoard));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from check if other player won");
        }
    }

    private void CheckIfSomeOneWon(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);
                boolean playerInsideBoard = gamesManager.getOneGame(gameName).getWinner() == indexPlayer;
                out.print(String.valueOf(playerInsideBoard));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from Check if player won");
        }
    }

    private void OtherPlayerInTheGame(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = ((Integer.parseInt(indexPlayerStr) + 1) % 2) + 1;
                boolean playerInsideBoard = (gamesManager.getOneGame(gameName)).checkIfPlayerConnected(indexPlayer);
                out.print(String.valueOf(playerInsideBoard));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from Other player In the Game");
        }

    }

    private void QuitGame(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                Gson gson = new Gson();
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                switch(indexPlayer)
                {
                    case 1:
                        gamesManager.getOneGame(gameName).getPlayerOne().setConnected(false);
                        break;
                    case 2:
                        gamesManager.getOneGame(gameName).getPlayerTwo().setConnected(false);
                        break;
                }

                gamesManager.RestartGame(gameName);
                String test = "Quit Game";
                String json = gson.toJson(test);
                out.print(json);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get quit game");
        }
    }

    private void getPosiblePlaceForMine(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                Gson gson = new Gson();
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                 char [][] possiblePlace= (gamesManager.getOneGame(gameName)).buildposiblePlaceFromBoard(indexPlayer);
                String json = gson.toJson(possiblePlace);
                out.print(json);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get poss mine place");
        }
    }


    private void getAttackBoard(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                Gson gson = new Gson();
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int[][] gameboard = null;

                if(indexPlayer == 1)
                {
                    gameboard = (gamesManager.getOneGame(gameName)).getPlayerOne().getHitsBoard();
                }
                else if(indexPlayer == 2)
                {
                    gameboard = (gamesManager.getOneGame(gameName)).getPlayerTwo().getHitsBoard();
                }

                String json = gson.toJson(gameboard);
                out.print(json);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get attack board");
        }
    }

    private void getPlayerBoard(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                Gson gson = new Gson();
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int[][] gameboard = null;

                if(indexPlayer == 1)
                {
                    gameboard = (gamesManager.getOneGame(gameName)).getPlayerOne().getShipBoard();
                }
                else if(indexPlayer == 2)
                {
                    gameboard = (gamesManager.getOneGame(gameName)).getPlayerTwo().getShipBoard();
                }

                String json = gson.toJson(gameboard);
                out.print(json);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get player board");
        }
    }

    private void AttackPlayer(HttpServletRequest request, HttpServletResponse response) {

        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());

        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);
                String strX = request.getParameter("x");
                String strY = request.getParameter("y");
                int x = (Integer.parseInt(strX));
                int y = (Integer.parseInt(strY));
                GameEngine gameEngine = gamesManager.getOneGame(gameName);

                int attackResult = gameEngine.attackOpponent(x,y,System.currentTimeMillis());

                if(gameEngine.isMineHit())
                {
                    attackResult = Type.MINE;
                }

                out.print(String.valueOf(attackResult));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from Attack player");
        }
    }

    private void isMyTurn(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                boolean isMyTurn= (gamesManager.getOneGame(gameName).getCurrentPlayerID() == indexPlayer);
                out.print(String.valueOf(isMyTurn));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from is My Turn");
        }

    }

    private void addMine(HttpServletRequest request, HttpServletResponse response) throws GameEngineException
    {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        if (gamesManager.getGames().size() > 0) {
            String gameName = request.getParameter("gameName");
            String indexPlayerStr = request.getParameter("gameindex");
            int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

            int x = Integer.parseInt(request.getParameter("x"));
            int y = Integer.parseInt(request.getParameter("y"));
            (gamesManager.getOneGame(gameName)).addMine(x,y);
        }

    }

    private void getNumberOfMine(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int NumberOfMine = 0;

                if(indexPlayer == 1)
                {
                    NumberOfMine = (gamesManager.getOneGame(gameName)).getPlayerOne().getMineNumber();
                }
                if(indexPlayer == 2)
                {
                    NumberOfMine = (gamesManager.getOneGame(gameName)).getPlayerTwo().getMineNumber();
                }

                out.print(String.valueOf(NumberOfMine));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Number Of Mine");
        }
    }

    private void getOpponentScore(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int OpponentBattleShipLeft = 0;

                if(indexPlayer == 2)//todo: check player index
                {
                    OpponentBattleShipLeft = (gamesManager.getOneGame(gameName)).getPlayerOne().getScore();
                }
                if(indexPlayer == 1)
                {
                    OpponentBattleShipLeft = (gamesManager.getOneGame(gameName)).getPlayerTwo().getScore();
                }

                out.print(String.valueOf(OpponentBattleShipLeft));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Opponent Opponent Score");
        }

    }

    public String calcShipsDetails(Map<String, Integer> shipsMap) {

        String result = "";

        int numberOfShip  = 0;

        for(Map.Entry<String, Integer> entry : shipsMap.entrySet())
        {
            String shipId = entry.getKey();
            int shipsNumber = entry.getValue();

            result = result + "\n" + shipId + ": " + shipsNumber;
        }

        return result;
    }

    private void getOpponentBattleShipLeft(HttpServletRequest request, HttpServletResponse response) {

        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                String OpponentBattleShipDetails = null;
                Map<String, Integer> opponentBattleShipMap = null;

                if(indexPlayer == 2)
                {
                    opponentBattleShipMap = (gamesManager.getOneGame(gameName)).getPlayerOne().getLiveShipsList();
                    OpponentBattleShipDetails = calcShipsDetails(opponentBattleShipMap);

                }
                if(indexPlayer == 1)
                {
                    opponentBattleShipMap = (gamesManager.getOneGame(gameName)).getPlayerTwo().getLiveShipsList();
                    OpponentBattleShipDetails = calcShipsDetails(opponentBattleShipMap);
                }

                out.print(OpponentBattleShipDetails);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Opponent BattleShip Left");
        }
    }

    private void getAverageMoveTime(HttpServletRequest request, HttpServletResponse response) {

        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                String AverageMoveTime = null;

                if(indexPlayer == 1)
                {
                    AverageMoveTime = "Average-Time: " + (gamesManager.getOneGame(gameName)).getPlayerOneAverageMoveTime();
                }
                if(indexPlayer == 2)
                {
                    AverageMoveTime = "Average-Time: " + (gamesManager.getOneGame(gameName)).getPlayerTwoAverageMoveTime();
                }

                out.print(AverageMoveTime);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Average Move Time");
        }
    }


    private void getOpponentAverageMoveTime(HttpServletRequest request, HttpServletResponse response) {

        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                String AverageMoveTime = null;

                if(indexPlayer == 2)
                {
                    AverageMoveTime = "Average-Time: " + (gamesManager.getOneGame(gameName)).getPlayerOneAverageMoveTime();
                }
                if(indexPlayer == 1)
                {
                    AverageMoveTime = "Average-Time: " + (gamesManager.getOneGame(gameName)).getPlayerTwoAverageMoveTime();
                }

                out.print(AverageMoveTime);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Average Move Time");
        }
    }



    private void getMiss(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int miss = 0;

                if(indexPlayer == 1)
                {
                    miss = (gamesManager.getOneGame(gameName)).getPlayerOneMissNumber();
                }
                if(indexPlayer == 2)
                {
                    miss = (gamesManager.getOneGame(gameName)).getPlayerTwoMissNumber();
                }

                out.print(String.valueOf(miss));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Miss");
        }
    }

    private void getOpponentMiss(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int miss = 0;

                if(indexPlayer == 2)
                {
                    miss = (gamesManager.getOneGame(gameName)).getPlayerOneMissNumber();
                }
                if(indexPlayer == 1)
                {
                    miss = (gamesManager.getOneGame(gameName)).getPlayerTwoMissNumber();
                }

                out.print(String.valueOf(miss));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Miss");
        }
    }

    private void getHit(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int hits = 0;

                if(indexPlayer == 1)
                {
                    hits = (gamesManager.getOneGame(gameName)).getPlayerOneHitsNumber();
                }
                if(indexPlayer == 2)
                {
                    hits = (gamesManager.getOneGame(gameName)).getPlayerTwoHitsNumber();
                }

                out.print(String.valueOf(hits));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Hit");
        }

    }

    private void getOpponentHit(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int hits = 0;

                if(indexPlayer == 2)
                {
                    hits = (gamesManager.getOneGame(gameName)).getPlayerOneHitsNumber();
                }
                if(indexPlayer == 1)
                {
                    hits = (gamesManager.getOneGame(gameName)).getPlayerTwoHitsNumber();
                }

                out.print(String.valueOf(hits));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Hit");
        }

    }

    private void getPlayerBattleShipLeft(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                String playerShipsDetails = null;
                Map<String, Integer> opponentBattleShipMap = null;

                if(indexPlayer == 1)
                {
                    opponentBattleShipMap = (gamesManager.getOneGame(gameName)).getPlayerOne().getLiveShipsList();
                    playerShipsDetails = calcShipsDetails(opponentBattleShipMap);

                }
                if(indexPlayer == 2)
                {
                    opponentBattleShipMap = (gamesManager.getOneGame(gameName)).getPlayerTwo().getLiveShipsList();
                    playerShipsDetails = calcShipsDetails(opponentBattleShipMap);
                }


                out.print(playerShipsDetails);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get BattleShipLeft");
        }

    }

    private void getPlayerScore(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);

                int playerScore = 0;

                if(indexPlayer == 1)
                {
                    playerScore = (gamesManager.getOneGame(gameName)).getPlayerOneScore();
                }
                else if(indexPlayer == 2)
                {
                    playerScore = (gamesManager.getOneGame(gameName)).getPlayerTwoScore();
                }

                out.print(String.valueOf(playerScore));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get Player Score");
        }
    }

    private void getTurnNumber(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
//                String indexPlayer = request.getParameter("gameindex");
//                int TurnNumber = (gamesManager.getOneGame(gameName)).getM_TurnNumber();
//                (gamesManager.getOneGame(gameName)).StartTurnOfPlayer(Integer.parseInt(indexPlayer));

                int turnNumber = (gamesManager.getOneGame(gameName)).getNumberOfTurns();
                out.print(String.valueOf(turnNumber));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from get turn number");
        }
    }

    private void getUserName(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String userName = SessionUtils.getUsername(request);
                out.print(userName);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from UserName");
        }
    }

    private void getOpponentName(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {

                String gameName = request.getParameter("gameName");
                String indexPlayerStr = request.getParameter("gameindex");
                int indexPlayer = (Integer.parseInt(indexPlayerStr) + 1);
                String userName = "";

                if(indexPlayer == 1)
                {
                    userName = gamesManager.getOneGame(gameName).getPlayerTwo().getUserName();
                }
                else if(indexPlayer == 2)
                {
                    userName = gamesManager.getOneGame(gameName).getPlayerOne().getUserName();
                }

                out.print(userName);
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from OpponentName");
        }
    }

    private void getBoardSize(HttpServletRequest request, HttpServletResponse response) {
        GameManager gamesManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            if (gamesManager.getGames().size() > 0) {
                String gameName = request.getParameter("gameName");
                int bordSize = (gamesManager.getOneGame(gameName)).getBoardSize();
                out.print(String.valueOf(bordSize));
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from getBoardSize");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            processRequest(request, response);
        }
        catch(GameEngineException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            processRequest(request, response);
        }
        catch(GameEngineException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
