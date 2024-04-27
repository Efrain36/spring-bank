package com.springtest.account.entity;


import com.springtest.account.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", unique = true, nullable = false)
    private UUID number;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountTypeEnum type;

    @Column(name="client_id",nullable = false)
    private Long clientId;

    @Column(name="balance", nullable = false)
    private Double balance;

    @Column(name="status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions;


}
