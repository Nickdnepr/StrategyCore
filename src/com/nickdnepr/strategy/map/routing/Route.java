package com.nickdnepr.strategy.map.routing;

import java.util.List;

public class Route {

    private List<PointWeightContainer> wayPoints;
    private double fullPrice;

    public Route(List<PointWeightContainer> wayPoints, double fullPrice) {
        this.wayPoints = wayPoints;
        this.fullPrice = fullPrice;
    }

    public List<PointWeightContainer> getWayPoints() {
        return wayPoints;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public String getRouteString() {
        if (wayPoints.isEmpty()) {
            return "Empty route";
        }
        String s = wayPoints.get(0).getPoint().getQualifier();
        if (wayPoints.size() > 1) {
            for (int i = 1; i < wayPoints.size(); i++) {
                s += " -> ";
                s += wayPoints.get(i).getPoint().getQualifier();
            }
        }
        return s;
    }
}
