package it.cgmconsulting.trupia.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class RentalRequest {
    @Min(1)
    private long customerId;
    @Min(1)
    private long inventoryId;
    @NotNull
    private LocalDateTime rentalDate;
    private LocalDateTime rentalReturn;

}
