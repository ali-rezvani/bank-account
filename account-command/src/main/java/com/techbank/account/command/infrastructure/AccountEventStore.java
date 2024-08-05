package com.techbank.account.command.infrastructure;

import com.techbank.account.command.domain.AccountAggregate;
import com.techbank.account.command.domain.EventStoreRepository;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.events.EventModel;
import com.techbank.cqrs.core.exception.AggregateNotFoundException;
import com.techbank.cqrs.core.exception.ConcurrencyException;
import com.techbank.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;
    @Override
    public void saveEvents(UUID aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream=eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion !=-1 && eventStream.get(eventStream.size()-1).getVersion()!=expectedVersion){
            throw new ConcurrencyException();
        }
        var version=expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel= EventModel.builder()
                    .id(event.getId())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .timestamp(Instant.now())
                    .eventData(event)
                    .build();
            var persistedEvent=eventStoreRepository.save(eventModel);
            if (persistedEvent!=null){
//                TODO produce event to kafka
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(UUID aggregateId) {
        var eventStream=eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream.isEmpty()){
            throw new AggregateNotFoundException("Incorrect account ID provided.");
        }
        return eventStream
                .stream()
                .map(EventModel::getEventData)
                .collect(Collectors.toList());
    }
}
