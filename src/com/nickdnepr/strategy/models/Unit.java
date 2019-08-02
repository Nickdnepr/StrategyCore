package com.nickdnepr.strategy.models;

import com.nickdnepr.strategy.map.Coordinates;
import com.nickdnepr.strategy.map.routing.Route;
import com.nickdnepr.strategy.map.routing.RoutingPredicate;

public class Unit {

    private final long id;
    private String title;
    private double actionPoints;
    private double maxActionPoints;
    private Coordinates coordinates;
    private RoutingPredicate routingPredicate;
    private Route destinationRoute;

    public Unit(String title, double actionPoints, Coordinates coordinates, RoutingPredicate routingPredicate) {
        this.id = System.currentTimeMillis();
        this.title = title;
        this.actionPoints = actionPoints;
        this.maxActionPoints = actionPoints;
        this.coordinates = coordinates;
        this.routingPredicate = routingPredicate;
    }

    public Unit(String title, double actionPoints, double maxActionPoints, Coordinates coordinates, RoutingPredicate routingPredicate) {
        this.id = System.currentTimeMillis();
        this.title = title;
        this.actionPoints = actionPoints;
        this.maxActionPoints = maxActionPoints;
        this.coordinates = coordinates;
        this.routingPredicate = routingPredicate;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(double actionPoints) {
        this.actionPoints = actionPoints;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public RoutingPredicate getRoutingPredicate() {
        return routingPredicate;
    }

    public void setRoutingPredicate(RoutingPredicate routingPredicate) {
        this.routingPredicate = routingPredicate;
    }

    public Route getDestinationRoute() {
        return destinationRoute;
    }

    public void setDestinationRoute(Route destinationRoute) {
        this.destinationRoute = destinationRoute;
    }

    public double getMaxActionPoints() {
        return maxActionPoints;
    }

    public void setMaxActionPoints(double maxActionPoints) {
        this.maxActionPoints = maxActionPoints;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", actionPoints=" + actionPoints +
                ", coordinates=" + coordinates +
                '}';
    }
}
