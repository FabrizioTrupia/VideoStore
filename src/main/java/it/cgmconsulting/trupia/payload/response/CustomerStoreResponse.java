package it.cgmconsulting.trupia.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CustomerStoreResponse {
    private String storeName;
    private long totalCustomers;
}
