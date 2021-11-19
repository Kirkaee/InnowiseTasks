package by.innowise.tasks.service;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.StudyHourMapper;
import by.innowise.tasks.repository.StudyHourRepository;
import by.innowise.tasks.util.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudyHourService {

    public static final String NOT_FOUND_BY_ID = "There is no such study hour with id: %s";

    @Autowired
    private StudyHourRepository studyHourRepository;
    @Autowired
    private StudyHourMapper studyHourMapper;
    @Autowired
    private ServiceFacade serviceFacade;


    public StudyHourDto save(StudyHourDto studyHourDto) {
        return serviceFacade.getService(studyHourDto).save(studyHourDto);
    }

    public void update(StudyHourDto studyHourDto) {
        if (checkForTypeMatch(studyHourDto)) {
            serviceFacade.getService(studyHourDto).update(studyHourDto);
        } else {
            changeStudyHour(studyHourDto);
        }
    }

    public boolean checkForTypeMatch(StudyHourDto studyHourDto) {
        return (studyHourDto instanceof EventDto && getStudyHourById(studyHourDto.getId()) instanceof  EventDto) ||
                (studyHourDto instanceof LessonDto && getStudyHourById(studyHourDto.getId()) instanceof LessonDto);
    }

    public void changeStudyHour(StudyHourDto studyHourDto) {
        studyHourRepository.deleteById(studyHourDto.getId());
        serviceFacade.getService(studyHourDto).update(studyHourDto);
    }

    @Transactional(readOnly = true)
    public List<StudyHourDto> getAllStudyHours() {
        return studyHourRepository.getAll()
                .map(studyHourMapper::toStudyHourDto)
                .toList();
    }

    public StudyHourDto getStudyHourById(Long id) {
        return studyHourRepository.findById(id)
                .map(studyHourMapper::toStudyHourDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteStudyHour(Long id) {
        studyHourRepository.deleteById(id);
    }









}
