package it.cgmconsulting.trupia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @AllArgsConstructor @ToString
public class RentalId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "inventory_id" , nullable = false)
    private Inventory inventory;
    @Column(nullable = false)
    private LocalDateTime rentalDate;

}
