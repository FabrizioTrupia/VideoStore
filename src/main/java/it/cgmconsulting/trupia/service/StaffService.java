package it.cgmconsulting.trupia.service;

import it.cgmconsulting.trupia.entity.Staff;
import it.cgmconsulting.trupia.exception.ResourceNotFoundException;
import it.cgmconsulting.trupia.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;

    protected Staff findStaffById(long staffId){
        Staff s = staffRepository.findById(staffId).orElseThrow(
                () -> new ResourceNotFoundException("Staff" , "staffId" , staffId)
        );
        return s;
    }

    protected boolean staffExistsById(Long id) {
        return staffRepository.existsById(id);
    }
}
