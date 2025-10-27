package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.model.*;
import com.dto.delivery_tour_optimizer.repository.*;
import java.util.List;

public class TourService {

    private TourRepository tourRepository;
    private DeliveryRepository deliveryRepository;
    private VehicleRepository vehicleRepository;
    private WarehouseRepository warehouseRepository;
    private TourOptimizer nearestNeighborOptimizer;
    private TourOptimizer clarkeWrightOptimizer;

    // Constructeur pour l'injection XML
    public TourService() {}

    public List<Delivery> getOptimizedTour(TourRequestDTO request) {
        System.out.println("Optimisation demandée avec l'algorithme: " + request.getOptimizerType());

        // Récupérer les données depuis la base
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Entrepôt non trouvé"));
        List<Delivery> deliveries = deliveryRepository.findAllById(request.getDeliveryIds());

        // VÉRIFIER LES CONTRAINTES AVANT l'optimisation
        // 1. Vérifier le nombre de livraisons
        if (deliveries.size() > vehicle.getMaxDeliveries()) {
            throw new RuntimeException("Trop de livraisons pour ce véhicule. Maximum: " + vehicle.getMaxDeliveries());
        }

        // 2. Vérifier le poids total
        double totalWeight = deliveries.stream().mapToDouble(Delivery::getWeight).sum();
        if (totalWeight > vehicle.getMaxWeight()) {
            throw new RuntimeException("Poids total trop élevé pour ce véhicule. Maximum: " + vehicle.getMaxWeight() + " kg");
        }

        // 3. Vérifier le volume total
        double totalVolume = deliveries.stream().mapToDouble(Delivery::getVolume).sum();
        if (totalVolume > vehicle.getMaxVolume()) {
            throw new RuntimeException("Volume total trop élevé pour ce véhicule. Maximum: " + vehicle.getMaxVolume() + " m³");
        }

        // Choisir l'algorithme
        TourOptimizer optimizer = request.getOptimizerType().equals("NEAREST_NEIGHBOR")
                ? nearestNeighborOptimizer
                : clarkeWrightOptimizer;

        // Calculer la tournée optimisée
        List<Delivery> optimizedRoute = optimizer.calculateOptimalTour(deliveries, warehouse, vehicle);

        // Assigner l'ordre de livraison
        for (int i = 0; i < optimizedRoute.size(); i++) {
            optimizedRoute.get(i).setDeliveryOrder(i + 1);
        }

        return optimizedRoute;
    }

    public double getTotalDistance(List<Delivery> route, Warehouse warehouse) {
        if (route.isEmpty()) return 0.0;

        double total = 0.0;
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        // Distance depuis l'entrepôt jusqu'à la première livraison
        for (Delivery delivery : route) {
            total += DistanceUtils.calculateDistance(currentLat, currentLon,
                    delivery.getLatitude(), delivery.getLongitude());
            currentLat = delivery.getLatitude();
            currentLon = delivery.getLongitude();
        }

        // Retour à l'entrepôt
        total += DistanceUtils.calculateDistance(currentLat, currentLon,
                warehouse.getLatitude(), warehouse.getLongitude());

        return total;
    }

    // Getters et Setters pour l'injection XML
    public void setTourRepository(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void setWarehouseRepository(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public void setNearestNeighborOptimizer(TourOptimizer nearestNeighborOptimizer) {
        this.nearestNeighborOptimizer = nearestNeighborOptimizer;
    }

    public void setClarkeWrightOptimizer(TourOptimizer clarkeWrightOptimizer) {
        this.clarkeWrightOptimizer = clarkeWrightOptimizer;
    }
}