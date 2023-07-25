package it.cgmconsulting.trupia.service;

import it.cgmconsulting.trupia.entity.Film;
import it.cgmconsulting.trupia.entity.Genre;
import it.cgmconsulting.trupia.entity.Language;
import it.cgmconsulting.trupia.exception.ResourceNotFoundException;
import it.cgmconsulting.trupia.payload.request.FilmRequest;
import it.cgmconsulting.trupia.payload.response.FilmResponse;
import it.cgmconsulting.trupia.repository.FilmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final GenreService genreService;
    private final LanguageService languageService;
    private final StaffService staffService;

    @Transactional
    public ResponseEntity<?> updateFilm(FilmRequest request , long filmId) {
        Film f = findFilmById(filmId);

        f.setDescription(request.getDescription());
        f.setTitle(request.getTitle());
        f.setReleaseYear(request.getReleaseYear());
        f.setGenre(genreService.findGenreById(request.getGenreId()));
        f.setLanguage(languageService.findLanguageById(request.getLanguageId()));

        return new ResponseEntity<>("Film has been updated" , HttpStatus.OK);
    }

    public ResponseEntity<?> findFilmsByLanguage(long languageId) {
        languageService.findLanguageById(languageId);
        return new ResponseEntity<>(filmRepository.findFilmsByLanguage(languageId) , HttpStatus.OK);
    }

    public ResponseEntity<?> findFilmByActors(List<Long> staffId) {
        List<Long> noStaffId = new ArrayList<>();

        // Verifica l'esistenza di ogni staffId nella lista
        for (Long id : staffId) {
            if (!staffService.staffExistsById(id)) {
                noStaffId.add(id);
            }
        }

        if (!noStaffId.isEmpty())
            return new ResponseEntity<>("These ids don't exist: " + noStaffId, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(filmRepository.findFilmByActors(staffId) , HttpStatus.OK);
    }

    protected Film findFilmById(long filmId){
        Film f = filmRepository.findById(filmId).orElseThrow(
                () -> new ResourceNotFoundException("Film" , "filmId" , filmId)
        );

        return f;
    }

    protected Film findFilmByTitle(String title) {
        Film f = filmRepository.findFilmByTitle(title).orElseThrow(
                () -> new ResourceNotFoundException("Title" , "title" , title)
        );

        return f;
    }
}
