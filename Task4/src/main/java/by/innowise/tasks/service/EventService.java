package by.innowise.tasks.service;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.EventMapper;
import by.innowise.tasks.repository.StudyHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    public static final String NOT_FOUND_BY_ID = "There is no such event with id: %s";

    @Autowired
    private StudyHourRepository studyHourRepository;

    @Autowired
    private EventMapper eventMapper;

    public EventDto saveEvent(EventDto eventDto) {
        return eventMapper
                .toEventDto(studyHourRepository
                        .save(eventMapper
                                .toEvent(eventDto)));
    }

    public List<EventDto> getEvents() {
        return studyHourRepository.findAll().stream()
                .filter(n -> n instanceof Event)
                .map(n -> eventMapper.toEventDto((Event) n)).toList();
    }

    public EventDto getEventById(Long id) {
        return studyHourRepository.findById(id).stream()
                .filter(n -> n instanceof Event)
                .map(n -> eventMapper.toEventDto((Event) n))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteEvent(Long id) {
        studyHourRepository
                .deleteById(id);
    }

    public EventDto updateEvent(Long id, EventDto eventDto) {
        eventDto.setId(id);
        return eventMapper
                .toEventDto(studyHourRepository
                        .save(eventMapper
                                .toEvent(eventDto)));
    }
}
