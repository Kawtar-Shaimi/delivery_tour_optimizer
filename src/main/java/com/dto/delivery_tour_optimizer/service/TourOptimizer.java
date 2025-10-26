package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import java.util.List;

public interface TourOptimizer {
    List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle);

    // Méthode utilitaire pour calculer la distance totale
    double calculateTotalDistance(List<Delivery> route, Warehouse warehouse);
}