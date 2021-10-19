package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.RoomDto;
import by.innowise.tasks.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    @Autowired
    private StudyHourMapper studyHourMapper;

    public RoomDto toRoomDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .type(room.getType())
                .capacity(room.getCapacity())
                .studyHoursDto(room.getStudyHours().stream()
                        .map(studyHour -> studyHourMapper.toStudyHourDto(studyHour))
                        .toList())
                .build();
    }

    public Room toRoom(RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.getId())
                .type(roomDto.getType())
                .capacity(roomDto.getCapacity())
                .studyHours(roomDto.getStudyHoursDto().stream()
                        .map(studyHourDto -> studyHourMapper.toStudyHour(studyHourDto))
                        .toList())
                .build();
    }
}
