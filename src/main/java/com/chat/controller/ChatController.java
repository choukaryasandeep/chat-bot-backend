package com.chat.controller;

import com.chat.entities.Message;
import com.chat.entities.Room;
import com.chat.paylorad.MessageRequest;
import com.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@CrossOrigin("http://localhost:3000")
public class ChatController {
    @Autowired
    private RoomService roomService;

    // for sending and receiving message
   @MessageMapping("/sendMessage/{roomId}") // /app/sendMessage/roomId
   @SendTo("/topic/room/{roomId}") //subscribe
   public Message sendMessage( @DestinationVariable String roomId,
           @RequestBody MessageRequest request
   ){
       Room room = roomService.findByRoomID(request.getRoomId());
       Message message = new Message();
       message.setContent(request.getContent());
       message.setSender(request.getSender());
       message.setTimeStamp(LocalDateTime.now());

       if (room!=null){
           room.getMessages().add(message);
           roomService.createRoom(room);
       }else {
           throw new RuntimeException("Room not found");
       }
       return message;
    }
}
