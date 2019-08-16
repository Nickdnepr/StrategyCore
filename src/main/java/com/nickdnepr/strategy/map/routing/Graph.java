package com.nickdnepr.strategy.map.routing;

import com.nickdnepr.strategy.map.routing.graphComponents.Point;
import com.nickdnepr.strategy.map.routing.graphComponents.Rib;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Graph implements Serializable {

    private String qualifier;
    private LinkedHashMap<String, Point> points;
    private LinkedHashMap<String, Rib> ribs;

    public Graph(String qualifier) {
        this.qualifier = qualifier;
        points = new LinkedHashMap<>();
        ribs = new LinkedHashMap<>();
    }

    public boolean addPoint(Point point) {
        if (points.get(point.getQualifier()) == null) {
            points.put(point.getQualifier(), point);
            return true;
        }
        return false;
    }

    public boolean addRib(Rib rib) {
        if (ribs.get(rib.getQualifier()) == null) {
            ribs.put(rib.getQualifier(), rib);
            return true;
        }
        return false;
    }

    public boolean addRib(Point source, Point destination, double price) {
        if (!points.values().contains(source) && points.values().contains(destination)) {
            return false;
        }
        Rib rib = new Rib(source, destination, price);
        addRib(rib);
        return addRib(rib);
    }

    public boolean removePoint(Point point) {
        return removePoint(point.getQualifier());
    }

    public boolean removePoint(String pointQualifier) {
        Point removed = points.remove(pointQualifier);
        if (removed != null) {
            for (Rib rib : removed.getOutComingRibs()) {
                removeRib(rib);
            }
            for (Rib rib : removed.getIncomingRibs()) {
                removeRib(rib);
            }
            return true;
        }
        return false;
    }

    public boolean removeRib(Rib rib) {
        return removeRib(rib.getQualifier());
    }

    public boolean removeRib(String ribQualifier) {
        return ribs.remove(ribQualifier) != null;
    }

    public List<String> getPointsQualifiers() {
        return new ArrayList<>();
    }

    public List<String> getRibsQualifiers() {
        return new ArrayList<>(ribs.keySet());
    }

    public List<Rib> getAllOutComingRibsOfPoint(Point point) {
        if (!ribs.values().containsAll(point.getOutComingRibs())) {
            throw new IllegalStateException("Invalid ribs specified in point");
        }
        return point.getOutComingRibs();
    }

    public Route getRoute(Point start, Point end, RoutingPredicate predicate) {
        if (start == null || end == null) {
            return null;
        }
        if (start == end) {
            return new Route(new ArrayList<>(), 0);
        }

        if (!(points.values().contains(start) && points.values().contains(end))) {
            return null;
        }
        List<Point> pointList = new ArrayList<>(points.values());
        List<PointWeightContainer> pointWeights = new ArrayList<>();
        for (Point point : pointList) {
            if (point.equals(start)) {
                pointWeights.add(new PointWeightContainer(point, 0));
            } else {
                pointWeights.add(new PointWeightContainer(point));
            }
        }
        double[][] links = new double[points.values().size()][points.values().size()];
        for (Rib rib : ribs.values()) {
            links[pointList.indexOf(rib.getSource())][pointList.indexOf(rib.getDestination())] = predicate.getResultingPrice(rib);
        }
        ///////////////////////////////////////////
        int stamp = pointList.indexOf(start);
        while (!allPointsProcessed(pointWeights)) {
            for (int i = 0; i < links[stamp].length; i++) {

                if (links[stamp][i] != 0) {
                    if (links[stamp][i] + pointWeights.get(stamp).getShortestRouteLength() < pointWeights.get(i).getShortestRouteLength()) {
                        pointWeights.get(i).setShortestRouteLength(links[stamp][i] + pointWeights.get(stamp).getShortestRouteLength());
                    }
                }
            }
            pointWeights.get(stamp).setProcessed(true);
            stamp = shortestWayIndex(pointWeights);
        }
        ///////////////////////////////////////////
        int routeStamp = pointList.indexOf(end);
        List<PointWeightContainer> reversedRoute = new ArrayList<>();
        reversedRoute.add(pointWeights.get(pointList.indexOf(end)));

        while (!reversedRoute.contains(pointWeights.get(pointList.indexOf(start)))) {
            for (int i = 0; i < links.length; i++) {
                if (Math.abs(pointWeights.get(routeStamp).getShortestRouteLength() - links[i][routeStamp] - pointWeights.get(i).getShortestRouteLength()) < 0.001 && routeStamp != i && links[i][routeStamp] != 0) {
                    reversedRoute.add(pointWeights.get(i));
                    routeStamp = i;
                }
            }
        }
        List<PointWeightContainer> directRoute = new ArrayList<>();
        double fullPrice = pointWeights.get(pointList.indexOf(end)).getShortestRouteLength();
        for (int i = reversedRoute.size() - 1; i >= 0; i--) {
            directRoute.add(reversedRoute.get(i));
        }
        return new Route(directRoute, fullPrice);
    }


    private boolean allPointsProcessed(List<PointWeightContainer> pointWeightContainers) {
        for (PointWeightContainer container : pointWeightContainers) {
            if (!container.isProcessed()) {
                return false;
            }
        }
        return true;
    }

    private int shortestWayIndex(List<PointWeightContainer> weightContainers) {
        double weight = weightContainers.get(0).getShortestRouteLength();
        int index = 0;
        for (PointWeightContainer container : weightContainers) {
            if (!container.isProcessed()) {
                index = weightContainers.indexOf(container);
                weight = container.getShortestRouteLength();
                break;
            }
        }
        if (weightContainers.size() > 1) {
            for (PointWeightContainer container : weightContainers) {
                if (container.getShortestRouteLength() < weight && !container.isProcessed()) {
                    weight = container.getShortestRouteLength();
                    index = weightContainers.indexOf(container);
                }
            }
        }
        return index;
    }

    public void serialize(String path) throws IOException {
        if (!path.isEmpty() && path.charAt(path.length() - 1) != '/') {
            path += "/";
        }
        FileOutputStream fos = new FileOutputStream(path + qualifier + ".gr");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
        fos.close();
    }

    public void serialize() throws IOException {
        serialize("");
    }

    public static Graph deserialize(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream osi = new ObjectInputStream(fis);
        return (Graph) osi.readObject();
    }

    public LinkedHashMap<String, Point> getPoints() {
        return points;
    }

    public LinkedHashMap<String, Rib> getRibs() {
        return ribs;
    }
}
