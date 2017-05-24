package com.cocinero.repository;

import com.cocinero.domain.Event;

import java.util.Collection;

public interface EventRepository extends AbstractRepository<Event>{

    Collection<Event> findUpcomingEvents();
}
