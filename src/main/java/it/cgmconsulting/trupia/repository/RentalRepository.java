package it.cgmconsulting.trupia.repository;

import it.cgmconsulting.trupia.entity.Rental;
import it.cgmconsulting.trupia.entity.RentalId;
import it.cgmconsulting.trupia.payload.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental , RentalId> {
    @Query(value = "SELECT new it.cgmconsulting.trupia.payload.response.CustomerStoreResponse(" +
            "r.rentalId.inventory.store.storeName, " +
            "COUNT(DISTINCT r.rentalId.customer.customerId) AS totalCustomers" +
            ") FROM Rental r " +
            "WHERE r.rentalId.inventory.store.storeName = :storeName " +
            "GROUP BY r.rentalId.inventory.store.storeName")
    CustomerStoreResponse countCustomersByStore(@Param("storeName") String storeName);

    @Query(value = "SELECT r FROM Rental r " +
            "WHERE r.rentalId.customer.customerId = :customerId " +
            "AND r.rentalId.inventory.inventoryId = :inventoryId AND r.rentalId.rentalDate = :rentalDate")
    Rental findSingleRental(
            @Param("customerId") long customerId,
            @Param("inventoryId") long inventoryId,
            @Param("rentalDate") LocalDateTime rentalDate);

    @Query(value = "SELECT new it.cgmconsulting.trupia.payload.response.CountRentalsResponse(" +
            "COUNT(r.rentalId) AS totalRentals" +
            ") FROM Rental r " +
            "WHERE (r.rentalId.rentalDate >= :start AND r.rentalId.rentalDate <= :end) " +
            "AND r.rentalId.inventory.store.storeId = :storeId " )
    CountRentalsResponse countRentalsInDateRangeByStore(@Param("storeId") long storeId , @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT new it.cgmconsulting.trupia.payload.response.FilmRentResponse(" +
            "r.rentalId.inventory.film.filmId, " +
            "r.rentalId.inventory.film.title, " +
            "r.rentalId.inventory.store.storeName" +
            ") From Rental r " +
            "WHERE r.rentalId.customer.customerId = :customerId")
    List<FilmRentResponse> findAllFilmsRentByOneCustomer(@Param("customerId") long customerId);

    @Query("SELECT NEW it.cgmconsulting.trupia.payload.response.FilmMaxRentResponse(" +
            "r.rentalId.inventory.film.filmId, " +
            "r.rentalId.inventory.film.title, " +
            "COUNT(r) AS totalRent" +
            ") FROM Rental r " +
            "GROUP BY r.rentalId.inventory.film.filmId, r.rentalId.inventory.film.title " +
            "HAVING COUNT(r) = ("+
            " SELECT MAX(cnt) FROM (" +
            "  SELECT COUNT(r2) AS cnt FROM Rental r2 " +
            "  GROUP BY r2.rentalId.inventory.film.filmId" +
            " ) AS counts)")
    List<FilmMaxRentResponse> findFilmWithMaxNumberOfRent();

    @Query("SELECT NEW it.cgmconsulting.trupia.payload.response.FilmRentableResponse(" +
            "r.rentalId.inventory.film.title, " +
            "r.rentalId.inventory.store.storeName, " +
            "COUNT(r.rentalId.rentalDate) AS totCopiesStore, " +
            "SUM(CASE WHEN r.rentalReturn IS NOT NULL THEN 1 ELSE 0 END) AS copiesAvailable" +
            ") FROM Rental r " +
            "WHERE r.rentalId.inventory.film.title = :title " +
            "GROUP BY r.rentalId.inventory.film.title, r.rentalId.inventory.store.storeName")
    List<FilmRentableResponse> findRentableFilms(@Param("title") String title);
}
