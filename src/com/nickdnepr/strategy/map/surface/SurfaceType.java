package com.nickdnepr.strategy.map.surface;

import static com.nickdnepr.strategy.Main.*;

public enum SurfaceType {

    EARTH(1, true), SAND(1.5, true), DIRT(2, true), WATER(1, false), CONCRETE(0.6, false), ASPHALT(0.75, false);

    private double movingCoefficient;
    private boolean isMutable;

    SurfaceType(double movingCoefficient, boolean isMutable) {
        this.movingCoefficient = movingCoefficient;
        this.isMutable = isMutable;
    }

    public double getMovingCoefficient() {
        return movingCoefficient;
    }

    public boolean isMutable() {
        return isMutable;
    }

    public String getDrawingString() {
        switch (this) {
            case EARTH: {
                return "E";
            }
            case DIRT: {
                return "D";
            }
            case WATER: {
                return "W";
            }
            case CONCRETE: {
                return "C";
            }
            case SAND: {
                return "S";
            }
            case ASPHALT: {
                return "A";
            }
        }
        return "?";
    }

    public String getColor() {
        switch (this) {
            case EARTH: {
                return ANSI_GREEN;
            }
            case DIRT: {
                return ANSI_RED;
            }
            case WATER: {
                return ANSI_BLUE;
            }
            case CONCRETE: {
                return ANSI_CYAN;
            }
            case SAND: {
                return ANSI_YELLOW;
            }
            case ASPHALT: {
                return "";
            }
        }
        return "";
    }

    public static SurfaceType getTypeByString(String s) {
        for (SurfaceType surfaceType : SurfaceType.values()) {
            if (surfaceType.getDrawingString().contains(s.charAt(0) + "")) {
                return surfaceType;
            }
        }
        return null;
    }
}
