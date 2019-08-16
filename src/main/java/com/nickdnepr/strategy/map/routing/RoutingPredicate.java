package com.nickdnepr.strategy.map.routing;

import com.nickdnepr.strategy.map.surface.SurfaceType;
import com.nickdnepr.strategy.map.routing.graphComponents.Point;
import com.nickdnepr.strategy.map.routing.graphComponents.Rib;

public interface RoutingPredicate {

    int INVALID_RIB_PRICE = 100000;

    RoutingPredicate SURFACE_DEFAULT = new RoutingPredicate() {
        @Override
        public double getResultingPrice(Rib rib) {
            if (rib.getDestination().getSurfaceType() == SurfaceType.WATER) {
                return INVALID_RIB_PRICE;
            }
            double heightDifference = rib.getDestination().getCoordinates().getHeight() - rib.getSource().getCoordinates().getHeight();
            if (heightDifference > 1 || heightDifference < -1) {
                return INVALID_RIB_PRICE;
            }
            return rib.getPrice() * Math.pow(1.2, heightDifference);
        }

        @Override
        public boolean validateRoute(Route route) {
            for (PointWeightContainer point : route.getWayPoints()) {
                if (point.getPoint().getSurfaceType() == SurfaceType.WATER) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean validatePoint(Point point) {
            if (point == null) {
                return false;
            }
            return point.getSurfaceType() != SurfaceType.WATER;
        }
    };

    RoutingPredicate IDEAL_HOVER = new RoutingPredicate() {
        @Override
        public double getResultingPrice(Rib rib) {
            if (rib.isCorner()) {
                return 1.41;
            }
            return 1;
        }

        @Override
        public boolean validateRoute(Route route) {
            return true;
        }

        @Override
        public boolean validatePoint(Point point) {
            return point != null;
        }
    };

    double getResultingPrice(Rib rib);

    boolean validateRoute(Route route);

    boolean validatePoint(Point point);

    default boolean validateRib(Rib rib){
        return getResultingPrice(rib)!=INVALID_RIB_PRICE;
    }
}
