package com.web.inventory.listener;

import com.product.dtos.InventoryCreateEvent;
import com.product.topics.KafkaTopicConstants;
import com.web.inventory.models.Inventory;
import com.web.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ProductCreatedListener {

    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = KafkaTopicConstants.INVENTORY_CREATE_EVENT,
            groupId = "product-group"
    )
    public void consume(
            String inventoryCreateJson,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        InventoryCreateEvent event = objectMapper.readValue(inventoryCreateJson, InventoryCreateEvent.class);

        System.out.println("📦 Product Event | Topic: %s | Partition: %s | Offset: %s | Product Name: %s | Product Id: %s"
                        .formatted(topic, partition, offset, event.sku(), event.productId())
        );

        // Business logic here
        System.out.println("Product Name : %s and Product Id : %s".formatted(event.sku(), event.productId()));
        processProduct(event);
    }

    public void processProduct(InventoryCreateEvent event) {
        // Example business logic
        /*System.out.println("Processing product: " + event.sku());
        if (inventoryService.findByProductId(event.productId())) {
            return;
        }

        Inventory inventory = Inventory.builder()
                .productId(event.productId())
                .productName(event.sku())
                .availableQuantity(0)
                .reservedQuantity(0)
                .build();

        inventoryService.saveInventory(inventory);*/
    }
}
