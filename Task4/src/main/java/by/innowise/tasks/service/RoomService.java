package by.innowise.tasks.service;

import by.innowise.tasks.dto.RoomDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.RoomMapper;
import by.innowise.tasks.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    public static final String NOT_FOUND_BY_ID = "There is no such room with id: %s";

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomDto saveRoom(RoomDto roomDto) {
        return roomMapper
                .toRoomDto(roomRepository
                        .saveAndFlush(roomMapper
                                .toRoom(roomDto)));
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getAllRooms() {
        return roomRepository.getAll()
                .map(roomMapper::toRoomDto)
                .toList();
    }

    public RoomDto getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::toRoomDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public void updateRoom(RoomDto roomDto) {
       roomRepository.save(roomMapper.toRoom(roomDto));
    }
}
