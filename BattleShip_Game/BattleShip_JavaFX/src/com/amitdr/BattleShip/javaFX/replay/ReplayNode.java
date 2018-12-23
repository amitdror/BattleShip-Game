package com.amitdr.BattleShip.javaFX.replay;

import com.amitdr.BattleShip.javaFX.boards.BoardGrid;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.util.List;

public class ReplayNode
{
    public String PlayerTurnLabel;
    public String TurnNumberLabel;

    public String P1ScoreLabel;
    public String P1HitsLabel;
    public String P1MissesLabel;
    public String P1AvgTimeLabel;
    public List<MenuItem> P1ShipsButton;
    public VBox P1MinesBox;

    public String P2ScoreLabel;
    public String P2HitsLabel;
    public String P2MissesLabel;
    public String P2AvgTimeLabel;
    public List<MenuItem> P2ShipsButton;
    public VBox P2MinesBox;

    public BoardGrid ShipGrid;
    public BoardGrid HitsGrid;

    public int AttackPosX;
    public int AttackPosY;
}
