package com.nickdnepr.strategy.utils;

import com.nickdnepr.strategy.Main;
import com.nickdnepr.strategy.map.Coordinates;
import com.nickdnepr.strategy.map.surface.SurfaceType;
import com.nickdnepr.strategy.map.units.UnitsMap;
import com.nickdnepr.strategy.models.Unit;
import javafx.util.Pair;

import java.util.LinkedHashMap;

import static com.nickdnepr.strategy.Main.ANSI_RESET;

public class MapDrawer {

    public static void drawReliefMap(UnitsMap unitsMap) {
        drawMapWithMarks(unitsMap, new LinkedHashMap<>());
    }

    // String to print, Color string
    private static void drawMapWithMarks(UnitsMap unitsMap, LinkedHashMap<Coordinates, Pair<String, String>> marks) {

        int xDelimiter = String.valueOf(unitsMap.getSurfaceMap().getWidth() - 1).length();
        int yDelimiter = String.valueOf(unitsMap.getSurfaceMap().getWidth() - 1).length();
        for (Pair<String, String> p : marks.values()) {
            if (p.getKey().length()>xDelimiter){
                xDelimiter=p.getKey().length();
            }
        }
        System.out.print("  ");
        for (int i = 0; i < xDelimiter - 1; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < unitsMap.getSurfaceMap().getWidth(); i++) {
            System.out.print(printSpaces(String.valueOf(i), xDelimiter, '_'));
            System.out.print(" ");
        }
        System.out.println("X");
        for (int i = 0; i < unitsMap.getSurfaceMap().getHeight(); i++) {
            System.out.print(printSpaces(String.valueOf(i), yDelimiter, '_') + " ");
            for (int k = 0; k < xDelimiter - 1; k++) {
                System.out.print(" ");
            }
            for (int j = 0; j < unitsMap.getSurfaceMap().getWidth(); j++) {
                if (unitsMap.getSurfaceMap().getPoint(j, i) != null) {
                    if (marks.get(new Coordinates(j, i)) != null) {
                        coloredPrint(printSpaces(marks.get(new Coordinates(j, i)).getKey(), xDelimiter, ' '), marks.get(new Coordinates(j, i)).getValue());
                    } else {
                        SurfaceType type = unitsMap.getSurfaceMap().getPoint(j, i).getSurfaceType();
                        coloredPrint(printSpaces(type.getDrawingString(), xDelimiter, ' '), type.getColor());
                    }
                } else {
                    System.out.print(printSpaces("*", xDelimiter, ' '));
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("Y");
    }

    public static void drawUnitOnMap(UnitsMap unitsMap, long unitId) {
        Unit unit = unitsMap.getUnitsBase().get(unitId);
        LinkedHashMap<Coordinates, Pair<String, String>> map = new LinkedHashMap<>();
        map.put(unit.getCoordinates(), new Pair<>("U", Main.ANSI_YELLOW + Main.ANSI_BLUE_BACKGROUND));
        drawMapWithMarks(unitsMap, map);
    }

    public static void drawHeightsMap(UnitsMap unitsMap) {
        LinkedHashMap<Coordinates, Pair<String, String>> heights = new LinkedHashMap<>();
        for (int i = 0; i < unitsMap.getSurfaceMap().getHeight(); i++) {
            for (int j = 0; j < unitsMap.getSurfaceMap().getWidth(); j++) {
                heights.put(new Coordinates(j, i), new Pair<>(Double.toString(unitsMap.getSurfaceMap().getPoint(j, i).getCoordinates().getHeight()), ""));
            }
        }
        drawMapWithMarks(unitsMap, heights);
    }

    private static void coloredPrint(String text, String textColor, String background) {
        System.out.print(background + textColor + text + ANSI_RESET);
    }

    private static void coloredPrint(String text, String textColor) {
        System.out.print(textColor + text + ANSI_RESET);
    }

    private static String printSpaces(String original, int fullLength, char delimiter) {
        if (original.length() > fullLength) {
            throw new IllegalStateException("specified string \"" + original + "\" is longer than " + fullLength);
        }
        int numInStr = original.length();
        while (numInStr != fullLength) {
            numInStr++;
            System.out.print(delimiter);
        }
        return original;
    }
}
