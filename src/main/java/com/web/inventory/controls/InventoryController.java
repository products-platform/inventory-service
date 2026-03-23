package com.web.inventory.controls;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;
import com.product.dtos.ReserveRequest;
import com.web.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public InventoryResponse addStock(@RequestBody InventoryRequest request) {
        return inventoryService.addStock(request);
    }

    @PostMapping("/reserve")
    public InventoryResponse reserve(@RequestBody ReserveRequest request) {
        return inventoryService.reserveStock(request);
    }

    @PostMapping("/release")
    public InventoryResponse release(@RequestBody ReserveRequest request) {
        return inventoryService.releaseStock(request);
    }

    @PostMapping("/deduct")
    public InventoryResponse deduct(@RequestBody ReserveRequest request) {
        return inventoryService.deductStock(request);
    }
}
