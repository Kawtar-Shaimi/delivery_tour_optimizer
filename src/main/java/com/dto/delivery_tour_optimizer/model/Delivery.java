package com.dto.delivery_tour_optimizer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private double latitude;
    private double longitude;
    private double weight;
    private double volume;
    private String timeSlot; // ex : 09:00-11:00

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne
    private Tour tour;
}