package com.amitdr.BattleShip.javaFX.tiles;

import com.amitdr.BattleShip.javaFX.controllers.GameController;
import com.amitdr.BattleShip.logic.engine.GameEngine;
import com.amitdr.BattleShip.logic.utils.GameUtils;

public class TileUtils
{
    private static GameEngine m_CurrentGameEngine;
    private static GameController m_GameController;

    public static void setCurrentGameEngine(GameEngine i_CurrentGameEngine)
    {
        m_CurrentGameEngine = i_CurrentGameEngine;
    }

    public static void setCurrentGameController(GameController i_GameController)
    {
        m_GameController = i_GameController;
    }

    public static GameEngine getGameEngine()
    {
        return m_CurrentGameEngine;
    }

    public static GameController getGameController()
    {
        return m_GameController;
    }

    public static boolean isPointAreaClear(int i_X, int i_Y, int[][] currPlayerBoard)
    {
        int boardSize = currPlayerBoard.length;

        for(int y = i_Y - 1; y <= i_Y + 1; y++)
        {
            for(int x = i_X - 1; x <= i_X + 1; x++)
            {
                if(GameUtils.isPointOnBoard(x, y, boardSize))
                {
                    if(!GameUtils.isEmptyPoint(currPlayerBoard, x, y))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isPointAreaClear(int i_X, int i_Y)
    {
        int[][] currPlayerBoard = m_CurrentGameEngine.getCurrentPlayer().getShipBoard();

        return isPointAreaClear(i_X,i_Y,currPlayerBoard);
    }
}
