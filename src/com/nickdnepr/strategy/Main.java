package com.nickdnepr.strategy;

import com.nickdnepr.strategy.map.*;
import com.nickdnepr.strategy.map.routing.Route;
import com.nickdnepr.strategy.map.routing.graphComponents.Point;
import com.nickdnepr.strategy.utils.InputRange;
import com.nickdnepr.strategy.utils.Strings;

import java.util.Scanner;

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

    public static final String VERSION = "0.0.1-alpha";
    private static final Scanner sc = new Scanner(System.in);
    private static GameMap presetGameMap;

    public static void coloredPrint(String text, String textColor, String background) {
        System.out.print(background + textColor + text + ANSI_RESET);
    }

    public static void coloredPrint(String text, String textColor) {
        System.out.print(textColor + text + ANSI_RESET);
    }


    public static void main(String[] args) {
        loadPresetMap();
        System.out.println("Welcome to the strategy core. Current version is "+VERSION+"");
        System.out.println(Strings.HELLO_STRING);
        boolean exit = false;
        GameMap gameMap = presetGameMap;
        while (!exit) {
            String command = sc.nextLine();
            if (command.equals("help")){
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
                gameMap = new GameMap(width, height);
                System.out.println("Created blank map with width " + width + " and height " + height);
                continue;
            }
            if (command.toLowerCase().equals("sh")) {
                if (gameMap != null) {
                    gameMap.printMap();
                } else {
                    System.out.println("Map is not created");
                    continue;
                }
            }
            if (command.toLowerCase().equals("ap")) {

                if (gameMap != null) {
                    Coordinates coordinates = getCoordinates(gameMap, "Please input coordinates to add point");
                    int x = coordinates.getX();
                    int y = coordinates.getY();
                    SurfaceType surfaceType = getSurfaceType("Please input surface type");
                    Point p = gameMap.addPoint(x, y, surfaceType);
                    System.out.println("Added point with coordinates x=" + p.getCoordinates().getX() + ", y=" + p.getCoordinates().getY() + " and surface type " + p.getSurfaceType().getDrawingString());
                } else {
                    System.out.println("Map is not created");
                    continue;
                }
            }
            if (command.toLowerCase().equals("pr")) {
                loadPresetMap();
                gameMap = presetGameMap;
                System.out.println("Loaded preset map");
                continue;
            }

            if (command.toLowerCase().equals("r")) {
                Coordinates start = getCoordinates(gameMap, "Please input start of the route");
                Coordinates end = getCoordinates(gameMap, "Please input end of the route");
                Route route;
                if (gameMap != null) {
                    route = gameMap.getRoute(start, end, RoutingPredicate.SURFACE_DEFAULT);
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
            }
        }
    }

    private static Coordinates getCoordinates(GameMap finalGameMap, String inviteString) {
        System.out.println(inviteString);
        int x = getInt("Please input x", i -> i > -1 && i < finalGameMap.getWidth());
        int y = getInt("Please input y", i -> i > -1 && i < finalGameMap.getHeight());
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

    private static void loadPresetMap() {
        presetGameMap = new GameMap(8, 8);
        presetGameMap.addPoint(0, 0, SurfaceType.ASPHALT);
        presetGameMap.addPoint(0, 1, SurfaceType.DIRT);
        presetGameMap.addPoint(0, 2, SurfaceType.DIRT);
        presetGameMap.addPoint(0, 3, SurfaceType.DIRT);
        presetGameMap.addPoint(0, 4, SurfaceType.EARTH);
        presetGameMap.addPoint(0, 5, SurfaceType.EARTH);
        presetGameMap.addPoint(0, 6, SurfaceType.EARTH);
        presetGameMap.addPoint(0, 7, SurfaceType.EARTH);

        presetGameMap.addPoint(1, 0, SurfaceType.EARTH);
        presetGameMap.addPoint(1, 1, SurfaceType.ASPHALT);
        presetGameMap.addPoint(1, 2, SurfaceType.ASPHALT);
        presetGameMap.addPoint(1, 3, SurfaceType.SAND);
        presetGameMap.addPoint(1, 4, SurfaceType.EARTH);
        presetGameMap.addPoint(1, 5, SurfaceType.SAND);
        presetGameMap.addPoint(1, 6, SurfaceType.SAND);
        presetGameMap.addPoint(1, 7, SurfaceType.SAND);

        presetGameMap.addPoint(2, 0, SurfaceType.SAND);
        presetGameMap.addPoint(2, 1, SurfaceType.SAND);
        presetGameMap.addPoint(2, 2, SurfaceType.SAND);
        presetGameMap.addPoint(2, 3, SurfaceType.ASPHALT);
        presetGameMap.addPoint(2, 4, SurfaceType.SAND);
        presetGameMap.addPoint(2, 5, SurfaceType.WATER);
        presetGameMap.addPoint(2, 6, SurfaceType.WATER);
        presetGameMap.addPoint(2, 7, SurfaceType.WATER);

        presetGameMap.addPoint(3, 0, SurfaceType.WATER);
        presetGameMap.addPoint(3, 1, SurfaceType.WATER);
        presetGameMap.addPoint(3, 2, SurfaceType.WATER);
        presetGameMap.addPoint(3, 3, SurfaceType.CONCRETE);
        presetGameMap.addPoint(3, 4, SurfaceType.WATER);
        presetGameMap.addPoint(3, 5, SurfaceType.WATER);
        presetGameMap.addPoint(3, 6, SurfaceType.WATER);
        presetGameMap.addPoint(3, 7, SurfaceType.WATER);

        presetGameMap.addPoint(4, 0, SurfaceType.WATER);
        presetGameMap.addPoint(4, 1, SurfaceType.WATER);
        presetGameMap.addPoint(4, 2, SurfaceType.WATER);
        presetGameMap.addPoint(4, 3, SurfaceType.CONCRETE);
        presetGameMap.addPoint(4, 4, SurfaceType.WATER);
        presetGameMap.addPoint(4, 5, SurfaceType.WATER);
        presetGameMap.addPoint(4, 6, SurfaceType.WATER);
        presetGameMap.addPoint(4, 7, SurfaceType.WATER);

        presetGameMap.addPoint(5, 0, SurfaceType.WATER);
        presetGameMap.addPoint(5, 1, SurfaceType.WATER);
        presetGameMap.addPoint(5, 2, SurfaceType.WATER);
        presetGameMap.addPoint(5, 3, SurfaceType.CONCRETE);
        presetGameMap.addPoint(5, 4, SurfaceType.WATER);
        presetGameMap.addPoint(5, 5, SurfaceType.SAND);
        presetGameMap.addPoint(5, 6, SurfaceType.SAND);
        presetGameMap.addPoint(5, 7, SurfaceType.SAND);

        presetGameMap.addPoint(6, 0, SurfaceType.SAND);
        presetGameMap.addPoint(6, 1, SurfaceType.SAND);
        presetGameMap.addPoint(6, 2, SurfaceType.SAND);
        presetGameMap.addPoint(6, 3, SurfaceType.ASPHALT);
        presetGameMap.addPoint(6, 4, SurfaceType.SAND);
        presetGameMap.addPoint(6, 5, SurfaceType.DIRT);
        presetGameMap.addPoint(6, 6, SurfaceType.ASPHALT);
        presetGameMap.addPoint(6, 7, SurfaceType.ASPHALT);

        presetGameMap.addPoint(7, 0, SurfaceType.DIRT);
        presetGameMap.addPoint(7, 1, SurfaceType.DIRT);
        presetGameMap.addPoint(7, 2, SurfaceType.DIRT);
        presetGameMap.addPoint(7, 3, SurfaceType.DIRT);
        presetGameMap.addPoint(7, 4, SurfaceType.ASPHALT);
        presetGameMap.addPoint(7, 5, SurfaceType.ASPHALT);
        presetGameMap.addPoint(7, 6, SurfaceType.DIRT);
        presetGameMap.addPoint(7, 7, SurfaceType.DIRT);
//        for (int i=0; i<presetGameMap.getWidth())
//        presetGameMap.printMap();
//        presetGameMap.getRoute(new Coordinates(0,3), new Coordinates(3,3), RoutingPredicate.SURFACE_DEFAULT);

    }
}
