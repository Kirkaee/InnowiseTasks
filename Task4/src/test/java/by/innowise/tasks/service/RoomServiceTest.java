package by.innowise.tasks.service;

import by.innowise.tasks.dto.RoomDto;
import by.innowise.tasks.entity.Room;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.RoomMapper;
import by.innowise.tasks.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static by.innowise.tasks.service.RoomService.NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    public static final Long DEFAULT_ID = 0L;
    public static final String DEFAULT_TYPE = "type";
    public static final Integer DEFAULT_CAPACITY = 100;

    public static final Room DEFAULT_ROOM = Room.builder()
            .id(DEFAULT_ID)
            .type(DEFAULT_TYPE)
            .capacity(DEFAULT_CAPACITY)
            .build();

    public static final RoomDto DEFAULT_ROOM_DTO = RoomDto.builder()
            .id(DEFAULT_ID)
            .type(DEFAULT_TYPE)
            .capacity(DEFAULT_CAPACITY)
            .build();

    public static final List<Room> DEFAULT_ROOM_LIST = List.of(DEFAULT_ROOM);

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomService roomService;

    @Test
    void saveRoom() {
        given(roomRepository.saveAndFlush(DEFAULT_ROOM)).willReturn(DEFAULT_ROOM);
        given(roomMapper.toRoom(DEFAULT_ROOM_DTO)).willReturn(DEFAULT_ROOM);
        given(roomMapper.toRoomDto(DEFAULT_ROOM)).willReturn(DEFAULT_ROOM_DTO);

        assertEquals(DEFAULT_ROOM_DTO, roomService.saveRoom(DEFAULT_ROOM_DTO));

        then(roomRepository).should(only()).saveAndFlush(DEFAULT_ROOM);
        then(roomMapper).should(times(1)).toRoom(DEFAULT_ROOM_DTO);
        then(roomMapper).should(times(1)).toRoomDto(DEFAULT_ROOM);
        then(roomMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getRooms() {
        given(roomRepository.getAll()).willReturn(Stream.ofNullable(DEFAULT_ROOM));
        given(roomMapper.toRoomDto(DEFAULT_ROOM)).willReturn(DEFAULT_ROOM_DTO);

        assertEquals(DEFAULT_ROOM_DTO, roomService.getAllRooms().get(0));

        then(roomRepository).should(only()).getAll();
        then(roomMapper).should(only()).toRoomDto(DEFAULT_ROOM);
    }

    @Test
    void getRoomById() {
        given(roomRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_ROOM));
        given(roomMapper.toRoomDto(DEFAULT_ROOM)).willReturn(DEFAULT_ROOM_DTO);

        assertEquals(DEFAULT_ROOM_DTO, roomService.getRoomById(DEFAULT_ID));

        then(roomRepository).should(only()).findById(DEFAULT_ID);
        then(roomMapper).should(only()).toRoomDto(DEFAULT_ROOM);
    }

    @Test
    void deleteRoom() {
        roomService.deleteRoom(DEFAULT_ID);

        then(roomRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void updateRoom() {
        given(roomRepository.save(DEFAULT_ROOM)).willReturn(DEFAULT_ROOM);
        given(roomMapper.toRoom(DEFAULT_ROOM_DTO)).willReturn(DEFAULT_ROOM);

        roomService.updateRoom(DEFAULT_ROOM_DTO);

        then(roomRepository).should(only()).save(DEFAULT_ROOM);
        then(roomMapper).should(only()).toRoom(DEFAULT_ROOM_DTO);

    }

    @Test
    void getRoomByIdNotFoundException() {

        given(roomRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> roomService.getRoomById(DEFAULT_ID));
        assertEquals(String.format(NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(roomRepository).should(only()).findById(DEFAULT_ID);
    }
}