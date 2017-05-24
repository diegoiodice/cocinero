package com.cocinero.infrastructure.repository;

import com.cocinero.domain.Event;
import com.cocinero.infrastructure.repository.mongo.MongoEventRepository;
import com.cocinero.infrastructure.repository.schemas.MongoEvent;
import com.cocinero.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryImpl extends AbstractRepositoryImpl<Event,MongoEvent> implements EventRepository {

    protected final ObjectMapper objectMapper;

    protected final MongoEventRepository mongoEventRepository;

    @Autowired
    public EventRepositoryImpl(ObjectMapper objectMapper, MongoEventRepository mongoEventRepository) {
        super(objectMapper,mongoEventRepository, Event.class);
        this.objectMapper = objectMapper;
        this.mongoEventRepository = mongoEventRepository;
    }

    @Override
    public Event save(Event event) {
        MongoEvent mongoEvent = objectMapper.convertValue(event, MongoEvent.class);
        mongoEvent.setCreated(new Date(Instant.now().toEpochMilli()));
        mongoEvent = mongoEventRepository.save(mongoEvent);
        return objectMapper.convertValue(mongoEvent, Event.class);
    }

    @Override
    public Collection<Event> findUpcomingEvents() {
        return mongoEventRepository
                .findUpcomingEvents(new Date(Instant.now().toEpochMilli()))
                .stream()
                .map(me-> objectMapper.convertValue(me,Event.class))
                .collect(Collectors.toList());
    }
}
