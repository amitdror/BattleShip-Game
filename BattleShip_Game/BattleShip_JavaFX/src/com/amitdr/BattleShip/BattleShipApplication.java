package com.amitdr.BattleShip;

import com.amitdr.BattleShip.javaFX.controllers.GameController;
import com.amitdr.BattleShip.javaFX.controllers.ScreensController;
import com.amitdr.BattleShip.logic.start.Settings;
import com.amitdr.BattleShip.logic.engine.GameEngine;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BattleShipApplication extends Application
{
    private static final String WINDOW_TITLE = "Battle Ship";
    public static final String LauncherScreen_FILE = "/com/amitdr/BattleShip/javaFX/FXML/Launcher.fxml";
    public static final String GameScreen_FILE = "/com/amitdr/BattleShip/javaFX/FXML/Game.fxml";
    public static final String LauncherScreen_ID = "LauncherScreen";
    public static final String GameScreen_ID = "GameScreen";

    private ScreensController m_ScreenController;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        m_ScreenController = new ScreensController();
        m_ScreenController.loadScreen(BattleShipApplication.LauncherScreen_ID, BattleShipApplication.LauncherScreen_FILE);
        m_ScreenController.setScreen(BattleShipApplication.LauncherScreen_ID);
        //Group root = new Group();
        //root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(m_ScreenController);
        m_ScreenController.setStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
