package by.innowise.tasks.util.service;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.service.LessonService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ServiceLessonImpl implements ServiceInterface<LessonDto, LessonService> {

    private final LessonService lessonService;

    public ServiceLessonImpl(@Lazy LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public LessonService getService(LessonDto something) {
        return lessonService;
    }

    @Override
    public String getType() {
        return "lesson";
    }
}
