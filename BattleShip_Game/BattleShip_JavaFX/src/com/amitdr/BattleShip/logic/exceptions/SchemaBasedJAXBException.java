package com.amitdr.BattleShip.logic.exceptions;

public class SchemaBasedJAXBException extends Exception
{
    private final String EXCEPTION_MESSAGE;

    public SchemaBasedJAXBException(String i_ExceptionMessage)
    {
        EXCEPTION_MESSAGE = i_ExceptionMessage;
    }

    @Override
    public String getMessage()
    {
        return EXCEPTION_MESSAGE;
    }
}
