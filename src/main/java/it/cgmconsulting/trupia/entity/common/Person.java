package it.cgmconsulting.trupia.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public class Person implements Serializable {
    @Column(nullable = false , length = 50 )
    private String firstname;
    @Column(nullable = false , length = 50)
    private String lastname;

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
