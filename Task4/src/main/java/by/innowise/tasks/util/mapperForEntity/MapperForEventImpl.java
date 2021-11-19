package by.innowise.tasks.util.mapperForEntity;

import by.innowise.tasks.entity.Event;
import by.innowise.tasks.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperForEventImpl implements MapperForEntityInterface<Event, EventMapper> {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public EventMapper getMapper(Event something) {
        return eventMapper;
    }

    @Override
    public String getType() {
        return "event";
    }
}
