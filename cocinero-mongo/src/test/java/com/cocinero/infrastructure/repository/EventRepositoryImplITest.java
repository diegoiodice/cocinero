package com.cocinero.infrastructure.repository;

import com.cocinero.domain.Event;
import com.cocinero.domain.Host;
import com.cocinero.infrastructure.SpringIntegrationTest;
import com.cocinero.repository.EventRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;


public class EventRepositoryImplITest extends SpringIntegrationTest{

    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void findUpcomingEvents() throws Exception {

        eventRepository.save(Event.builder()
                .name("test event")
                .type("test type")
                .maxAttendants(5)
                .amount(new BigDecimal(3))
                .eventDate(df.parse("01/03/2000"))
                .description("test description")
                .host(Host.builder().id("123456789012345678901234").build())
                .build());

        eventRepository.save(Event.builder()
                .name("test event")
                .type("test type")
                .maxAttendants(5)
                .amount(new BigDecimal(3))
                .eventDate(df.parse("01/03/2021"))
                .description("test description")
                .host(Host.builder().id("123456789012345678901234").build())
                .build());

        assertThat(eventRepository.findUpcomingEvents()).hasSize(1);
        assertThat(eventRepository.find()).hasSize(2);

    }

}