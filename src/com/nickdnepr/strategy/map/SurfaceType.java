package com.nickdnepr.strategy.map;

import com.nickdnepr.strategy.Main;

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
                return ANSI_GREEN + "E" + ANSI_RESET;
            }
            case DIRT: {
                return ANSI_RED + "D" + ANSI_RESET;
            }
            case WATER: {
                return ANSI_BLUE + "W" + ANSI_RESET;
            }
            case CONCRETE: {
                return ANSI_CYAN + "C" + ANSI_RESET;
            }
            case SAND: {
                return ANSI_YELLOW + "S" + ANSI_RESET;
            }
            case ASPHALT: {
                return "A";
            }
        }
        return "?";
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
