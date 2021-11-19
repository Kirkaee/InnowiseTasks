package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.util.MapperFacade;
import org.springframework.stereotype.Component;

@Component
public class EventMapper extends StudyHourMapper{

    @Override
    public StudyHourDto toStudyHourDto(StudyHour studyHour) {
        Event event = (Event) studyHour;
        return EventDto.builder()
                .id(event.getId())
                .classDate(event.getClassDate())
                .type(event.getType())
                .topic(event.getTopic())
                .build();
    }

    @Override
    public StudyHour toStudyHour(StudyHourDto studyHourDto) {
        EventDto eventDto = (EventDto) studyHourDto;
        return Event.builder()
                .id(eventDto.getId())
                .classDate(eventDto.getClassDate())
                .type(eventDto.getType())
                .topic(eventDto.getTopic())
                .build();
    }

}
