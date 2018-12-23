package com.amitdr.BattleShip.logic.start;

import com.amitdr.BattleShip.logic.engine.entities.GameShip;
import com.amitdr.BattleShip.logic.engine.entities.Player;
import com.amitdr.BattleShip.logic.engine.entities.types.GameShipType;
import com.amitdr.BattleShip.logic.engine.types.Type;
import com.amitdr.BattleShip.logic.exceptions.SettingInvalidException;
import com.amitdr.BattleShip.logic.jaxb.schema.generated.BattleShipGame;
import com.amitdr.BattleShip.logic.jaxb.schema.generated.BattleShipGame.Boards.Board;
import com.amitdr.BattleShip.logic.jaxb.schema.generated.BattleShipGame.ShipTypes;
import com.amitdr.BattleShip.logic.utils.GameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings
{
    private static final int MAX_BOARD_SIZE = 20;
    private static final int MIN_BOARD_SIZE = 5;
    private static final int EMPTY = -1;
    private static final String PLAYER_ONE_NAME = "Player 1";
    private static final String PLAYER_TWO_NAME = "Player 2";

    private Map<Integer, GameShipType> m_ShipTypes;
    private Map<Integer, List<GameShip>> m_ShipsP1;
    private Map<Integer, List<GameShip>> m_ShipsP2;
    private boolean m_GameLoaded;
    private int[][] m_BoardP1;
    private int[][] m_BoardP2;
    private int m_BoardSize;
    private int m_Mines;
    private String m_GameType;

    public Settings()
    {
        m_GameLoaded = false;
    }

    public void buildGameSettings(BattleShipGame i_GameData) throws SettingInvalidException
    {
        m_GameType = i_GameData.getGameType();
        checkBoardSize(i_GameData);
        checkNumberOfBoards(i_GameData.getBoards());
        checkShipTypes(i_GameData.getShipTypes());
        checkMine(i_GameData.getMine());
        buildGameEntities(i_GameData);
        checkShipsAmount();

        m_GameLoaded = true;
    }

    public String getGameType(){
        return m_GameType;
    }

    public Player getFirstPlayer() throws SettingInvalidException
    {
        if(m_GameLoaded)
        {
            int[][] newPlayerBoard = copyBoard(m_BoardP1);
            HashMap<Integer, List<GameShip>> newShipList = copyShipHashMap(m_ShipsP1);
            return new Player(PLAYER_ONE_NAME, newShipList, newPlayerBoard, new int[m_BoardSize][m_BoardSize], m_Mines);
        }
        else
        {
            throw new SettingInvalidException("Game Not Loaded - Reload Your Data File First.");
        }
    }

    public Player getSecondPlayer() throws SettingInvalidException
    {
        if(m_GameLoaded)
        {
            int[][] newPlayerBoard = copyBoard(m_BoardP2);
            HashMap<Integer, List<GameShip>> newShipList = copyShipHashMap(m_ShipsP2);

            return new Player(PLAYER_TWO_NAME, newShipList, newPlayerBoard, new int[m_BoardSize][m_BoardSize], m_Mines);
        }
        else
        {
            throw new SettingInvalidException("Game Not Loaded - Reload Your Data File First");
        }
    }

    private HashMap<Integer, List<GameShip>> copyShipHashMap(Map<Integer, List<GameShip>> i_ShipsMapToCopy)
    {
        HashMap<Integer, List<GameShip>> resHashMap = new HashMap<>();

        for(Map.Entry<Integer, List<GameShip>> entry : i_ShipsMapToCopy.entrySet())
        {
            int typeID = entry.getKey();
            List<GameShip> shipsList = entry.getValue();
            List<GameShip> newShipsList = new ArrayList<GameShip>();

            for(GameShip ship : shipsList)
            {
                List<GameShip.ShipPart> newShipPartsList = new ArrayList<>();

                for(GameShip.ShipPart shipPart : ship.getShipParts())
                {
                    GameShip.ShipPart newShipPart = new GameShip.ShipPart(shipPart.getX(), shipPart.getY());
                    newShipPartsList.add(newShipPart);
                }

                GameShip newGameShip = new GameShip(typeID, newShipPartsList);
                newShipsList.add(newGameShip);
            }

            resHashMap.put(typeID, newShipsList);
        }

        return resHashMap;
    }

    private int[][] copyBoard(int[][] i_BoardToCopy)
    {
        int size = i_BoardToCopy.length;
        int[][] res = new int[size][size];

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                res[i][j] = i_BoardToCopy[i][j];
            }
        }
        return res;
    }


    public Map<Integer, GameShipType> getShipTypes() throws SettingInvalidException
    {
        if(m_GameLoaded)
        {
            return new HashMap<Integer, GameShipType>(m_ShipTypes);
        }
        else
        {
            throw new SettingInvalidException("======= Game Not Loaded - Reload Your Data File First =======");
        }
    }


    public int getBoardSize() throws SettingInvalidException
    {
        if(m_GameLoaded)
        {
            return m_BoardSize;
        }
        else
        {
            throw new SettingInvalidException("======= Game Not Loaded - Reload Your Data File First =======");
        }
    }

    public int getMinesNumber() throws SettingInvalidException
    {
        if(m_GameLoaded)
        {
            return m_Mines;
        }
        else
        {
            throw new SettingInvalidException("======= Game Not Loaded - Reload Your Data File First =======");
        }
    }

    public static int getEMPTY()
    {
        return EMPTY;
    }

    private void buildGameEntities(BattleShipGame i_GameData) throws SettingInvalidException
    {
        m_ShipsP1 = new HashMap<Integer, List<GameShip>>();
        m_ShipsP2 = new HashMap<Integer, List<GameShip>>();
        m_ShipTypes = new HashMap<Integer, GameShipType>();
        Type.clearTypesList();

        for(ShipTypes.ShipType shipType : i_GameData.getShipTypes().getShipType())
        {
            String strTypeID = shipType.getId();
            int typeID = strTypeID.hashCode();

            Type.addType(typeID, strTypeID);
            m_ShipsP1.put(typeID, new ArrayList<GameShip>());
            m_ShipsP2.put(typeID, new ArrayList<GameShip>());
            GameShipType newShipType = new GameShipType(typeID, shipType.getScore(), shipType.getAmount(), shipType.getLength());
            if(m_ShipTypes.containsKey(typeID))
            {
                throw new SettingInvalidException("Setting Error - Check you .xml File there is two different ship types with the same name");
            }
            m_ShipTypes.put(typeID, newShipType);
        }

        Board boardP1 = i_GameData.getBoards().getBoard().get(0);
        Board boardP2 = i_GameData.getBoards().getBoard().get(1);
        m_BoardP1 = getEmptyBoard();
        m_BoardP2 = getEmptyBoard();

        buildShipsAndBoard(m_ShipsP1, m_BoardP1, boardP1);
        buildShipsAndBoard(m_ShipsP2, m_BoardP2, boardP2);
    }

    private void buildShipsAndBoard(Map<Integer, List<GameShip>> i_Ships, int[][] i_PlayerBoard, Board i_Board) throws SettingInvalidException
    {
        for(Board.Ship ship : i_Board.getShip())
        {
            GameShip newGameShip = createShip(ship, i_PlayerBoard);
            int shipType = ship.getShipTypeId().hashCode();
            i_Ships.get(shipType).add(newGameShip);
        }
    }

    private GameShip createShip(Board.Ship i_Ship, int[][] i_PlayerBoard) throws SettingInvalidException
    {
        List<GameShip.ShipPart> shipParts = new ArrayList<GameShip.ShipPart>();
        int typeID = i_Ship.getShipTypeId().hashCode();
        int posY = i_Ship.getPosition().getX() - 1;
        int posX = i_Ship.getPosition().getY() - 1;
        String shipDirection = i_Ship.getDirection();
        int shipLength = m_ShipTypes.get(typeID).getLength();

        switch(shipDirection)
        {
            case "ROW":
                for(int i = 0; i < shipLength; i++)
                {
                    if(GameUtils.isPointOnBoard(posX + i, posY, m_BoardSize) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, posX + i, posY) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, posX + i, posY, typeID))
                    {
                        i_PlayerBoard[posY][posX + i] = typeID;
                        shipParts.add(new GameShip.ShipPart(posX + i, posY));
                    }
                    else
                    {
                        throw new SettingInvalidException("Setting Error - Check you .xml File the ships are not positioned correctly");
                    }
                }
                break;
            case "COLUMN":
                for(int i = 0; i < shipLength; i++)
                {
                    if(GameUtils.isPointOnBoard(posX, posY + i, m_BoardSize) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, posX, posY + i) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, posX, posY + i, typeID))
                    {
                        i_PlayerBoard[posY + i][posX] = typeID;
                        shipParts.add(new GameShip.ShipPart(posX, posY + i));
                    }
                    else
                    {
                        throw new SettingInvalidException("Setting Error - Check you .xml File the ships are not properly positioned");
                    }
                }
                break;
            default:
                if(!createLShapeShip(i_PlayerBoard, shipParts, posX, posY, shipLength, shipDirection, typeID))
                {
                    throw new SettingInvalidException("Setting Error - Check you .xml File the ships are not properly positioned");
                }
                break;
        }

        GameShip resShip = new GameShip(typeID, shipParts);
        return resShip;
    }

    private boolean createLShapeShip(int[][] i_PlayerBoard, List<GameShip.ShipPart> i_ShipParts,
                                     int i_PosX, int i_PosY, int i_ShipLength, String i_ShipDirection, int i_TypeID)
    {
        boolean result = false;

        switch(i_ShipDirection)
        {
            case "DOWN_RIGHT":
                for(int i = 0; i < i_ShipLength; i++)
                {
                    if(GameUtils.isPointOnBoard(i_PosX, i_PosY - i, m_BoardSize) &&
                            GameUtils.isPointOnBoard(i_PosX + i, i_PosY, m_BoardSize) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, i_PosX, i_PosY - i) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, i_PosX + i, i_PosY) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, i_PosX, i_PosY - i, i_TypeID) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, i_PosX + i, i_PosY, i_TypeID))
                    {
                        i_PlayerBoard[i_PosY - i][i_PosX] = i_TypeID;
                        if(i != 0)
                        {
                            i_PlayerBoard[i_PosY][i_PosX + i] = i_TypeID;
                        }
                        i_ShipParts.add(new GameShip.ShipPart(i_PosX, i_PosY - i));
                        i_ShipParts.add(new GameShip.ShipPart(i_PosX + i, i_PosY));
                        result = true;
                    }
                }
                break;
            case "UP_RIGHT":
                for(int i = 0; i < i_ShipLength; i++)
                {
                    if(GameUtils.isPointOnBoard(i_PosX, i_PosY + i, m_BoardSize) &&
                            GameUtils.isPointOnBoard(i_PosX + i, i_PosY, m_BoardSize) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, i_PosX, i_PosY + i) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, i_PosX + i, i_PosY) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, i_PosX, i_PosY + i, i_TypeID) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, i_PosX + i, i_PosY, i_TypeID))
                    {
                        i_PlayerBoard[i_PosY + i][i_PosX] = i_TypeID;
                        i_PlayerBoard[i_PosY][i_PosX + i] = i_TypeID;
                        i_ShipParts.add(new GameShip.ShipPart(i_PosX, i_PosY + i));
                        if(i != 0)
                        {
                            i_ShipParts.add(new GameShip.ShipPart(i_PosX + i, i_PosY));
                        }
                        result = true;
                    }
                }
                break;
            case "RIGHT_UP":
                for(int i = 0; i < i_ShipLength; i++)
                {
                    if(GameUtils.isPointOnBoard(i_PosX, i_PosY - i, m_BoardSize) &&
                            GameUtils.isPointOnBoard(i_PosX - i, i_PosY, m_BoardSize) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, i_PosX, i_PosY - i) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, i_PosX - i, i_PosY) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, i_PosX, i_PosY - i, i_TypeID) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, i_PosX - i, i_PosY, i_TypeID))
                    {
                        i_PlayerBoard[i_PosY - i][i_PosX] = i_TypeID;
                        i_PlayerBoard[i_PosY][i_PosX - i] = i_TypeID;
                        i_ShipParts.add(new GameShip.ShipPart(i_PosX, i_PosY - i));
                        if(i != 0)
                        {
                            i_ShipParts.add(new GameShip.ShipPart(i_PosX - i, i_PosY));
                        }
                        result = true;
                    }
                }
                break;
            case "RIGHT_DOWN":
                for(int i = 0; i < i_ShipLength; i++)
                {
                    if(GameUtils.isPointOnBoard(i_PosX, i_PosY + i, m_BoardSize) &&
                            GameUtils.isPointOnBoard(i_PosX - i, i_PosY, m_BoardSize) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, i_PosX, i_PosY + i) &&
                            GameUtils.isEmptyPoint(i_PlayerBoard, i_PosX - i, i_PosY) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, i_PosX, i_PosY + i, i_TypeID) &&
                            GameUtils.isPointAreaClear(i_PlayerBoard, i_PosX - i, i_PosY, i_TypeID))
                    {
                        i_PlayerBoard[i_PosY + i][i_PosX] = i_TypeID;
                        i_PlayerBoard[i_PosY][i_PosX - i] = i_TypeID;
                        i_ShipParts.add(new GameShip.ShipPart(i_PosX, i_PosY + i));
                        if(i != 0)
                        {
                            i_ShipParts.add(new GameShip.ShipPart(i_PosX - i, i_PosY));
                        }
                        result = true;
                    }
                }
                break;

        }

        return result;
    }


    private int[][] getEmptyBoard()
    {
        int[][] resArray = new int[m_BoardSize][m_BoardSize];

        for(int i = 0; i < m_BoardSize; i++)
        {
            for(int j = 0; j < m_BoardSize; j++)
            {
                resArray[i][j] = Type.EMPTY;
            }
        }

        return resArray;
    }

    private void checkBoardSize(BattleShipGame i_Game) throws SettingInvalidException
    {
        m_BoardSize = i_Game.getBoardSize();

        if(m_BoardSize < MIN_BOARD_SIZE || m_BoardSize > MAX_BOARD_SIZE)
        {
            throw new SettingInvalidException("Settings Error - Check your .xml file the board size mut be between " +
                    MIN_BOARD_SIZE + " - " + MAX_BOARD_SIZE);
        }
    }

    private void checkNumberOfBoards(BattleShipGame.Boards i_GameBoards) throws SettingInvalidException
    {
        if(i_GameBoards == null)
        {
            throw new SettingInvalidException("Settings Error - Check your .xml file the boards settings are missing");
        }
        if(i_GameBoards.getBoard().size() != 2)
        {
            throw new SettingInvalidException("Settings Error - Check your .xml file you need to have 2 boards");
        }
    }

    private void checkShipTypes(BattleShipGame.ShipTypes i_GameShipTypes) throws SettingInvalidException
    {
        if(i_GameShipTypes == null || i_GameShipTypes.getShipType() == null || i_GameShipTypes.getShipType().size() == 0)
        {
            throw new SettingInvalidException("Settings Error - Check your .xml file the ShipTypes settings are missing");
        }

        for(ShipTypes.ShipType shipType : i_GameShipTypes.getShipType())
        {
            if(shipType.getId() == null)
            {
                throw new SettingInvalidException("Setting Error - Check you .xml File the ShipType ID not given");
            }
            if(shipType.getAmount() <= 0)
            {
                throw new SettingInvalidException("Setting Error - Check you .xml File the ShipType amount must be non-negative number");
            }
            if(shipType.getLength() <= 0)
            {
                throw new SettingInvalidException("Setting Error - Check you .xml File the ShipType length must be non-negative number");
            }
            if(shipType.getScore() <= 0)
            {
                throw new SettingInvalidException("Setting Error - Check you .xml File the ShipType score must be non-negative number");
            }
        }

    }

    private void checkMine(BattleShipGame.Mine i_GameMine) throws SettingInvalidException
    {
        if(i_GameMine != null)
        {
            m_Mines = i_GameMine.getAmount();

            if(m_Mines < 0 || m_Mines > 2)
            {
                throw new SettingInvalidException("Setting Error - Check your .xml File the mine amount must be positive number or zero");
            }
        }
        else
        {
            m_Mines = 0;
        }
    }

    private void checkShipsAmount() throws SettingInvalidException
    {
        for(Map.Entry<Integer, GameShipType> type : m_ShipTypes.entrySet())
        {
            int typeID = type.getKey();
            int amountOfShips = type.getValue().getAmount();

            if(amountOfShips != m_ShipsP1.get(typeID).size() || amountOfShips != m_ShipsP2.get(typeID).size())
            {
                throw new SettingInvalidException("Setting Error - Check your .xml File the ships amount don't match there ShipType amount");
            }
        }
    }

}
