package com.amitdr.BattleShip.logic.start;

import com.amitdr.BattleShip.logic.engine.GameEngine;

import java.util.*;


public class Launcher
{
    private boolean m_GameFileLoaded;
    private boolean m_GameEnded;
    private GameEngine m_GameEngine;
    private Settings m_Settings;
  //  private GameUI m_GameUI;

    public Launcher()
    {
        m_GameFileLoaded = false;
        m_GameEnded = false;
        m_Settings = new Settings();
    }

    public void start()
    {
        while(!m_GameEnded)
        {
            String userOption = printMainMenu();

            switch(userOption)
            {
                case "1":
                    loadFile();
                    System.out.println("\n======= Game File Loaded Successfully =======");
                    break;
                case "2":
                    startTheGame();
                    break;
                case "3":
                    m_GameEnded = true;
                    System.out.println("BYE BYE...");
                    break;
                default:
                    System.out.println("Invalid input - Please enter one of the option above");
            }
        }
    }

    private String printMainMenu()
    {
        System.out.println("\nPlease select one of the options using the number associated with it:");
        System.out.println("(1) Load Game File");
        System.out.println("(2) Start Game");
        System.out.println("(3) Exit Game");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void loadFile()
    {
        m_GameFileLoaded = false;

        while(!m_GameFileLoaded)
        {
            try
            {
                String path = getFilePathFromUser();
                //BattleShipGame gameData = (SchemaBasedJAXB.createBattleShipGame(path));
//                m_Settings.buildGameSettings(gameData);
//                m_GameEngine = new GameEngine(m_Settings);
                m_GameFileLoaded = true;
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    private String getFilePathFromUser()
    {
        System.out.print("\nPlease enter the full path to your '.xml' file: ");
        Scanner scanner = new Scanner(System.in);
        String userFilePath = scanner.nextLine();

        return userFilePath;
    }

    private void startTheGame()
    {
        if(m_GameFileLoaded)
        {
            try
            {
            //    m_GameUI = new GameUI(m_GameEngine);
            //    m_GameUI.start();
           //     m_GameEnded = !m_GameUI.isRestartGame();
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("Can't start the game - Please load your data file (.xml) before attempting to start the game.");
        }
    }
}
