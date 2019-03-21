package com.nickdnepr.strategy.map.routing.graphComponents;

import com.nickdnepr.strategy.map.Coordinates;
import com.nickdnepr.strategy.map.SurfaceType;
import com.nickdnepr.strategy.models.Unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point implements Serializable {
    private String qualifier;
    private SurfaceType surfaceType;
    private List<Rib> outComingRibs;
    private List<Rib> incomingRibs;
    private Coordinates coordinates;
    private Unit holdingUnit;

    public Point(String qualifier, SurfaceType surfaceType, List<Rib> outComingRibs, List<Rib> incomingRibs) {
        this.qualifier = qualifier;
        this.surfaceType = surfaceType;
        this.outComingRibs = outComingRibs;
        this.incomingRibs = incomingRibs;
    }

    public Point(String qualifier, SurfaceType surfaceType) {
        this.qualifier = qualifier;
        this.surfaceType = surfaceType;
        outComingRibs = new ArrayList<>();
        incomingRibs = new ArrayList<>();
        coordinates = new Coordinates(0, 0, 0);
    }

    public Point(String qualifier, SurfaceType surfaceType, Coordinates coordinates) {
        this.qualifier = qualifier;
        this.surfaceType = surfaceType;
        outComingRibs = new ArrayList<>();
        incomingRibs = new ArrayList<>();
        this.coordinates = coordinates;
    }

    public Point(String qualifier, SurfaceType surfaceType, int x, int y) {
        this.qualifier = qualifier;
        this.surfaceType = surfaceType;
        outComingRibs = new ArrayList<>();
        incomingRibs = new ArrayList<>();
        this.coordinates = new Coordinates(x, y, 0);
    }

    public Point(String qualifier, SurfaceType surfaceType, int x, int y, double height) {
        this.qualifier = qualifier;
        this.surfaceType = surfaceType;
        outComingRibs = new ArrayList<>();
        incomingRibs = new ArrayList<>();
        this.coordinates = new Coordinates(x, y, height);
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public SurfaceType getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(SurfaceType surfaceType) {
        this.surfaceType = surfaceType;
    }

    public boolean addOutComingRib(Rib ribQualifier) {
        if (outComingRibs.contains(ribQualifier))
            return false;
        outComingRibs.add(ribQualifier);
        return true;
    }

    public boolean addInComingRib(Rib ribQualifier) {
        if (incomingRibs.contains(ribQualifier))
            return false;
        incomingRibs.add(ribQualifier);
        return true;
    }

    public boolean removeRib(String ribQualifier) {
        return outComingRibs.remove(ribQualifier);
    }

    public List<Rib> getOutComingRibs() {
        return outComingRibs;
    }

    public List<Rib> getIncomingRibs() {
        return incomingRibs;
    }

    public List<String> getOutComingRibsQualifiers() {
        List<String> strings = new ArrayList<>();
        for (Rib rib : outComingRibs) {
            strings.add(rib.getQualifier());
        }
        return strings;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Unit getHoldingUnit() {
        return holdingUnit;
    }

    public void setHoldingUnit(Unit holdingUnit) {
        this.holdingUnit = holdingUnit;
    }

    @Override
    public String toString() {
        return "Point{" +
                "qualifier='" + qualifier + '\'' +
//                ", surfaceType=" + surfaceType +
//                ", outComingRibs=" + outComingRibs +
//                ", incomingRibs=" + incomingRibs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(qualifier, point.qualifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualifier);
    }
}