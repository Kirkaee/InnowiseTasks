package by.innowise.tasks.controller;

import by.innowise.tasks.dto.SpeakerDto;
import by.innowise.tasks.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speaker")
@RequiredArgsConstructor
public class SpeakerController {

    private final SpeakerService speakerService;

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

    @PutMapping()
    public void updateSpeaker(@RequestBody SpeakerDto speakerDto) {
        speakerService.updateSpeaker(speakerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSpeaker(@PathVariable Long id) {
        speakerService.deleteSpeaker(id);
    }
}
