package it.cgmconsulting.trupia.repository;

import it.cgmconsulting.trupia.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store , Long> {
    Optional<Store> findByStoreName(String storeName);

}
