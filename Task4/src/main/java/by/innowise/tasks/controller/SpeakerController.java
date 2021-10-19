package by.innowise.tasks.controller;

import by.innowise.tasks.dto.SpeakerDto;
import by.innowise.tasks.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speaker")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    @GetMapping
    public List<SpeakerDto> getAllSpeakers() {
        return speakerService.getAllSpeakers();
    }

    @GetMapping("/{id}")
    public SpeakerDto getSpeakerById(@PathVariable Long id) {
        return speakerService.getSpeakerById(id);
    }

    @PostMapping
    public void saveSpeaker(@RequestBody SpeakerDto speakerDto) {
        speakerService.saveSpeaker(speakerDto);
    }

    @PutMapping("/{id}")
    public void updateSpeaker(@PathVariable Long id, @RequestBody SpeakerDto speakerDto) {
        speakerService.updateSpeaker(id, speakerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSpeaker(@PathVariable Long id) {
        speakerService.deleteSpeaker(id);
    }
}
