package com.nickdnepr.strategy.map.routing;

import com.nickdnepr.strategy.map.routing.graphComponents.Point;

public class PointWeightContainer {

    private Point point;
    private double shortestRouteLength;
    private boolean processed;

    public PointWeightContainer(Point point) {
        this.point = point;
        shortestRouteLength = 1000000001;
        processed = false;
    }

    public PointWeightContainer(Point point, double shortestRouteLength) {
        this.point = point;
        this.shortestRouteLength = shortestRouteLength;
        processed = false;
    }

    public Point getPoint() {
        return point;
    }

    public double getShortestRouteLength() {
        return shortestRouteLength;
    }

    public void setShortestRouteLength(double shortestRouteLength) {
        this.shortestRouteLength = shortestRouteLength;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public PointWeightContainer(Point point, double shortestRouteLength, boolean processed) {
        this.point = point;
        this.shortestRouteLength = shortestRouteLength;
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "PointWeightContainer{" +
                "point=" + point +
                ", shortestRouteLength=" + shortestRouteLength +
                ", processed=" + processed +
                '}';
    }
}
