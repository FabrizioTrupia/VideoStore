package it.cgmconsulting.trupia.controller;

import it.cgmconsulting.trupia.payload.request.FilmRequest;
import it.cgmconsulting.trupia.payload.request.RentalRequest;
import it.cgmconsulting.trupia.service.FilmService;
import it.cgmconsulting.trupia.service.InventoryService;
import it.cgmconsulting.trupia.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class VideoStoreController {
    private final FilmService filmService;
    private final InventoryService inventoryService;
    private final RentalService rentalService;

    //NUMERO 1
    @PutMapping("/update-film/{filmId}")
    public ResponseEntity<?> updateFilm(@RequestBody @Valid FilmRequest request , @PathVariable long filmId){
        return filmService.updateFilm(request , filmId);
    }

    //NUMERO 2
    @GetMapping("/find-films-by-language/{languageId}")
    public ResponseEntity<?> findFilmsByLanguage (@PathVariable long languageId){
        return filmService.findFilmsByLanguage(languageId);
    }

    //NUMERO 3
    @PutMapping("/add-film-to-store/{storeId}/{filmId}")
    public ResponseEntity<?> addFilmToStore(@PathVariable long storeId , @PathVariable long filmId){
        return inventoryService.addFilmToStore(storeId , filmId);
    }

    //NUMERO 4
    @GetMapping("/count-customers-by-store/{storeName}")
    public ResponseEntity<?> countCustomersByStore(@PathVariable String storeName){
        return rentalService.countCustomersByStore(storeName);
    }

    //NUMERO 5
    @PutMapping("/add-update-rental")
    public ResponseEntity<?> addOrUpdateRental(@RequestBody @Valid RentalRequest request){
        return rentalService.addOrUpdateRental(request);
    }

    //NUMERO 6
    @GetMapping("/count-rentals-in-date-range-by-store/{storeId}")
    public ResponseEntity<?> countRentalsInDateRangeByStore(@PathVariable long storeId ,
                                                     @RequestParam (required = false) LocalDate start ,
                                                     @RequestParam (required = false) LocalDate end){
        return rentalService.countRentalsInDateRangeByStore(storeId , start , end);
    }


    //NUMERO 7
    @GetMapping("/find-all-films-rent-by-one-customer/{customerId}")
    public ResponseEntity<?> findAllFilmsRentByOneCustomer(@PathVariable long customerId){
        return rentalService.findAllFilmsRentByOneCustomer(customerId);
    }

    //NUMERO 8
    @GetMapping("/find-film-with-max-number-of-rent")
    public ResponseEntity<?> findFilmWithMaxNumberOfRent(){
        return rentalService.findFilmWithMaxNumberOfRent();
    }

    //NUMERO 9
    @GetMapping("/find-films-by-actors")
    public ResponseEntity<?> findFilmByActors(@RequestParam List<Long> staffId){
        return filmService.findFilmByActors(staffId);
    }

    //NUMERO 10
    @GetMapping("/find-rentable-films")
    public ResponseEntity<?> findRentableFilms(@RequestParam String title){
        return rentalService.findRentableFilms(title);
    }
}
