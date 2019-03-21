package com.nickdnepr.strategy.map;

import com.nickdnepr.strategy.map.routing.Graph;
import com.nickdnepr.strategy.map.routing.Route;
import com.nickdnepr.strategy.map.routing.graphComponents.Point;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private Point[][] gameMap;
    private List<Point> points;
    private int width;
    private int height;

    public GameMap(int width, int height) {
        gameMap = new Point[height][width];
        points = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    public void addPoint(Point point) {
        if (points.contains(point)) {
            throw new IllegalArgumentException("cannot add the same point twice");
        }
        if (gameMap[point.getCoordinates().getY()][point.getCoordinates().getX()] != null) {
            throw new IllegalArgumentException("this coordinates are already assigned by another point");
        }
        points.add(point);
        gameMap[point.getCoordinates().getY()][point.getCoordinates().getX()] = point;
    }

    public Point addPoint(int x, int y, SurfaceType surfaceType) {
        String qualifier = x + "_" + y;
        Point point = new Point(qualifier, surfaceType, x, y);
        addPoint(point);
        return point;
    }

    public void printMap() {
        System.out.print("   ");

        for (int i = 0; i < gameMap[0].length; i++) {
            System.out.print(i + "  ");
        }
        System.out.println("X");

        for (int i = 0; i < gameMap.length; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < gameMap[i].length; j++) {
                if (gameMap[i][j] != null) {
                    System.out.print(gameMap[i][j].getSurfaceType().getDrawingString());
                } else {
                    System.out.print('*');
                }
                System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println("Y");
        System.out.println();
    }

    public Graph getRoutingGraph(RoutingPredicate predicate, Coordinates start) {
        Graph graph = new Graph("tmp");

        int[][] checkedMatrix = new int[height][width];
        List<Coordinates> possibleOnes = new ArrayList<>();
        List<Point> areaOfRouting = new ArrayList<>();
        System.out.println(gameMap[start.getY()][start.getX()].getSurfaceType());
        if (predicate.validatePoint(gameMap[start.getY()][start.getX()])) {
            checkedMatrix[start.getY()][start.getX()] = 1;
            possibleOnes.add(start);
            areaOfRouting.add(gameMap[start.getY()][start.getX()]);
            graph.addPoint(gameMap[start.getY()][start.getX()]);
        } else {
            System.out.println("start of the route is not valid");
            return null;
//            throw new IllegalArgumentException("start of the route is not valid");
        }

        while (!possibleOnes.isEmpty()) {
            Coordinates currentOne = possibleOnes.get(0);

            for (Coordinates neighbour : getPossibleNeighbours(checkedMatrix, currentOne)) {
                if (predicate.validatePoint(gameMap[neighbour.getY()][neighbour.getX()])) {
                    graph.addPoint(gameMap[neighbour.getY()][neighbour.getX()]);
                    checkedMatrix[neighbour.getY()][neighbour.getX()] = 1;
                    possibleOnes.add(neighbour);
                    areaOfRouting.add(gameMap[neighbour.getY()][neighbour.getX()]);
                    addRibsForPoint(graph, gameMap[neighbour.getY()][neighbour.getX()], areaOfRouting);
                } else {
                    checkedMatrix[neighbour.getY()][neighbour.getX()] = -1;
                }
            }
            checkedMatrix[currentOne.getY()][currentOne.getX()] = 2;
            possibleOnes.remove(currentOne);
//            System.out.println(possibleOnes.toString());
        }
//        printCheckedMatrix(checkedMatrix);
        return graph;
    }

    public Route getRoute(Coordinates start, Coordinates end, RoutingPredicate predicate) {
        if (gameMap[start.getY()][start.getX()] == null || gameMap[end.getY()][end.getX()] == null) {
            System.out.println("Not exists");
            return null;
        }
        Graph graph = getRoutingGraph(predicate, start);
        if (graph==null){
            return null;
        }
        return graph.getRoute(gameMap[start.getY()][start.getX()], gameMap[end.getY()][end.getX()], predicate);
    }

    private List<Coordinates> getPossibleNeighbours(int[][] checkedMatrix, Coordinates currentPosition) {
        List<Coordinates> resultList = new ArrayList<>();
        boolean canGoLeft = false;
        boolean canGoRight = false;
        if (currentPosition.getX() > 0) {
            canGoLeft = true;
            if (checkedMatrix[currentPosition.getY()][currentPosition.getX() - 1] == 0) {
                resultList.add(new Coordinates(currentPosition.getX()-1, currentPosition.getY()));
            }
        }
        if (currentPosition.getX() < width - 1) {
            canGoRight = true;
            if (checkedMatrix[currentPosition.getY()][currentPosition.getX() + 1] == 0) {
                resultList.add(new Coordinates(currentPosition.getX()+1, currentPosition.getY()));
            }
        }
        if (currentPosition.getY() > 0) {
            if (checkedMatrix[currentPosition.getY() - 1][currentPosition.getX()] == 0) {
                resultList.add(new Coordinates(currentPosition.getX(), currentPosition.getY()-1));
            }
            if (canGoLeft) {
                if (checkedMatrix[currentPosition.getY() - 1][currentPosition.getX() - 1] == 0) {
                    resultList.add(new Coordinates(currentPosition.getX() - 1, currentPosition.getY() - 1));
                }
            }
            if (canGoRight) {
                if (checkedMatrix[currentPosition.getY() - 1][currentPosition.getX() + 1] == 0) {
                    resultList.add(new Coordinates(currentPosition.getX() + 1, currentPosition.getY() - 1));
                }
            }
        }
        if (currentPosition.getY() < height - 1) {
            if (checkedMatrix[currentPosition.getY() + 1][currentPosition.getX()] == 0) {
                resultList.add(new Coordinates(currentPosition.getX(), currentPosition.getY()+1));
            }
            if (canGoLeft) {
                if (checkedMatrix[currentPosition.getY() + 1][currentPosition.getX() - 1] == 0) {
                    resultList.add(new Coordinates(currentPosition.getX() - 1, currentPosition.getY() + 1));
                }
            }
            if (canGoRight) {
                if (checkedMatrix[currentPosition.getY() + 1][currentPosition.getX() + 1] == 0) {
                    resultList.add(new Coordinates(currentPosition.getX() + 1, currentPosition.getY() + 1));
                }
            }
        }
        return resultList;
    }

    private void addRibsForPoint(Graph graph, Point addedPoint, List<Point> knownArea) {
        for (Point p : knownArea) {
            int deltaX = Math.abs(p.getCoordinates().getX() - addedPoint.getCoordinates().getX());
            int deltaY = Math.abs(p.getCoordinates().getY() - addedPoint.getCoordinates().getY());
            if (deltaX <= 1 && deltaY <= 1 && addedPoint != p) {
                double price = 1;
                if (deltaX == deltaY) {
                    price = 1.41;
                }
                graph.addRib(p, addedPoint, price * addedPoint.getSurfaceType().getMovingCoefficient());
                graph.addRib(addedPoint, p, price * p.getSurfaceType().getMovingCoefficient());
            }
        }
    }

    private void printCheckedMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public Point getPoint(int x, int y) {
        return gameMap[y][x];
    }


    public List<Point> getPoints() {
        return points;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
