package com.web.inventory.controls;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;
import com.web.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryRestController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public InventoryResponse addStock(@RequestBody InventoryRequest request) {
        return inventoryService.addStock(request);
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestBody List<InventoryRequest> inventoryRequests) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.reserveStock(inventoryRequests));
    }

    @PostMapping("/release")
    public ResponseEntity<String> release(@RequestBody List<InventoryRequest> inventoryRequests) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.releaseStock(inventoryRequests));
    }

    @PostMapping("/deduct")
    public InventoryResponse deduct(@RequestBody List<InventoryRequest> inventoryRequests) {
        return inventoryService.deductStock(inventoryRequests);
    }
}
