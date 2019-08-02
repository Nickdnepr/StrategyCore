package com.nickdnepr.strategy.map.routing;

import java.util.List;

public class Route {

    private List<PointWeightContainer> wayPoints;
    private double fullPrice;

    public Route(List<PointWeightContainer> wayPoints, double fullPrice) {
        for (int i = wayPoints.size() - 1; i > 0; i--) {
            wayPoints.get(i).setShortestRouteLength(wayPoints.get(i).getShortestRouteLength() - wayPoints.get(i - 1).getShortestRouteLength());
        }
        wayPoints.remove(0);
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
        StringBuilder s = new StringBuilder(wayPoints.get(0).getPoint().getQualifier());
        if (wayPoints.size() > 1) {
            for (int i = 1; i < wayPoints.size(); i++) {
                s.append(" -> ");
                s.append(wayPoints.get(i).getPoint().getQualifier());
            }
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return "Route{" +
                "wayPoints=" + wayPoints +
                ", fullPrice=" + fullPrice +
                '}';
    }
}
