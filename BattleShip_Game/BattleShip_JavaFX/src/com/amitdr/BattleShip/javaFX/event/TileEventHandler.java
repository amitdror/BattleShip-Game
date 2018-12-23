package com.amitdr.BattleShip.javaFX.event;

import com.amitdr.BattleShip.javaFX.boxes.AlertBox;
import com.amitdr.BattleShip.javaFX.controllers.GameController;
import com.amitdr.BattleShip.javaFX.controllers.ScreensController;
import com.amitdr.BattleShip.javaFX.tiles.Tile;
import com.amitdr.BattleShip.logic.engine.GameEngine;
import com.amitdr.BattleShip.logic.exceptions.GameEngineException;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class TileEventHandler implements EventHandler<MouseEvent>
{
    private GameController m_GameController;
    private GameEngine m_GameEngine;

    public TileEventHandler(GameController i_MyController)
    {
        this.m_GameController = i_MyController;
        this.m_GameEngine = m_GameController.getGameEngine();
    }

    @Override
    public void handle(MouseEvent event)
    {
        if(event.getSource() instanceof Tile)
        {
            Tile tile = (Tile) event.getSource();
            int x = tile.getX();
            int y = tile.getY();
            //tile.setDisable(true);

            try
            {
                m_GameEngine.attackOpponent(x, y, System.currentTimeMillis());
                m_GameController.render();
            }
            catch(GameEngineException e)
            {
                e.printStackTrace();
            }

        }
    }
}
