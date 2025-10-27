package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.enums.VehicleType;
import com.dto.delivery_tour_optimizer.repository.VehicleRepository;
import java.util.List;

public class VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleService() {}

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle createVehicle(VehicleType type, String licensePlate) {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(type);
        vehicle.setLicensePlate(licensePlate);

        // Définir les contraintes selon le type
        switch (type) {
            case BIKE:
                vehicle.setMaxWeight(50.0);
                vehicle.setMaxVolume(0.5);
                vehicle.setMaxDeliveries(15);
                break;
            case VAN:
                vehicle.setMaxWeight(1000.0);
                vehicle.setMaxVolume(8.0);
                vehicle.setMaxDeliveries(50);
                break;
            case TRUCK:
                vehicle.setMaxWeight(5000.0);
                vehicle.setMaxVolume(40.0);
                vehicle.setMaxDeliveries(100);
                break;
        }

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    // Setter pour l'injection XML
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
}