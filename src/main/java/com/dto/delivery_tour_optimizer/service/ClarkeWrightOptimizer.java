package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import java.util.*;

public class ClarkeWrightOptimizer implements TourOptimizer {

    @Override
    public List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle) {
        if (deliveries.isEmpty()) return new ArrayList<>();

        // Étape 1: Calculer les "économies" pour chaque paire
        List<DeliveryPair> pairs = new ArrayList<>();

        for (int i = 0; i < deliveries.size(); i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                Delivery d1 = deliveries.get(i);
                Delivery d2 = deliveries.get(j);

                double saving = DistanceUtils.distanceToWarehouse(d1, warehouse)
                        + DistanceUtils.distanceToWarehouse(d2, warehouse)
                        - DistanceUtils.distanceBetween(d1, d2);

                pairs.add(new DeliveryPair(d1, d2, saving));
            }
        }

        // Étape 2: Trier par économie décroissante
        pairs.sort((p1, p2) -> Double.compare(p2.saving, p1.saving));

        // Étape 3: Construire la route en partant de la meilleure paire
        return buildSimpleRoute(pairs, deliveries);
    }

    private List<Delivery> buildSimpleRoute(List<DeliveryPair> pairs, List<Delivery> allDeliveries) {
        if (pairs.isEmpty()) return new ArrayList<>(allDeliveries);

        // On prend la meilleure paire comme base
        DeliveryPair bestPair = pairs.get(0);
        List<Delivery> route = new ArrayList<>();
        route.add(bestPair.delivery1);
        route.add(bestPair.delivery2);

        // On ajoute les autres livraisons dans l'ordre des économies
        Set<Delivery> added = new HashSet<>();
        added.add(bestPair.delivery1);
        added.add(bestPair.delivery2);

        for (DeliveryPair pair : pairs) {
            if (!added.contains(pair.delivery1)) {
                route.add(pair.delivery1);
                added.add(pair.delivery1);
            }
            if (!added.contains(pair.delivery2)) {
                route.add(pair.delivery2);
                added.add(pair.delivery2);
            }
        }

        return route;
    }

    private static class DeliveryPair {
        Delivery delivery1;
        Delivery delivery2;
        double saving;

        DeliveryPair(Delivery d1, Delivery d2, double saving) {
            this.delivery1 = d1;
            this.delivery2 = d2;
            this.saving = saving;
        }
    }
}