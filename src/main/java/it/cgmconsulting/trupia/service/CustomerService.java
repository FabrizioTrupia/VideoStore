package it.cgmconsulting.trupia.service;

import it.cgmconsulting.trupia.entity.Customer;
import it.cgmconsulting.trupia.exception.ResourceNotFoundException;
import it.cgmconsulting.trupia.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    protected Customer findCustomerById(long customerId){
        Customer c = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer" , "customerId" , customerId)
        );

        return c;
    }
}
