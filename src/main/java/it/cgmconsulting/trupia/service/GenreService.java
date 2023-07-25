package it.cgmconsulting.trupia.service;

import it.cgmconsulting.trupia.entity.Genre;
import it.cgmconsulting.trupia.exception.ResourceNotFoundException;
import it.cgmconsulting.trupia.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    protected Genre findGenreById(long genreId){
        Genre g = genreRepository.findById(genreId).orElseThrow(
                () -> new ResourceNotFoundException("Genre" , "genreId" , genreId)
        );
        return g;
    }


}
