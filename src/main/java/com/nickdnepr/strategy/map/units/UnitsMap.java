package com.nickdnepr.strategy.map.units;

import com.nickdnepr.strategy.map.Coordinates;
import com.nickdnepr.strategy.map.routing.Route;
import com.nickdnepr.strategy.map.routing.RoutingPredicate;
import com.nickdnepr.strategy.map.routing.graphComponents.Point;
import com.nickdnepr.strategy.map.routing.graphComponents.Rib;
import com.nickdnepr.strategy.map.surface.SurfaceMap;
import com.nickdnepr.strategy.models.Player;
import com.nickdnepr.strategy.models.Unit;
import com.nickdnepr.strategy.utils.UnitMovesDrawer;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UnitsMap {

    private SurfaceMap surfaceMap;
    private Unit[][][] unitsMap;
    private LinkedHashMap<Long, Unit> unitsBase;
    private LinkedHashMap<Long, Player> playersBase;
    private Player currentPlayer;

    public UnitsMap(SurfaceMap surfaceMap) {
        this.surfaceMap = surfaceMap;
        unitsMap = new Unit[surfaceMap.getHeight()][surfaceMap.getWidth()][10];
        unitsBase = new LinkedHashMap<>();
        playersBase = new LinkedHashMap<>();
    }

    public boolean addPlayer(Player player) {
//        System.out.println(player.getId());
        if (playersBase.containsValue(player)) {
            return false;
        }
        playersBase.put(player.getId(), player);
        if (currentPlayer == null) {
            currentPlayer = player;
        }
        return true;
    }

    public boolean setUnitOwner(long unitId, long ownerId) {
        if (unitsBase.get(unitId) == null || playersBase.get(ownerId) == null) {
            return false;
        }
        unitsBase.get(unitId).setOwner(playersBase.get(ownerId));
        return true;
    }

    public Unit findUnitById(Long id) {
        return unitsBase.get(id);
    }

    public Unit[] getUnitsOnCell(int x, int y) {
        return unitsMap[y][x];
    }

    public Unit[] getUnitsOnCell(Coordinates coordinates) {
        return getUnitsOnCell(coordinates.getX(), coordinates.getY());
    }

    public boolean addUnit(Unit unit) {
        System.out.println(ObjectSizeCalculator.getObjectSize(unit));
        if (unit.getRoutingPredicate().validatePoint(surfaceMap.getPoint(unit.getCoordinates()))) {
            return addIfNotFull(unit);
        }
        return false;
    }

    private boolean addIfNotFull(Unit unit) {
        Unit[] unitsOnCell = getUnitsOnCell(unit.getCoordinates());
        for (int i = 0; i < getUnitsOnCell(unit.getCoordinates()).length; i++) {
            if (unitsOnCell[i] == null) {
                unitsOnCell[i] = unit;
                unitsBase.put(unit.getId(), unit);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUnit(Unit unit) {
        Unit[] unitsOnCell = getUnitsOnCell(unit.getCoordinates());
        for (int i = 0; i < unitsOnCell.length; i++) {
            if (unitsOnCell[i].equals(unit)) {
                unitsOnCell[i] = null;
                unitsBase.remove(unit.getId());
                return true;
            }
        }
        return false;
    }

    private boolean cellIsFull(Point point) {
        for (Unit unit : getUnitsOnCell(point.getCoordinates())) {
            if (unit == null) {
//                System.out.println("Cell is not full");
                return false;
            }
        }
//        System.out.println("Cell is full");
        return true;
    }

    public Route calculateRoute(Unit unit, Coordinates destination) {
        Route route = surfaceMap.getRoute(unit.getCoordinates(), destination, new RoutingPredicate() {
            @Override
            public double getResultingPrice(Rib rib) {
                if (cellIsFull(rib.getDestination())) {
                    return 1000000000;
                }
                return unit.getRoutingPredicate().getResultingPrice(rib);
            }

            @Override
            public boolean validateRoute(Route route) {
                return unit.getRoutingPredicate().validateRoute(route);
            }

            @Override
            public boolean validatePoint(Point point) {
                return unit.getRoutingPredicate().validatePoint(point) && !cellIsFull(point);
            }
        });
        unit.setDestinationRoute(route);
        return route;
    }

    public void moveUnit(Unit unit, UnitMovesDrawer drawer) {
        if (unit.getDestinationRoute() == null || calculateRoute(unit, unit.getDestinationRoute().getWayPoints().get(unit.getDestinationRoute().getWayPoints().size() - 1).getPoint().getCoordinates()) == null) {
            System.out.println("Unit has no destination point or point became unreachable");
            return;
        }
        while (unit.getActionPoints() > 0) {
            if (unit.getDestinationRoute().getWayPoints().isEmpty()) {
                unit.setDestinationRoute(null);
                return;
            }
            if (unit.getActionPoints() < unit.getDestinationRoute().getWayPoints().get(0).getShortestRouteLength()) {
                unit.setActionPoints(0);
                continue;
            }
            deleteUnit(unit);
            unit.setCoordinates(unit.getDestinationRoute().getWayPoints().get(0).getPoint().getCoordinates());
            addIfNotFull(unit);
            unit.setActionPoints(unit.getActionPoints() - unit.getDestinationRoute().getWayPoints().get(0).getShortestRouteLength());
            unit.getDestinationRoute().getWayPoints().remove(0);
            drawer.drawIteration(unit);
        }
    }

    public void printMap() {
        for (int i = 0; i < unitsMap.length; i++) {
            for (int j = 0; j < unitsMap[i].length; j++) {
                System.out.print("{");
                for (int k = 0; k < unitsMap[i][j].length; k++) {
                    if (unitsMap[i][j][k] != null) {
                        System.out.print(unitsMap[i][j][k].getTitle() + ", ");
                    } else {
                        System.out.print("null, ");
                    }
                }
                System.out.print("}, ");
            }
            System.out.println();
        }
    }

    public LinkedHashMap<Long, Unit> getUnitsBase() {
        return unitsBase;
    }

    public SurfaceMap getSurfaceMap() {
        return surfaceMap;
    }

    public LinkedHashMap<Long, Player> getPlayersBase() {
        return playersBase;
    }

    public void endRound() {
        ArrayList<Player> players = new ArrayList<>(playersBase.values());
        if (players.indexOf(currentPlayer) < players.size()-1) {
            currentPlayer = players.get(players.indexOf(currentPlayer)+1);

        } else {
            unitsBase.values().forEach(unit -> unit.setActionPoints(unit.getMaxActionPoints()));
            currentPlayer = players.get(0);
            System.out.println("New round");
        }
        System.out.println(currentPlayer.getName()+ "'s turn");
    }
}
