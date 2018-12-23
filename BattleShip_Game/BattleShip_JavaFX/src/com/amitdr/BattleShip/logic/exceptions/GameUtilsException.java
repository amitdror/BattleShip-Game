package com.amitdr.BattleShip.logic.exceptions;

public class GameUtilsException extends Exception
{
    private final String EXCEPTION_MESSAGE;

    public GameUtilsException(String i_ExceptionMessage)
    {
        EXCEPTION_MESSAGE = "\nSyntactic Error - " + i_ExceptionMessage;
    }

    @Override
    public String getMessage()
    {
        return EXCEPTION_MESSAGE;
    }
}
