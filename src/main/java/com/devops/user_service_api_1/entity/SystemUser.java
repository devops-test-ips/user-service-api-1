package com.devops.user_service_api_1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="system_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SystemUser {
    @Id
    @Column(name="user_id")
    private String userId;
    @Column(unique = true, length = 150, nullable = false)
    private String email;
    @Column(columnDefinition = "TINYINT", nullable = false)
    private boolean status;

    @OneToOne(mappedBy = "systemUser",
            fetch = FetchType.LAZY)
    private BillingAddress billingAddress;

}
