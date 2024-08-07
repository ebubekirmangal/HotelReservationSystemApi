package com.tobeto.hotelReservationSystem.services.dtos.responses.room;

import com.tobeto.hotelReservationSystem.entities.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListRoomResponse {

    private int id;

    private RoomType roomType;

//    private String roomNumber;

    private int capacity;

    private double price;

    private  boolean available;

    private List<Integer> featureIds;

    private List<Integer> imageIds;

    private String hotelName;
}
