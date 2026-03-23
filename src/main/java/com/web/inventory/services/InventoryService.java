package com.web.inventory.services;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;
import com.product.dtos.ReserveRequest;

public interface InventoryService {

    InventoryResponse addStock(InventoryRequest request);

    InventoryResponse reserveStock(ReserveRequest request);

    InventoryResponse releaseStock(ReserveRequest request);

    InventoryResponse deductStock(ReserveRequest request);

   /* InventoryDto getInventory(Long productId);

    InventoryDto reserve(Long productId, Integer quantity);

    InventoryDto confirm(Long productId, Integer quantity);

    InventoryDto release(Long productId, Integer quantity);

    void saveInventory(Inventory inventory);

    boolean findByProductId(Long aLong);

    boolean reserveStock(List<OrderItemEvent> items);*/
}
