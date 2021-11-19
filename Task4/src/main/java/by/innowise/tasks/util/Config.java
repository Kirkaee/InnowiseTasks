package by.innowise.tasks.util;

import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.mapper.StudyHourMapper;
import by.innowise.tasks.service.StudyHourService;
import by.innowise.tasks.util.mapperForDto.MapperForDtoInterface;
import by.innowise.tasks.util.mapperForEntity.MapperForEntityInterface;
import by.innowise.tasks.util.service.ServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Configuration
public class Config {

    @Bean
    public <T extends StudyHourDto, R extends StudyHourService> Map<String, ServiceInterface<T, R>>
    serviceInterfaceMap(final List<ServiceInterface<T, R>> serviceInterfaces) {
        return serviceInterfaces.stream()
                .collect(Collectors.toMap(ServiceInterface::getType, identity()));
    }

    @Bean
    public <T extends StudyHourDto, R extends StudyHourMapper> Map<String, MapperForDtoInterface<T, R>>
    mapperForDtoInterfaceMap(final List<MapperForDtoInterface<T, R>> mapperForDtoInterfaces) {
        return mapperForDtoInterfaces.stream()
                .collect(Collectors.toMap(MapperForDtoInterface::getType, identity()));
    }

    @Bean
    public <T extends StudyHour, R extends StudyHourMapper> Map<String, MapperForEntityInterface<T, R>>
    mapperForEntityInterfaceMap(final List<MapperForEntityInterface<T, R>> mapperForEntityInterfaces) {
        return mapperForEntityInterfaces.stream()
                .collect(Collectors.toMap(MapperForEntityInterface::getType, identity()));
    }
}
