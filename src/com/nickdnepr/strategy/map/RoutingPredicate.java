package com.nickdnepr.strategy.map;

import com.nickdnepr.strategy.map.routing.PointWeightContainer;
import com.nickdnepr.strategy.map.routing.Route;
import com.nickdnepr.strategy.map.routing.graphComponents.Point;
import com.nickdnepr.strategy.map.routing.graphComponents.Rib;

public interface RoutingPredicate {

    RoutingPredicate SURFACE_DEFAULT = new RoutingPredicate() {
        @Override
        public double getResultingPrice(Rib rib) {
            if (rib.getDestination().getSurfaceType() == SurfaceType.WATER) {
                return 1000000000;
            }
            return rib.getPrice();
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
            return point!=null;
        }
    };

    double getResultingPrice(Rib rib);

    boolean validateRoute(Route route);

    boolean validatePoint(Point point);
}
