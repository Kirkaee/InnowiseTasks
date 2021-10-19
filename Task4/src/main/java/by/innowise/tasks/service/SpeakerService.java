package by.innowise.tasks.service;

import by.innowise.tasks.dto.SpeakerDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.SpeakerMapper;
import by.innowise.tasks.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerService {

    public static final String NOT_FOUND_BY_ID = "There is no such speaker with id: %s";

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private SpeakerMapper speakerMapper;

    public SpeakerDto saveSpeaker(SpeakerDto speakerDto) {
        return speakerMapper
                .toSpeakerDto(speakerRepository
                        .save(speakerMapper
                                .toSpeaker(speakerDto)));
    }

    public List<SpeakerDto> getAllSpeakers() {
        return speakerRepository
                .findAll()
                .stream()
                .map(speakerMapper::toSpeakerDto)
                .toList();
    }

    public SpeakerDto getSpeakerById(Long id) {
        return speakerRepository
                .findById(id)
                .map(speakerMapper::toSpeakerDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteSpeaker(Long id) {
        speakerRepository
                .deleteById(id);
    }

    public SpeakerDto updateSpeaker(Long id, SpeakerDto speakerDto) {
        speakerDto.setId(id);
        return speakerMapper
                .toSpeakerDto(speakerRepository
                        .save(speakerMapper
                                .toSpeaker(speakerDto)));
    }
}
