package it.cgmconsulting.trupia.repository;

import it.cgmconsulting.trupia.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory , Long> {
}
