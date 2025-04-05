package com.chat.service;

import com.chat.entities.Room;

public interface RoomService {
    Room findByRoomID(String id);
    Room createRoom(Room room);
}
