package com.tobeto.hotelReservationSystem.controllers;

import com.tobeto.hotelReservationSystem.entities.Room;
import com.tobeto.hotelReservationSystem.entities.enums.RoomType;
import com.tobeto.hotelReservationSystem.services.abstracts.RoomService;
import com.tobeto.hotelReservationSystem.services.dtos.requests.room.AddRoomRequest;
import com.tobeto.hotelReservationSystem.services.dtos.requests.room.UpdateRoomRequest;
import com.tobeto.hotelReservationSystem.services.dtos.responses.room.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomsController {

    private final RoomService roomService;

    @PostMapping("/manager/add")
    public AddRoomResponse add(@RequestBody AddRoomRequest request) {
        return roomService.add(request);
    }

    @PutMapping("/manager/update")
    public UpdateRoomResponse update(@RequestBody UpdateRoomRequest request) {
        return roomService.update(request);
    }

    @DeleteMapping("/manager/delete/{id}")
    public void delete(@PathVariable("id") int id) {
         roomService.delete(id);
    }

    @GetMapping("/manager/getAll")
    public List<ListRoomResponse> getAll()
    {
        return roomService.getAll();
    }

    @GetMapping("/manager/getById/{id}")
    public GetByIdRoomResponse getById(@PathVariable int id)
    {
        return roomService.getById(id);
    }

    @GetMapping("/findAvailable")
    public List<ListRoomResponse> findAvailableRooms(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam RoomType roomType) {
        return roomService.findAvailableRooms(startDate, endDate,roomType);
    }
    @GetMapping("/manager/getAllRoomByHotelId/{hotelId}")
    public List<GetAllRoomByHotelIdResponse> getAllRoomByHotelId(@PathVariable int hotelId) {
        return roomService.getAllRoomByHotelId(hotelId);
    }


}
