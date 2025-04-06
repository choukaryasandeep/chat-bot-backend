package com.chat.controller;

import com.chat.entities.Message;
import com.chat.entities.Room;
import com.chat.payload.RoomRequest;
import com.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/v1/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    // create
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest request){
        String roomId = request.getRoomId();
        // room already exist
        if (roomService.findByRoomID(roomId) !=null){
            return ResponseEntity.badRequest().body("Room already exist");
        }
        // new room
        Room room = new Room();
        room.setRoomId(roomId);
        Room savedRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    // get room by id
    @GetMapping("rooms/{id}")
    public Room findByRoomID(@PathVariable("id") String id){
        return roomService.findByRoomID(id);
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<?> JoinRoom(@PathVariable String roomId){
        Room room = roomService.findByRoomID(roomId);
        if (room==null){
            return ResponseEntity.badRequest()
                    .body("Room not found!!");
        }
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId
                                    , @RequestParam(value = "page" ,defaultValue = "0",required = false) int pages,
                                     @RequestParam(value = "size",defaultValue = "20",required = false) int size){
        Room room = roomService.findByRoomID(roomId);
        if (room==null){
            return  ResponseEntity.badRequest().build();
        }
        List<Message> messages = room.getMessages();
        int start = Math.max(0,messages.size()-(pages+1)*size);
        int end = Math.min(messages.size(),start+size);
        List<Message> paginatedMessage = messages.subList(start, end);
        return ResponseEntity.ok(paginatedMessage);
    }

}
