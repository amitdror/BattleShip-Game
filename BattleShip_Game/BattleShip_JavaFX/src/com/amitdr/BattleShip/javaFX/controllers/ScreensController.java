package com.amitdr.BattleShip.javaFX.controllers;

import com.amitdr.BattleShip.javaFX.interfaces.ControlledScreen;
import com.amitdr.BattleShip.logic.start.Settings;
import com.amitdr.BattleShip.logic.engine.GameEngine;
import com.amitdr.BattleShip.logic.exceptions.SettingInvalidException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;

public class ScreensController extends StackPane
{
    private HashMap<String, Node> m_Screens = new HashMap<>();
    private HashMap<String, ControlledScreen> m_Controllers = new HashMap<>();
    private Stage m_MyStage;
    private GameEngine m_GameEngine;
    private Settings m_Settings;


    public ScreensController()
    {
        super();
    }

    public void setStage(Stage i_MyStage)
    {
        m_MyStage = i_MyStage;
    }

    public Stage getStage()
    {
        return m_MyStage;
    }

    public void setSettings(Settings i_Settings)
    {
        m_Settings = i_Settings;
    }

    public Settings getSettings()
    {
        return m_Settings;
    }

    public void setGameEngine(GameEngine i_GameEngine)
    {
        m_GameEngine = i_GameEngine;
    }

    public GameEngine getGameEngine()
    {
         GameEngine restartGameEngine = null;

        try
        {
            restartGameEngine =  new GameEngine(m_Settings);
        }
        catch(SettingInvalidException e)
        {
            e.printStackTrace();
        }

        return restartGameEngine;
    }

    public void addScreen(String i_ScreenName, Node i_Screen, ControlledScreen i_ScreenController)
    {
        m_Controllers.put(i_ScreenName, i_ScreenController);
        m_Screens.put(i_ScreenName, i_Screen) ;
    }

    public Node getScreen(String i_ScreenName)
    {
        return m_Screens.get(i_ScreenName);
    }

    public boolean loadScreen(String i_ScreenName, String i_Resource)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(i_Resource));
            Parent loadScreen = (Parent) loader.load();
            ControlledScreen screenController = (ControlledScreen) loader.getController();
            screenController.setScreenParent(this);
            //screenController.initialize(this);
            addScreen(i_ScreenName, loadScreen, screenController);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setScreen(final String i_ScreenName)
    {
        ControlledScreen controlledScreen = m_Controllers.get(i_ScreenName);

        if(controlledScreen != null)
        {
            controlledScreen.initialize(this);
        }

        if(m_Screens.get(i_ScreenName) != null)
        {
            final DoubleProperty opacity = opacityProperty();

            if(!getChildren().isEmpty())
            {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>()
                        {
                            @Override
                            public void handle(ActionEvent event)
                            {
                                getChildren().remove(0);
                                getChildren().add(0, m_Screens.get(i_ScreenName));
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();
            }
            else
            {
                setOpacity(0.0);
                getChildren().add(m_Screens.get(i_ScreenName));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }

            return true;
        }
        else{
            return false;
        }
    }

    public boolean inloadScreen(String i_ScreenName)
    {
        return (m_Screens.remove(i_ScreenName) == null);
    }

}
