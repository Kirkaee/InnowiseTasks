package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.RoomDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.Room;
import by.innowise.tasks.entity.StudyHour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final StudyHourMapper studyHourMapper;

    public RoomDto toRoomDto(Room room) {
        List<StudyHourDto> studyHours = room.getStudyHours().stream()
                .map(studyHourMapper::toStudyHourDto)
                .toList();
        return RoomDto.builder()
                .id(room.getId())
                .type(room.getType())
                .capacity(room.getCapacity())
                .studyHoursDto(studyHours)
                .build();
    }

    public Room toRoom(RoomDto roomDto) {
        List<StudyHour> studyHours = roomDto.getStudyHoursDto().stream()
                .map(studyHourMapper::toStudyHour)
                .toList();
        return Room.builder()
                .id(roomDto.getId())
                .type(roomDto.getType())
                .capacity(roomDto.getCapacity())
                .studyHours(studyHours)
                .build();
    }
}
