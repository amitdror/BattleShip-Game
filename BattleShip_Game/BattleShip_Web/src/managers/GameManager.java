package managers;


import com.amitdr.BattleShip.logic.engine.GameEngine;
import com.amitdr.BattleShip.logic.jaxb.schema.generated.BattleShipGame;
import com.amitdr.BattleShip.logic.start.Settings;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.*;

import static com.amitdr.BattleShip.logic.jaxb.schema.SchemaBasedJAXB.createBattleShipGame;

public class GameManager
{
    private final Map<String, GameEngine> m_Games;
    private final Map<String,String> m_GamesFilesPath;

    public GameManager()
    {
        m_GamesFilesPath = new HashMap<String, String>();
        m_Games = new HashMap<String, GameEngine>();
    }

    public void addGame(String i_GameName, String i_XmlFilePath, String i_UserName) throws Exception
    {
        BattleShipGame gameData = createBattleShipGame(i_XmlFilePath);
        Settings settings = new Settings();
        settings.buildGameSettings(gameData);
        GameEngine gameEngine = new GameEngine(settings);
        gameEngine.start();

        gameEngine.setUploaderName(i_UserName);
        m_GamesFilesPath.put(i_GameName,i_XmlFilePath);
        m_Games.put(i_GameName,gameEngine);
    }

    public void removeGame(String i_GameName)
    {
        m_Games.remove(i_GameName);
        m_GamesFilesPath.remove(i_GameName);
    }

    public Map<String, String> getGames()
    {
        return Collections.unmodifiableMap(m_GamesFilesPath);
    }

    public boolean isGameExists(String i_GameName)
    {
        return m_Games.containsKey(i_GameName);
    }

    public boolean isGameStarted(String i_GameName)
    {
         return m_Games.get(i_GameName).isGameStarted();
    }

    public int addPlayer(String i_GameName, String i_UsernameFromSession)
    {
        return m_Games.get(i_GameName).addPlayer(i_UsernameFromSession);
    }

    public GameEngine getOneGame(String i_GameName)
    {
        return m_Games.get(i_GameName);
    }

    public void RestartGame(String i_GameName) throws Exception {

        String userName = m_Games.get(i_GameName).getUploaderName();
        m_Games.remove(i_GameName);
        String xmlFilePath = m_GamesFilesPath.get(i_GameName);

        addGame(i_GameName, xmlFilePath, userName);
      }
}
