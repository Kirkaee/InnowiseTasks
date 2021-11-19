package by.innowise.tasks.util.mapperForEntity;

import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperForLessonImpl implements MapperForEntityInterface<Lesson, LessonMapper> {

    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public LessonMapper getMapper(Lesson something) {
        return lessonMapper;
    }

    @Override
    public String getType() {
        return "lesson";
    }
}
