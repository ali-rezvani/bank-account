package com.techbank.account.command.api.commands;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithdrawFundCommand command);
    void handle(CloseAccountCommand command);
}
