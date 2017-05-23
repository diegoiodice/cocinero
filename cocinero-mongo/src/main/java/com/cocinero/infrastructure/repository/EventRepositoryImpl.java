package com.cocinero.infrastructure.repository;

import com.cocinero.domain.Event;
import com.cocinero.repository.EventRepository;
import com.cocinero.infrastructure.repository.schemas.MongoEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepositoryImpl implements EventRepository {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoEventRepository mongoEventRepository;

    @Override
    public Event create(Event event) {
        MongoEvent mongoEvent = objectMapper.convertValue(event, MongoEvent.class);
        mongoEvent = mongoEventRepository.save(mongoEvent);
        return objectMapper.convertValue(mongoEvent, Event.class);
    }
}
