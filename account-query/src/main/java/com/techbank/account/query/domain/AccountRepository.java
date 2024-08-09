package com.techbank.account.query.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, UUID> {
    Optional<BankAccount> findByAccountHolder(String accountHolder);
    List<BankAccount> findByBalanceGreaterThan(Double balance);
    List<BankAccount> findByBalanceLessThan(Double balance);
}
