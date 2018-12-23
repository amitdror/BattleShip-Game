package com.amitdr.BattleShip.logic.engine.types;

import java.util.HashMap;
import java.util.Map;

public class Type
{
    public static final int DESTROYED = -2;
    public static final int MINE = -3;
    public static final int EMPTY = 0;
    public static final int MISS = -1;
    public static final int INVALID_HIT = -9;
    public static final int HIT = 1;

    private static Map<Integer, String> m_TypesList = new HashMap<>();


    public static void addType(int i_TypeID, String i_StrTypeID)
    {
        m_TypesList.put(i_TypeID, i_StrTypeID);
    }

    public static String getTypeAsString(int i_TypeID)
    {
        return m_TypesList.get(i_TypeID);
    }

    public static void clearTypesList()
    {
        m_TypesList.clear();
    }
}
