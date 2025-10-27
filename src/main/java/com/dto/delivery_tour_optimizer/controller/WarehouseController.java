package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @RequestMapping("/api/warehouses")
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @RequestMapping("/api/warehouses/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        try {
            Warehouse warehouse = warehouseService.getWarehouseById(id);
            return ResponseEntity.ok(warehouse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/api/warehouses", method = RequestMethod.POST)
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.saveWarehouse(warehouse);
    }

    @RequestMapping(value = "/api/warehouses/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouse) {
        try {
            Warehouse existingWarehouse = warehouseService.getWarehouseById(id);
            warehouse.setId(id);
            Warehouse updatedWarehouse = warehouseService.saveWarehouse(warehouse);
            return ResponseEntity.ok(updatedWarehouse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/api/warehouses/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        try {
            warehouseService.deleteWarehouse(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}