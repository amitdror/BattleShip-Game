package com.amitdr.BattleShip.logic.engine.entities;

import java.util.List;

public class GameShip
{
    //Nested Class ShipPart
    public static class ShipPart
    {
        private int m_X;
        private int m_Y;
        private boolean m_Destroyed;

        public ShipPart(int m_X, int m_Y)
        {
            this.m_X = m_X;
            this.m_Y = m_Y;
            this.m_Destroyed = false;
        }
        public int getX()
        {
            return m_X;
        }
        public int getY()
        {
            return m_Y;
        }
        public boolean isDestroyed()
        {
            return m_Destroyed;
        }
        public void setDestroyed(){m_Destroyed = true;}
    }

    //Class GameShip
    private int m_TypeID;
    private boolean m_Sunk;
    private List<ShipPart> m_ShipParts;

    public GameShip(int m_TypeID, List<ShipPart> m_ShipParts)
    {
        this.m_TypeID = m_TypeID;
        this.m_Sunk = false;
        this.m_ShipParts = m_ShipParts;
    }

    public int getTypeID()
    {
        return m_TypeID;
    }

    public List<ShipPart> getShipParts()
    {
        return m_ShipParts;
    }

    public boolean isSunk()
    {
        if(!m_Sunk)
        {
            m_Sunk = true;

            for(ShipPart shipPart : m_ShipParts)
            {
                if(!shipPart.isDestroyed())
                {
                    m_Sunk = false;
                }
            }
        }

        return m_Sunk;
    }

}
