package com.amitdr.BattleShip.javaFX.interfaces;

import com.amitdr.BattleShip.javaFX.controllers.ScreensController;

public interface ControlledScreen
{
    public void setScreenParent(ScreensController i_ScreensController);

    public void initialize(ScreensController i_ScreensController);
}
