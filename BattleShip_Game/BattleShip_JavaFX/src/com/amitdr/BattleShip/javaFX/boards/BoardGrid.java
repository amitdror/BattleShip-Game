package com.amitdr.BattleShip.javaFX.boards;

import com.amitdr.BattleShip.javaFX.tiles.Tile;
import com.amitdr.BattleShip.logic.engine.types.Type;
import com.amitdr.BattleShip.logic.exceptions.SettingInvalidException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BoardGrid extends GridPane
{
    private int m_BoardSize;
    //private Tile[][] m_TileArray;
   // private Map<Integer, Map<Integer, Tile>> m_TileMap;

    public BoardGrid(int[][] i_Board, double i_TileWidthSize, double i_TileHeightSize, boolean i_Disable, EventHandler<MouseEvent> i_OnClick)
    {
        this.setGridLinesVisible(true);
        //this.setDisable(i_Disable);
        this.setStyle("-fx-border-color: white");
        this.setStyle("-fx-background-color: #BEB9B5");
        this.setAlignment(Pos.CENTER);

        int boardSize = i_Board.length;
        this.m_BoardSize = boardSize;
        //this.m_TileArray = new Tile[boardSize][boardSize];
        //this.m_TileMap = new HashMap<Integer, Map<Integer, Tile>>();
        char ch = 'A';

        this.add(new Tile(0, 0, i_TileWidthSize, i_TileHeightSize, "/", null, Type.INVALID_HIT), 0, 0);

        for(int i = 1; i <= boardSize; i++)
        {
            Tile letterText = new Tile(i, 0, i_TileWidthSize, i_TileHeightSize, String.valueOf(ch), null, Type.INVALID_HIT);
            Tile numberText = new Tile(0, i, i_TileWidthSize, i_TileHeightSize, String.valueOf(i), null, Type.INVALID_HIT);
            this.add(letterText, i, 0);
            this.add(numberText, 0, i);
            ch++;
        }

        for(int row = 1; row <= boardSize; row++)
        {
           // Map<Integer, Tile> newRow = new HashMap<>();

            for(int col = 1; col <= boardSize; col++)
            {
                Tile cell = new Tile(col - 1, row - 1, i_TileWidthSize, i_TileHeightSize, "", i_OnClick, i_Board[row - 1][col - 1], i_Disable);
                //newRow.put(col - 1, cell);
                this.add(cell, col, row);
//                m_TileArray[row-1][col-1] = cell;
            }
            //m_TileMap.put(row - 1, newRow);
        }
    }

//    public void render(int[][] i_Board)
//    {
//        for(int row = 0; row < m_BoardSize; row++)
//        {
//            for(int col = 0; col < m_BoardSize; col++)
//            {
////                Tile cell = m_TileArray[row][col];
//                Tile cell = m_TileMap.get(row).get(col);
//                cell.setColor(i_Board[row][col]);
//                System.out.println();
//            }
//        }
//    }
}
