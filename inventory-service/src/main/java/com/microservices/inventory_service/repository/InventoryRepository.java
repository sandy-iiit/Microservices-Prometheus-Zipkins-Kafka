package com.microservices.inventory_service.repository;

import com.microservices.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);

}
