package it.cgmconsulting.trupia.repository;

import it.cgmconsulting.trupia.entity.Language;
import it.cgmconsulting.trupia.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language , Long> {



}
