package com.amitdr.BattleShip.logic.engine.entities;

import com.amitdr.BattleShip.logic.engine.entities.types.GameShipType;
import com.amitdr.BattleShip.logic.engine.types.Type;
import com.amitdr.BattleShip.logic.exceptions.GameEngineException;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Player
{
    private Map<Integer, List<GameShip>> m_ShipList;
    private Map<Integer, GameShipType> m_ShipTypes;
    private Map<String, Integer> m_LiveShipsList;
    private int[][] m_ShipBoard; //Empty = -1 | ship = shipTypeID | Destroyed = -2 | Miss = 0
    private int[][] m_HitsBoard;   //Empty = -1 | Hit = 1 | Miss = 0;
    private long m_AverageMoveTime;
    private int m_NumberOfTurns;
    private String m_PlayerName;
    private int m_Score;
    private int m_Misses;
    private int m_Hits;
    private int m_Mines;
    private boolean m_IsConnected = false;
    private String m_PlayerUsername;


    //NEW*********************
    public void setUsername(String i_PlayerName)
    {
          m_PlayerUsername = i_PlayerName;
    }
    public String getUserName(){
        return m_PlayerUsername;
    }

    public void setConnected(boolean i_IsConnected){
        m_IsConnected = i_IsConnected;
    }

    public boolean isConnected(){
        return m_IsConnected;
    }

    //*************************
    public Player(String i_PlayerName, Map<Integer, List<GameShip>> i_ShipList, int[][] i_ShipBoard, int[][] i_HitsBoard, int i_Mines)
    {
        this.m_PlayerName = i_PlayerName;
        this.m_ShipBoard = i_ShipBoard;
        this.m_HitsBoard = i_HitsBoard;
        this.m_ShipList = i_ShipList;
        this.m_AverageMoveTime = 0;
        this.m_NumberOfTurns = 0;
        this.m_Mines = i_Mines;
        this.m_Misses = 0;
        this.m_Hits = 0;
        this.m_Score = 0;
        updateLiveShipsList();
    }

    public int attack(Player i_Enemy, int i_X, int i_Y)
    {
        int attackResult = Type.INVALID_HIT;

        if(m_HitsBoard[i_Y][i_X] == Type.EMPTY)
        {
            int[] tempScore = new int[1];
            tempScore[0] = m_Score;
            int enemyResult = i_Enemy.attackShipBoard(this, i_X, i_Y, tempScore);
            m_Score = tempScore[0];
            attackResult = updateBoards(enemyResult, i_X, i_Y);

            return attackResult;
        }

        return Type.INVALID_HIT;
    }

    private int attackShipBoard(Player i_Attacker, int i_X, int i_Y, int[] i_EnempyScore)// throws GameEngineException
    {
        int hitResult = m_ShipBoard[i_Y][i_X];

        switch(hitResult)
        {
            case Type.MISS:
                break;
            case Type.DESTROYED:
                break;
            case Type.EMPTY:
                m_ShipBoard[i_Y][i_X] = Type.MISS;
                break;
            case Type.MINE:
                m_ShipBoard[i_Y][i_X] = Type.DESTROYED;
                this.attack(i_Attacker, i_X, i_Y);
                break;
            default: //hit ship
                 i_EnempyScore[0] += setShipPartAttack(i_X, i_Y);
                m_ShipBoard[i_Y][i_X] = Type.DESTROYED;
        }

        return hitResult;
    }

    private int updateBoards(int i_AttackResult, int i_X, int i_Y)
    {
        int updateResult = Type.INVALID_HIT;

        if(i_AttackResult != Type.INVALID_HIT)
        {

            switch(i_AttackResult)
            {
                case Type.EMPTY:
                    m_HitsBoard[i_Y][i_X] = Type.MISS;
                    updateResult = Type.MISS;
                    m_Misses++;
                    break;
                case Type.DESTROYED:
                    break;
                case Type.MISS:
                    break;
                case Type.MINE:
                    this.m_HitsBoard[i_Y][i_X] = Type.MINE;
                    updateResult = Type.MINE;
                    this.m_Hits++;
                    break;
                default:
                    this.m_HitsBoard[i_Y][i_X] = Type.DESTROYED;
                    this.m_Hits++;
                    updateResult = Type.HIT;
                    break;
            }
        }
        return updateResult;
    }

    private int setShipPartAttack(int i_X, int i_Y)
    {
        int shipTypeID = m_ShipBoard[i_Y][i_X];
        int score = 0;

        for(GameShip ship : m_ShipList.get(shipTypeID))
        {
            for(GameShip.ShipPart shipPart : ship.getShipParts())
            {
                if(shipPart.getX() == i_X && shipPart.getY() == i_Y)
                {
                    shipPart.setDestroyed();
                    if(ship.isSunk())
                    {
                        int typeID = ship.getTypeID();
                        score += m_ShipTypes.get(ship.getTypeID()).getScore();
                    }
                    return score;
                }
            }
        }
        return score;
    }

    public void setShipTypes(Map<Integer, GameShipType> i_ShipTypes)
    {
        m_ShipTypes = i_ShipTypes;
    }

    public void hitPlusOne()
    {
        m_Hits++;
    }

    public void missPlusOne()
    {
        m_Misses++;
    }

    public void addMine(int i_X, int i_Y) throws GameEngineException
    {
        if(m_ShipBoard[i_Y][i_X] == Type.EMPTY)
        {
            m_ShipBoard[i_Y][i_X] = Type.MINE;
            m_Mines--;
        }
        else
        {
            throw new GameEngineException("You Can't Place The Mine There!");
        }
    }

    private boolean updateLiveShipsList()
    {
        boolean result = true;
        Map<String, Integer> liveShips = new HashMap<>();

        for(Map.Entry<Integer, List<GameShip>> entry : m_ShipList.entrySet())
        {
            int shipsNumber = 0;
            int shipTypeID = entry.getKey();
            String strShipType = Type.getTypeAsString(shipTypeID);

            for(GameShip ship : entry.getValue())
            {
                if(!ship.isSunk())
                {
                    shipsNumber++;
                    result = false;
                }
            }
            liveShips.put(strShipType, shipsNumber);
        }
        m_LiveShipsList = liveShips;

        return result;
    }

    public boolean isTheLoser()
    {
        return updateLiveShipsList();
    }

    public Map<String, Integer> getLiveShipsList()
    {
        return m_LiveShipsList;
    }


    public void updateAverageMoveTime(long i_TurnTime)
    {
        long newTime = (m_AverageMoveTime * m_NumberOfTurns) + i_TurnTime;
        m_NumberOfTurns++;
        m_AverageMoveTime = newTime / m_NumberOfTurns;
    }

    public int getScore()
    {
        return m_Score;
    }

    public int[][] getShipBoard()
    {
        return m_ShipBoard;
    }

    public Map<Integer, List<GameShip>> getPlayerShipList()
    {
        return m_ShipList;
    }

    public int[][] getHitsBoard()
    {
        return m_HitsBoard;
    }

    public String getPlayerName()
    {
        return m_PlayerName;
    }

    public int getMissNumber()
    {
        return m_Misses;
    }

    public int getHitsNumber()
    {
        return m_Hits;
    }

    public long getAverageMoveTime()
    {
        return TimeUnit.MILLISECONDS.toSeconds(m_AverageMoveTime);
    } //todo: fix time

    public int getMineNumber()
    {
        return m_Mines;
    }

}
