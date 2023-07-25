package it.cgmconsulting.trupia.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class FilmRequest {
    @NotBlank @Size(min = 2, max = 100)
    private String title;
    @NotBlank @Size(min = 2, max= 20000)
    private String description;
    @Positive @Min(1900) @Max(2100)
    private short releaseYear;
    @Min(1)
    private long languageId;
    @Min(1)
    private long genreId;
}
