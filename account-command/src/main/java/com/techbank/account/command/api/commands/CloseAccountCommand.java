package com.techbank.account.command.api.commands;

import com.techbank.cqrs.core.commands.BaseCommand;

import java.util.UUID;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(UUID id) {
        super(id);
    }
}
