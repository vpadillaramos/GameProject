package com.vpr.pruebatiles.entities;

import com.vpr.pruebatiles.util.Constantes;

public class Room {

    // Attributes
    public Constantes.RoomType roomType;
    public String mapName;
    public boolean isVisited;

    // Constructor
    public Room(String mapName, Constantes.RoomType roomType){
        this.mapName = mapName;
        this.roomType = roomType;
    }
}
