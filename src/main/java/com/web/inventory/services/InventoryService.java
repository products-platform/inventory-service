package com.web.inventory.services;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse addStock(InventoryRequest request);

    String reserveStock(List<InventoryRequest> inventoryRequests);

    String releaseStock(List<InventoryRequest> inventoryRequests);

    InventoryResponse deductStock(List<InventoryRequest> inventoryRequests);

}
