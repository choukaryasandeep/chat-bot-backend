package com.chat.service;

import com.chat.entities.Room;
import com.chat.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    private RoomRepository repo;
    @Override
    public Room findByRoomID(String id) {
        return repo.findByRoomId(id);
    }

    @Override
    public Room createRoom(Room room) {
       return repo.save(room);
    }
}
