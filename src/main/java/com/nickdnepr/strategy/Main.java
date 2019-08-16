package com.nickdnepr.strategy;

import com.nickdnepr.strategy.map.*;
import com.nickdnepr.strategy.map.routing.Route;
import com.nickdnepr.strategy.map.routing.RoutingPredicate;
import com.nickdnepr.strategy.map.routing.graphComponents.Point;
import com.nickdnepr.strategy.map.surface.SurfaceMap;
import com.nickdnepr.strategy.map.surface.SurfaceType;
import com.nickdnepr.strategy.map.units.UnitsMap;
import com.nickdnepr.strategy.models.Player;
import com.nickdnepr.strategy.models.Unit;
import com.nickdnepr.strategy.utils.InputRange;
import com.nickdnepr.strategy.utils.MapDrawer;
import com.nickdnepr.strategy.utils.Strings;
import com.nickdnepr.strategy.utils.UnitMovesDrawer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String VERSION = "0.0.3-alpha";
    private static final Scanner sc = new Scanner(System.in);
    private static UnitsMap unitsMap;
    private static SurfaceMap presetSurfaceMap;


    public static void main(String[] args) {
//        System.out.println(Math.pow(1.2, 4));
//        if (System.console() != null && System.getenv().get("TERM") != null) {
//            System.out.println("\u001B[36m"+"Menu optionzzzzzzzzz"+"\u001B[0m");
//        } else {
//            System.out.println("Fuck you, sorry");
//        }
        loadPresetMap();
        unitsMap.addPlayer(new Player("Player1"));
        unitsMap.addPlayer(new Player("Player2"));
        System.out.println(unitsMap.getPlayersBase().toString());
//        MapDrawer.drawReliefMap(unitsMap);
//        MapDrawer.drawHeightsMap(unitsMap);
//        System.out.println(presetSurfaceMap.getRoute(new Coordinates(0, 0), new Coordinates(7, 7), RoutingPredicate.SURFACE_DEFAULT));
//        testUnits();
        System.out.println("Welcome to the strategy core. Current version is " + VERSION + "");
        System.out.println(Strings.HELLO_STRING);
        boolean exit = false;
        SurfaceMap surfaceMap = presetSurfaceMap;
        while (!exit) {
            String command = sc.nextLine();
            if (command.equals("help")) {
                System.out.println(Strings.HELLO_STRING);
            }
            if (command.toLowerCase().equals("exit")) {
                exit = true;
                continue;
            }
            if (command.toLowerCase().equals("nm")) {
                System.out.println("Please input map sizes");
                int width = getInt("Please input width", InputRange.POSITIVE_NUMBERS);
                int height = getInt("Please input height", InputRange.POSITIVE_NUMBERS);
                surfaceMap = new SurfaceMap(width, height);
                System.out.println("Created blank map with width " + width + " and height " + height);
                continue;
            }
            if (command.toLowerCase().equals("sh")) {
                if (surfaceMap != null) {
                    MapDrawer.drawReliefMap(unitsMap);
                } else {
                    System.out.println("Map is not created");
                    continue;
                }
            }
            if (command.toLowerCase().equals("ap")) {

                if (surfaceMap != null) {
                    Coordinates coordinates = getCoordinates(surfaceMap, "Please input coordinates to add point");
                    int x = coordinates.getX();
                    int y = coordinates.getY();
                    SurfaceType surfaceType = getSurfaceType("Please input surface type");
                    Point p = surfaceMap.addPoint(x, y, surfaceType);
                    System.out.println("Added point with coordinates x=" + p.getCoordinates().getX() + ", y=" + p.getCoordinates().getY() + " and surface type " + p.getSurfaceType().getDrawingString());
                } else {
                    System.out.println("Map is not created");
                    continue;
                }
            }
            if (command.toLowerCase().equals("pr")) {
                loadPresetMap();
                surfaceMap = presetSurfaceMap;
                System.out.println("Loaded preset map");
                continue;
            }

            if (command.toLowerCase().equals("r")) {
                Coordinates start = getCoordinates(surfaceMap, "Please input start of the route");
                Coordinates end = getCoordinates(surfaceMap, "Please input end of the route");
                Route route;
                if (surfaceMap != null) {
                    route = surfaceMap.getRoute(start, end, RoutingPredicate.SURFACE_DEFAULT);
                } else {
                    System.out.println("Map in not created");
                    continue;
                }
                if (route == null) {
                    System.out.println("Specified destination in unreachable");
                    continue;
                }
                System.out.println(route.getRouteString());
                System.out.println(route.getFullPrice());
                System.out.println(route.toString());
            }
            if (command.toLowerCase().equals("un")) {
                System.out.println("List of units:");
                System.out.println(unitsMap.getUnitsBase().toString());
//                unitsMap.getUnitsBase().values().stream().filter(unit -> unit!=null).collect(Collectors.toList());
            }
            if (command.toLowerCase().equals("au")) {
                System.out.println("Please input unit name");
                String name = sc.nextLine();
                System.out.println("Please input action points");
                Double actionPoints = Double.valueOf(sc.nextLine());
                Unit unit = new Unit(name, actionPoints, getCoordinates(surfaceMap, "Please input unit coordinates"), RoutingPredicate.SURFACE_DEFAULT);
                if (unitsMap.addUnit(unit)) {
                    System.out.println("Unit added successfully");
                    System.out.println(unit.toString());
                } else {
                    System.out.println("Could not add unit");
                }
            }
            if (command.toLowerCase().equals("sur")) {
                System.out.println("Please input unit id");
                Long unitId = Long.valueOf(sc.nextLine());
                Coordinates destination = getCoordinates(surfaceMap, "Please input destination coordinates");
                Route route = unitsMap.calculateRoute(unitsMap.findUnitById(unitId), destination);
            }
            if (command.toLowerCase().equals("mou")) {
                System.out.println("Please input unit id");
                Long unitId = Long.valueOf(sc.nextLine());
                unitsMap.moveUnit(unitsMap.findUnitById(unitId), UnitMovesDrawer.CONSOLE_DRAWER);
            }
            if (command.toLowerCase().equals("cls")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
            if (command.toLowerCase().equals("shu")) {
                System.out.println("Please input unit id");
                long unitId = Long.parseLong(sc.nextLine());
                MapDrawer.drawUnitOnMap(unitsMap, unitId);
            }
            if (command.toLowerCase().equals("shh")) {
                MapDrawer.drawHeightsMap(unitsMap);
            }
            if (command.toLowerCase().equals("end")) {
                unitsMap.endRound();
            }
            if (command.toLowerCase().equals("pl")) {
                System.out.println(unitsMap.getPlayersBase().values().toString());
            }
        }
    }

    private static Coordinates getCoordinates(SurfaceMap finalSurfaceMap, String inviteString) {
        System.out.println(inviteString);
        int x = getInt("Please input x", i -> i > -1 && i < finalSurfaceMap.getWidth());
        int y = getInt("Please input y", i -> i > -1 && i < finalSurfaceMap.getHeight());
        return new Coordinates(x, y);
    }

    private static int getInt(String inviteString, InputRange inputRange) {
        System.out.print(inviteString + " ");
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                if (!inputRange.validateInt(value)) {
                    System.out.println("Input is out of range. Try again");
                    continue;
                }
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Try again");
            }
        }
    }

    private static SurfaceType getSurfaceType(String inviteString) {
        System.out.print(inviteString + " ");
        while (true) {
            SurfaceType type = SurfaceType.getTypeByString(sc.nextLine().toUpperCase());
            if (type == null) {
                System.out.println("Invalid input. Try again");
                continue;
            }
            return type;
        }
    }

    private static void testUnits() {
        UnitsMap unitsMap = new UnitsMap(presetSurfaceMap);
        Unit baseUnit = new Unit("Hero", 10, new Coordinates(0, 0), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit = new Unit("unit1", 10, new Coordinates(1, 1), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit2 = new Unit("unit2", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit3 = new Unit("unit3", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit4 = new Unit("unit4", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit5 = new Unit("unit5", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit6 = new Unit("unit6", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit7 = new Unit("unit7", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit8 = new Unit("unit8", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit9 = new Unit("unit9", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        Unit unit10 = new Unit("unit10", 10, new Coordinates(3, 3), RoutingPredicate.SURFACE_DEFAULT);
        System.out.println(unitsMap.addUnit(unit));
        System.out.println(unitsMap.addUnit(unit2));
        System.out.println(unitsMap.addUnit(unit3));
        System.out.println(unitsMap.addUnit(unit4));
        System.out.println(unitsMap.addUnit(unit5));
        System.out.println(unitsMap.addUnit(unit6));
        System.out.println(unitsMap.addUnit(unit7));
        System.out.println(unitsMap.addUnit(unit8));
        System.out.println(unitsMap.addUnit(unit9));
        System.out.println(unitsMap.addUnit(unit10));
        unitsMap.addUnit(baseUnit);
//        System.out.println(unitsMap.deleteUnit(unit2));
        unitsMap.printMap();
        System.out.println(Arrays.toString(unitsMap.getUnitsOnCell(new Coordinates(0, 0))));
        System.out.println(unitsMap.calculateRoute(baseUnit, new Coordinates(7, 7)) + "-----");
        unitsMap.calculateRoute(unit, new Coordinates(3, 3));
        unitsMap.moveUnit(unit, UnitMovesDrawer.CONSOLE_DRAWER);
        unitsMap.moveUnit(baseUnit, UnitMovesDrawer.CONSOLE_DRAWER);
    }

    private static void loadPresetMap() {
        presetSurfaceMap = new SurfaceMap(8, 8);
        presetSurfaceMap.addPoint(0, 0, SurfaceType.ASPHALT);
        presetSurfaceMap.addPoint(0, 1, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(0, 2, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(0, 3, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(0, 4, SurfaceType.EARTH);
        presetSurfaceMap.addPoint(0, 5, SurfaceType.EARTH);
        presetSurfaceMap.addPoint(0, 6, SurfaceType.EARTH);
        presetSurfaceMap.addPoint(0, 7, SurfaceType.EARTH);

        presetSurfaceMap.addPoint(1, 0, SurfaceType.EARTH);
        presetSurfaceMap.addPoint(1, 1, SurfaceType.ASPHALT);
        presetSurfaceMap.addPoint(1, 2, SurfaceType.ASPHALT);
        presetSurfaceMap.addPoint(1, 3, SurfaceType.SAND);
        presetSurfaceMap.addPoint(1, 4, SurfaceType.EARTH);
        presetSurfaceMap.addPoint(1, 5, SurfaceType.SAND);
        presetSurfaceMap.addPoint(1, 6, SurfaceType.SAND);
        presetSurfaceMap.addPoint(1, 7, SurfaceType.SAND);

        presetSurfaceMap.addPoint(2, 0, SurfaceType.SAND);
        presetSurfaceMap.addPoint(2, 1, SurfaceType.SAND);
        presetSurfaceMap.addPoint(2, 2, SurfaceType.SAND);
        presetSurfaceMap.addPoint(2, 3, SurfaceType.ASPHALT);
        presetSurfaceMap.addPoint(2, 4, SurfaceType.SAND);
        presetSurfaceMap.addPoint(2, 5, SurfaceType.WATER);
        presetSurfaceMap.addPoint(2, 6, SurfaceType.WATER);
        presetSurfaceMap.addPoint(2, 7, SurfaceType.WATER);

        presetSurfaceMap.addPoint(3, 0, SurfaceType.WATER);
        presetSurfaceMap.addPoint(3, 1, SurfaceType.WATER);
        presetSurfaceMap.addPoint(3, 2, SurfaceType.WATER);
        presetSurfaceMap.addPoint(3, 3, 1, SurfaceType.CONCRETE);
        presetSurfaceMap.addPoint(3, 4, SurfaceType.WATER);
        presetSurfaceMap.addPoint(3, 5, SurfaceType.WATER);
        presetSurfaceMap.addPoint(3, 6, SurfaceType.WATER);
        presetSurfaceMap.addPoint(3, 7, SurfaceType.WATER);

        presetSurfaceMap.addPoint(4, 0, SurfaceType.WATER);
        presetSurfaceMap.addPoint(4, 1, SurfaceType.WATER);
        presetSurfaceMap.addPoint(4, 2, 2, SurfaceType.CONCRETE);
        presetSurfaceMap.addPoint(4, 3, 3, SurfaceType.CONCRETE);
        presetSurfaceMap.addPoint(4, 4, SurfaceType.WATER);
        presetSurfaceMap.addPoint(4, 5, SurfaceType.WATER);
        presetSurfaceMap.addPoint(4, 6, SurfaceType.WATER);
        presetSurfaceMap.addPoint(4, 7, SurfaceType.WATER);

        presetSurfaceMap.addPoint(5, 0, SurfaceType.WATER);
        presetSurfaceMap.addPoint(5, 1, SurfaceType.WATER);
        presetSurfaceMap.addPoint(5, 2, SurfaceType.WATER);
        presetSurfaceMap.addPoint(5, 3, 4, SurfaceType.CONCRETE);
        presetSurfaceMap.addPoint(5, 4, SurfaceType.WATER);
        presetSurfaceMap.addPoint(5, 5, SurfaceType.SAND);
        presetSurfaceMap.addPoint(5, 6, SurfaceType.SAND);
        presetSurfaceMap.addPoint(5, 7, SurfaceType.SAND);

        presetSurfaceMap.addPoint(6, 0, SurfaceType.SAND);
        presetSurfaceMap.addPoint(6, 1, SurfaceType.SAND);
        presetSurfaceMap.addPoint(6, 2, SurfaceType.SAND);
        presetSurfaceMap.addPoint(6, 3, 3, SurfaceType.ASPHALT);
        presetSurfaceMap.addPoint(6, 4, SurfaceType.SAND);
        presetSurfaceMap.addPoint(6, 5, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(6, 6, SurfaceType.ASPHALT);
        presetSurfaceMap.addPoint(6, 7, SurfaceType.ASPHALT);

        presetSurfaceMap.addPoint(7, 0, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(7, 1, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(7, 2, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(7, 3, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(7, 4, 2, SurfaceType.ASPHALT);
        presetSurfaceMap.addPoint(7, 5, 1, SurfaceType.ASPHALT);
        presetSurfaceMap.addPoint(7, 6, SurfaceType.DIRT);
        presetSurfaceMap.addPoint(7, 7, SurfaceType.DIRT);
        unitsMap = new UnitsMap(presetSurfaceMap);
        unitsMap.addUnit(new Unit("Hero", 15.0, new Coordinates(0, 0), RoutingPredicate.SURFACE_DEFAULT));
    }
}