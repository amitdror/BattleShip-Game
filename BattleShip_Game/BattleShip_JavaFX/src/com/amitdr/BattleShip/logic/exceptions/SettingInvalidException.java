package com.amitdr.BattleShip.logic.exceptions;

public class SettingInvalidException extends Exception
{
    private final String EXCEPTION_MESSAGE;

    public SettingInvalidException(String i_ExceptionMessage)
    {
        EXCEPTION_MESSAGE = "\n" + i_ExceptionMessage;
    }

    @Override
    public String getMessage()
    {
        return EXCEPTION_MESSAGE;
    }
}
