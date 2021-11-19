package by.innowise.tasks.service;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.EventMapper;
import by.innowise.tasks.repository.StudyHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService extends StudyHourService{

    public static final String NOT_FOUND_BY_ID = "There is no such event with id: %s";

    @Autowired
    private StudyHourRepository studyHourRepository;
    @Autowired
    private EventMapper eventMapper;

    public StudyHourDto save(StudyHourDto studyHourDto) {
        return eventMapper
                .toStudyHourDto(studyHourRepository
                        .saveAndFlush(eventMapper
                                .toStudyHour((EventDto) studyHourDto)));
    }

    public void update(StudyHourDto studyHourDto) {
        studyHourRepository.save(eventMapper.toStudyHour((EventDto) studyHourDto));
    }

    @Transactional(readOnly = true)
    public List<StudyHourDto> getAllStudyHours() {
        return studyHourRepository.getAll()
                .filter(n -> n instanceof Event)
                .map(n -> eventMapper.toStudyHourDto((Event) n))
                .toList();
    }

    public StudyHourDto getStudyHourById(Long id) {
        return studyHourRepository.findById(id).stream()
                .filter(n -> n instanceof Event)
                .map(n -> eventMapper.toStudyHourDto((Event) n))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteEvent(Long id) {
        studyHourRepository.deleteById(id);
    }

}
