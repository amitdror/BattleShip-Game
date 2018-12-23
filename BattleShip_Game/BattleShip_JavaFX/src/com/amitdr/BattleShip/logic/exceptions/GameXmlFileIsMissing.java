package com.amitdr.BattleShip.logic.exceptions;

public class GameXmlFileIsMissing extends Exception
{
    private final String EXCEPTION_MESSAGE = "Can't start the game - Please load your level file (.xml) before attempting to start the game";

    public GameXmlFileIsMissing()
    {
    }

    @Override
    public String getMessage()
    {
        return EXCEPTION_MESSAGE;
    }
}