package by.innowise.tasks.util.service;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.service.EventService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ServiceEventImpl implements ServiceInterface<EventDto, EventService> {

    private final EventService eventService;

    public ServiceEventImpl(@Lazy EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public EventService getService(EventDto something) {
        return eventService;
    }

    @Override
    public String getType() {
        return "event";
    }
}
