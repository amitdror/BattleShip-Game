package com.amitdr.BattleShip.javaFX.boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox
{
    private static boolean m_Answer;

    public static boolean display(String i_Title, String i_Message)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(i_Title);
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(i_Message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            m_Answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            m_Answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        //display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return m_Answer;
    }


}
