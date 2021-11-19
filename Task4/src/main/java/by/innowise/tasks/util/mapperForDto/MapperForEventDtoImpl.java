package by.innowise.tasks.util.mapperForDto;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperForEventDtoImpl implements MapperForDtoInterface<EventDto, EventMapper> {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public EventMapper getMapper(EventDto something) {
        return eventMapper;
    }

    @Override
    public String getType() {
        return "event";
    }
}
