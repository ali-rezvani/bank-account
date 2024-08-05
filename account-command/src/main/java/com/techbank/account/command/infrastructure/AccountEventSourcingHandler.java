package com.techbank.account.command.infrastructure;

import com.techbank.account.command.domain.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.handler.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(UUID id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var lastVersion = events.stream()
                    .map(BaseEvent::getVersion)
                    .max(Comparator.naturalOrder());
            aggregate.setVersion(lastVersion.get());
        }
        return aggregate;
    }
}
