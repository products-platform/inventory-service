package com.web.inventory.services;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;
import com.product.enums.OrderStatus;
import com.web.inventory.mappers.InventoryMapper;
import com.web.inventory.models.Inventory;
import com.web.inventory.models.InventoryTransaction;
import com.web.inventory.repos.InventoryRepository;
import com.web.inventory.repos.InventoryTxnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTxnRepository txnRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse addStock(InventoryRequest request) {
        Inventory inventory = inventoryRepository
                .findByVariantSkuAndLocationId(request.variantName(), Long.valueOf(request.locationId()))
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setVariantSku(request.variantName());
                    inv.setLocationId(Long.valueOf(request.locationId()));
                    return inv;
                });

        inventory.setAvailableQty(inventory.getAvailableQty() + request.quantity());

        inventoryRepository.save(inventory);

        //logTxn(request.variantName(), request.locationId(), "ADD", request.quantity(), null);

        return inventoryMapper.toRecord(inventory);
    }


    @Override
    @Transactional
    public String reserveStock(List<InventoryRequest> requests) {
        for (InventoryRequest req : requests) {
            int updated = inventoryRepository.reserveStock(
                    req.variantName(),   // SKU
                    req.locationId(),
                    req.quantity()
            );

            if (updated == 0) {
                throw new RuntimeException(
                        "Insufficient stock for SKU: " + req.variantName()
                );
            }

            InventoryTransaction inventoryTransaction = inventoryMapper.toEntity(req);

            txnRepository.save(inventoryTransaction);
        }
        return "Reserved";
    }

    @Override
    @Transactional
    public String releaseStock(List<InventoryRequest> requests) {
        for (InventoryRequest req : requests) {

            int updated = inventoryRepository.releaseStock(
                    req.variantName(),
                    req.locationId(),
                    req.quantity()
            );

            if (updated == 0) {
                throw new RuntimeException("Release failed for SKU: " + req.variantName());
            }

            InventoryTransaction inventoryTransaction = inventoryMapper.toEntity(req);
            inventoryTransaction.setStatus(OrderStatus.RELEASED);

            txnRepository.save(inventoryTransaction);
        }
        return "Released";
    }

    @Override
    public InventoryResponse deductStock(List<InventoryRequest> inventoryRequests) {
        return null;
    }

    /*@Override
    public InventoryResponse reserveStock(List<InventoryRequest> inventoryRequests) {

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
    public InventoryResponse releaseStock(List<InventoryRequest> inventoryRequests) {

        Inventory inventory = getInventory(request);

        inventory.setReservedQty(inventory.getReservedQty() - request.quantity());
        inventory.setAvailableQty(inventory.getAvailableQty() + request.quantity());

        logTxn(request.variantSku(), request.locationId(), "RELEASE",
                request.quantity(), request.referenceId());

        return map(inventory);
    }

    @Override
    public InventoryResponse deductStock(List<InventoryRequest> inventoryRequests) {

        Inventory inventory = getInventory(request);

        inventory.setReservedQty(inventory.getReservedQty() - request.quantity());

        logTxn(request.variantSku(), request.locationId(), "DEDUCT",
                request.quantity(), request.referenceId());

        return map(inventory);
    }

    private Inventory getInventory(List<InventoryRequest> inventoryRequests) {
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
    }*/
}
