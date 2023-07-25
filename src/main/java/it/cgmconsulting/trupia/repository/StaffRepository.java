package it.cgmconsulting.trupia.repository;

import it.cgmconsulting.trupia.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff , Long> {
}
