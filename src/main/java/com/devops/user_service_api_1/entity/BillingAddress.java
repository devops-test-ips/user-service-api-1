package com.devops.user_service_api_1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="billing_address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingAddress {
    @Id
    @Column(name="address_id")
    private String addressId;
    private String address;
    private String city;
    private String country;
    private String postal;

    @OneToOne
    @JoinColumn(name="user_id", unique = true)
    private SystemUser systemUser;
}
