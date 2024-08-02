package com.techbank.account.command.api.commands;

import com.techbank.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundCommand extends BaseCommand {
    private Double amount;
}
