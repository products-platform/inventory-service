package com.web.inventory.repos;

import com.web.inventory.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /*Optional<Inventory> findByProductId(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i WHERE i.productId IN :ids")
    List<Inventory> findAllByProductIdsForUpdate(@Param("ids") List<Long> ids);*/

    Optional<Inventory> findByVariantSkuAndLocationId(String sku, Long locationId);

    @Modifying
    @Query("UPDATE Inventory i " +
            "SET i.availableQty = i.availableQty - :qty, " +
            "i.reservedQty = i.reservedQty + :qty " +
            "WHERE i.variantSku = :sku " +
            "AND i.locationId = :locationId " +
            "AND i.availableQty >= :qty")
    int reserveStock(String sku, Integer locationId, Integer qty);

    @Modifying
    @Transactional
    @Query("UPDATE Inventory i " +
            "SET i.availableQty = i.availableQty + :qty, " +
            "i.reservedQty = i.reservedQty - :qty " +
            "WHERE i.variantSku = :sku " +
            "AND i.locationId = :locationId " +
            "AND i.reservedQty >= :qty")
    int releaseStock(String sku, Integer locationId, Integer qty);
}
