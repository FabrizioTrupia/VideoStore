package it.cgmconsulting.trupia.service;

import it.cgmconsulting.trupia.entity.Language;
import it.cgmconsulting.trupia.exception.ResourceNotFoundException;
import it.cgmconsulting.trupia.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    protected Language findLanguageById(long languageId){
        Language l = languageRepository.findById(languageId).orElseThrow(
                () -> new ResourceNotFoundException("Language" , "languageId" , languageId)
        );
        return l;
    }

}
