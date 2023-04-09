package com.sulek.seller.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "seller")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller extends BaseEntity {
    @Id
    @SequenceGenerator(name = "seller_seq", sequenceName = "S_SELLER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_seq")
    @Column(name = "id", columnDefinition = "numeric(19)")
    private Long id;

    @Column(name = "business_name", length = 200, unique = true)
    private String businessName;

    @Column(name = "business_address")
    private String businessAddress;

    @Column(name = "pwd", length = 200)
    private String password;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "email_activision", length = 200)
    private Boolean emailActivision;
}
