package com.web.inventory.services;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;
import com.product.dtos.ReserveRequest;
import com.web.inventory.models.Inventory;
import com.web.inventory.models.InventoryTransaction;
import com.web.inventory.repos.InventoryRepository;
import com.web.inventory.repos.InventoryTxnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTxnRepository txnRepository;

    @Override
    public InventoryResponse addStock(InventoryRequest request) {
        Inventory inventory = inventoryRepository
                .findByVariantSkuAndLocationId(request.variantSku(), request.locationId())
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setVariantSku(request.variantSku());
                    inv.setLocationId(request.locationId());
                    return inv;
                });

        inventory.setAvailableQty(inventory.getAvailableQty() + request.quantity());

        inventoryRepository.save(inventory);

        logTxn(request.variantSku(), request.locationId(), "ADD", request.quantity(), null);

        return map(inventory);
    }

    @Override
    public InventoryResponse reserveStock(ReserveRequest request) {

        Inventory inventory = getInventory(request);

        if (inventory.getAvailableQty() < request.quantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setAvailableQty(inventory.getAvailableQty() - request.quantity());
        inventory.setReservedQty(inventory.getReservedQty() + request.quantity());

        logTxn(request.variantSku(), request.locationId(), "RESERVE",
                request.quantity(), request.referenceId());

        return map(inventory);
    }

    @Override
    public InventoryResponse releaseStock(ReserveRequest request) {

        Inventory inventory = getInventory(request);

        inventory.setReservedQty(inventory.getReservedQty() - request.quantity());
        inventory.setAvailableQty(inventory.getAvailableQty() + request.quantity());

        logTxn(request.variantSku(), request.locationId(), "RELEASE",
                request.quantity(), request.referenceId());

        return map(inventory);
    }

    @Override
    public InventoryResponse deductStock(ReserveRequest request) {

        Inventory inventory = getInventory(request);

        inventory.setReservedQty(inventory.getReservedQty() - request.quantity());

        logTxn(request.variantSku(), request.locationId(), "DEDUCT",
                request.quantity(), request.referenceId());

        return map(inventory);
    }

    private Inventory getInventory(ReserveRequest request) {
        return inventoryRepository
                .findByVariantSkuAndLocationId(request.variantSku(), request.locationId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    private void logTxn(String sku, Long locId, String type, Integer qty, String ref) {
        InventoryTransaction txn = new InventoryTransaction();
        txn.setVariantSku(sku);
        txn.setLocationId(locId);
        txn.setType(type);
        txn.setQuantity(qty);
        txn.setReferenceId(ref);
        txn.setCreatedAt(Instant.now());
        txnRepository.save(txn);
    }

    private InventoryResponse map(Inventory inv) {
        return new InventoryResponse(
                inv.getVariantSku(),
                inv.getLocationId(),
                inv.getAvailableQty(),
                inv.getReservedQty()
        );
    }
}
