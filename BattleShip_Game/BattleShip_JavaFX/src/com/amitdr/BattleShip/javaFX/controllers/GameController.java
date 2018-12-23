package com.amitdr.BattleShip.javaFX.controllers;

import com.amitdr.BattleShip.BattleShipApplication;
import com.amitdr.BattleShip.javaFX.boards.BoardGrid;
import com.amitdr.BattleShip.javaFX.boxes.AlertBox;
import com.amitdr.BattleShip.javaFX.boxes.ConfirmBox;
import com.amitdr.BattleShip.javaFX.event.TileEventHandler;
import com.amitdr.BattleShip.javaFX.interfaces.ControlledScreen;
import com.amitdr.BattleShip.javaFX.replay.Replay;
import com.amitdr.BattleShip.javaFX.replay.ReplayNode;
import com.amitdr.BattleShip.javaFX.tiles.Mine;
import com.amitdr.BattleShip.javaFX.tiles.TileUtils;
import com.amitdr.BattleShip.logic.start.Settings;
import com.amitdr.BattleShip.logic.engine.GameEngine;
import javafx.application.Platform;
import javafx.beans.value.ObservableListValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GameController implements ControlledScreen
{
    @FXML
    private VBox m_P1Info;
    @FXML
    private Label m_P1ScoreLabel;
    @FXML
    private Label m_P1HitsLabel;
    @FXML
    private Label m_P1MissesLabel;
    @FXML
    private Label m_P1AvgTimeLabel;
    @FXML
    private MenuButton m_P1ShipsButton;
    @FXML
    private Label m_PlayerTurnLabel;
    @FXML
    private Label m_TurnNumberLabel;
    @FXML
    private VBox m_P2Info;
    @FXML
    private VBox m_Info;
    @FXML
    private Label m_P2ScoreLabel;
    @FXML
    private Label m_P2HitsLabel;
    @FXML
    private Label m_P2MissesLabel;
    @FXML
    private Label m_P2AvgTimeLabel;
    @FXML
    private MenuButton m_P2ShipsButton;
    @FXML
    private GridPane m_ShipsGrid;
    @FXML
    private GridPane m_HitsGrid;
    @FXML
    private StackPane m_ShipsStackPane;
    @FXML
    private StackPane m_HitsStackPane;
    @FXML
    private MenuItem m_NewGameMenuItem;
    @FXML
    private MenuItem m_RestartGameMenuItem;
    @FXML
    private MenuItem m_SurrenderMenuItem;
    @FXML
    private MenuItem m_ExitMenuItem;
    @FXML
    private VBox m_P1MinesBox;
    @FXML
    private VBox m_P2MinesBox;
    @FXML
    private GridPane m_P1MineGridPane;
    @FXML
    private GridPane m_P2MineGridPane;
    @FXML
    private StackPane m_P1MineStack;
    @FXML
    private StackPane m_P2MineStack;
    @FXML
    private HBox m_RepalyPanel;
    @FXML
    private Button m_NextButton;
    @FXML
    private Button m_PrevButton;


    private BoardGrid m_P1ShipsGrid;
    private BoardGrid m_P2ShipsGrid;
    private BoardGrid m_P1HitsGrid;
    private BoardGrid m_P2HitsGrid;
    private ScreensController m_MyScreensController;
    private GameEngine m_GameEngine;
    private Settings m_Settings;
    private Replay m_Replay;
    private ReplayNode m_ReplayNode;

    @FXML
    void newGameMenuItem_OnClick(ActionEvent event)
    {
        m_MyScreensController.setScreen(BattleShipApplication.LauncherScreen_ID);
    }

    @FXML
    void restartGameMenuItem_OnClick(ActionEvent event)
    {
        m_MyScreensController.setScreen(BattleShipApplication.GameScreen_ID);
    }

    @FXML
    void exitMenuItem_OnClick(ActionEvent event)
    {
        Platform.exit();
    }

    @FXML
    void surrenderMenuItem_OnClick(ActionEvent event)
    {
        m_GameEngine.stop();
        render();
    }

    @Override
    public void initialize(ScreensController i_ScreensController)
    {
        try
        {
            m_GameEngine = m_MyScreensController.getGameEngine();
            m_Settings = m_MyScreensController.getSettings();
            m_GameEngine.start();
            TileUtils.setCurrentGameEngine(m_GameEngine);
            TileUtils.setCurrentGameController(this);
            m_Info.setStyle("-fx-background-color: #FEF6EB");
            m_P1Info.setStyle("-fx-background-color: #BEB9B5 ");
            m_P2Info.setStyle("-fx-background-color: #BEB9B5 ");

            //Replay
            m_Replay = new Replay(this);

            render();
        }
        catch(Exception e)

        {
            e.printStackTrace();
        }
    }

    public void replayRender(ReplayNode i_ReplayNode)
    {
        m_PlayerTurnLabel.setText(i_ReplayNode.PlayerTurnLabel);
        m_TurnNumberLabel.setText(i_ReplayNode.TurnNumberLabel);

        m_P1ScoreLabel.setText(i_ReplayNode.P1ScoreLabel);
        m_P1HitsLabel.setText(i_ReplayNode.P1HitsLabel);
        m_P1MissesLabel.setText(i_ReplayNode.P1MissesLabel);
        m_P1AvgTimeLabel.setText(i_ReplayNode.P1AvgTimeLabel);
        m_P1ShipsButton.getItems().clear();
        m_P1ShipsButton.getItems().addAll(i_ReplayNode.P1ShipsButton);

        m_P2ScoreLabel.setText(i_ReplayNode.P2ScoreLabel);
        m_P2HitsLabel.setText(i_ReplayNode.P2HitsLabel);
        m_P2MissesLabel.setText(i_ReplayNode.P2MissesLabel);
        m_P2AvgTimeLabel.setText(i_ReplayNode.P2AvgTimeLabel);
        m_P2ShipsButton.getItems().clear();
        m_P2ShipsButton.getItems().addAll(i_ReplayNode.P2ShipsButton);

        m_ShipsStackPane.getChildren().clear();
        m_ShipsStackPane.getChildren().add(i_ReplayNode.ShipGrid);

        m_HitsStackPane.getChildren().clear();
        m_HitsStackPane.getChildren().add(i_ReplayNode.HitsGrid);

        m_P1MineStack.getChildren().clear();
        m_P2MineStack.getChildren().clear();

        m_P1MineStack.getChildren().add(i_ReplayNode.P1MinesBox);
        m_P2MineStack.getChildren().add(i_ReplayNode.P2MinesBox);
    }


    public void render()
    {
        m_ReplayNode = new ReplayNode();

        int currentPlayer = m_GameEngine.getCurrentPlayerID();
        int numberOfTurns = m_GameEngine.getNumberOfTurns();

        if(currentPlayer != 0)
            m_PlayerTurnLabel.setText("Player " + currentPlayer + " Turn");
        else
        {
            m_PlayerTurnLabel.setText("Player " + m_GameEngine.getWinner() + " Win!!!");
            numberOfTurns--;
        }
        m_ReplayNode.PlayerTurnLabel = m_PlayerTurnLabel.getText();

        m_TurnNumberLabel.setText("Turn Number: " + (numberOfTurns + 1));
        m_ReplayNode.TurnNumberLabel = m_TurnNumberLabel.getText();

        m_P1ScoreLabel.setText("Score: " + m_GameEngine.getPlayerOneScore());
        m_P1HitsLabel.setText("Hits: " + m_GameEngine.getPlayerOneHitsNumber());
        m_P1MissesLabel.setText("Miss: " + m_GameEngine.getPlayerOneMissNumber());
        m_P1AvgTimeLabel.setText("Avg Time: " + m_GameEngine.getPlayerOneAverageMoveTime());
        m_ReplayNode.P1ShipsButton = renderLiveShips(m_P1ShipsButton, m_GameEngine.getPlayerOneLiveShipsList());

        //Replay
        m_ReplayNode.P1ScoreLabel = m_P1ScoreLabel.getText();
        m_ReplayNode.P1HitsLabel = m_P1HitsLabel.getText();
        m_ReplayNode.P1MissesLabel = m_P1MissesLabel.getText();
        m_ReplayNode.P1AvgTimeLabel = m_P1AvgTimeLabel.getText();
        //m_ReplayNode.P1ShipsButton = m_P1ShipsButton.getItems();

        m_P2ScoreLabel.setText("Score: " + m_GameEngine.getPlayerTwoScore());
        m_P2HitsLabel.setText("Hits: " + m_GameEngine.getPlayerTwoHitsNumber());
        m_P2MissesLabel.setText("Miss: " + m_GameEngine.getPlayerTwoMissNumber());
        m_P2AvgTimeLabel.setText("Avg Time: " + m_GameEngine.getPlayerTwoAverageMoveTime());
        m_ReplayNode.P2ShipsButton = renderLiveShips(m_P2ShipsButton, m_GameEngine.getPlayerTwoLiveShipsList());

        //Replay
        m_ReplayNode.P2ScoreLabel = m_P2ScoreLabel.getText();
        m_ReplayNode.P2HitsLabel = m_P2HitsLabel.getText();
        m_ReplayNode.P2MissesLabel = m_P2MissesLabel.getText();
        m_ReplayNode.P2AvgTimeLabel = m_P2AvgTimeLabel.getText();
        //m_ReplayNode.P2ShipsButton = m_P2ShipsButton.getItems();

        int currentPlayerID = m_GameEngine.getCurrentPlayerID();

        renderCurrentPlayerBoards(currentPlayerID);
        renderCurrentPlayerMine();

        m_ReplayNode.AttackPosX = m_GameEngine.getAttackPosX();
        m_ReplayNode.AttackPosY = m_GameEngine.getAttackPosY();

        m_Replay.addPlayerMove(m_ReplayNode);


        if(currentPlayer == 0)
        {
            m_SurrenderMenuItem.setDisable(true);

            String theWinner = "Player " + m_GameEngine.getWinner() + " Win!!!";
            AlertBox alertBox = new AlertBox(theWinner, theWinner);

            alertBox.getNewGameButton().setOnAction((e)->{
                newGameMenuItem_OnClick(e);
                alertBox.close();
            });
            alertBox.getRestartButton().setOnAction((e)->{
                restartGameMenuItem_OnClick(e);
                alertBox.close();
            });
            alertBox.getReplayButton().setOnAction((e)->{
                m_Replay.setNextEvent(m_NextButton);
                m_Replay.setPrevEvent(m_PrevButton);
                m_RepalyPanel.setDisable(false);
                m_RepalyPanel.setVisible(true);

                m_ShipsStackPane.setDisable(true);
                m_HitsStackPane.setDisable(true);
                m_P1MineStack.setDisable(true);
                m_P2MineStack.setDisable(true);
                m_P1MinesBox.setDisable(true);
                m_P2MinesBox.setDisable(true);

                alertBox.close();
            });

            alertBox.display();
        }

    }

    private List<MenuItem> renderLiveShips(MenuButton i_ShipsMenuButton, Map<String, Integer> i_ShipList)
    {
        i_ShipsMenuButton.getItems().clear();
        List<MenuItem> list = new ArrayList<>();

        for(Map.Entry<String, Integer> entry : i_ShipList.entrySet())
        {
            String shipTypeName = entry.getKey();
            int liveShipsNumber = entry.getValue();

            MenuItem menuItem = new MenuItem();
            menuItem.setText(shipTypeName + ": " + liveShipsNumber);
            list.add(menuItem);
            i_ShipsMenuButton.getItems().add(menuItem);
        }

        return list;
    }

    private void renderCurrentPlayerMine()
    {
        m_P1MinesBox.getChildren().clear();
        m_P2MinesBox.getChildren().clear();

        VBox mineBoxP1 = new VBox(2);
        mineBoxP1.setAlignment(Pos.CENTER);
        mineBoxP1.getChildren().addAll(createNumbersNodes(m_GameEngine.getPlayerOne().getMineNumber()));

        VBox mineBoxP2 = new VBox(2);
        mineBoxP2.setAlignment(Pos.CENTER);
        mineBoxP2.getChildren().addAll(createNumbersNodes(m_GameEngine.getPlayerTwo().getMineNumber()));

        m_P1MinesBox = mineBoxP1;
        m_P2MinesBox = mineBoxP2;

        //Replay
        m_ReplayNode.P1MinesBox = m_P1MinesBox;
        m_ReplayNode.P2MinesBox = m_P2MinesBox;

        m_P1MineStack.getChildren().clear();
        m_P2MineStack.getChildren().clear();

        m_P1MineStack.getChildren().add(m_P1MinesBox);
        m_P2MineStack.getChildren().add(m_P2MinesBox);
    }

    public static Node[] createNumbersNodes(final int i_NumberOfMines)
    {
        Node[] nodes = new Node[i_NumberOfMines];

        for(int i = 0; i < nodes.length; i++)
        {
            nodes[i] = Mine.createNumberNode(i);
        }
        return nodes;
    }

    private void renderCurrentPlayerBoards(int i_CurrentPlayerID)
    {
        TileEventHandler tileEvent = new TileEventHandler(this);
        int boardSize = m_GameEngine.getBoardSize();
        double widthSize = m_ShipsGrid.getPrefWidth() / (double) (boardSize + 2);
        double heightSize = m_ShipsGrid.getPrefHeight() / (double) (boardSize + 2);

        switch(i_CurrentPlayerID)
        {
            case 0: //We have a Winner
                renderCurrentPlayerBoards(m_GameEngine.getWinner());
                m_P1MineStack.setDisable(true);
                m_P2MineStack.setDisable(true);
                m_P1MinesBox.setDisable(true);
                m_P2MinesBox.setDisable(true);
                break;
            case 1:
                //render P1 boards
                m_P1ShipsGrid = new BoardGrid(m_GameEngine.getPlayerOne().getShipBoard(), widthSize, heightSize, true, null);
                m_P1HitsGrid = new BoardGrid(m_GameEngine.getPlayerOne().getHitsBoard(), widthSize, heightSize, false, tileEvent);

                //Replay
                m_ReplayNode.ShipGrid = m_P1ShipsGrid;
                m_ReplayNode.HitsGrid = m_P1HitsGrid;

                m_ShipsStackPane.getChildren().remove(0);
                m_ShipsStackPane.getChildren().add(0, m_P1ShipsGrid);

                m_HitsStackPane.getChildren().remove(0);
                m_HitsStackPane.getChildren().add(0, m_P1HitsGrid);

                m_P1MineStack.setDisable(false);
                m_P2MineStack.setDisable(true);
                break;
            case 2:
                //render P2 boards
                m_P2ShipsGrid = new BoardGrid(m_GameEngine.getPlayerTwo().getShipBoard(), widthSize, heightSize, true, null);
                m_P2HitsGrid = new BoardGrid(m_GameEngine.getPlayerTwo().getHitsBoard(), widthSize, heightSize, false, tileEvent);

                //Replay
                m_ReplayNode.ShipGrid = m_P2ShipsGrid;
                m_ReplayNode.HitsGrid = m_P2HitsGrid;

                m_ShipsStackPane.getChildren().remove(0);
                m_ShipsStackPane.getChildren().add(0, m_P2ShipsGrid);

                m_HitsStackPane.getChildren().remove(0);
                m_HitsStackPane.getChildren().add(0, m_P2HitsGrid);

                m_P2MineStack.setDisable(false);
                m_P1MineStack.setDisable(true);
                break;
        }
    }

    public GameEngine getGameEngine()
    {
        return m_GameEngine;
    }

    public Settings getSettings()
    {
        return m_Settings;
    }

    @Override
    public void setScreenParent(ScreensController i_ScreensController)
    {
        m_MyScreensController = i_ScreensController;
    }
}
