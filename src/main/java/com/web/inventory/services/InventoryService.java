package com.web.inventory.services;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;
import com.product.dtos.ReserveRequest;

public interface InventoryService {

    InventoryResponse addStock(InventoryRequest request);

    InventoryResponse reserveStock(ReserveRequest request);

    InventoryResponse releaseStock(ReserveRequest request);

    InventoryResponse deductStock(ReserveRequest request);

}
