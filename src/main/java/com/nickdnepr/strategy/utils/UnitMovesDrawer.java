package com.nickdnepr.strategy.utils;

import com.nickdnepr.strategy.models.Unit;

public interface UnitMovesDrawer {

    UnitMovesDrawer CONSOLE_DRAWER = unit -> System.out.println(unit.getTitle() + " has moved to " + unit.getCoordinates() + " " + unit.getActionPoints() + " points left");

    void drawIteration(Unit unit);
}
