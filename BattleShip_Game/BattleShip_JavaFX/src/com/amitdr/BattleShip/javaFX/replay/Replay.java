package com.amitdr.BattleShip.javaFX.replay;


import com.amitdr.BattleShip.javaFX.boards.BoardGrid;
import com.amitdr.BattleShip.javaFX.controllers.GameController;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Replay
{
    private GameController m_GameController;
    private List<ReplayNode> m_ReplayMoves = new ArrayList<>();
    private int m_MovesIndex = -1;

    public Replay(GameController i_GameController)
    {
        this.m_GameController = i_GameController;
    }

    public void addPlayerMove(ReplayNode i_CurrentPlayerMove)
    {
        m_ReplayMoves.add(i_CurrentPlayerMove);
        m_MovesIndex++;
    }

    private ReplayNode getNextPlayerMove()
    {
        if(m_MovesIndex < m_ReplayMoves.size() - 1)
        {
            return m_ReplayMoves.get(++m_MovesIndex);
        }
        return m_ReplayMoves.get(m_MovesIndex);
    }

    private ReplayNode getPrevPlayerMove()
    {
        if(m_MovesIndex > 0)
        {
            return m_ReplayMoves.get(--m_MovesIndex);
        }
        return m_ReplayMoves.get(m_MovesIndex);
    }

    public void setNextEvent(Button i_NextButton)
    {
        i_NextButton.setDisable(false);
        i_NextButton.setVisible(true);

        i_NextButton.setOnAction((e)->{
            m_GameController.replayRender(this.getNextPlayerMove());
        });
    }

    public void setPrevEvent(Button i_PrevButton)
    {
        i_PrevButton.setDisable(false);
        i_PrevButton.setVisible(true);

        i_PrevButton.setOnAction((e)->{
            m_GameController.replayRender(this.getPrevPlayerMove());
        });
    }



}
