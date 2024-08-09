package com.techbank.account.query.infrastructure.handler;

import com.techbank.account.common.event.AccountClosedEvent;
import com.techbank.account.common.event.AccountOpenedEvent;
import com.techbank.account.common.event.FundsDepositedEvent;
import com.techbank.account.common.event.FundsWithdrawnEvent;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountEventHandler implements EventHandler{
    private final AccountRepository accountRepository;
    @Override
    public void on(AccountOpenedEvent event) {
        var account= BankAccount.builder()
                .id(UUID.randomUUID())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .creationDate(event.getCreatedTime())
                .build();
        accountRepository.save(account);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var account= accountRepository.findById(event.getId());
        if (account.isEmpty()){
            return;
        }
        var currentBalance=account.get().getBalance();
        var latestBalance=currentBalance+event.getAmount();
        account.get().setBalance(latestBalance);
        accountRepository.save(account.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var account= accountRepository.findById(event.getId());
        if (account.isEmpty()){
            return;
        }
        /*if (account.get().getBalance()<event.getAmount()){
            return;
        }*/
        var currentBalance=account.get().getBalance();
        var latestBalance=currentBalance-event.getAmount();
        account.get().setBalance(latestBalance);
        accountRepository.save(account.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
