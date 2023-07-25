package it.cgmconsulting.trupia.service;

import it.cgmconsulting.trupia.entity.Store;
import it.cgmconsulting.trupia.exception.ResourceNotFoundException;
import it.cgmconsulting.trupia.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    protected Store findStoreById(long storeId){
        Store s = storeRepository.findById(storeId).orElseThrow(
                () -> new ResourceNotFoundException("Store" , "storeId" , storeId)
        );
        return s;
    }

    protected Store findStoreByStoreName(String storeName){
        Store s = storeRepository.findByStoreName(storeName).orElseThrow(
                () -> new ResourceNotFoundException("Store" , "storeName" , storeName)
        );
        return s;
    }

    protected boolean isPresentStoreName(String storeName){
        if (storeRepository.findByStoreName(storeName).isPresent())
            return true;
        return false;
    }
}
