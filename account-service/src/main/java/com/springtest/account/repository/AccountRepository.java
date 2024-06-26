package com.springtest.account.repository;

import com.springtest.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByClientId (Long clientId);
}
