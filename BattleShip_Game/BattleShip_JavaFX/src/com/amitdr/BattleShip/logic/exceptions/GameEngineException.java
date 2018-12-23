package com.amitdr.BattleShip.logic.exceptions;

public class GameEngineException extends Exception
{
    private final String EXCEPTION_MESSAGE;

    public GameEngineException(String i_ExceptionMessage)
    {
        EXCEPTION_MESSAGE = "\n========================================\n" + i_ExceptionMessage +
                            "\n========================================\n";
    }

    @Override
    public String getMessage()
    {
        return EXCEPTION_MESSAGE;
    }
}

