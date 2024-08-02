package com.techbank.account.command.api.commands;

import com.techbank.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DepositFundsCommand extends BaseCommand {
    private Double amount;
}
