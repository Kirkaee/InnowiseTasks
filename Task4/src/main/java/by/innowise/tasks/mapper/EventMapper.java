package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper extends StudyHourMapper {

    public Event toEvent(EventDto eventDto) {
        return Event.builder()
                .id(eventDto.getId())
                .classDate(eventDto.getClassDate())
                .type(eventDto.getType())
                .topic(eventDto.getTopic())
                .build();
    }

    public EventDto toEventDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .classDate(event.getClassDate())
                .type(event.getType())
                .topic(event.getTopic())
                .build();
    }
}
