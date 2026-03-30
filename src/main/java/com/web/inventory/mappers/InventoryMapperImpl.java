package com.web.inventory.mappers;

import com.product.dtos.InventoryRequest;
import com.product.dtos.InventoryResponse;
import com.product.enums.OrderStatus;
import com.web.inventory.models.Inventory;
import com.web.inventory.models.InventoryTransaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InventoryMapperImpl implements InventoryMapper {
    @Override
    public InventoryResponse toRecord(Inventory inventory) {
        return null;
    }

    @Override
    public InventoryTransaction toEntity(InventoryRequest req) {
        // ✅ Insert transaction log
        return InventoryTransaction.builder()
                .variantSku(req.variantName())
                .locationId(Long.valueOf(req.locationId()))
                .status(OrderStatus.RESERVED)
                .quantity(req.quantity())
                .referenceId(UUID.randomUUID().toString())
                .build();
    }
}
