package com.amitdr.BattleShip.javaFX.tiles;


import com.amitdr.BattleShip.logic.engine.GameEngine;
import com.amitdr.BattleShip.logic.engine.types.Type;
import com.amitdr.BattleShip.logic.exceptions.GameEngineException;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.concurrent.Callable;
import java.util.logging.Handler;


public class Tile extends StackPane
{
    private Text m_Text = new Text();
    private boolean m_IsShipBoardTile;
    private int m_ID;
    private int m_X, m_Y;

    public Tile(int i_X, int i_Y, double i_Width, double i_Height)
    {
        Rectangle border = new Rectangle(i_Width, i_Height);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        setStyle("-fx-background-color: black");
    }

    public Tile(int i_X, int i_Y, double i_Width, double i_Height, String i_Text, EventHandler<MouseEvent> i_OnClick, int i_ID, boolean i_Disable)
    {
        Rectangle border = new Rectangle(i_Width, i_Height);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        m_IsShipBoardTile = i_Disable;

        this.m_X = i_X;
        this.m_Y = i_Y;
        this.m_Text.setText(i_Text);
        this.m_Text.setFont(Font.font(Math.min(i_Height, i_Width) / 2));
        this.m_ID = i_ID;
        setColor(i_ID, i_Disable);
        setAlignment(Pos.CENTER);
        getChildren().addAll(border, m_Text);

        if(i_OnClick != null)
        {
           if(!m_IsShipBoardTile)
           {
               setOnMouseClicked(i_OnClick);
           }
        }

        this.setOnDragOver((event) -> {
            if(event.getDragboard().hasString())
            {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        this.setOnDragEntered((event) -> {
           if(m_IsShipBoardTile)
            {
                if(event.getDragboard().hasString())
                {
                    if(TileUtils.isPointAreaClear(m_X, m_Y))
                    {
                        this.setStyle("-fx-background-color: green");
                    }
                    else
                    {
                        this.setStyle("-fx-background-color: red");
                    }
                }
            }
            event.consume();
        });

        this.setOnDragExited((event) -> {
            this.setStyle("-fx-background-color: #FEF6EB");
            //this.setStyle("-fx-border-color: #FEF6EB; -fx-border-width: 1");
            event.consume();
            setDisable(!m_IsShipBoardTile);
        });

        this.setOnDragDropped((event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if(TileUtils.isPointAreaClear(m_X, m_Y))
            {
                try
                {
                    TileUtils.getGameEngine().addMine(m_X,m_Y);
                    TileUtils.getGameController().render();
                }
                catch(GameEngineException e)
                {
                    e.printStackTrace();
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

    }

    public Tile(int i_X, int i_Y, double i_Width, double i_Height, String i_Text, EventHandler<MouseEvent> i_OnClick, int i_ID)
    {
        this(i_X, i_Y, i_Width, i_Height, i_Text, i_OnClick, i_ID, true);
    }

    public void setColor(int i_ID, boolean i_Visible)
    {
        m_IsShipBoardTile = i_Visible;
        setDisable(true);

        switch(i_ID)
        {
            case Type.EMPTY:
                setStyle("-fx-background-color: #FEF6EB");
                setDisable(false);
                break;
            case Type.MISS:
                setStyle("-fx-background-color: dimgray");
                m_IsShipBoardTile = true;
                break;
            case Type.DESTROYED:
                setStyle("-fx-background-color: darkred");
                m_IsShipBoardTile = true;
                break;
            case Type.MINE:
                setStyle("-fx-background-color: blue");
                m_IsShipBoardTile = true;
                break;
            case Type.INVALID_HIT:
                setStyle("-fx-background-color: #C25B56");
                m_IsShipBoardTile = true;
                break;
            default:
                if(i_Visible)
                {
                    setStyle("-fx-background-color: limegreen");
                }
                else
                {
                    setStyle("-fx-background-color: #FEF6EB");
                }
        }
    }

    public int getX()
    {
        return m_X;
    }

    public int getY()
    {
        return m_Y;
    }

    private void open()
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), m_Text);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }


//    private void close()
//    {
//        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0), m_Text);
//        fadeTransition.setToValue(0);
//        fadeTransition.play();
//    }
}
