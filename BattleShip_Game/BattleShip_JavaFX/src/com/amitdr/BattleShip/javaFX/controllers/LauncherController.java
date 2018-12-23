package com.amitdr.BattleShip.javaFX.controllers;

import com.amitdr.BattleShip.BattleShipApplication;
import com.amitdr.BattleShip.javaFX.boxes.AlertPromptDialog;
import com.amitdr.BattleShip.javaFX.interfaces.ControlledScreen;
import com.amitdr.BattleShip.javaFX.boxes.AlertBox;
import com.amitdr.BattleShip.logic.jaxb.schema.generated.BattleShipGame;
import com.amitdr.BattleShip.logic.start.Settings;
import com.amitdr.BattleShip.logic.engine.GameEngine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.amitdr.BattleShip.logic.jaxb.schema.SchemaBasedJAXB.createBattleShipGame;

public class LauncherController implements ControlledScreen
{
    private static final int LOADING_TIME = 1;//////////////////todo: change back

    @FXML
    private Button m_StartGameButton;
    @FXML
    private Button m_LoadFileButton;
    @FXML
    private Button m_ExitButton;
    @FXML
    private ProgressBar m_LoadBar;

    private boolean m_IsFileSelected;
    private ScreensController m_MyScreensController;

    @FXML
    void startGameButton_Click(ActionEvent event)
    {
        if(m_IsFileSelected)
        {
            m_MyScreensController.loadScreen(BattleShipApplication.GameScreen_ID, BattleShipApplication.GameScreen_FILE);
            m_MyScreensController.setScreen(BattleShipApplication.GameScreen_ID);
        }
    }

    @FXML
    void loadFileButton_Click(ActionEvent event)
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select xml file");
           // fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/res/XML"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
            File userXmlFile = fileChooser.showOpenDialog(null);

            if(userXmlFile != null)
            {
                BattleShipGame gameData = createBattleShipGame(userXmlFile.getAbsolutePath());
                Settings settings = new Settings();
                settings.buildGameSettings(gameData);
                GameEngine gameEngine = new GameEngine(settings);
                m_MyScreensController.setGameEngine(gameEngine);
                m_MyScreensController.setSettings(settings);
                activeLoadBar();
            }
        }
        catch(Exception e)
        {
            //AlertBox.display("Load File Error", e.getMessage());
            AlertPromptDialog.show(m_MyScreensController.getStage(),e.getMessage());
        }
    }

    @FXML
    void exitButton_Click(ActionEvent event)
    {
        Stage currentStage = (Stage) m_ExitButton.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void setScreenParent(ScreensController i_ScreensParent)
    {
        m_MyScreensController = i_ScreensParent;
    }

    @Override
    public void initialize(ScreensController i_ScreensController)
    {
        m_LoadBar.setVisible(false);
        m_IsFileSelected = false;
    }

    private void activeLoadBar()
    {
        Timeline task = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(m_LoadBar.progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(LOADING_TIME),
                        new KeyValue(m_LoadBar.progressProperty(), 1)
                )
        );

        task.setOnFinished(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                m_IsFileSelected = true;
                m_StartGameButton.setDisable(false);
                m_LoadFileButton.setDisable(false);
                m_ExitButton.setDisable(false);
                m_LoadBar.setVisible(false);
            }
        });

        m_StartGameButton.setDisable(true);
        m_LoadFileButton.setDisable(true);
        m_ExitButton.setDisable(true);
        m_LoadBar.setVisible(true);
        task.playFromStart();
    }

}

