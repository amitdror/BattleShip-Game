package com.amitdr.BattleShip.logic.engine;

import com.amitdr.BattleShip.javaFX.tiles.TileUtils;
import com.amitdr.BattleShip.logic.engine.entities.Player;
import com.amitdr.BattleShip.logic.engine.entities.types.GameShipType;
import com.amitdr.BattleShip.logic.engine.types.Type;
import com.amitdr.BattleShip.logic.exceptions.GameEngineException;
import com.amitdr.BattleShip.logic.exceptions.SettingInvalidException;
import com.amitdr.BattleShip.logic.start.Settings;
import com.amitdr.BattleShip.logic.utils.GameUtils;


import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GameEngine
{
    private static int DESTROYED = 2;
    private static int EMPTY = -1;
    private static int HIT = 1;
    private static int MISS = 0;

    private enum eTurn
    {
        FirstPlayer, SecondPlayer
    }

    private Map<Integer, GameShipType> m_ShipTypes;
    private int m_Winner;
    private Settings m_Settings;
    private Player m_Player1;
    private Player m_Player2;
    private int m_BoardSize;
    private eTurn m_PlayerTurn;
    private int m_TurnsNumber;
    private long m_StartGameTime;
    private long m_StartTurnTime;
    private boolean m_EngineRunning;
    private int m_AttackingX;
    private int m_AttackingY;
    private String m_GameType;
    private String m_UploaderName;
    private boolean m_isMineHit = false;

    public GameEngine(Settings i_Settings) throws SettingInvalidException
    {
        this.m_Settings = i_Settings;
    }

    //NEW***********
    public boolean isMineHit()
    {
        return m_isMineHit;
    }


    public boolean checkIfPlayerConnected(int i_PlayerIndex)
    {
        boolean isConnected = false;

        switch(i_PlayerIndex)
        {
            case 1:
                isConnected = m_Player1.isConnected();
                break;
            case 2:
                isConnected = m_Player2.isConnected();
                break;
        }

        return isConnected;
    }

    public char[][] buildposiblePlaceFromBoard(int i_PlayerNumber)
    {
        char[][] boardToReturn = new char[m_BoardSize][m_BoardSize];

        for(int y = 0; y < m_BoardSize; y++)
        {
            for(int x = 0; x < m_BoardSize; x++)
            {
                if(validMinePlace(i_PlayerNumber, y, x))
                    boardToReturn[y][x] = 'T';
                else
                {
                    boardToReturn[y][x] = 'F';
                }
            }
        }
        return boardToReturn;
    }

    private boolean validMinePlace(int i_PlayerNumber, int i_Y, int i_X)
    {
        switch(i_PlayerNumber)
        {
            case 1:
                return TileUtils.isPointAreaClear(i_X,i_Y, m_Player1.getShipBoard());
            case 2:
                return TileUtils.isPointAreaClear(i_X,i_Y, m_Player2.getShipBoard());
            default:
                return false;
        }
    }


    public boolean isGameStarted()
    {
        return m_Player1.isConnected() && m_Player2.isConnected();
    }

    public String getUploaderName()
    {
        return m_UploaderName;
    }

    public void setUploaderName(String i_UserName)
    {
        m_UploaderName = i_UserName;
    }

    public String getGameType()
    {
        return m_GameType;
    }

    public int getNumberOfConncetedPlayer()
    {
        int numberOfConnectedPlayers = 0;

        if(m_Player1.isConnected())
        {
            numberOfConnectedPlayers++;
        }
        if(m_Player2.isConnected())
        {
            numberOfConnectedPlayers++;
        }

        return numberOfConnectedPlayers;
    }

    public int addPlayer(String i_UsernameFromSession)
    {

        int numberOfConnectedPlayers = 0;

        if(!m_Player1.isConnected())
        {
            m_Player1.setConnected(true);
            m_Player1.setUsername(i_UsernameFromSession);
        }
        else if(!m_Player2.isConnected())
        {
            m_Player2.setConnected(true);
            m_Player2.setUsername(i_UsernameFromSession);
            numberOfConnectedPlayers = 1;
        }
        else
        {
            numberOfConnectedPlayers = 2;
        }

        return numberOfConnectedPlayers;
    }


    public void start() throws SettingInvalidException
    {
        init();
    }

    private void init() throws SettingInvalidException
    {
        m_StartGameTime = System.currentTimeMillis();
        m_StartTurnTime = System.currentTimeMillis();
        m_Player1 = m_Settings.getFirstPlayer();
        m_Player2 = m_Settings.getSecondPlayer();
        m_ShipTypes = m_Settings.getShipTypes();
        m_Player1.setShipTypes(m_ShipTypes);
        m_Player2.setShipTypes(m_ShipTypes);
        m_PlayerTurn = eTurn.FirstPlayer;
        m_BoardSize = m_Settings.getBoardSize();
        m_TurnsNumber = 0;
        m_Winner = 0;
        m_EngineRunning = true;
        m_GameType = m_Settings.getGameType();
    }

    public void stop()
    {
        m_EngineRunning = false;

        switch(m_PlayerTurn)
        {
            case FirstPlayer:
                m_Winner = 2;
                break;
            case SecondPlayer:
                m_Winner = 1;
                break;
        }
    }

    public int attackOpponent(int i_PosX, int i_PosY, long i_PlayerTurnTime) throws GameEngineException
    {
        int attackResult = Type.INVALID_HIT;
        m_isMineHit = false;

        if(m_Winner == 0)
        {
            checkInput(i_PosX, i_PosY);

            //
            m_AttackingX = i_PosX;
            m_AttackingY = i_PosY;
            //
            long turnTime = i_PlayerTurnTime - m_StartTurnTime;

            switch(m_PlayerTurn)
            {
                case FirstPlayer:
                    attackResult = m_Player1.attack(m_Player2, i_PosX, i_PosY);

                    if(attackResult == Type.MINE)
                    {
                        m_isMineHit = true;
                    }

                    if(attackResult == Type.INVALID_HIT)
                    {
                        throw new GameEngineException("You Already Attack This Point - Try Again...");
                    }
                    if(attackResult != Type.HIT)
                    {
                        m_PlayerTurn = eTurn.SecondPlayer;
                    }
                    m_Player1.updateAverageMoveTime(turnTime);
                    break;
                case SecondPlayer:
                    attackResult = m_Player2.attack(m_Player1, i_PosX, i_PosY);
                    if(attackResult == Type.INVALID_HIT)
                    {
                        throw new GameEngineException("You Already Attack This Point - Try Again...");
                    }
                    if(attackResult != Type.HIT)
                    {
                        m_PlayerTurn = eTurn.FirstPlayer;
                    }
                    m_Player2.updateAverageMoveTime(turnTime);
                    break;
            }
            m_TurnsNumber++;
            checkWinner();
            m_StartTurnTime = System.currentTimeMillis();
        }

        return attackResult;
    }

    public int getAttackPosX()
    {
        return m_AttackingX;
    }

    public int getAttackPosY()
    {
        return m_AttackingY;
    }

//    public boolean attack(Player i_Attacker, Player i_Defender, int i_PosX, int i_PosY)
//    {
//        int hitResult = i_Defender.getShipBoard()[i_PosY][i_PosX];
//        boolean changeTurn = false;
//
//        switch(hitResult)
//        {
//            case Type.EMPTY:
//                i_Attacker.getHitsBoard()[i_PosY][i_PosX] = Type.MISS;
//                i_Defender.getShipBoard()[i_PosY][i_PosX] = Type.MISS;
//                i_Attacker.missPlusOne();
//                break;
//            case Type.MISS:
//                break;
//            case Type.DESTROYED:
//                break;
//            case Type.MINE:
//                i_Attacker. getHitsBoard()[i_PosY][i_PosX] = Type.MINE;
//                i_Defender.getShipBoard()[i_PosY][i_PosX] = Type.DESTROYED;
//                attack(i_Defender,i_Attacker, i_PosX,i_PosY);
//            default: //hit ship
//                i_Attacker.getHitsBoard()[i_PosY][i_PosX] = Type.HIT;
//                i_Defender.getShipBoard()[i_PosY][i_PosX] = Type.DESTROYED;
//                i_Attacker.hitPlusOne();
//                break;
//        }
//
//        return changeTurn;
//    }

    public int getMinesNumber()
    {
        int currentPlayerMineNumber = 0;

        switch(m_PlayerTurn)
        {
            case FirstPlayer:
                currentPlayerMineNumber = m_Player1.getMineNumber();
                break;
            case SecondPlayer:
                currentPlayerMineNumber = m_Player2.getMineNumber();
                break;
        }

        return currentPlayerMineNumber;
    }

    public boolean addMine(int i_PosX, int i_PosY) throws GameEngineException
    {
        boolean isAddMine = false;

        checkInput(i_PosX, i_PosY);

        switch(m_PlayerTurn)
        {
            case FirstPlayer:
                if(TileUtils.isPointAreaClear(i_PosX, i_PosY, m_Player1.getShipBoard()))
                {
                    m_Player1.addMine(i_PosX, i_PosY);
                    isAddMine = true;
                }
                break;
            case SecondPlayer:
                if(TileUtils.isPointAreaClear(i_PosX, i_PosY, m_Player2.getShipBoard()))
                {
                    m_Player2.addMine(i_PosX, i_PosY);
                    isAddMine = true;
                }
                break;
        }

        return isAddMine;
    }

    public Player getPlayerOne()
    {
        return m_Player1;
    }

    public Player getPlayerTwo()
    {
        return m_Player2;
    }

    public int getCurrentPlayerID()
    {
        int playerID = 0;

        if(m_Winner == 0)
        {
            switch(m_PlayerTurn)
            {
                case FirstPlayer:
                    return playerID = 1;
                case SecondPlayer:
                    return playerID = 2;
            }
        }

        return playerID = 0;
    }

    public Player getCurrentPlayer()
    {
        if(m_Winner == 0)
        {
            switch(m_PlayerTurn)
            {
                case FirstPlayer:
                    return m_Player1;
                case SecondPlayer:
                    return m_Player2;
            }
        }
        return null;
    }

    public int getBoardSize()
    {
        return m_BoardSize;
    }

    public int getWinner()
    {
        if(m_Winner == 0)
        {
            checkWinner();
        }
        return m_Winner;
    }

    private void checkWinner()
    {
        if(m_Player1.isTheLoser())
        {
            m_Winner = 2;
            m_EngineRunning = false;
        }
        else if(m_Player2.isTheLoser())
        {
            m_Winner = 1;
            m_EngineRunning = false;
        }
    }

    public boolean isRunning()
    {
        return m_EngineRunning;
    }

    public int getNumberOfTurns()
    {
        return m_TurnsNumber;
    }

    public String getTimeFromStart()
    {
        long currentGameTime = System.currentTimeMillis() - m_StartGameTime;
        long timeInSeconds = TimeUnit.MILLISECONDS.toSeconds(currentGameTime);
        long timeInMinutes = TimeUnit.SECONDS.toMinutes(timeInSeconds - (timeInSeconds % 60));

        return String.format("%d : %d", timeInMinutes, timeInSeconds % 60);
    }

    public Map<String, Integer> getPlayerOneLiveShipsList()
    {
        return m_Player1.getLiveShipsList();
    }

    public Map<String, Integer> getPlayerTwoLiveShipsList()
    {
        return m_Player2.getLiveShipsList();
    }

    public int getPlayerOneMissNumber()
    {
        return m_Player1.getMissNumber();
    }

    public int getPlayerOneHitsNumber()
    {
        return m_Player1.getHitsNumber();
    }

    public int getPlayerTwoMissNumber()
    {
        return m_Player2.getMissNumber();
    }

    public int getPlayerTwoHitsNumber()
    {
        return m_Player2.getHitsNumber();
    }

    public int getPlayerOneScore()
    {
        return m_Player1.getScore();
    }

    public int getPlayerTwoScore()
    {
        return m_Player2.getScore();
    }

    public float getPlayerOneAverageMoveTime()
    {
        return m_Player1.getAverageMoveTime();
    }

    public float getPlayerTwoAverageMoveTime()
    {
        return m_Player2.getAverageMoveTime();
    }

    private void checkInput(int i_PosX, int i_PosY) throws GameEngineException
    {
        if(i_PosX < 0 || i_PosX >= m_BoardSize || i_PosY < 0 || i_PosY >= m_BoardSize)
        {
            throw new GameEngineException("Input Error - Choose position on the board");
        }
    }

}
