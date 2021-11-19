package by.innowise.tasks.controller;

import by.innowise.tasks.dto.RoomDto;
import by.innowise.tasks.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomDto getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public void saveRoom(@RequestBody RoomDto roomDto) {
        roomService.saveRoom(roomDto);
    }

    @PutMapping()
    public void updateRoom(@RequestBody RoomDto roomDto) {
        roomService.updateRoom(roomDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
