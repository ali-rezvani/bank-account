package com.techbank.account.command.api.commands;

import com.techbank.account.command.domain.AccountAggregate;
import com.techbank.cqrs.core.handler.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCommandHandler implements CommandHandler {
    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.deposit(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        if (command.getAmount()>aggregate.getBalance()){
            throw new IllegalStateException("Amount is greater than balance");
        }
        aggregate.withdraw(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.close();
        eventSourcingHandler.save(aggregate);
    }
}
