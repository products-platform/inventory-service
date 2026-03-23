package com.web.inventory.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "INVENTORY_TXN")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String variantSku;
    private Long locationId;
    private String type;
    private Integer quantity;
    private String referenceId;

    private Instant createdAt;
}
