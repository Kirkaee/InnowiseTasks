package by.innowise.tasks.util;

import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.mapper.StudyHourMapper;
import by.innowise.tasks.util.mapperForDto.MapperForDtoInterface;
import by.innowise.tasks.util.mapperForEntity.MapperForEntityInterface;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperFacade {

    private final Map<String, MapperForDtoInterface<StudyHourDto, StudyHourMapper>> mapperForDtoInterfaceMap;

    private final Map<String, MapperForEntityInterface<StudyHour, StudyHourMapper>> mapperForEntityInterfaceMap;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MapperFacade(final Map<String, MapperForDtoInterface<StudyHourDto, StudyHourMapper>> mapperForDtoInterfaceMap,
                        final Map<String, MapperForEntityInterface<StudyHour, StudyHourMapper>> mapperForEntityInterfaceMap) {
        this.mapperForDtoInterfaceMap = mapperForDtoInterfaceMap;
        this.mapperForEntityInterfaceMap = mapperForEntityInterfaceMap;
    }

    public StudyHourMapper getMapperForDto(final StudyHourDto studyHourDto) {
        return mapperForDtoInterfaceMap.get(studyHourDto.getType()).getMapper(studyHourDto);
    }

    public StudyHourMapper getMapperForEntity(final StudyHour studyHour) {
        return mapperForEntityInterfaceMap.get(studyHour.getType()).getMapper(studyHour);
    }

}
