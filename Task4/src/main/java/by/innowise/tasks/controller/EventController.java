package by.innowise.tasks.controller;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<StudyHourDto> getAllEvents() {
        return eventService.getAllStudyHours();
    }

    @GetMapping("/{id}")
    public StudyHourDto getEventById(@PathVariable Long id) {
        return eventService.getStudyHourById(id);
    }

    @PostMapping
    public void saveEvent(@RequestBody EventDto eventDto) {
        eventService.save(eventDto);
    }

    @PutMapping()
    public void updateEvent(@RequestBody EventDto eventDto) {
        eventService.update(eventDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
