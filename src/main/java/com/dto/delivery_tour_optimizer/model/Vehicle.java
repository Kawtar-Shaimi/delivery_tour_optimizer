package com.dto.delivery_tour_optimizer.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private String licensePlate;
    private double maxWeight;    // en kg
    private double maxVolume;    // en m³
    private int maxDeliveries;   // nombre max de livraisons

    // Méthode utilitaire pour vérifier les contraintes
    public boolean canCarry(double weight, double volume) {
        return weight <= maxWeight && volume <= maxVolume;
    }
}