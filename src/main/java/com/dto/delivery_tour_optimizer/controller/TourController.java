package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.service.TourService;
import com.dto.delivery_tour_optimizer.service.WarehouseService;
import com.dto.delivery_tour_optimizer.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class TourController {

    private TourService tourService;
    private WarehouseService warehouseService;
    private VehicleService vehicleService;

    // CONSTRUCTEUR SANS PARAMÈTRES obligatoire pour XML
    public TourController() {}

    // SETTERS pour l'injection XML
    public void setTourService(TourService tourService) {
        this.tourService = tourService;
    }

    public void setWarehouseService(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @RequestMapping(value = "/api/tours/optimize", method = RequestMethod.POST)
    public ResponseEntity<?> optimizeTour(@RequestBody TourRequestDTO request) {
        try {
            List<Delivery> optimizedRoute = tourService.getOptimizedTour(request);

            // Calculer la distance totale
            Warehouse warehouse = warehouseService.getWarehouseById(request.getWarehouseId());
            double totalDistance = tourService.getTotalDistance(optimizedRoute, warehouse);

            // Créer la réponse
            OptimizationResponse response = new OptimizationResponse(optimizedRoute, totalDistance, request.getOptimizerType());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    // Classe interne pour la réponse d'optimisation
    public static class OptimizationResponse {
        private List<Delivery> optimizedRoute;
        private double totalDistance;
        private String algorithmUsed;

        public OptimizationResponse(List<Delivery> optimizedRoute, double totalDistance, String algorithmUsed) {
            this.optimizedRoute = optimizedRoute;
            this.totalDistance = totalDistance;
            this.algorithmUsed = algorithmUsed;
        }

        // Getters
        public List<Delivery> getOptimizedRoute() { return optimizedRoute; }
        public double getTotalDistance() { return totalDistance; }
        public String getAlgorithmUsed() { return algorithmUsed; }
    }

    // Classe interne pour les erreurs
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() { return error; }
    }
}