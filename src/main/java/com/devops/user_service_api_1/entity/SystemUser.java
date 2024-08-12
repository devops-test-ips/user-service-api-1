package com.devops.user_service_api_1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="system_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SystemUser {
    @Id
    @Column(name="user_id")
    private String userId;

    @Column(unique = true, length = 150, nullable = false)
    private String email;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private boolean status;

    @Column(name="full_name", length = 150, nullable = false)
    private String fullName;

    @Column(name = "is_account_non_expired", columnDefinition = "TINYINT")
    private boolean isAccountNonExpired;

    @Column(name = "is_credentials_non_expired", columnDefinition = "TINYINT")
    private boolean isCredentialsNonExpired;

    @Column(name = "is_account_non_locked", columnDefinition = "TINYINT")
    private boolean isAccountNonLocked;

    @Column(name = "is_enabled", columnDefinition = "TINYINT")
    private boolean isEnabled;

    @OneToOne(mappedBy = "systemUser",
            fetch = FetchType.LAZY)
    private BillingAddress billingAddress;

}
