package com.amitdr.BattleShip.logic.exceptions;

public class NotXmlFileException extends Exception
{
    private final String EXCEPTION_MESSAGE = "File not loaded successfully! - File is not an .xml file.";

    public NotXmlFileException()
    {
    }

    @Override
    public String getMessage()
    {
        return EXCEPTION_MESSAGE;
    }
}
