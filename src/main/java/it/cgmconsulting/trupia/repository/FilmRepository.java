package it.cgmconsulting.trupia.repository;

import it.cgmconsulting.trupia.entity.Film;
import it.cgmconsulting.trupia.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film , Long> {
    @Query(value = "SELECT new it.cgmconsulting.trupia.payload.response.FilmResponse(" +
            "f.filmId, " +
            "f.title, " +
            "f.description, " +
            "f.releaseYear, " +
            "f.language.languageName" +
            ") FROM Film f " +
            "WHERE f.language.languageId = :languageId ")
    List<FilmResponse> findFilmsByLanguage(@Param("languageId") long languageId);

    @Query("SELECT new it.cgmconsulting.trupia.payload.response.FilmResponse(" +
            "f.filmId, " +
            "f.title, " +
            "f.description, " +
            "f.releaseYear, " +
            "f.language.languageName" +
            ") FROM Film f " +
            "WHERE f.filmId IN (" +
            "SELECT f2.filmId FROM Film f2 JOIN f2.staffMembers s WHERE s.staffId IN :staffId " +
            "GROUP BY f2.filmId " +
            "HAVING COUNT(DISTINCT s.staffId) = (SELECT COUNT(DISTINCT s2.staffId) FROM Film f2 JOIN f2.staffMembers s2 WHERE s2.staffId IN :staffId)" +
            ") ORDER BY f.title")
    List<FilmResponse> findFilmByActors(@Param("staffId") List<Long> staffId);

    Optional<Film> findFilmByTitle(String title);
}
