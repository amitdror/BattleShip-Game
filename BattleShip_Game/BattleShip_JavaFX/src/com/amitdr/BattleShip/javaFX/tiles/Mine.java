package com.amitdr.BattleShip.javaFX.tiles;

import com.amitdr.BattleShip.logic.engine.types.Type;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class Mine
{
    private static final int NUMBER_OF_NODES = 10;

    public static Node createNumberNode(int number)
    {
        final Label mine = new Label();

        mine.setText("Mine");
        mine.setPrefSize(50, 75);
        mine.setAlignment(Pos.CENTER);
        mine.setStyle("-fx-border-color: gray; -fx-border-width: 1");

        mine.setOnDragDetected((event) -> {
            WritableImage snapshot = mine.snapshot(new SnapshotParameters(), null);
            Dragboard db = mine.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(number + "");
            db.setContent(content);
            db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);

            event.consume();
        });

        mine.setOnDragDone((event) -> {

            if(event.getTransferMode() == TransferMode.MOVE)
            {
                mine.setText("");
                mine.setDisable(true);
                mine.setStyle("-fx-background-color: black");
            }
            event.consume();
        });
        return mine;
    }


    }
