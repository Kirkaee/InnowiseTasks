package by.innowise.tasks.util;

import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.mapper.StudyHourMapper;
import by.innowise.tasks.service.StudyHourService;
import by.innowise.tasks.util.mapperForDto.MapperForDtoInterface;
import by.innowise.tasks.util.mapperForEntity.MapperForEntityInterface;
import by.innowise.tasks.util.service.ServiceInterface;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ServiceFacade {

    private final Map<String, ServiceInterface<StudyHourDto, StudyHourService>> serviceInterfaceMap;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ServiceFacade(final Map<String, ServiceInterface<StudyHourDto, StudyHourService>> serviceInterfaceMap) {
        this.serviceInterfaceMap = serviceInterfaceMap;
    }

    public StudyHourService getService(final StudyHourDto studyHourDto) {
        return serviceInterfaceMap.get(studyHourDto.getType()).getService(studyHourDto);
    }

}
