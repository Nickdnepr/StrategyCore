package com.nickdnepr.strategy.map;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private int x;
    private int y;
    private double height;

    public Coordinates(int x, int y, double height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, height);
    }
}
