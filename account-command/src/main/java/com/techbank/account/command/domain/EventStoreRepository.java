package com.techbank.account.command.domain;

import com.techbank.cqrs.core.events.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel, UUID> {
    List<EventModel> findByAggregateIdentifier(UUID aggregateIdentifier);
}
