package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import java.util.*;

public class NearestNeighborOptimizer implements TourOptimizer {

    @Override
    public List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle) {
        if (deliveries.isEmpty()) return new ArrayList<>();

        List<Delivery> result = new ArrayList<>();
        List<Delivery> remaining = new ArrayList<>(deliveries);

        // Point de départ : l'entrepôt
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        while (!remaining.isEmpty()) {
            // Trouver la livraison la plus proche
            Delivery nearest = findNearest(currentLat, currentLon, remaining);

            // L'ajouter au résultat
            result.add(nearest);
            remaining.remove(nearest);

            // Mettre à jour la position actuelle
            currentLat = nearest.getLatitude();
            currentLon = nearest.getLongitude();
        }

        return result;
    }

    private Delivery findNearest(double currentLat, double currentLon, List<Delivery> deliveries) {
        Delivery nearest = deliveries.get(0);
        double minDistance = DistanceUtils.calculateDistance(currentLat, currentLon,
                nearest.getLatitude(), nearest.getLongitude());

        for (Delivery delivery : deliveries) {
            double distance = DistanceUtils.calculateDistance(currentLat, currentLon,
                    delivery.getLatitude(), delivery.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = delivery;
            }
        }

        return nearest;
    }
}