package com.web.inventory.repos;

import com.web.inventory.models.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTxnRepository extends JpaRepository<InventoryTransaction, Long> {
}
