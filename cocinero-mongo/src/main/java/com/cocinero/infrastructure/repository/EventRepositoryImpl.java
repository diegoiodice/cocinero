package com.cocinero.infrastructure.repository;

import com.cocinero.domain.Event;
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
public class EventRepositoryImpl implements EventRepository {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoEventRepository mongoEventRepository;

    @Override
    public Event create(Event event) {
        MongoEvent mongoEvent = objectMapper.convertValue(event, MongoEvent.class);
        mongoEvent.setCreated(new Date(Instant.now().toEpochMilli()));
        mongoEvent = mongoEventRepository.save(mongoEvent);
        return objectMapper.convertValue(mongoEvent, Event.class);
    }

    @Override
    public Event save(Event event) {
        return this.create(event);
    }

    @Override
    public Collection<Event> find() {
        return mongoEventRepository.findAll().stream().map(me-> objectMapper.convertValue(me,Event.class)).collect(Collectors.toList());
    }

    @Override
    public Event findById(String id) {
        return objectMapper.convertValue(mongoEventRepository.findById(id),Event.class);
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
