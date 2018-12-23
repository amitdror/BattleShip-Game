package com.amitdr.BattleShip.logic.utils;

import com.amitdr.BattleShip.logic.engine.types.Type;
import com.amitdr.BattleShip.logic.exceptions.GameUtilsException;

public class GameUtils
{

    public static boolean isPointOnBoard(int i_X, int i_Y, int i_BoardSize)
    {
        return (i_X >= 0 && i_X < i_BoardSize && i_Y >= 0 && i_Y < i_BoardSize);

    }

    public static boolean isEmptyPoint(int[][] i_Board, int i_X, int i_Y)
    {
        return i_Board[i_Y][i_X] == Type.EMPTY || i_Board[i_Y][i_X] == Type.MISS;
    }

    public static boolean isPointAreaClear(int[][] i_Board, int i_X, int i_Y, int i_ShipTypeID)
    {
        int boardSize = i_Board.length;

        for(int y = i_Y - 1; y <= i_Y + 1; y++)
        {
            for(int x = i_X - 1; x <= i_X + 1; x++)
            {
                if(isPointOnBoard(x, y, boardSize))
                {
                    if(!isEmptyPoint(i_Board, x, y))
                    {
                        if(i_ShipTypeID != i_Board[y][x])
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

//
//    public static void printTestingBoard(int[][] i_Board) //todo: delete this function
//    {
//        for(int i = 0; i < i_Board.length; i++)
//        {
//            for(int j = 0; j < i_Board.length; j++)
//            {
//                if(i_Board[i][j] == -1)
//                {
//                    System.out.print(" * ");
//                }
//                else
//                {
//                    System.out.print(" @ ");
//                }
//            }
//            System.out.println();
//        }
//    }

    public static void calcPosition(int[] i_PosX, int[] i_PosY, int i_BoardSize, String i_UserPosition) throws GameUtilsException
    {

        if(i_UserPosition != null && i_UserPosition.length() >= 3)
        {
            char regex = i_UserPosition.toCharArray()[1];

            if(regex == '_')
            {
                String[] inputParts = i_UserPosition.split("_");

                if(isValidLetterOnBoard(inputParts[0], i_BoardSize) && isValidNumberOnBoard(inputParts[1], i_BoardSize))
                {
                    i_PosX[0] = (int) inputParts[0].toCharArray()[0] - 'A' + 1;
                    i_PosY[0] = Integer.parseInt(inputParts[1]);
                    return;
                }
            }
        }
    }

    private static boolean isValidNumberOnBoard(String i_RowStr, int i_BoardSize) throws GameUtilsException
    {
        try {
            int rowNumber = Integer.parseInt(i_RowStr);

            if(rowNumber > 0 && rowNumber <= i_BoardSize)
            {
                return true;
            }
            else
            {
                throw new GameUtilsException("Row index must be a number between 1 - " + i_BoardSize);
            }
        }
        catch(Exception e) {
            throw new GameUtilsException("Row index must be a number between 1 - " + i_BoardSize);
        }
    }

    private static boolean isValidLetterOnBoard(String i_ColStr, int i_BoardSize) throws GameUtilsException
    {
        if(i_ColStr.length() == 1)
        {
            char colChar = i_ColStr.toCharArray()[0];
            char lastLetter = (char)('A'  + (i_BoardSize - 1));
            return (colChar >= 'A' && colChar <= lastLetter);
        }
        else
        {
            char lastLetter = (char)('A'  + (i_BoardSize - 1));
            throw new GameUtilsException("Col index must be a capital letter between A- " + lastLetter);
        }

    }


}
