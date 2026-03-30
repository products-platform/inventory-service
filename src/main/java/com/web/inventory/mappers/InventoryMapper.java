package com.web.inventory.mappers;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;
import com.web.inventory.models.Inventory;
import com.web.inventory.models.InventoryTransaction;

public interface InventoryMapper {
    InventoryResponse toRecord(Inventory inventory);

    InventoryTransaction toEntity(InventoryRequest req);
}
