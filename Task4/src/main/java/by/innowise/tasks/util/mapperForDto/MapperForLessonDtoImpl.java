package by.innowise.tasks.util.mapperForDto;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperForLessonDtoImpl implements MapperForDtoInterface<LessonDto, LessonMapper> {

    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public LessonMapper getMapper(LessonDto something) {
        return lessonMapper;
    }

    @Override
    public String getType() {
        return "lesson";
    }
}
