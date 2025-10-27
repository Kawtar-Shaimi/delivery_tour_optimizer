package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.service.DeliveryService;
import com.dto.delivery_tour_optimizer.service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class DeliveryController {

    private DeliveryService deliveryService;
    private TourService tourService;

    // CONSTRUCTEUR SANS PARAMÈTRES obligatoire pour XML
    public DeliveryController() {}

    // SETTERS pour l'injection XML
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void setTourService(TourService tourService) {
        this.tourService = tourService;
    }

    @RequestMapping("/api/deliveries")
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @RequestMapping("/api/deliveries/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        try {
            Delivery delivery = deliveryService.getDeliveryById(id);
            return ResponseEntity.ok(delivery);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/api/deliveries", method = RequestMethod.POST)
    public Delivery createDelivery(@RequestBody Delivery delivery) {
        return deliveryService.saveDelivery(delivery);
    }

    @RequestMapping(value = "/api/deliveries/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, @RequestBody Delivery delivery) {
        try {
            Delivery existingDelivery = deliveryService.getDeliveryById(id);
            delivery.setId(id);
            Delivery updatedDelivery = deliveryService.saveDelivery(delivery);
            return ResponseEntity.ok(updatedDelivery);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/api/deliveries/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        try {
            deliveryService.deleteDelivery(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}