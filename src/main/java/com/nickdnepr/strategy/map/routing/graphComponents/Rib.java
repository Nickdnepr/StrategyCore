package com.nickdnepr.strategy.map.routing.graphComponents;

import com.nickdnepr.strategy.map.routing.graphComponents.Point;

import java.io.Serializable;

public class Rib implements Serializable {
    private String qualifier;
    private Point source;
    private Point destination;
    private double price;

    public Rib(Point source, Point destination, double price) {
        this.qualifier = source.getQualifier() + "->" + destination.getQualifier();
        this.source = source;
        this.destination = destination;
        this.price = price;
        source.addOutComingRib(this);
        destination.addInComingRib(this);
    }

    public Rib(Point source, Point destination) {
        this.qualifier = source.getQualifier() + "->" + destination.getQualifier();
        this.source = source;
        this.destination = destination;
        this.price = isCorner() ? 1.41 : 1;
        source.addOutComingRib(this);
        destination.addInComingRib(this);
    }

    public boolean isCorner() {
        return Math.abs(source.getCoordinates().getX() - destination.getCoordinates().getX()) == 1 && Math.abs(source.getCoordinates().getY() - destination.getCoordinates().getY()) == 1;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public Point getSource() {
        return source;
    }

    public void setSource(Point source) {
        this.source = source;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Rib{" +
                "qualifier='" + qualifier + '\'' +
                ", source='" + source.getQualifier() + '\'' +
                ", destination='" + destination.getQualifier() + '\'' +
                ", price=" + price +
                '}';
    }
}
