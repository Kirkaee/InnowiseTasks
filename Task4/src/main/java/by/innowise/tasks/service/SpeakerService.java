package by.innowise.tasks.service;

import by.innowise.tasks.dto.SpeakerDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.SpeakerMapper;
import by.innowise.tasks.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakerService {

    public static final String NOT_FOUND_BY_ID = "There is no such speaker with id: %s";

    private final SpeakerRepository speakerRepository;
    private final SpeakerMapper speakerMapper;

    public SpeakerDto saveSpeaker(SpeakerDto speakerDto) {
        return speakerMapper
                .toSpeakerDto(speakerRepository
                        .saveAndFlush(speakerMapper
                                .toSpeaker(speakerDto)));
    }

    @Transactional(readOnly = true)
    public List<SpeakerDto> getAllSpeakers() {
        return speakerRepository.getAll()
                .map(speakerMapper::toSpeakerDto)
                .toList();
    }

    public SpeakerDto getSpeakerById(Long id) {
        return speakerRepository.findById(id)
                .map(speakerMapper::toSpeakerDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteSpeaker(Long id) {
        speakerRepository.deleteById(id);
    }

    public void updateSpeaker(SpeakerDto speakerDto) {
        speakerRepository.save(speakerMapper.toSpeaker(speakerDto));
    }
}
