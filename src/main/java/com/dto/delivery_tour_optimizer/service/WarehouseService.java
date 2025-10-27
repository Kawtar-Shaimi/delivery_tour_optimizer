package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.repository.WarehouseRepository;
import java.util.List;

public class WarehouseService {

    private WarehouseRepository warehouseRepository;

    public WarehouseService() {}

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrepôt non trouvé"));
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    // Setter pour l'injection XML
    public void setWarehouseRepository(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }
}