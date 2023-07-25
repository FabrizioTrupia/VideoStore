package it.cgmconsulting.trupia.service;

import it.cgmconsulting.trupia.entity.Customer;
import it.cgmconsulting.trupia.entity.Inventory;
import it.cgmconsulting.trupia.entity.Rental;
import it.cgmconsulting.trupia.entity.RentalId;
import it.cgmconsulting.trupia.payload.request.RentalRequest;
import it.cgmconsulting.trupia.payload.response.CountRentalsResponse;
import it.cgmconsulting.trupia.payload.response.CustomerStoreResponse;
import it.cgmconsulting.trupia.payload.response.FilmRentableResponse;
import it.cgmconsulting.trupia.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final StoreService storeService;
    private final CustomerService customerService;
    private final InventoryService inventoryService;
    private final FilmService filmService;

    public ResponseEntity<?> countCustomersByStore(String storeName) {
        CustomerStoreResponse totalCustomers = rentalRepository.countCustomersByStore(storeName);
        storeService.findStoreByStoreName(storeName);

        if (totalCustomers == null)
            return new ResponseEntity<>("Store hasn't customer" , HttpStatus.OK);

        return new ResponseEntity<>(totalCustomers , HttpStatus.OK);
    }

    public ResponseEntity<?> addOrUpdateRental(RentalRequest request) {
        RentalId rI = new RentalId(new Customer(request.getCustomerId()), new Inventory(request.getInventoryId()), request.getRentalDate());
        customerService.findCustomerById(request.getCustomerId());
        inventoryService.findInventoryById(request.getInventoryId());

        // Verifica se esiste un noleggio
        Rental existingRental = rentalRepository.findSingleRental(
                request.getCustomerId(), request.getInventoryId(), request.getRentalDate());

        if (existingRental != null) {
            // Aggiorna la data di restituzione solo se è null
            if (existingRental.getRentalReturn() == null) {
                existingRental.setRentalReturn(LocalDateTime.now());
                rentalRepository.save(existingRental);
                return new ResponseEntity<>("Rental updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The movie has already been returned", HttpStatus.BAD_REQUEST);
            }
        }
        // Altrimenti crea un nuovo noleggio
        Rental r = new Rental(rI, request.getRentalReturn());
        rentalRepository.save(r);
        return new ResponseEntity<>("Rental created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<?> countRentalsInDateRangeByStore(long storeId, LocalDate start, LocalDate end) {
        if (start == null || end == null)
            return new ResponseEntity<>("Start date or end date are null" , HttpStatus.BAD_REQUEST);

        LocalDateTime s = start.atTime(0,0,0);
        LocalDateTime e = end.atTime(23,59,59);

        CountRentalsResponse count = rentalRepository.countRentalsInDateRangeByStore(storeId, s, e);

        if (count == null)
            return new ResponseEntity<>("The store has not made any rentals in this period", HttpStatus.OK);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }


    public ResponseEntity<?> findAllFilmsRentByOneCustomer(long customerId){
        customerService.findCustomerById(customerId);
        return new ResponseEntity<>(rentalRepository.findAllFilmsRentByOneCustomer(customerId) , HttpStatus.OK);
    }

    public ResponseEntity<?> findFilmWithMaxNumberOfRent() {
        return new ResponseEntity<>(rentalRepository.findFilmWithMaxNumberOfRent() , HttpStatus.OK);
    }

    public ResponseEntity<?> findRentableFilms(String title) {
        filmService.findFilmByTitle(title);
        return new ResponseEntity<>(rentalRepository.findRentableFilms(title) , HttpStatus.OK);
    }
}
