package com.cocinero.infrastructure.repository;

import com.cocinero.domain.Event;
import com.cocinero.domain.EventRepository;
import com.cocinero.domain.User;
import com.cocinero.infrastructure.repository.mongo.MongoEventRepository;
import com.cocinero.infrastructure.repository.mongo.MongoUserRepository;
import com.cocinero.infrastructure.repository.mongo.schemas.MongoEvent;
import com.cocinero.infrastructure.repository.mongo.schemas.MongoUser;
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
