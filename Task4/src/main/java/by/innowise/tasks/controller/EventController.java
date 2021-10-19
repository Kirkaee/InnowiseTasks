package by.innowise.tasks.controller;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public void saveEvent(@RequestBody EventDto eventDto) {
        eventService.saveEvent(eventDto);
    }

    @PutMapping("/{id}")
    public void updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
        eventService.updateEvent(id, eventDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
