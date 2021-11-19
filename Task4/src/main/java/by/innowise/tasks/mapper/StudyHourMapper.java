package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.util.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudyHourMapper {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    private MapperFacade facade;

    public StudyHourDto toStudyHourDto(StudyHour studyHour) {
        return facade.getMapperForEntity(studyHour).toStudyHourDto(studyHour);
    }

    public StudyHour toStudyHour(StudyHourDto studyHourDto) {
        return facade.getMapperForDto(studyHourDto).toStudyHour(studyHourDto);
    }

}
