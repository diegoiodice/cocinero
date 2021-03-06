package com.cocinero.infrastructure.repository.mongo;

import com.cocinero.infrastructure.repository.schemas.MongoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface MongoEventRepository extends MongoRepository<MongoEvent,String> {

    @Query("{ 'eventDate' : { $gt: ?0 } }")
    List<MongoEvent> findUpcomingEvents(Date date);
}
