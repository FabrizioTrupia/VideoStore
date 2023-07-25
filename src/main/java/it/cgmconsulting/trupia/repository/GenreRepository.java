package it.cgmconsulting.trupia.repository;

import it.cgmconsulting.trupia.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre , Long> {
}
