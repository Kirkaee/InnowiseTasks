package by.innowise.tasks.controller;

import by.innowise.tasks.dto.RoomDto;
import by.innowise.tasks.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

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

    @PutMapping("/{id}")
    public void updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        roomService.updateRoom(id, roomDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
