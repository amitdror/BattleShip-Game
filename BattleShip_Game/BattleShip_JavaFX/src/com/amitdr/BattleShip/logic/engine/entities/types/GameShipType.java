package com.amitdr.BattleShip.logic.engine.entities.types;

public class GameShipType
{
    private int m_TypeID;
    private int m_Score;
    private int m_Amount;
    private int m_Length;

    public GameShipType(int i_TypeID, int i_Score, int i_Amount, int i_Length)
    {
        this.m_TypeID = i_TypeID;
        this.m_Score = i_Score;
        this.m_Amount = i_Amount;
        this.m_Length = i_Length;
    }
    public int getTypeID()
    {
        return m_TypeID;
    }
    public int getScore()
    {
        return m_Score;
    }
    public int getAmount()
    {
        return m_Amount;
    }
    public int getLength()
    {
        return m_Length;
    }
}
