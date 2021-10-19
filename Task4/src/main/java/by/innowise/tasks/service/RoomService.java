package by.innowise.tasks.service;

import by.innowise.tasks.dto.RoomDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.RoomMapper;
import by.innowise.tasks.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    public static final String NOT_FOUND_BY_ID = "There is no such room with id: %s";

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    public RoomDto saveRoom(RoomDto roomDto) {
        return roomMapper
                .toRoomDto(roomRepository
                        .save(roomMapper
                                .toRoom(roomDto)));
    }

    public List<RoomDto> getAllRooms() {
        return roomRepository
                .findAll()
                .stream()
                .map(roomMapper::toRoomDto)
                .toList();
    }

    public RoomDto getRoomById(Long id) {
        return roomRepository
                .findById(id)
                .map(roomMapper::toRoomDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteRoom(Long id) {
        roomRepository
                .deleteById(id);
    }

    public RoomDto updateRoom(Long id, RoomDto roomDto) {
        roomDto.setId(id);
        return roomMapper
                .toRoomDto(roomRepository
                        .save(roomMapper
                                .toRoom(roomDto)));
    }
}
