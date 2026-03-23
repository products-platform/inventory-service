package com.web.inventory.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "INVENTORY",
        uniqueConstraints = @UniqueConstraint(columnNames = {"VARIANT_SKU", "LOCATION_ID"}))
@Getter
@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VARIANT_SKU", nullable = false)
    private String variantSku;

    @Column(name = "LOCATION_ID", nullable = false)
    private Long locationId;

    @Column(name = "AVAILABLE_QTY", nullable = false)
    private Integer availableQty = 0;

    @Column(name = "RESERVED_QTY", nullable = false)
    private Integer reservedQty = 0;
}
