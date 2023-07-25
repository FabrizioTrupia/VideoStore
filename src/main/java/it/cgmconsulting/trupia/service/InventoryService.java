package it.cgmconsulting.trupia.service;

import it.cgmconsulting.trupia.entity.Customer;
import it.cgmconsulting.trupia.entity.Film;
import it.cgmconsulting.trupia.entity.Inventory;
import it.cgmconsulting.trupia.entity.Store;
import it.cgmconsulting.trupia.exception.ResourceNotFoundException;
import it.cgmconsulting.trupia.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final FilmService filmService;
    private final StoreService storeService;

    public ResponseEntity<?> addFilmToStore(long storeId, long filmId) {
       filmService.findFilmById(filmId);
       storeService.findStoreById(storeId);

       Inventory i = new Inventory(new Store(storeId) , new Film(filmId));

       inventoryRepository.save(i);
       return new ResponseEntity<>("Add film to store" , HttpStatus.OK);
    }

    protected Inventory findInventoryById(long inventoryId){
        Inventory i = inventoryRepository.findById(inventoryId).orElseThrow(
                () -> new ResourceNotFoundException("Inventory" , "customerId" , inventoryId)
        );

        return i;
    }

}
