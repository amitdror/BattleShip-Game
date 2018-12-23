package com.amitdr.BattleShip.javaFX.boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox
{
    private Stage m_Window;
    private Button m_NewGameButton;
    private Button m_RestartButton;
    private Button m_ReplayButton;
    private Button m_Close;

    public AlertBox(String i_Title, String i_Message)
    {
        m_Window = new Stage();

        m_Window.initModality(Modality.APPLICATION_MODAL); //Block interaction with other windows
        m_Window.setTitle(i_Title);
        m_Window.setMinWidth(400);
        m_Window.setMinHeight(400);

        //Label
        Label label = new Label();
        label.setText(i_Message);

        //NewGameButton
       m_NewGameButton = new Button("New Game");

        //RestartGameButton
        m_RestartButton = new Button("Restart Game");

        //ReplayButton
         m_ReplayButton = new Button("Replay");

        //Close Button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> m_Window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, m_NewGameButton, m_RestartButton, m_ReplayButton, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        m_Window.setScene(scene);
        m_Window.setOnCloseRequest((e)->e.consume());
    }

    public Button getNewGameButton()
    {
        return m_NewGameButton;
    }

    public Button getRestartButton()
    {
        return m_RestartButton;
    }

    public Button getReplayButton()
    {
        return m_ReplayButton;
    }

    public void display()
    {
        m_Window.showAndWait();
    }

    public void close()
    {
        m_Window.close();
    }

}
